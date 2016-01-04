package com.microsoft.msdn.action;

import com.microsoft.msdn.util.action.BaseActionImpl;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Struts2注解包：org.apache.struts2.convention.annotation.* (struts2-convention-plugin-*.jar)
 * Struts2验证注解包：com.opensymphony.xwork2.validator.annotations.* (xwork-core-*.jar)
 * Struts2-Spring结合包：struts2-spring-plugin-*.jar
 * 加入Spring结合包后，@Action的className为Spring Bean id
 */
@Controller("testAction")
@ParentPackage("msdn")
@Namespace("/me")
public class TestAction extends BaseActionImpl {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Action(value = "index", className = "testAction", results = {
            @Result(name = "success", location = "/index.jsp")
    })
    public String toIndex() {
        this.msg = "hello,struts2";

        return SUCCESS;
    }
}
