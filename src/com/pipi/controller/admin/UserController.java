package com.pipi.controller.admin;


import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.admin.Role;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.adminIService.IUserService;
import com.pipi.vo.Page;
import com.pipi.vo.UserRoleVo;
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
import java.util.Iterator;
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
    public Map<String, Object> queryAllUsers(Integer startPage, Integer pageSize) {
        if (startPage == null) {
            startPage = 1;
        }
        if (pageSize == null) {
            pageSize = SystemConstant.PAGE_SIZE;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            Page page = new Page();
            page.setPageSize(pageSize);
            page.setStartPage(startPage);
            List<UserRoleVo> list = (List<UserRoleVo>) userService.queryAllUsers(page);
            map.put("list", list);
            map.put("page", page);
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
//    @RequestMapping("user_updateUserById.ajax")
//    @ResponseBody
//    public Map<String, Object> updateUser(User user) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("data", false);
//        try {
//            if (user.getId() == null || !(user.getId() instanceof Integer)) {
//                throw new BusinessException("未指定用户");
//            }
//            User existUser = (User) userService.queryObjectByID(User.class, user.getId());
//            existUser.setLoginName(user.getLoginName());
//            existUser.setPassword(user.getPassword());
//            existUser.setUserName(user.getUserName());
//            userService.update(existUser);
//            map.put("data", true);
//            map.put("msg", "操作成功");
//        } catch (BusinessException e) {
//            map.put("msg", e.getMessage());
//        }
//        return map;
//    }

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
            User user = getCurrentUser(request);
            if (user == null) {
                throw new BusinessException("未登录,请先登陆");
            } else {
                Iterator iterator = user.getRoles().iterator();
                Role role = (Role) userService.queryObjectByID(Role.class, Integer.valueOf(iterator.next().toString()));
                map.put("current_user", user);
                map.put("role", role);
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
     * @param user
     * @param roleIds
     * @return
     * @author yahto
     */
    @RequestMapping("user_updateUser.ajax")
    @ResponseBody
    public Map<String, Object> updateUser(User user, Integer[] roleIds, String currentLoginName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            userService.updateUser(user, roleIds, currentLoginName);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 根据Id查询用户
     *
     * @param id
     * @return
     * @author hbwj
     */
    @RequestMapping("user_queryUserById.ajax")
    @ResponseBody
    public Map<String, Object> queryUserById(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            map.put("user", userService.queryUserById(id));
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
        }
        return map;

    }

    /**
     * 添加用户
     *
     * @param user
     * @param roleIds
     * @return
     * @author hbwj
     */
    @RequestMapping("user_addUser.ajax")
    @ResponseBody
    public Map<String, Object> addUser(User user, Integer[] roleIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", false);
        try {
            userService.addUser(user, roleIds);
            map.put("msg", "操作成功");
            map.put("data", true);
        } catch (BusinessException e) {
            map.put("msg", e.getMessage());
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
