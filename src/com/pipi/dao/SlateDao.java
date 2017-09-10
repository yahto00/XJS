package com.pipi.dao;

import com.pipi.dao.idao.ISlateDao;
import com.pipi.entity.Slate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yahto on 10/09/2017.
 */
@Repository
public class SlateDao extends BaseDao implements ISlateDao {

    @Override
    public void addBatchSlate(List<Slate> slateList) {
        for (Slate slate : slateList) {
            save(slate);
        }
    }
}
