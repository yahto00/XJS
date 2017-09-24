package com.pipi.service.adminSerivce;

import java.util.List;
import java.util.Map;

import com.pipi.service.iservice.adminIService.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pipi.common.aop.MyLog;
import com.pipi.dao.idao.adminIDao.IDictionaryDao;
import com.pipi.entity.admin.Dictionary;
import com.pipi.service.BaseService;
import com.pipi.vo.ListVo;

/**
 * Created by yahto on 07/05/2017.
 */
@Service(value = "dictionaryService")
public class DictionaryService extends BaseService implements IDictionaryService {

    @Autowired
    IDictionaryDao dictionaryDao;

    @Override
    public ListVo<Dictionary> getDictionaryList(int i, int j, Map<String, Object> paramMap) {
        return this.dictionaryDao.getDictionaryList(i, j, paramMap);
    }

    @Override
    @MyLog(operationName = "增加字典", operationType = "add")
    public void addDictionary(Dictionary dictionary) {
        this.dictionaryDao.save(dictionary);

    }

    @Override
    @MyLog(operationName = "更新字典", operationType = "update")
    public void updateDictionary(Dictionary dictionary) {
        Dictionary dic = (Dictionary) this.dictionaryDao.getObjectByID(Dictionary.class, dictionary.getId());
        dic.setName(dictionary.getName());
        dic.setSubmitDate(dictionary.getSubmitDate());
        this.dictionaryDao.update(dic);
    }

    @Override
    public int getMaxSortByDictionaryType(String type) {
        int size = 0;
        List<?> listDictionary = this.dictionaryDao.getObjectList("select max(d.sort) from Dictionary d where d.code = '" + type + "'");
        if (!CollectionUtils.isEmpty(listDictionary)) {
            String strSize = String.valueOf(listDictionary.get(0));
            if (!strSize.equals("null")) {
                size = (Integer) listDictionary.get(0);
            }
        }
        return size;
    }

    @Override
    @MyLog(operationName = "删除字典", operationType = "delete")
    public void deleteDictionary(String ids) {
        this.dictionaryDao.delete(Dictionary.class, ids);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dictionary> getDictionaryByName(Dictionary dictionary) {
        String hql = "select w from Dictionary w where w.code='" + dictionary.getCode() + "' and w.isDelete=0 and w.name='" + dictionary.getName() + "'";
        return (List<Dictionary>) this.dictionaryDao.getObjectList(hql);
    }

}
