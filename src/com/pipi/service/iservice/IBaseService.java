package com.pipi.service.iservice;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by yahto on 07/05/2017.
 */
public interface IBaseService {

	/** 保存信息到数据库 */
	public void add(Object entity);

	/** 保存信息，并返回主键(实体是游离态不会出错) */
	public Serializable save(Object entity);

	/** 更新信息 */
	public void update(Object entity);

	/** 根据hql进行更新操作 */
	public void updateByHql(String hql);

	/** 更新或保存信息 */
	public void saveOrUpdate(Object entity);
	
	/** 追加信息到已有的实体中 */
	public void merge(Object entity);

	/** 从数据库真正删除数据 */
	public void delete(Object entity);

	/** 根据id，逻辑删除数据 */
	public void delete(Class<?> clazz, int id);

	/** 逻辑删除表中记录(前提是:传人的实体类必须要有id字段作为主键) */
	void delete(Class<?> c, final String ids);

	/** 物理删除表中的数据 */
	void deleteEntities(Class<?> c, String ids);

	/** 通过实体和ID查询信息 */
	public Object queryObjectByID(Class<?> c, int id);

	/** 查询该实体下的全部信息 */
	public List<?> queryAll(Class<?> c);

	/** 根据HQL，取得结果列表 */
	public List<?> queryObjectList(String hql);

	/** 通地hql语句返回查询list */
	public List<?> queryObjectListByMap(String hql, Map<?, ?> map);

	/** 通地hql语句返回查询list */
	public List<?> queryObjectListByList(String hql, List<?> list);

	/** 通地hql语句返回查询unqueresult */
	public Object queryUnqueResult(String hql, Map<?, ?> map);

	/** 通过native sql 取得记录列表 */
	public List<?> queryListByNavtiveSql(String sql);
	
	/** 执行本地sql语句 */
	void executeNativeSql(String sql);

	/** 通过hql和map参数分页查询,pageSize默认为PAGE_SIZE */
	public List<?> findPageByQuery(int start, String hql, Map<?, ?> map);

	/** 通过hql和map参数分页查询 */
	public List<?> findPageByQuery(int start, int pageSize, String hql, Map<?, ?> map);

	/** 根据查询条件查询记录数的个数 */
	Long queryTotalCount(String hql, Map<?, ?> map);

	/** 通过native sql 取得记录列表 */
	public Integer queryObjectByNavtiveSql(String sql);
	
	public void batchExecuteNativeSql(final List<String> queryString);
	
	/** 获取通过CODe */
	//public String queryCode(String subfiex,Class<?> clazz, String field);
	
}
