package com.pipi.service;

import com.pipi.entity.Development;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.IDevelopmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yahto on 14/10/2017
 */
@Service
public class DevelopmentService extends BaseService implements IDevelopmentService {
    @Override
    public List<Development> queryAllDevelopment(User user) {
        StringBuilder hql = new StringBuilder("from Development where isDelete = 0 and status = 0");
        if (user.getRoles().contains(1)) {
            //超级管理员 查询到所有的成品
            return (List<Development>) baseDao.getObjectListByNativeHql(hql.toString());
        }else {
            hql.append(" and user.id = " + user.getId());
            return (List<Development>) baseDao.getObjectListByNativeHql(hql.toString());
        }
    }
}
