package com.pipi.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pipi.common.constant.SystemConstant;
import com.pipi.controller.BaseController;
import com.pipi.entity.admin.User;
import com.pipi.util.JsonUtils;


/**
 * 权限拦截器
 * Created by yahto on 07/05/2017.
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(AuthorityInterceptor.class);	// 让打印的日志中带有的类信息，否则不好查找日志
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		// 1.取得请求路径
		// 使用 getRequestURI 可以得到完整路径
		String servletPath = request.getServletPath();	// 取得访问路径 /article_manager.do
		String queryString = request.getQueryString();	// 取得查询参数
		String requestUrl = queryString == null ? servletPath : (servletPath + '?' + queryString);
		//
		
		// 2.检查Action类型[Spring MVC 3.1]
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Object bean = handlerMethod.getBean();
		if (!(bean instanceof BaseController))
			return true;	// 非本系统的Action
		Integer actionId = BaseController.getActionId(BaseController.getActionPath(servletPath));
		if (actionId == null || actionId == 0)
			return true;	// 该方法不需要登录
		//BaseController controller = (BaseController)bean;
		Object userO = request.getSession().getAttribute(SystemConstant.CURRENT_USER);
		String contextPath = request.getContextPath();
		
		boolean flag = false;
		if(userO != null && (userO instanceof User)){
			User user = (User)userO;
			//3.记录请求日志no_authorization
			logger.info(user.getLoginName() + "-" + requestUrl);
			flag = user.getPrivs().contains(actionId);
		}
		if(!flag){
			if(servletPath.endsWith(".ajax")){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				map.put("msg", "您未登录或者没有访问权限");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = null;
			    try {
			        out = response.getWriter();
			        out.append(JsonUtils.jsonFromObject(map));
			    } catch (IOException e) {  
			        e.printStackTrace();  
			    } finally {  
			        if (out != null) {  
			            out.close();  
			        }  
			    }  
			} else if(userO == null){
				response.sendRedirect(contextPath + "/pages/login.html");//登录页面
			} else
				response.sendRedirect(contextPath + "/no_authorization.jsp");
		}
		return flag;
	}
	
	
}