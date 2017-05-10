package com.pipi.controller.admin;


import com.pipi.common.exception.BusinessException;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理类
 * 
 * @author yahto
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
