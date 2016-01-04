package com.microsoft.msdn.util.database.hibernate;


import org.hibernate.Query;
import org.hibernate.Session;

public interface QueryEvent {
    public void beforeCountQuery(Query query, Session session);

    public void afterCountQuery(Query query, Session session);

    public void beforeListQuery(Query query, Session session);

    public void afterListQuery(Query query, Session session);
}
