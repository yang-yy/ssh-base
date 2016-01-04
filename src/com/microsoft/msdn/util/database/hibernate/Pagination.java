package com.microsoft.msdn.util.database.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-12
 * Time: ����2:08
 * To change this template use File | Settings | File Templates.
 */
public class Pagination {
    public static final int PAGE_PREV_COUNT = 3;

    public final int[] pageSizes = new int[]{20, 30, 40, 50, 60, 70, 80, 90, 100};

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int currPage;

    /**
     * 分页记录
     */
    private List resultList;

    private Query listQuery;

    private Query countQuery;

    private Session session;

    private QueryEvent event;

    public int getFirstIndex() {
        if ((currPage <= 0) || (pageSize <= 0)) return -1;
        return pageSize * (currPage - 1);
    }

    public int getPageCount() {
        int pageCount = -1;
        if ((totalCount < 0) || (pageSize <= 0))
            pageCount = -1;
        else {
            pageCount = totalCount / pageSize;
            if (totalCount % pageSize != 0) pageCount++;
            if (totalCount == 0) pageCount = 0;
        }
        return pageCount;
    }

    public Pagination list() {
        if (event != null) event.beforeCountQuery(countQuery, session);
        Object cnt = null;
        List cntList = countQuery.list();
        if (cntList == null || cntList.size() == 0)
            cnt = 0;
        else
            cnt = cntList.get(0);
        if (cnt instanceof Long)
            this.setTotalCount(((Long) cnt).intValue());
        else if (cnt instanceof Integer)
            this.setTotalCount((Integer) cnt);
        if (event != null) event.afterCountQuery(countQuery, session);

        if (event != null) event.beforeListQuery(listQuery, session);
        this.setResultList(listQuery.list());
        if (event != null) event.afterListQuery(listQuery, session);
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

    public Query getListQuery() {
        return listQuery;
    }

    public Query getCountQuery() {
        return countQuery;
    }

    public Session getSession() {
        return session;
    }

    public QueryEvent getEvent() {
        return event;
    }

    public int[] getPageSizes() {
        return pageSizes;
    }
}
