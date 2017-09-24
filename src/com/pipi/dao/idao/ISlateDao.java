package com.pipi.dao.idao;

import com.pipi.entity.Slate;

import java.util.List;

/**
 * Created by yahto on 10/09/2017.
 */
public interface ISlateDao extends IBaseDao {
    void addBatchSlate(List<Slate> slateList);
}
