package com.pipi.service.iservice.adminIService;

import com.pipi.service.iservice.IBaseService;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by yahto on 07/05/2017.
 */
public interface IUserService extends IBaseService{

    /** 用户登录功能*/
    void queryUserForLogin(String loginName, String password, HttpServletRequest request);
    /** 批量删除用户*/
    void deleteUserByIds(Integer[] ids);
}
