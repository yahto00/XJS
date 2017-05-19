package com.pipi.dao.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pipi.dao.idao.adminIDao.IDictionaryDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.pipi.dao.BaseDao;
import com.pipi.entity.admin.Dictionary;
import com.pipi.vo.ListVo;


/**
 * Created by yahto on 07/05/2017.
 */
@Repository(value="dictionaryDao")
public class DictionaryDao extends BaseDao implements IDictionaryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictionary> getDictionaryTypeList(){
		String hql = "select a from Dictionary a";
		List<Dictionary> list = (List<Dictionary>) this.getObjectList(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListVo<Dictionary> getDictionaryList(int i, int j, Map<String, Object> paramMap){
		ListVo<Dictionary> lv = new ListVo<Dictionary>();
		StringBuffer hqlList = new StringBuffer(); //取得列表的hql语句
		hqlList.append("select a from Dictionary a where a.isDelete = 0");
		StringBuffer hqlCount = new StringBuffer(); //取得总数的hql语句
		hqlCount.append("select count(a.id) from Dictionary a where a.isDelete = 0");
		Map<Object,Object> map = new HashMap<Object,Object> ();
		String code = (String) paramMap.get("code");
		String name = (String) paramMap.get("name");
		if(!StringUtils.isBlank(code)){
			hqlList.append(" and a.code = :code");   
			hqlCount.append(" and a.code = :code");   
			map.put("code", code);  
		}
		if(!StringUtils.isBlank(name)){
			hqlList.append(" and a.name like :name");   
			hqlCount.append(" and a.name like :name");   
			map.put("name", "%"+name+"%");  
		}
		hqlList.append(" order by a.sort asc");
		List<Dictionary> list = (List<Dictionary>)this.findPageByQuery(i, j, hqlList.toString(), map); 
		long count = this.getTotalCount(hqlCount.toString(), map);
		if(count < 1)
			lv.setTotalSize(1);
		else
			lv.setTotalSize(((int)count - 1)/j + 1);
		lv.setList(list);
		return lv;
	}
}
