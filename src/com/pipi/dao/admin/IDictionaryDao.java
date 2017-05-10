package com.pipi.dao.admin;

import java.util.List;
import java.util.Map;

import com.pipi.dao.IBaseDao;
import com.pipi.entity.admin.Dictionary;
import com.pipi.vo.ListVo;

/**
 * @author liuyang
 */
public interface IDictionaryDao extends IBaseDao {
	/**
	 * 获取字典类型集合
	 *@date 2014-10-27
	 *@return List<Dictionary>
	 */
	public List<Dictionary> getDictionaryTypeList();

	/**
	 *@date 2014-10-27
	 *@param i
	 *@param j
	 *@param paramMap
	 *@return void
	 * @throws Exception 
	 */
	public ListVo<Dictionary> getDictionaryList(int i, int j, Map<String, Object> paramMap);
}
