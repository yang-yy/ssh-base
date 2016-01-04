package com.microsoft.msdn.util.database.hibernate;

import org.hibernate.*;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DAO������
 */
public class BasicHibernateDaoImpl<T extends Serializable, PK extends Serializable> implements BasicHibernateDao<T, PK> {
    public String emptyString = "";

    private HibernateTemplate template;

    private Class<T> persistentClass;

    public BasicHibernateDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T get(PK id) throws Exception {
        return this.getTemplate().get(persistentClass, id);
    }

    @Override
    public T getWithLock(PK id, LockMode lockMode) throws Exception {
        T t = this.getTemplate().get(persistentClass, id, lockMode);
        if (t != null) this.flush();
        return t;
    }

    @Override
    public T load(PK id) throws Exception {
        return this.getTemplate().load(persistentClass, id);
    }

    @Override
    public T loadWithLock(PK id, LockMode lockMode) throws Exception {
        T t = this.getTemplate().load(persistentClass, id, lockMode);
        if (t != null)
            this.flush();
        return t;
    }

    @Override
    public List<T> loadAll() throws Exception {
        return this.getTemplate().loadAll(persistentClass);
    }

    @Override
    public void update(T entity) throws Exception {
        this.getTemplate().update(entity);
    }

    @Override
    public void updateWithLock(T entity, LockMode lockMode) throws Exception {
        this.getTemplate().update(entity, lockMode);
        this.flush();
    }

    @Override
    public void save(T entity) throws Exception {
        this.getTemplate().save(entity);
    }

    @Override
    public void saveOrUpdate(T entity) throws Exception {
        this.getTemplate().saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection<T> entities) throws Exception {
        //this.getTemplate().saveOrUpdateAll(entities);
    }

    @Override
    public void delete(T entity) throws Exception {
        this.getTemplate().delete(entity);
    }

    @Override
    public void deleteWithLock(T entity, LockMode lockMode) throws Exception {
        this.getTemplate().delete(entity, lockMode);
        this.flush();
    }

    @Override
    public void deleteByKey(PK id) throws Exception {
        this.getTemplate().delete(this.load(id));
    }

    @Override
    public void deleteByKeyWithLock(PK id, LockMode lockMode) throws Exception {
        this.deleteWithLock(this.load(id), lockMode);
    }

    @Override
    public void deleteAll(Collection<T> entities) throws Exception {
        this.getTemplate().deleteAll(entities);
    }

    @Override
    public int updateByHql(String queryString) throws Exception {
        Assert.notNull(queryString);
        return this.getTemplate().bulkUpdate(queryString);
    }

    @Override
    public int updateByHql(String queryString, Collection<Object> values) throws Exception {
        Assert.notNull(queryString);
        return this.getTemplate().bulkUpdate(queryString, values.toArray());
    }


    @Override
    public <P extends Object> Query createHql(String hql, boolean isNamedSql, P values) throws Exception {
        Assert.notNull(hql);
        Query query = null;
        query = isNamedSql ? this.getSession().getNamedQuery(hql) : this.getSession().createQuery(hql);

        if (values == null)
            return query;
        else {
            if (values instanceof Collection) {
                Collection collection = (Collection) values;
                Iterator iterator = collection.iterator();
                int index = 0;
                while (iterator.hasNext())
                    query.setParameter(index++, iterator.next());
            } else if (values instanceof Map)
                query.setProperties((Map) values);
            else if (values instanceof Object[]) {
                Object[] objects = (Object[]) values;
                for (int index = 0; index < objects.length; index++)
                    query.setParameter(index, objects[index]);
            } else
                query.setProperties(values);
        }
        return query;
    }

    @Override
    public <P> Query createHql(String hql, P values) throws Exception {
        return this.createHql(hql, false, values);
    }

    @Override
    public <P> Pagination findByHqlWithPage(int pageNo, int pageSize, String hql, boolean isNamedSql, QueryEvent event, P values) throws Exception {
        Assert.notNull(hql);
        Pagination pagination = new Pagination();
        Query query = null;
        Session session = getSession();

        { //取总记录
            query = createHql(getRowCountHql(hql), isNamedSql, values);
            pagination.setPageSize(pageSize);
            pagination.setCurrPage(pageNo);
            updatePaginationProperty(pagination, "countQuery", query);
            updatePaginationProperty(pagination, "session", session);
            updatePaginationProperty(pagination, "event", event);
        }

        { //取列表
            query = this.createHql(hql, isNamedSql, values);
            query.setFirstResult(pagination.getFirstIndex());
            query.setMaxResults(pagination.getPageSize());
            updatePaginationProperty(pagination, "listQuery", query);
        }
        return pagination;
    }

    @Override
    public <P extends Object> Pagination findByHqlWithPage(int pageNo, int pageSize, String hql, boolean isNamedSql, P values) throws Exception {
        return findByHqlWithPage(pageNo, pageSize, hql, isNamedSql, null, values);
    }

    @Override
    public <P extends Object> SQLQuery createSql(String sql, boolean isNamedSql, P values) throws Exception {
        Assert.notNull(sql);
        SQLQuery query = null;
        query = isNamedSql ? (SQLQuery) this.getSession().getNamedQuery(sql) : this.getSession().createSQLQuery(sql);
        if (values == null)
            return query;
        else {
            if (values instanceof Collection) {
                Collection collection = (Collection) values;
                Iterator iterator = collection.iterator();
                int index = 0;
                while (iterator.hasNext())
                    query.setParameter(index++, iterator.next());
            } else if (values instanceof Map)
                query.setProperties((Map) values);
            else if (values instanceof Object[]) {
                Object[] objects = (Object[]) values;
                for (int index = 0; index < objects.length; index++)
                    query.setParameter(index, objects[index]);
            } else
                query.setProperties(values);
        }
        return query;
    }

