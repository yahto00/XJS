package com.pipi.common.constant;


/**
 * 系统常量
 * Created by yahto on 07/05/2017.
 */
public class SystemConstant {

	
	/** 当前用户 */
	public static final String CURRENT_USER = "current_user";
	
	/** 系统日期格式 **/
	public static final String DATE_PATTEN = "yyyy-MM-dd";

	/** 系统日期时间格式 **/
	public static final String TIME_PATTEN = "yyyy-MM-dd HH:mm:ss";

	/** 系统日期时间格式 **/
	public static final String TIME_ONLY = "HH:mm:ss";
	
	/** 分页单个页面默认显示条数 **/
	public static final int PAGE_SIZE = 100;//Integer.MAX_VALUE;
	
	/** 系统默认收包裹截止时间 **/
	public static final String DEADLINE = "2016-11-21 23:59:59";

	/** 管理员登录名,系统默认的一个管理员帐号 */
	public static final String USER_ADMIN_LOGINNAME = "adminSerivce";
	
	/**临时订单编号的自动递增时，保存当前的编号*/
	public static final String TEMPORDER_NO = "/TempOrderNo.txt";
	/** 分页单个页面默认显示条数 **/
	public static final Double WEIGHT_LIMIT = 2.0;//Integer.MAX_VALUE;
}
