package com.microsoft.msdn.action;

import com.microsoft.msdn.util.action.BaseActionImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SpringMVC注解包：org.springframework.web.bind.annotation(spring-web-*.jar)
 */
@Component
@RequestMapping("/me")
public class TestController extends BaseActionImpl {

    @RequestMapping("/index")
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello,world");
        return "/index.jsp";
    }
}
