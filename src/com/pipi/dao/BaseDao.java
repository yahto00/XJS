package com.pipi.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pipi.dao.idao.IBaseDao;
import com.pipi.vo.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.pipi.common.constant.SystemConstant;

/**
 * Created by yahto on 07/05/2017.
 */
@Repository
public class BaseDao extends HibernateDaoSupport implements IBaseDao {
	@Resource
	public void setSessionFactoryOverride(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Session getHBSession(){
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

	/** 保存一个实体 */
	@Override
	public void add(Object entity){
			this.getHibernateTemplate().persist(entity);
	}

	@Override
	public Serializable save(Object entity){
		return this.getHibernateTemplate().save(entity);
	}

	/** update 实体 */
	@Override
	public void update(Object entity){
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public void updateByHql(String hql){
		this.getHBSession().createQuery(hql).executeUpdate();
	}

	/** 添加或者保存实体 */
	@Override
	public void saveOrUpdate(Object entity){
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void merge(Object entity){
		this.getHibernateTemplate().merge(entity);
	}

	/** 删除实体 */
	@Override
	public Object delete(Object entity){
		this.getHibernateTemplate().delete(entity);
		return null;
	}

	@Override
	public void delete(Class<?> clazz, int id){
		String hql = "update " + clazz.getName() + " a set a.isDelete = 1 where a.id = " + id;
		this.getHBSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void delete(Class<?> clazz, String ids){
		String hql = "update " + clazz.getName() + " a SET a.isDelete = 1 where a.id in (" + ids + ")";
		this.getHBSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void deleteEntities(Class<?> clazz, String ids){
		String hql = "delete from " + clazz.getName() + " a where a.id in (" + ids + ")";
		this.getHBSession().createQuery(hql).executeUpdate();
	}

	/** 通过id获取实体 */
	@Override
	public Object getObjectByID(Class<?> cls, int id){
		return this.getHibernateTemplate().get(cls, id);
	}

	/** 获取所有实体 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAllObjects(Class<T> c){
		String hql = "select o from " + c.getName() + " o where o.isDelete = 0 order by o.id desc";
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public List<?> getObjectList(String hql){
		Query query = this.getHBSession().createQuery(hql);
		return query.list();
	}

	@Override
	public List<?> getObjectListByMap(String hql, Map<?, ?> map){
		List<?> result = new ArrayList<Object>();
		Query query = this.getHBSession().createQuery(hql);
		if (map != null) {
			Iterator<?> it = map.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				query.setParameter(key.toString(), map.get(key));
			}
		}
		result = query.list();
		return result;
	}

	@Override
	public List<?> getObjectListByList(String hql, List<?> list){
		Query query = this.getHBSession().createQuery(hql);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				query.setParameter(i, list.get(i));
			}
		}
		return query.list();
	}

	/** 获取单个实体 */
	@Override
	public Object getUnqueResult(String hql, Map<?, ?> map) {
		Query query = this.getHBSession().createQuery(hql);
		if(map != null) {
			Iterator<?> it = map.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				query.setParameter(key.toString(), map.get(key));
			}
		}
		return query.uniqueResult();
	}
	
	/** 执行本地sql,返回list */
	@Override
	public List<?> getObjectsByNativeSql(final String sql) {
		return getHBSession().createSQLQuery(sql).list();
	}
	
	/** 执行本地sql,返回list */
	@Override
	public Object getObjectByNativeSql2(final String sql) {
		return getHBSession().createSQLQuery(sql).uniqueResult();
	}

	@Override
	public List<?> getObjectListByNativeHql(String hql) {
		return (List<?>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public List<?> getAllObjectByPage(Class<?> clazz,Page page) {
		String hql = "from " + clazz.getName();
		Query query = getHBSession().createQuery(hql);
		query.setFirstResult((page.getStartPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return (List<?>) query.list();
	}

	@Override
	public Integer getObjectCount(Class<?> clazz) {
		String hql = "select count (*) from " + clazz.getName();
		Query query = getHBSession().createQuery(hql);
		return Integer.valueOf(((Long)query.uniqueResult()).intValue());
	}

	@Override
	public List<?> getAllObjectByPageHql(String hql, Page page) {
		Query query = getHBSession().createQuery(hql);
		query.setFirstResult((page.getStartPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return (List<?>) query.list();
	}

	@Override
	public Integer getObjectCountByHql(String hql) {
		Query query = getHBSession().createQuery(hql);
		return ((Long)query.uniqueResult()).intValue();
	}

	/** 执行本地sql语句，可以执行update，delete，insert等操作 */
	@Override
	public void executeNativeSql(final String sql) {
		getHBSession().createSQLQuery(sql).executeUpdate();
		/*this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) {
				session.createSQLQuery(sql).executeUpdate();
				return null;
			}
		});*/
	}

	/** 执行批量数据操作（包括插入，删除，和更新）native sql */
	@Override
	public void batchExecuteNativeSql(final List<String> queryString){
		this.getHBSession().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				Statement statement = conn.createStatement();
				for(int i = 0; i < queryString.size(); i++)
					statement.addBatch(queryString.get(i));
				statement.executeBatch();
				statement.close();
			}
		});
	}
	
	/***/
	public void batchExecuteNativeSql3(final String[] queryString){
		this.getHBSession().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				Statement statement = conn.createStatement();
				for(int i = 0; i < queryString.length; i++)
					statement.addBatch(queryString[i]);
				statement.executeBatch();
				statement.close();
			}
		});
	}

	/** 执行批量数据操作（包括插入，删除，和更新）native sql */
	@Override
	public void batchUpdate(final String[] queryString) {
		getHBSession().doWork(new Work(){
			@Override
			public void execute(Connection connect) throws SQLException {
				Statement statement = connect.createStatement();
				for (int i = 0; i < queryString.length; i++)
					statement.addBatch(queryString[i]);
				statement.executeBatch();
				statement.close();
			}
		});
	}

	@Override
	public List<?> findPageByQuery(int startPage, String hql, Map<?, ?> map) {
		return findPageByQuery(startPage, SystemConstant.PAGE_SIZE, hql, map);
	}

	@Override
	public List<?> findPageByQuery(int startPage, int pageSize, String hql, Map<?, ?> map) {
		Query query = this.getHBSession().createQuery(hql);
		if (map != null) {
			Iterator<?> it = map.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				query.setParameter(key.toString(), map.get(key));
			}
		}
		if(pageSize > 0){
			query.setFirstResult(startPage <= 0 ? 0 : (startPage - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getListMapNativeSql(String sql) {
		List<Map<String,Object>> list = null;
		Session sess = this.getHibernateTemplate().getSessionFactory().openSession();
		list = sess.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		sess.close();
		return list;
	}

	@Override
	public Long getTotalCount(String hql, Map<?, ?> map) {
		return (Long)getUnqueResult(hql, map);
	}

		@Override
		public Integer getObjectByNativeSql(String sql) {
			return getHBSession().createSQLQuery(sql).list().size();
		}
}
