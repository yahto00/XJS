package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.common.logaop.MyLog;
import com.pipi.entity.Slate;
import com.pipi.entity.StabKind;
import com.pipi.service.iservice.ISlateService;
import org.springframework.stereotype.Service;

/**
 * Created by yahto on 13/05/2017.
 */
@Service
public class SlateService extends BaseService implements ISlateService {
    @Override
    @MyLog(operationName = "添加板材",operationType = "add")
    public void addSlate(Slate slate, Integer kindId, Integer stabKindId) {
        if (slate == null){
            throw new BusinessException("未填写板材信息");
        }
        if (kindId == null){
            throw new BusinessException("未指定板材所属种类");
        }
        if (stabKindId == null){
            throw new BusinessException("未指定板材所属扎");
        }
        slate.setKindId(kindId);//关联种类
        StabKind stabKind = (StabKind) queryObjectByID(StabKind.class,stabKindId);
        stabKind.setCurrentCount(stabKind.getCurrentCount()+1);//入库数量加1
        stabKind.setCurrentAcreage(stabKind.getCurrentAcreage() + slate.getHeight()*slate.getLength());//改变在库面积
        slate.setStabKindId(stabKindId);//关联板材
        add(slate);
    }
}
