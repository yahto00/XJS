package com.pipi.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定拦截器 暂未使用
 * Created by yahto on 07/05/2017.
 */
public class MyInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex)	throws Exception {
		//Object handler是下一个拦截器
		System.out.println("最后执行！！！一般用于释放资源或处理异常，记录异常日志等！！");
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,	ModelAndView modelAndView) throws Exception {
		System.out.println("Action执行之后，生成视图之前执行，有机会修改ModelAndView！！");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		System.out.println("action之前执行，可以进行编码、安全控制等处理！！！");
		return true;  //返回true，表示继续执行action
	}
}