    @Override
    public <P> SQLQuery createSql(String sql, P values) throws Exception {
        return this.createSql(sql, false, values);
    }

    @Override
    public <P> Pagination findBySqlWithPage(int pageNo, int pageSize, String sql, boolean isNamedSql, QueryEvent event, P values) throws Exception {
        Assert.notNull(sql);
        Pagination pagination = new Pagination();
        SQLQuery query = null;
        Session session = getSession();

        { //取总记录数
            query = createSql(getRowCountSql(sql), isNamedSql, values);
            query = query.addScalar("cnt", StandardBasicTypes.INTEGER);
            pagination.setPageSize(pageSize);
            pagination.setCurrPage(pageNo);
            updatePaginationProperty(pagination, "countQuery", query);
            updatePaginationProperty(pagination, "session", session);
            updatePaginationProperty(pagination, "event", event);
        }

        if (sql.indexOf("limit") == -1) {
            if ((pagination.getFirstIndex() < 0) && pageSize < 0)
                query = createSql(sql, isNamedSql, values);
            else if ((pagination.getFirstIndex() < 0) && pageSize >= 0) {
                query = createSql(sql + " limit :pageSize", isNamedSql, values);
                query.setInteger("pageSize", pageSize);
            } else if ((pagination.getFirstIndex() >= 0) && pageSize < 0) {
                query = createSql(sql + " limit :offset,:pageSize", isNamedSql, values);
                query.setInteger("offset", pagination.getFirstIndex());
                query.setInteger("pageSize", 1);
            } else {
                query = createSql(sql + " limit :offset,:pageSize", isNamedSql, values);
                query.setInteger("offset", pagination.getFirstIndex());
                query.setInteger("pageSize", pagination.getPageSize());
            }
        } else
            query = createSql(sql, isNamedSql, values);
        updatePaginationProperty(pagination, "listQuery", query);
        return pagination;
    }

    @Override
    public <P extends Object> Pagination findBySqlWithPage(int pageNo, int pageSize, String sql, boolean isNamedSql, P values) throws Exception {
        return findBySqlWithPage(pageNo, pageSize, sql, isNamedSql, null, values);
    }

    public Date getOffsetWithCurDateBySql(int value, String OffsetUnit) throws Exception {
        Assert.notNull(OffsetUnit);
        String sql = "SELECT ADDDATE(CURDATE(),INTERVAL " + value + " " + OffsetUnit + ") as day from dual";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.addScalar("day", StandardBasicTypes.DATE);
        return (Date) query.list().get(0);

    }

    @Override
    public Date getNow() throws Exception {
        String sql = "SELECT NOW() as day from dual";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.addScalar("day", StandardBasicTypes.TIMESTAMP);
        return (Timestamp) query.list().get(0);
    }

    @Override
    public int randomInt(int min, int max) throws Exception {
        double d = Math.random();
        if (max < min) {
            min = min ^ max;
            max = min ^ max;
            min = min ^ max;
        } else if (max == min)
            max++;
        int result = (int) (d * (max - min) + min);
        return result;
    }

    @Override
    public int randomInt() throws Exception {
        return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public String selectActivityId(String prefix) throws Exception {
        String rtn = prefix;
        Random r = new Random();
        rtn += "_" + String.valueOf(System.currentTimeMillis());
        rtn += "_" + r.nextInt(10000);
        return rtn;
    }

    public void flush() throws Exception {
        this.getTemplate().flush();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    @Override
    public final SessionFactory getSessionFactory() throws Exception {
        return this.getTemplate().getSessionFactory();
    }

    @Override
    public final Session getSession() throws Exception {
        return this.getTemplate().getSessionFactory().getCurrentSession();
    }

    public final Connection getConnection() throws Exception {
        return SessionFactoryUtils.getDataSource(this.getSessionFactory()).getConnection();
    }

    private int findInHql(String hql, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(hql);
        while (m.find())
            return m.start();
        return -1;
    }

    private String getRowCountSql(String sql) {
        return "select count(*) cnt from (\n" + sql + ")\n a";
    }

    private String getRowCountHql(String hql) {
        hql = " " + hql;
        int fromIndex = findInHql(hql, "(?i)\\s+from\\s+\\S*");
        String projectionHql = hql.substring(0, fromIndex);

        hql = hql.substring(fromIndex);
        String rowCountHql = hql.replace("fetch", "");

        int index = rowCountHql.indexOf("order by");
        if (index > 0) {
            rowCountHql = rowCountHql.substring(0, index);
        }

        if (projectionHql.indexOf("select") == -1) {
            return "select count(*)" + rowCountHql;
        } else {
            return projectionHql.replace("select", "select count(") + ") " + rowCountHql;
        }
    }

    private void updatePaginationProperty(Pagination p, String propertyName, Object value) throws Exception {
        Class clazz = p.getClass();
        Field field = clazz.getDeclaredField(propertyName);
        if (field == null) return;
        field.setAccessible(true);
        field.set(p, value);
    }

}
