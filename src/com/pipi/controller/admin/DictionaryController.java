package com.pipi.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pipi.common.constant.SystemConstant;
import com.pipi.controller.BaseController;
import com.pipi.entity.admin.Dictionary;
import com.pipi.service.iservice.adminIService.IDictionaryService;
import com.pipi.util.XMLUtil;
import com.pipi.vo.ListVo;


/**
 * Created by yahto on 07/05/2017.
 */
@Controller
@SessionAttributes({SystemConstant.CURRENT_USER})
public class DictionaryController extends BaseController {
	/**log4j 日志处理对象*/
	private static final Logger log = Logger.getLogger(DictionaryController.class);
	@Resource
	IDictionaryService dictionaryService;

	/**
	 * 获取字典类型列表
	 * @return List<Map<String, Object>> 
	 */
	@RequestMapping("dictionary_list_type.ajax")
	@ResponseBody
	public List<Map<String, Object>> getDictionaryTypeList(){
		List<Map<String,Object>> dictionaryTypeList = XMLUtil.getDictionaryTypeList();
		return dictionaryTypeList;
	}
	
	/**
	 * 获取字典列表
	 * @param request
	 * @param start 开始的条数
	 * @param limit 个数
	 * @param code 字典编码
	 * @param name 字典名字
	 * @return ListVo<Dictionary>
	 * @throws Exception
	 */
	@RequestMapping("dictionary_list.ajax")
	@ResponseBody
	public ListVo<Dictionary> getDictionaryList(HttpServletRequest request, Integer start, Integer limit, String code, String name) throws Exception{
		if(start == null)
			start = 1;
		if(limit == null)
			limit = SystemConstant.PAGE_SIZE;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("code", code);
		paramMap.put("name", name);
		ListVo<Dictionary> lv = null;
		try {
			lv = dictionaryService.getDictionaryList(start,limit,paramMap);
		} catch (Exception e) {
			log.error("获取字典列表错误");
			e.printStackTrace();
		}
		return lv;
	}

	/**
	 * 更新user
	 * @return map 返回的更新后的信息
	 */
	@RequestMapping("dictionary_get.ajax")
	@ResponseBody
	public Map<String, Object> getDictionary(Integer id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		if(id == null){
			map.put("msg", "字典id不能为空");
			return map;
		}
		try {
			Dictionary dictionary = (Dictionary) dictionaryService.queryObjectByID(Dictionary.class, id);
			if(dictionary == null){
				map.put("msg", "字典不存在");
			} else
				map.put("dictionary", dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "系统错误，请重新尝试");
		}
		return map;
	}
	
	/**
	 * 添加字典
	 * @param dictionary 字典对象
	 * @param modelMap
	 * @return Map 添加成功与否的状态
	 */
	@RequestMapping("dictionary_add.ajax")
	@ResponseBody
	public Map<String, Object> addDictionary(Dictionary dictionary, ModelMap modelMap){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int maxSort = dictionaryService.getMaxSortByDictionaryType(dictionary.getCode());
			dictionary.setSort(maxSort+1);
			dictionary.setSubmitDate(new Date());
			dictionaryService.addDictionary(dictionary);
			map.put("success", true);
			map.put("msg", "添加字典成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "添加字典失败！");
		}
		return map;
	}
	
	/**
	 * 修改字典
	 * @param request
	 * @param dic 字典对象
	 * @param modelMap
	 * @return Map 修改成功与否
	 */
	@RequestMapping("dictionary_update.ajax")
	@ResponseBody
	public Map<String, Object> updateDictionary(HttpServletRequest request, Dictionary dic, ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionaryService.updateDictionary(dic);

			map.put("success", true);
			map.put("msg", "修改字典成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "修改字典失败！");
		}
		return map;
	}
	
	/**
	 * 删除字典
	 * @param ids id组成的字符串，如果有多个id则用逗号隔开
	 * @return Map 删除成功与否的状态
	 */
	@RequestMapping("dictionary_delete.ajax")
	@ResponseBody
	public Map<String, Object> deleteDictionary(String ids){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionaryService.deleteDictionary(ids);
			map.put("success", true);
			map.put("msg", "删除字典成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "删除字典失败！");
		}
		return map;
	}
	
	/**
	 * 添加修改字典时验证字典是否唯一
	 * @param request
	 * @param dictionary
	 * @return map 返回验证成功的
	 */
	@RequestMapping("dictionary_validate.ajax")
	@ResponseBody
	public Map<String, Object> validateDictionary(HttpServletRequest request, Dictionary dictionary) {
		Map<String, Object> vaildator = new HashMap<String, Object>();
		try
		{
			List<Dictionary> list=dictionaryService.getDictionaryByName(dictionary);
			if(list.size()!=0)
			{
				vaildator.put("success", true);
				vaildator.put("valid", false);
				vaildator.put("reason", "该配置项已经存在！");
			} else {
				vaildator.put("success", true);
				vaildator.put("valid", true);
				vaildator.put("reason", "");
			}
		}catch(Exception e){
			vaildator.put("success", false);
			vaildator.put("valid", false);
			vaildator.put("reason", "服务器验证登录名失败！");
		}
		return vaildator;
	}
}
