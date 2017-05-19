package com.pipi.controller.admin;


import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.adminIService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.pipi.controller.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yahto on 07/05/2017.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录功能
     *
     * @param loginName
     * @param password
     * @param request
     * @return
     * @author yahto
     */
    @RequestMapping("user_userLogin.ajax")
    @ResponseBody
    public Map<String, Object> userLogin(String loginName, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            userService.queryUserForLogin(loginName, password, request);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());

        }
        return map;
    }

    /**
     * 用户退出登陆功能
     *
     * @param request
     * @return
     * @author yahto
     */
    @RequestMapping("user_userLogout.ajax")
    @ResponseBody
    public Map<String, Object> userLogout(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(SystemConstant.CURRENT_USER);
            if (user == null) {
                throw new BusinessException("当前没有用户登录");
            } else {
                session.invalidate();
                map.put("data", true);
                map.put("msg", "操作成功");
            }
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 查询所有用户功能
     *
     * @return
     * @author yahto
     */
    @RequestMapping("user_queryAllUsers.ajax")
    @ResponseBody
    public Map<String, Object> queryAllUsers() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            List<User> list = (List<User>) userService.queryAll(User.class);
            map.put("list", list);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 批量删除用户功能 假删除
     *
     * @param ids
     * @return
     * @author yahto
     */
    @RequestMapping("user_deleteUserByIds.ajax")
    @ResponseBody
    public Map<String, Object> deleteUserByIds(Integer[] ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            userService.deleteUserByIds(ids);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 编辑用户功能
     *
     * @param user
     * @return
     * @author yahto
     */
    @RequestMapping("user_updateUserById.ajax")
    @ResponseBody
    public Map<String, Object> updateUser(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            if (user.getId() == null || !(user.getId() instanceof Integer)) {
                throw new BusinessException("未指定用户");
            }
            User existUser = (User) userService.queryObjectByID(User.class, user.getId());
            existUser.setLoginName(user.getLoginName());
            existUser.setPassword(user.getPassword());
            existUser.setUserName(user.getUserName());
            userService.update(existUser);
            map.put("data", true);
            map.put("msg", "操作成功");
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 检测当前浏览器是否有用户已经登陆功能
     *
     * @param request
     * @return
     * @arthor yahto
     */
    @RequestMapping("user_queryCurrentUser.ajax")
    @ResponseBody
    public Map<String, Object> queryCurrentUser(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            User user = (User) request.getSession().getAttribute(SystemConstant.CURRENT_USER);
            if (user == null) {
                throw new BusinessException("未登录,请先登陆");
            } else {
                map.put("current_user", user);
                map.put("roles", user.getRoles());
                map.put("privs", user.getPrivs());
            }
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 修改用户角色功能
     *
     * @param userId
     * @param roleIds
     * @return
     * @author yahto
     */
    @RequestMapping("user_updateUserRoleById.ajax")
    @ResponseBody
    public Map<String, Object> updateUserRoleById(Integer userId, Integer[] roleIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            userService.updateUser(userId, roleIds);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", "操作失败");
        }
        return map;
    }

    /**
     * 其他页面直接返回
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "{path}", method = RequestMethod.GET)
    public String otherPath(@PathVariable("path") String path) {
        return path;
    }

    /**
     * 其他页面直接返回
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "/pages/{path}", method = RequestMethod.GET)
    public String pagesPath(@PathVariable("path") String path) {
        return path;
    }
}
