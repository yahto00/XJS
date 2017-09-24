package com.pipi.dao.idao;

import com.pipi.entity.ProcessSlate;

import java.util.List;

/**
 * Created by yahto on 13/07/2017.
 */
public interface IProcessDao extends IBaseDao {
    void addBatchProcessSlate(List<ProcessSlate> processSlates);
}
