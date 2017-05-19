package com.pipi.service;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.common.logaop.MyLog;
import com.pipi.entity.Slate;
import com.pipi.entity.SlateOnChange;
import com.pipi.entity.StabKind;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.ISlateService;
import com.pipi.util.DSUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by yahto on 13/05/2017.
 */
@Service
public class SlateService extends BaseService implements ISlateService {
    @Override
    @MyLog(operationName = "添加板材", operationType = "add")
    public void addSlate(Slate slate, Integer kindId, Integer stabKindId, Float loseAcreage, HttpServletRequest request) {
        if (slate == null) {
            throw new BusinessException("未填写板材信息");
        }
        if (kindId == null) {
            throw new BusinessException("未指定板材所属种类");
        }
        if (stabKindId == null) {
            throw new BusinessException("未指定板材所属扎");
        }
        slate.setKindId(kindId);//关联种类
        StabKind stabKind = (StabKind) queryObjectByID(StabKind.class, stabKindId);
        stabKind.setCurrentCount(stabKind.getCurrentCount() + 1);//入库数量加1
        stabKind.setCurrentAcreage(stabKind.getCurrentAcreage() + slate.getHeight() * slate.getLength() - loseAcreage);//改变在库面积
        slate.setStabKindId(stabKindId);//关联板材
        add(slate);
        SlateOnChange slateOnChange = new SlateOnChange();
        slateOnChange.setDescription("增加板材");
        slateOnChange.setOp_time(new Date());
        User user = (User)(request.getSession().getAttribute(SystemConstant.CURRENT_USER));
        slateOnChange.setUserId(user.getId());
        add(slateOnChange);
    }

    @Override
    @MyLog(operationName = "删除板材", operationType = "delete")
    public void deleteSlateByIds(Integer[] ids, Integer stabKindId,HttpServletRequest request) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("未填指定板材");
        }
        StabKind stabKind = (StabKind) queryObjectByID(StabKind.class, stabKindId);
        Float outAcreage = 0f;
        String hql = "from Slate where id in (" + DSUtil.parseIntegerArr(ids) + ")";
        List<Slate> list = (List<Slate>) queryObjectList(hql);
        for (Slate slate :
                list) {
            outAcreage += slate.getLength() * slate.getHeight();
        }
        stabKind.setOut_time(new Date());//记录出库时间
        stabKind.setOutCount(ids.length);//记录出库数量
        stabKind.setOutAcreage(outAcreage);//记录出库面积
        //如果出库面积大于当前扎的面积 将扎面积置为0
        if (stabKind.getCurrentAcreage() < outAcreage) {
            stabKind.setCurrentAcreage(0f);
        } else {
            stabKind.setCurrentAcreage(stabKind.getCurrentAcreage() - outAcreage);
        }
        delete(Slate.class, DSUtil.parseIntegerArr(ids));
        SlateOnChange slateOnChange = new SlateOnChange();
        slateOnChange.setDescription("删除板材");
        slateOnChange.setOp_time(new Date());
        User user = (User)(request.getSession().getAttribute(SystemConstant.CURRENT_USER));
        slateOnChange.setUserId(user.getId());
        add(slateOnChange);
    }
}
