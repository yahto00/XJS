package com.pipi.dao.idao.adminIDao;

import java.util.List;
import java.util.Map;

import com.pipi.dao.idao.IBaseDao;
import com.pipi.entity.admin.Dictionary;
import com.pipi.vo.ListVo;


/**
 * Created by yahto on 07/05/2017.
 */
public interface IDictionaryDao extends IBaseDao {
    /**
     * 获取字典类型集合
     *
     * @return List<Dictionary>
     * @date 2014-10-27
     */
    public List<Dictionary> getDictionaryTypeList();

    /**
     * @param i
     * @param j
     * @param paramMap
     * @return void
     * @throws Exception
     * @date 2014-10-27
     */
    public ListVo<Dictionary> getDictionaryList(int i, int j, Map<String, Object> paramMap);
}
