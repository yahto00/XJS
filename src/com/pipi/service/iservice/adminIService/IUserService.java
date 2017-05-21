package com.pipi.service.iservice.adminIService;

import com.pipi.entity.admin.User;
import com.pipi.service.iservice.IBaseService;
import com.pipi.vo.UserRoleVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by yahto on 07/05/2017.
 */
public interface IUserService extends IBaseService{

    /** 用户登录功能*/
    void queryUserForLogin(String loginName, String password, HttpServletRequest request);
    /** 批量删除用户*/
    void deleteUserByIds(Integer[] ids);
    /** 修改用户角色*/
    void updateUser(User user,Integer[] roleIds,String currentLoginName);
   /** 根据Id查找用户*/
    User queryUserById(Integer id);
    /** 添加用户功能*/
    void addUser(User user, Integer[] roleIds);
    /** 查询所有用户功能*/
    List<UserRoleVo> queryAllUsers();
}
