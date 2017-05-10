package com.pipi.service.admin;

import com.pipi.service.IBaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuyang
 */
public interface IUserService extends IBaseService{

    /**
     * 用户登录功能
     * @author yahto
     * @param loginName
     * @param password
     * @param request
     */
    void queryUserForLogin(String loginName, String password, HttpServletRequest request);
}
