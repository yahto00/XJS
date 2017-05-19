package com.pipi.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.pipi.common.constant.SystemConstant;
import com.pipi.entity.admin.Log;
import com.pipi.entity.admin.User;
import com.pipi.service.iservice.IBaseService;
import com.pipi.service.iservice.adminIService.ILogService;
import com.pipi.service.iservice.adminIService.IUserService;
import com.pipi.util.JsonUtils;


/**
 * 系统转发基类处理
 * Created by yahto on 07/05/2017.
 */
@Controller
public class BaseController {
	public static final String AJAX_REQUEST = "XMLHttpRequest";	// ajax请求标志
	public static final String FILE_SECURITY = "security.json";
	public static final String CHARSET = "UTF-8";

	/** action路径的权限映射表 */
	public static Map<String, Integer> ACTION_MAP;		// {actionPath:预设权限ID}映射表，例如{"account_find":1}
	public static Map<String, Map<String, Integer>> ACTION_CATALOG;	// 模块别名映射表
	
	/**log4j 日志处理对象*/
	private static final Logger log = Logger.getLogger(BaseController.class);

	/**逻辑处理接口基类*/
	@Autowired
	public IBaseService baseService;
	
	/**日志逻辑处理接口*/
	@Autowired
	public ILogService logService;
	
	/**员工管理逻辑处理接口*/
	//以后要删除，供给模拟当前操作使用
	@Autowired
	public IUserService userService;
	
	// 返回路径 actionPath对应的ID，权限拦截器中要用到
	public static Integer getActionId(String actionPath){
		if (ACTION_MAP == null)
			loadActionSetting();
		return ACTION_MAP.get(actionPath);
	}

	private static void loadActionSetting(){
		ACTION_MAP = new HashMap<String, Integer>();
		ACTION_CATALOG = new LinkedHashMap<String, Map<String, Integer>>();
		
		JsonNode rootNode = null;
		InputStream inputStream = null;
		try {
			inputStream = SystemConstant.class.getClassLoader().getResourceAsStream(FILE_SECURITY);
			InputStreamReader reader = new InputStreamReader(inputStream, CHARSET);
			StringBuilder buffer = new StringBuilder();
			char[] cbuf = new char[256];
			int c;
			while ((c = reader.read(cbuf)) >= 0) {
				buffer.append(cbuf, 0, c);
			}
			rootNode = JsonUtils.readTree(buffer.substring(buffer.indexOf("{")));
			reader.close();
		} catch (FileNotFoundException e) {
			log.error("类路径下没找到" + FILE_SECURITY + "文件");
		} catch (IOException e) {
		} finally {
			if (inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {}
			}
		}
		if (rootNode == null){
			log.error("加载" + FILE_SECURITY + "文件失败");
			return;
		}
		
		// 遍历js文件（json数据）的根节点
		Iterator<String> rootIter = rootNode.fieldNames();
		while (rootIter.hasNext()){
			String name = rootIter.next();
			String[] vName = name.split("\\s*,\\s*");
			
			JsonNode node = rootNode.path(name);
			// 遍历模块节点
			Map<String, Integer> mapAlias = new LinkedHashMap<String, Integer>();
			Set<Integer> privSet = new HashSet<Integer>();
			Iterator<String> nodeIter = node.fieldNames();
			while (nodeIter.hasNext()){
				String method = nodeIter.next();
				String[] vMethod = method.split("\\s*,\\s*");
				int privId = node.path(method).asInt();
				ACTION_MAP.put(vName[0] + '_' + vMethod[0], privId);

				// 方法别名映射表
				String methodAlias = vMethod.length >= 2 ? vMethod[1] : vMethod[0];
				if (privId > 0 && !methodAlias.equals("-") && !privSet.contains(privId)){
					mapAlias.put(methodAlias, privId);
					privSet.add(privId);
				}
			}

			// 模块别名映射表
			if (mapAlias.size() > 0){
				String moduleAlias = vName.length >= 2 ? vName[1] : vName[0];
				ACTION_CATALOG.put(moduleAlias, mapAlias);
			}
		}
	}
	// 返回权限目录
	public static Map<String, Map<String, Integer>> getActionCatalog(){
		if (ACTION_CATALOG == null)
			loadActionSetting();
		return ACTION_CATALOG;
	}
	// 判断是否为ajax请求
	public static boolean isAjax(HttpServletRequest request){
		String servletPath = request.getServletPath();
		return servletPath.endsWith(".json") || AJAX_REQUEST.equals(request.getHeader("X-Requested-With"));
	}
	/* 取得访问路径，把 /account_find.do 转为 account_find **/
	public static String getActionPath(String servletPath){
		int p0 = servletPath.lastIndexOf('/');
		p0 = (p0 == -1) ? 0 : (p0 + 1);
		int p = servletPath.lastIndexOf('.');
		return (p == -1) ? servletPath.substring(p0) : servletPath.substring(p0, p);
	}

	/**
	 * 取得当前登录用户的登录IP
	 * @param request servlet内置对象
	 * @return String 当前登录用户的IP地址
	 */
	public String getCurrentUserIP(HttpServletRequest request) {
		return request.getRemoteAddr();
		
	}

	/**
	 * 下载文件
	 * @param fileName  要下载的文件文件名 
	 * @param request   servlet内置对象
	 * @param response  servlet内置对象
	 * @return String null
	 * @throws Exception
	 */
	public String downLoadFile(String fileName,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		response.reset();// 可以加也可以不加
		response.setContentType("application/x-download;charset=UTF-8");

		String filePath = request.getSession().getServletContext().getRealPath("/upload/") + "/";
		String downLoadPath = filePath + fileName;
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

		try {
			long fileLength = new File(downLoadPath).length();
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null){
				bis.close();
				bis = null;
			}
			if (bos != null){
				bos.close();
				bos = null;
			}
		}
		return null;
	}
	
	/**
	 * 取得当前登录员工对象
	 * @param request  servlet内置对象
	 * @return 当前登录员工对象
	 */
	public User getCurrentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(SystemConstant.CURRENT_USER);
	}
	
	/**
	 *  添加日志信息
	 * @param content 日志内容
	 * @param request servlet内置对象
	 * @throws Exception 
	 * @return void
	 */
	public void addLog(String content, HttpServletRequest request) throws Exception{
		Log logInfo = new Log();
		logInfo.setIp(this.getCurrentUserIP(request));
		logInfo.setContent(content);
		logInfo.setSubmitDate(new Date());
		logService.add(logInfo);
	}
}
