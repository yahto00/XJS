package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.common.aop.MyLog;
import com.pipi.entity.Slate;
import com.pipi.entity.StabKind;
import com.pipi.service.iservice.IStabKindService;
import com.pipi.util.DSUtil;
import com.pipi.util.ObjectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yahto on 14/05/2017.
 */
@Service
public class StabKindService extends BaseService implements IStabKindService {
    @Override
    @MyLog(operationName = "添加扎", operationType = "add")
    public void addStabKind(StabKind stabKind) {
        String[] params = {"num", "originalCount", "originalAcreage"};
        if (ObjectUtil.objectIsEmpty(stabKind, params)) {
            throw new BusinessException("未填写完整信息");
        }
        if (stabKind.getKind() == null) {
            throw new BusinessException("未指定扎所属的种类");
        }
        add(stabKind);
    }

    @Override
    @MyLog(operationName = "批量删除扎", operationType = "delete")
    public void deleteStabKindByIds(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("未指定要删除的扎");
        }
        //先去查找板材表里面是否有属于当前扎的 若有 直接返回失败
        String hql = "from Slate where isDelete=0 and stabKind.id in (" +
                DSUtil.parseIntegerArr(ids) + ")";
        List<Slate> slateList = (List<Slate>) baseDao.getObjectListByNativeHql(hql);
        if (!CollectionUtils.isEmpty(slateList)) {
            throw new BusinessException("当前扎下面还有板材 不能删除");
        } else {
            delete(StabKind.class, DSUtil.parseIntegerArr(ids));
        }
    }

    @Override
    public List<StabKind> queryAllStabKind() {
        return (List<StabKind>) queryAll(StabKind.class);
    }

    @Override
    public List<StabKind> queryALLStabKindByKindId(Integer id, String num) {
        if (id == null && StringUtils.isBlank(num)) {
            throw new BusinessException("没有填写查询条件");
        }
        StringBuilder hql = new StringBuilder("from StabKind where isDelete=0");
        if (!StringUtils.isBlank(num)) {
            hql.append(" and num like '%" + num + "%'");
        }
        if (id != null) {
            hql.append(" and kind.id=" + id);
        }
        return (List<StabKind>) baseDao.getObjectListByNativeHql(hql.toString());
    }
}
