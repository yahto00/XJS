package com.pipi.service.admin;

import java.util.List;
import java.util.Map;

import com.pipi.entity.admin.Dictionary;
import com.pipi.service.IBaseService;
import com.pipi.vo.ListVo;

/**
 * @author liuyang
 */
public interface IDictionaryService extends IBaseService {

	/** 获取字典集合 */
	public ListVo<Dictionary> getDictionaryList(int i, int j, Map<String, Object> paramMap);

	/** 添加字典 */
	public void addDictionary(Dictionary dictionary);

	/** 修改字典 */
	public void updateDictionary(Dictionary dictionary);

	/** 获取字典最大顺序  */
	public int getMaxSortByDictionaryType(String type);

	/** 批量删除字典 */
	public void deleteDictionary(String ids);
	
	/** 通过名字获取字典列表 */
	public List<Dictionary> getDictionaryByName(Dictionary dictionary);

}
