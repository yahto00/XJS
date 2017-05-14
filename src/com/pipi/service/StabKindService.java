package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.StabKind;
import org.springframework.stereotype.Service;

/**
 * Created by yahto on 14/05/2017.
 */
@Service
public class StabKindService extends BaseService implements IStabKindService {
    @Override
    public void addStabKind(StabKind stabKind) {
        if (stabKind == null){
            throw new BusinessException("未接收到添加的参数");
        }
        if (stabKind.getKindId() == null){
            throw new BusinessException("未指定扎所属的种类");
        }
        save(stabKind);
    }
}
