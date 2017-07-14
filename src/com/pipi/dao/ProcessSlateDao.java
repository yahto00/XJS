package com.pipi.dao;

import com.pipi.dao.idao.IProcessDao;
import com.pipi.entity.ProcessSlate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yahto on 13/07/2017.
 */
@Repository
public class ProcessSlateDao extends BaseDao implements IProcessDao{
    @Override
    public void addBatchProcessSlate(List<ProcessSlate> processSlates) {
        for (ProcessSlate processSlate : processSlates) {
            save(processSlate);
        }
    }
}
