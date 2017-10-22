package com.pipi.service;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.common.aop.MyLog;
import com.pipi.dao.idao.IProcessDao;
import com.pipi.entity.*;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.ISlateService;
import com.pipi.util.DSUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by yahto on 13/05/2017.
 */
@Service
public class SlateService extends BaseService implements ISlateService {
    @Autowired
    private IProcessDao processDao;

    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 1, 5,
            TimeUnit.SECONDS, new
            ArrayBlockingQueue<Runnable>(10));

    @Override
    @MyLog(operationName = "添加板材", operationType = "add")
    public void backSlate(Slate slate, Integer kindId, Integer stabKindId, HttpServletRequest request) {
        if (slate == null) {
            throw new BusinessException("未填写板材信息");
        }
        if (kindId == null) {
            throw new BusinessException("未指定板材所属种类");
        }
        if (stabKindId == null) {
            throw new BusinessException("未指定板材所属扎");
        }
        slate.setKind((Kind) queryObjectByID(Kind.class, kindId));//关联种类
        StabKind stabKind = (StabKind) queryObjectByID(StabKind.class, stabKindId);
        stabKind.setCurrentCount(stabKind.getCurrentCount() + 1);//入库数量加1
        stabKind.setCurrentAcreage(stabKind.getCurrentAcreage() + slate.getHeight() * slate.getLength());//改变在库面积
        stabKind.setBackCount(stabKind.getBackCount() + 1);
        slate.setStabKind(stabKind);//关联板材
        Integer slateId = (Integer) save(slate);
        SlateOnChange slateOnChange = new SlateOnChange();
        slateOnChange.setOp_time(new Date());
        User user = (User) (request.getSession().getAttribute(SystemConstant.CURRENT_USER));
        slateOnChange.setDescription("用户：" + user.getUserName() + " 增加板材 " + slate.getSlateName() + " " + slateId);
        slateOnChange.setUserId(user.getId());
        add(slateOnChange);
    }

    //todo 用单线程出库
    @Override
    @MyLog(operationName = "删除板材,出库", operationType = "delete")
    public void deleteSlateByIds(Integer[] ids, Integer stabKindId, HttpServletRequest request) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("未填指定板材");
        }
        if (stabKindId == null) {
            throw new BusinessException("未制定扎,请重试");
        }
        if ((User) request.getSession().getAttribute(SystemConstant.CURRENT_USER) == null) {
            throw new BusinessException("请登陆后再操作");
        }
        StabKind stabKind = (StabKind) queryObjectByID(StabKind.class, stabKindId);
        String finalIds = DSUtil.parseIntegerArr(ids);
        String hql = "from Slate where isDelete=0 and id in (" + finalIds + ")";
        List<Slate> list = (List<Slate>) queryObjectList(hql);
        synchronized (list) {
            Float outAcreage = 0f;
            if (list.size() < ids.length) {
                throw new BusinessException("指定的板材中已经有被出库的板材，请刷新重试");
            }
            List<ProcessSlate> processSlates = new ArrayList<>(8);
            for (Slate slate : list) {
                outAcreage += slate.getLength() * slate.getHeight();
                ProcessSlate processSlate = new ProcessSlate();
                processSlate.setKind(slate.getKind());
                processSlate.setAcreage(slate.getHeight() * slate.getLength());
                processSlate.setProNum(String.valueOf(ids.length));
                processSlate.setSlateName(slate.getSlateName());
                processSlate.setUser((User) request.getSession().getAttribute(SystemConstant.CURRENT_USER));
                processSlate.setPrice(slate.getPrice());
                processSlate.setStabKind(slate.getStabKind());
                processSlate.setKind(slate.getKind());
                processSlates.add(processSlate);
            }
            //板材转到加工厂
            processDao.addBatchProcessSlate(processSlates);
            stabKind.setOut_time(new Date());//记录出库时间
            stabKind.setOutCount(ids.length);//记录出库数量
            stabKind.setOutAcreage(outAcreage);//记录出库面积
            stabKind.setCurrentCount(stabKind.getCurrentCount() - ids.length);
            //如果出库面积大于当前扎的面积 将扎面积置为0
            if (stabKind.getCurrentAcreage() < outAcreage) {
                stabKind.setCurrentAcreage(0f);
            } else {
                stabKind.setCurrentAcreage(stabKind.getCurrentAcreage() - outAcreage);
            }
            delete(Slate.class, finalIds);
            update(stabKind);
            SlateOnChange slateOnChange = new SlateOnChange();
            slateOnChange.setOp_time(new Date());
            User user = (User) (request.getSession().getAttribute(SystemConstant.CURRENT_USER));
            slateOnChange.setDescription("用户：" + user.getUserName() + " 删除板材 " + finalIds);
            slateOnChange.setUserId(user.getId());
            add(slateOnChange);
        }
    }

    @Override
    @MyLog(operationName = "添加扎的时候添加板材", operationType = "add")
    public void addSlate(List<Slate> slateList, HttpServletRequest request) {
        if (CollectionUtils.isEmpty(slateList)) {
            throw new BusinessException("未填写板材信息");
        }
        for (Slate slate :
                slateList) {
            add(slate);
        }
        SlateOnChange slateOnChange = new SlateOnChange();
        slateOnChange.setOp_time(new Date());
        User user = (User) (request.getSession().getAttribute(SystemConstant.CURRENT_USER));
        slateOnChange.setDescription("用户：" + user.getUserName() + " 增加" + slateList.size() + "块板材 id集合为" + DSUtil.parseObjectList(slateList));
        slateOnChange.setUserId(user.getId());
        add(slateOnChange);
    }

    @Override
    public List<Slate> querySlateByStabKindId(Integer stabKindId) {
        if (stabKindId == null) {
            throw new BusinessException("未指定扎");
        }
        String hql = "from Slate where isDelete=0 and stabKind.id=" + stabKindId;
        return (List<Slate>) baseDao.getObjectListByNativeHql(hql);
    }
}
