package com.pipi.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pipi.common.constant.SystemConstant;
import com.pipi.controller.BaseController;
import com.pipi.entity.admin.Log;
import com.pipi.service.admin.ILogService;
import com.pipi.util.DateUtil;
import com.pipi.vo.ListVo;

/**
 * Created by yahto on 07/05/2017.
 */
@Controller
public class LogController extends BaseController {
	@Resource
	ILogService logService;

	/**
	 * 日志查询，模糊查询
	 * 
	 * @param start
	 *            开始的条数（需要计算开始页数）
	 * @param limit
	 *            每页条数
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return ListVo<Log>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("log_list.ajax")
	@ResponseBody
	public ListVo<Log> getLogs(
			Integer start,
			Integer limit,
			HttpServletRequest request,
			@RequestParam(value = "startTime", required = false) Date startTime,
			@RequestParam(value = "endTime", required = false) Date endTime) {
		if(start == null)
			start = 1;
		if(limit == null)
			limit = SystemConstant.PAGE_SIZE;
		ListVo<Log> listVo = new ListVo<Log>();
		Object uu = request.getSession().getAttribute(SystemConstant.CURRENT_USER);
		/** listData和all分别放当前页的日志数据和所有数据 */
		List<Log> listData = new ArrayList<Log>();
		List<Log> all = new ArrayList<Log>();

		if (uu != null) {
			String hql = "from Log log where log.isDelete=0";
			/** 存放hql语句占位符的值 */
			Map<String, Object> mmm = new HashMap<String, Object>();

			if (startTime != null) {
				hql += " and log.submitDate >= :startTime";
				mmm.put("startTime", startTime);
			}
			if (endTime != null) {
				String end = DateUtil.dateToString(endTime, "yyyy-MM-dd");
				end += " 23:59:59";
				hql += " and log.submitDate <= :endTime";
				mmm.put("endTime", DateUtil.stringToDate(end));
			}
			try {
				all = (List<Log>) logService.queryObjectListByMap(hql, mmm);
				listData = (List<Log>) logService.findPageByQuery(start,
						limit, hql, mmm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		listVo.setList(listData);
		if(all.size() <= 1)
			listVo.setTotalSize(1);
		else
			listVo.setTotalSize((all.size() - 1)/limit + 1);
		return listVo;
	}
}
