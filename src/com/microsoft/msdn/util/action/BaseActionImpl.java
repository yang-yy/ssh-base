package com.microsoft.msdn.util.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class BaseActionImpl extends ActionSupport implements BaseAction,
        ServletResponseAware, ServletRequestAware, SessionAware,
        ApplicationAware {
    /**
     *
     */
    private static final long serialVersionUID = 2713542632203593406L;

    protected String ajaxErrorMessage;

    @Autowired
    @Qualifier("threadPool")
    protected ThreadPoolExecutor threadPool;

    protected String AJAXSUCCESS = "ajaxsuccess";

    protected String AJAXERROR = "ajaxerror";

    protected String AJAXFILETYPEERROR = "ajaxFileTypeError";

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected Map<String, Object> session;

    protected Map<String, Object> application;

    protected void ajaxError(Throwable e) {
        e.printStackTrace();
        String stack = printException(e);
        this.setAjaxErrorMessage(stack);
        this.addActionError(stack);
    }

    protected void actionError(Throwable e) {
        e.printStackTrace();
        this.addActionError(printException(e));
    }

    private String printException(Throwable e) {
        StringBuffer sql = new StringBuffer();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public String maskNumber(String number) {
        if (number == null)
            return number;

        char[] chars = number.trim().toCharArray();
        if (chars.length < 7)
            return number.trim();
        for (int i = 3; i <= 6; i++)
            chars[i] = '*';
        return new String(chars);
    }

    public String maskEMail(String mail) {
        if (mail == null)
            return mail;
        mail = StringUtils.trimToEmpty(mail);
        if (mail.indexOf("@") == -1) {
            char[] chars = mail.toCharArray();
            for (int i = chars.length - 1; i >= 1; i--)
                chars[i] = '*';
            return new String(chars);
        } else {
            String domain = mail.substring(mail.indexOf("@") + 1);
            String name = mail.substring(0, mail.indexOf("@"));
            return StringUtils
                    .join(new Object[]{maskEMail(name), "@", domain});
        }
    }

    public String getAjaxErrorMessage() {
        return ajaxErrorMessage;
    }

    public void setAjaxErrorMessage(String ajaxErrorMessage) {
        this.ajaxErrorMessage = ajaxErrorMessage;
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public ServletContext getApplication() {
        return ServletActionContext.getServletContext();
    }

    public PageContext getPageContext() {
        return ServletActionContext.getPageContext();
    }

    @Override
    public void setServletRequest(HttpServletRequest arg0) {
        this.request = arg0;
    }

    @Override
    public void setServletResponse(HttpServletResponse arg0) {
        this.response = arg0;
    }

    @Override
    public void setSession(Map<String, Object> arg0) {
        this.session = arg0;
    }

    @Override
    public void setApplication(Map<String, Object> arg0) {
        this.application = arg0;
    }
}
