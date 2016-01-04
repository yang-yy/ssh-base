package com.microsoft.msdn.util.database;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class DataSourceExchange implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        DataSource annotation = method.getAnnotation(DataSource.class);
        if (annotation == null)
            DataSourceHolder.setDataSource(DataSource.master);
        else
            DataSourceHolder.setDataSource(annotation.name());
        Object rtn = methodInvocation.proceed();
        return rtn;
    }
}
