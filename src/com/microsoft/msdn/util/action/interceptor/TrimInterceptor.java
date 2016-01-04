package com.microsoft.msdn.util.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TrimInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
        Set entrySet = parameters.entrySet();
        String[] strings = null;
        String[] values = null;
        int strLength = 0;
        for (Iterator it = entrySet.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String[]) {
                values = (String[]) value;
                strLength = values.length;
                strings = new String[strLength];
                for (int i = 0; i < strLength; i++)
                    strings[i] = values[i].trim();

                parameters.put((String) key, strings);
            }
        }

        invocation.getInvocationContext().setParameters(parameters);
        return invocation.invoke();
    }
}
