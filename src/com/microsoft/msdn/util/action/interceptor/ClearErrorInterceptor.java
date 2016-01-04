package com.microsoft.msdn.util.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-23
 * Time: ����6:16
 * To change this template use File | Settings | File Templates.
 */
public class ClearErrorInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionSupport support = (ActionSupport) actionInvocation.getAction();
        support.clearActionErrors();
        support.clearFieldErrors();
        return actionInvocation.invoke();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
