package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.common.logaop.MyLog;
import com.pipi.entity.Slate;
import com.pipi.entity.StabKind;
import com.pipi.util.DSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yahto on 14/05/2017.
 */
@Service
public class StabKindService extends BaseService implements IStabKindService {
    @Override
    @MyLog(operationName = "添加扎",operationType = "add")
    public void addStabKind(StabKind stabKind) {
        if (stabKind == null){
            throw new BusinessException("未接收到添加的信息");
        }
        if (stabKind.getKindId() == null){
            throw new BusinessException("未指定扎所属的种类");
        }
        save(stabKind);
    }

    @Override
    @MyLog(operationName = "批量删除扎",operationType = "delete")
    public void deleteStabKindByIds(Integer[] ids) {
        if (ids == null || ids.length == 0){
            throw new BusinessException("未指定要删除的扎");
        }
        //先去查找板材表里面是否有属于当前扎的 若有 直接返回失败
        String hql = "from Slate s where stabKindId in (" +
                DSUtil.parseIntegerArr(ids) + ")";
        List<Slate> slateList = (List<Slate>) baseDao.getObjectList(hql);
        if (slateList.size() != 0){
            throw new BusinessException("当前扎下面还有板材 不能删除");
        }else {
            delete(StabKind.class,DSUtil.parseIntegerArr(ids));
        }
    }
}
