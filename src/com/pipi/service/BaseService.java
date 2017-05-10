package com.pipi.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pipi.dao.IBaseDao;

/**
 * 业务处理基接口实现
 * @author liuyang
 */
@Service
public class BaseService implements IBaseService {
	@Autowired
	protected IBaseDao baseDao;

	@Override
	public void add(Object entity) {
		this.baseDao.add(entity);
	}

	@Override
	public Serializable save(Object entity) {
		return this.baseDao.save(entity);
	}

	@Override
	public void update(Object entity) {
		this.baseDao.update(entity);
	}

	@Override
	public void updateByHql(String hql) {
		this.baseDao.updateByHql(hql);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		this.baseDao.saveOrUpdate(entity);
	}

	@Override
	public void merge(Object entity) {
		this.baseDao.merge(entity);
	}

	@Override
	public void delete(Object entity) {
		this.baseDao.delete(entity);
	}

	@Override
	public void delete(Class<?> clazz, int id) {
		this.baseDao.delete(clazz, id);
	}

	@Override
	public void delete(Class<?> c, String ids) {
		this.baseDao.delete(c, ids);
	}

	@Override
	public void deleteEntities(Class<?> c, String ids) {
		this.baseDao.deleteEntities(c, ids);
	}

	@Override
	public Object queryObjectByID(Class<?> c, int id) {
		return this.baseDao.getObjectByID(c, id);
	}

	@Override
	public List<?> queryAll(Class<?> c) {
		return this.baseDao.getAllObjects(c);
	}

	@Override
	public List<?> queryObjectList(String hql) {
		return this.baseDao.getObjectList(hql);
	}

	@Override
	public List<?> queryObjectListByMap(String hql, Map<?,?> map){
		return this.baseDao.getObjectListByMap(hql, map);
	}

	@Override
	public List<?> queryObjectListByList(String hql, List<?> list){
		return this.baseDao.getObjectListByList(hql, list);
	}

	@Override
	public Object queryUnqueResult(String hql, Map<?,?> map){
		return this.baseDao.getUnqueResult(hql,map);
	}
	
	@Override
	public List<?> queryListByNavtiveSql(String sql) {
		return this.baseDao.getObjectsByNativeSql(sql);
	}

	@Override
	public void executeNativeSql(String sql) {
		this.baseDao.executeNativeSql(sql);
	}

	@Override
	public List<?> findPageByQuery(int start, String hql, Map<?, ?> map) {
		return this.baseDao.findPageByQuery(start, hql, map);
	}

	@Override
	public List<?> findPageByQuery(int start, int pageSize, String hql, Map<?, ?> map) {
		return this.baseDao.findPageByQuery(start, pageSize, hql, map);
	}

	@Override
	public Long queryTotalCount(String hql, Map<?, ?> map) {
		return this.baseDao.getTotalCount(hql, map);
	}

	@Override
	public Integer queryObjectByNavtiveSql(String sql) {
		return this.baseDao.getObjectByNativeSql(sql);
	}

	@Override
	public void batchExecuteNativeSql(List<String> queryString) {
		this.baseDao.batchExecuteNativeSql(queryString);
	}
}
