package com.microsoft.msdn.util.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ��̬���Դ
 * User: WUXD
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSource();
    }
}
