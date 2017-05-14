package com.pipi.controller.admin;


import com.pipi.common.constant.SystemConstant;
import com.pipi.common.exception.BusinessException;
import com.pipi.entity.admin.User;
import com.pipi.service.admin.IUserService;
import com.pipi.service.admin.UserService;
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
	 * @author yahto
	 * @param loginName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("user_userLogin.ajax")
	@ResponseBody
	public Map<String,Object> userLogin(String loginName, String password, HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data",false);
		try {
			userService.queryUserForLogin(loginName,password,request);
			map.put("data",true);
			map.put("msg","操作成功");
		}catch (BusinessException e){
			map.put("msg",e.getMessage());

		}
		return map;
	}

	/**
	 * 用户退出登陆功能
	 * @author yahto
	 * @param request
	 * @return
	 */
	@RequestMapping("user_userLogout.ajax")
	@ResponseBody
	public Map<String,Object> userLogout(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data",false);
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(SystemConstant.CURRENT_USER);
			if (user == null){
				throw new BusinessException("当前没有用户登录");
			}else {
				session.invalidate();
				map.put("data",true);
				map.put("msg","操作成功");
			}
		}catch (BusinessException e){
			map.put("msg",e.getMessage());
		}
		return map;
	}

	/**
	 * 查询所有用户功能
	 * @author yahto
	 * @return
	 */
	@RequestMapping("user_queryAllUsers.ajax")
	@ResponseBody
	public Map<String,Object> queryAllUsers(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data",false);
		try {
			List<User> list = (List<User>) userService.queryAll(User.class);
			map.put("list",list);
			map.put("data",true);
			map.put("msg","操作成功");
		}catch (BusinessException e){
			map.put("msg",e.getMessage());
		}
		return map;
	}

	/**
	 * 批量删除用户功能 假删除
	 * @author yahto
	 * @param ids
	 * @return
	 */
	@RequestMapping("user_deleteUserByIds.ajax")
	@ResponseBody
	public Map<String,Object> deleteUserByIds(Integer[] ids){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data",false);
		try {
			userService.deleteUserByIds(ids);
			map.put("data",true);
			map.put("msg","操作成功");
		}catch (BusinessException e){
			map.put("msg",e.getMessage());
		}
		return map;
	}

	/**
	 * 编辑用户功能
	 * @author yahto
	 * @param user
	 * @return
	 */
	@RequestMapping("user_editUserById.ajax")
	@ResponseBody
	public Map<String,Object> editUser(User user){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data",false);
		try {
			if (user.getId() == null || !(user.getId()instanceof Integer)){
				throw new BusinessException("未指定用户");
			}
			User existUser = (User) userService.queryObjectByID(User.class,user.getId());
			existUser.setLoginName(user.getLoginName());
			existUser.setPassword(user.getPassword());
			existUser.setUserName(user.getUserName());
			userService.update(existUser);
			map.put("data",true);
			map.put("msg","操作成功");
		}catch (BusinessException e){
			map.put("msg",e.getMessage());
		}
		return map;
	}
	/**
	 * 其他页面直接返回
	 * @param path
	 * @return
	 */
	@RequestMapping(value="{path}",method = RequestMethod.GET)
	public String otherPath(@PathVariable("path") String path) {
		return path;
	}

	/**
	 * 其他页面直接返回
	 * @param path
	 * @return
	 */
	@RequestMapping(value="/pages/{path}",method = RequestMethod.GET)
	public String pagesPath(@PathVariable("path") String path) {
		return path;
	}
}
