package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.common.logaop.MyLog;
import com.pipi.entity.Kind;
import com.pipi.entity.StabKind;
import com.pipi.service.iservice.IKindService;
import com.pipi.util.DSUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by yahto on 14/05/2017.
 */
@Service
public class KindService extends BaseService implements IKindService {
    @Override
    @MyLog(operationName = "批量删除种类", operationType = "delete")
    public void deleteKindByIds(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            throw new BusinessException("未指定要删除的种类");
        }
        //先去查找板材表里面是否有属于当前扎的 若有 直接返回失败
        String hql = "from StabKind s where kindId in (" +
                DSUtil.parseIntegerArr(ids) + ")";
        List<StabKind> slateList = (List<StabKind>) baseDao.getObjectList(hql);
        if (!CollectionUtils.isEmpty(slateList)) {
            throw new BusinessException("当前种类下面还有扎 不能删除");
        } else {
            delete(Kind.class, DSUtil.parseIntegerArr(ids));
        }
    }
}
