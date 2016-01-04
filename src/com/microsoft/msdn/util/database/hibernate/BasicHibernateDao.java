package com.microsoft.msdn.util.database.hibernate;

import org.hibernate.*;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-13
 * Time: ����3:08
 * To change this template use File | Settings | File Templates.
 */
public interface BasicHibernateDao<T extends Serializable, PK extends Serializable> {
    public T get(PK id) throws Exception;

    public T getWithLock(PK id, LockMode lockMode) throws Exception;

    public T load(PK id) throws Exception;

    public T loadWithLock(PK id, LockMode lockMode) throws Exception;

    public List<T> loadAll() throws Exception;

    public void update(T entity) throws Exception;

    public void updateWithLock(T entity, LockMode lockMode) throws Exception;

    public void save(T entity) throws Exception;

    public void saveOrUpdate(T entity) throws Exception;

    public void saveOrUpdateAll(Collection<T> entities) throws Exception;

    public void delete(T entity) throws Exception;

    public void deleteWithLock(T entity, LockMode lockMode) throws Exception;

    public void deleteByKey(PK id) throws Exception;

    public void deleteByKeyWithLock(PK id, LockMode lockMode) throws Exception;

    public void deleteAll(Collection<T> entities) throws Exception;

    public void flush() throws Exception;

    public int updateByHql(String queryString) throws Exception;

    public int updateByHql(String queryString, Collection<Object> values) throws Exception;

    public <P extends Object> Query createHql(String hql, boolean isNamedSql, P values) throws Exception;

    public <P extends Object> Query createHql(String hql, P values) throws Exception;

    public <P extends Object> Pagination findByHqlWithPage(int pageNo, int pageSize, String hql, boolean isNamedSql, QueryEvent event, P values) throws Exception;

    public <P extends Object> Pagination findByHqlWithPage(int pageNo, int pageSize, String hql, boolean isNamedSql, P values) throws Exception;

    public <P extends Object> SQLQuery createSql(String sql, boolean isNamedSql, P values) throws Exception;

    public <P extends Object> SQLQuery createSql(String sql, P values) throws Exception;

    public <P extends Object> Pagination findBySqlWithPage(int pageNo, int pageSize, String sql, boolean isNamedSql, QueryEvent event, P values) throws Exception;

    public <P extends Object> Pagination findBySqlWithPage(int pageNo, int pageSize, String sql, boolean isNamedSql, P values) throws Exception;

    public Date getOffsetWithCurDateBySql(int value, String OffsetUnit) throws Exception;

    public Date getNow() throws Exception;

    public int randomInt(int min, int max) throws Exception;

    public int randomInt() throws Exception;

    public String selectActivityId(String prefix) throws Exception;

    public SessionFactory getSessionFactory() throws Exception;

    public Session getSession() throws Exception;

    public Connection getConnection() throws Exception;
}
