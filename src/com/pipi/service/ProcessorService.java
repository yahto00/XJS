package com.pipi.service;

import com.pipi.common.exception.BusinessException;
import com.pipi.entity.ProcessSlate;
import com.pipi.entity.Slate;
import com.pipi.entity.StabKind;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.IProcessorService;
import com.pipi.vo.Page;
import com.pipi.vo.SlateDataVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by yahto on 10/09/2017.
 */
@Service
public class ProcessorService extends BaseService implements IProcessorService {

    @Override
    public void backStorage(Integer processSlateId, Integer stabKindId, String description, List<SlateDataVo> voList) {
        if (processSlateId == null) {
            throw new BusinessException("未指定返库的板材，请重试");
        }
        if (stabKindId == null) {
            throw new BusinessException("未指定板材所属的扎，请重试");
        }
        StabKind stabKind = (StabKind) queryObjectByID(StabKind.class, stabKindId);
        ProcessSlate processSlate = (ProcessSlate) queryObjectByID(ProcessSlate.class, processSlateId);
        float originalAcreage = getAcreage(voList);
        if (processSlate.getAcreage() == null || processSlate.getAcreage() < originalAcreage) {
            throw new BusinessException("返库面积不能大于原板材面积");
        }
        processSlate.setAcreage(processSlate.getAcreage() - originalAcreage);
        if (processSlate.getAcreage() <= 0) {
            delete(ProcessSlate.class, processSlate.getId());
        } else {
            update(processSlate);
        }
        stabKind.setCurrentAcreage(stabKind.getCurrentAcreage() + originalAcreage);
        stabKind.setCurrentCount(stabKind.getCurrentCount() + 1);
        stabKind.setBackCount(1);
        for (SlateDataVo data : voList) {
            Slate slate = new Slate();
            slate.setStabKind(stabKind);
            slate.setKind(stabKind.getKind());
            slate.setHeight(data.getHeight());
            slate.setLength(data.getLength());
            slate.setSlateName(processSlate.getSlateName());
            slate.setPrice(processSlate.getPrice());
            save(stabKind);
            save(slate);
        }

    }

    private float getAcreage(List<SlateDataVo> voList) {
        float acreage = 0f;
        for (SlateDataVo vo : voList) {
            acreage += vo.getHeight() * vo.getLength();
        }
        return acreage;
    }

    @Override
    public List<ProcessSlate> queryProcessSlateByPage(User user, Page page) {
        StringBuilder hql = new StringBuilder();
        StringBuilder countHql = new StringBuilder("select count(*) from ProcessSlate");
        if (user.getRoles().contains(1)) {
            //如果是超级管理员直接查询到所有的加工板材
            hql.append("from ProcessSlate where isDelete=0 ");
            countHql.append(" where isDelete=0");
            Integer totalCount = baseDao.getObjectCountByHql(countHql.toString());
            if (totalCount == null || totalCount == 0) {
                page.setTotalCount(0);
            } else {
                page.setTotalCount(totalCount);
            }
            return (List<ProcessSlate>) baseDao.getAllObjectByPageHql(hql.toString(), page);
        } else {
            hql.append("from ProcessSlate where isDelete=0 and user.id = " + user.getId());
            countHql.append(" where isDelete=0 and user.id=" + user.getId());
            Integer totalCount = baseDao.getObjectCountByHql(countHql.toString());
            if (totalCount == null || totalCount == 0) {
                page.setTotalCount(0);
            } else {
                page.setTotalCount(totalCount);
            }
            return (List<ProcessSlate>) baseDao.getAllObjectByPageHql(hql.toString(), page);
        }
    }
}
