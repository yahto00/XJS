package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.ProcessSlate;
import com.pipi.entity.Slate;
import com.pipi.entity.StabKind;
import com.pipi.service.iservice.IProcessorService;
import org.springframework.stereotype.Service;


/**
 * Created by yahto on 10/09/2017.
 */
@Service
public class ProcessorService extends BaseService implements IProcessorService {

    @Override
    public void backStorage(Integer processSlateId, Integer stabKindId, String description, float length, float height) {
        if (processSlateId == null) {
            throw new BusinessException("未指定返库的板材，请重试");
        }
        if (stabKindId == null) {
            throw new BusinessException("未指定板材所属的扎，请重试");
        }
        StabKind stabKind = (StabKind) queryObjectByID(StabKind.class, stabKindId);
        ProcessSlate processSlate = (ProcessSlate) queryObjectByID(ProcessSlate.class, processSlateId);
        processSlate.setAcreage(processSlate.getAcreage() - length * height);
        if (processSlate.getAcreage() <= 0) {
            delete(ProcessSlate.class, processSlate.getId());
        } else {
            save(processSlate);
        }
        stabKind.setCurrentAcreage(stabKind.getCurrentAcreage() + length * height);
        stabKind.setCurrentCount(stabKind.getCurrentCount() + 1);
        stabKind.setBackCount(1);
        Slate slate = new Slate();
        slate.setStabKind(stabKind);
        slate.setKind(stabKind.getKind());
        slate.setHeight(height);
        slate.setLength(length);
        slate.setSlateName(processSlate.getSlateName());
        slate.setPrice(processSlate.getPrice());
        save(stabKind);
        save(slate);
    }
}