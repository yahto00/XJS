package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.common.logaop.MyLog;
import com.pipi.entity.Kind;
import com.pipi.util.DSUtil;
import org.springframework.stereotype.Service;

/**
 * Created by yahto on 14/05/2017.
 */
@Service
public class KindService extends BaseService implements IKindService {
    @Override
    @MyLog(operationName = "批量删除种类",operationType = "delete")
    public void deleteKindByIds(Integer[] ids) {
        if (ids == null || ids.length == 0){
            throw new BusinessException("未指定要删除的种类");
        }
        delete(Kind.class, DSUtil.parseIntegerArr(ids));
    }
}
