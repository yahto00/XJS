package com.pipi.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 控制类或者系统抛异常后的处理
 * @author liuyang
 *
 */
@Component
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		/**异常信息打印到后台*/
		ex.printStackTrace();// 1.取得请求路径
		// 使用 getRequestURI 可以得到完整路径
		String servletPath = request.getServletPath();	// 取得访问路径 /article_manager.do
		if(servletPath.endsWith(".ajax"))
			return null;
		Map<String, Object> model = new HashMap<String, Object>();
		/**把异常对象传到前台页面*/
		model.put("ex", ex);
		
		/**根据不同错误转向不同页面*/
		if(ex instanceof BusinessException)
			return new ModelAndView("error-business", model);
		
		return new ModelAndView("error", model);
	}
	
}