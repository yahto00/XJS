package com.pipi.service.iservice;

import com.pipi.entity.Development;
import com.pipi.entity.admin.User;

import java.util.List;

/**
 * Created by yahto on 14/10/2017
 */
public interface IDevelopmentService extends IBaseService{
    List<Development> queryAllDevelopment(User user);
}
