package com.pipi.service;

import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.Development;
import com.pipi.entity.ProcessSlate;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.IDevelopmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public void produceDevelopment(Integer processSlateId, List<Development> list, HttpServletRequest request) {
        if (processSlateId == null){
            throw new BusinessException("未指定生成成品的板材");
        }
        if (list == null){
            throw new BusinessException("未填写成品数据");
        }
        if (request.getSession().getAttribute(SystemConstant.CURRENT_USER) == null){
            throw new BusinessException("未登录,请先登录");
        }
        ProcessSlate processSlate = (ProcessSlate) queryObjectByID(ProcessSlate.class,processSlateId);
        User user = (User) request.getSession().getAttribute(SystemConstant.CURRENT_USER);
        User existUser = (User) queryObjectByID(User.class,user.getId());
        delete(ProcessSlate.class,processSlateId);
        for (Development development : list) {
            development.setProcessSlate(processSlate);
            development.setStatus(0);
            development.setUser(existUser);
            add(development);
        }
    }
}
