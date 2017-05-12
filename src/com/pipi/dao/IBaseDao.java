package com.pipi.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;


/**
 * Created by yahto on 07/05/2017.
 */
public interface IBaseDao {
	/** 获取hibernate的Session */
	public Session getHBSession();

	/** 添加一个实体 */
	public void add(Object entity);
	
	/** 保存一个实体 */
	public Serializable save(Object entity);

	/** update 实体 */
	public void update(Object entity);

	/** 根据hql来执行更新操作 */
	public void updateByHql(String hql);

	/** 添加或者保存实体 */
	public void saveOrUpdate(Object entity);

	/** 合并（保存或更新）一个实体 */
	public void merge(Object entity);

	/** 删除实体 */
	public Object delete(Object entity);

	/** 物理删除ids指向的对象 */
	public void deleteEntities(Class<?> clazz, String ids);

	/** 根据id，删除数据：假删 */
	public void delete(Class<?> clazz, int id);

	/** 根据ids,字符串，删除数据：假删 */
	public void delete(Class<?> c, final String ids);

	/** 通过id获取实体 */
	public Object getObjectByID(Class<?> cls, int id);

	/** 通过id降序获取所有实体 */
	//public List<?> getAllObjects(Class<?> c);

	/** 通过id降序获取所有实体 
	 * @param <T>*/
	public <T> List<T> getAllObjects(Class<T> c);

	/** 通地hql语句返回查询list */
	public List<?> getObjectList(String hql);

	/** 通地hql语句返回查询list */
	public List<?> getObjectListByMap(String hql, Map<?, ?> map);

	/** 通地hql语句返回查询list */
	public List<?> getObjectListByList(String hql, List<?> list);

	/** 通地hql语句返回查询unqueresult */
	public Object getUnqueResult(String hql, Map<?, ?> map);
	
	/**	执行本地sql,返回list */
	public List<?> getObjectsByNativeSql(final String sql);

	/** 执行本地sql语句，可以执行update，delete，insert等操作 */
	public void executeNativeSql(final String sql);

	/**
	 * 批量执行本地sql语句
	 * @param	queryString sql语句集
	 */
	public void batchExecuteNativeSql(final List<String> queryString);

	/** 执行批量数据操作（包括插入，删除，和更新）native sql*/
	public void batchUpdate(String[] queryString);

	/** 执行本地sql,返回List<Map> */
	public List<Map<String,Object>> getListMapNativeSql(final String sql);

	/** 通过hql和map参数分页查询，startPage默认为1，pageSize默认为PAGE_SIZE的值，在SystemConstant类里 */
	public List<?> findPageByQuery(int startPage, String hql, Map<?, ?> map);

	/** 通过hql和map参数分页查询，startPage默认为1，pageSize默认为无穷，即默认不分页 */
	public List<?> findPageByQuery(int startPage, int pageSize, String hql, Map<?, ?> map);

	/** 获取分页查询总条数 */
	public Long getTotalCount(String hql, Map<?, ?> map);

	/** 执行本地sql,返回Integer */
	public Integer getObjectByNativeSql(final String sql);
	/** 执行本地sql,返回Object */
	public Object getObjectByNativeSql2(final String sql);

	/** 获取最新消息 */
	//public List<?> getRecent(int typeId);

	/** 执行本地的hql查询语句 返回List<T>*/

	public List<?> getObjectListByNativeHql(String hql);

}
