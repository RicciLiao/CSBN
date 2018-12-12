package com.test.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyInterceptor1 extends AbstractInterceptor {
    private String Msg;

    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        System.out.println(actionInvocation.getAction());
        if(request.getMethod().equals("post")){
            return actionInvocation.invoke();
        } else {
            ActionContext actionContext = ActionContext.getContext();
            Map<String, Object> session = actionContext.getSession();
            if(session.get("userName") != null){
                return actionInvocation.invoke();
            } else {
                request.setAttribute("Msg", "error from MyInterceptor1");return "error";
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
