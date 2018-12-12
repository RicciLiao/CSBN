package com.test.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyInterceptor extends AbstractInterceptor {
    private String Msg;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String intercept(ActionInvocation invocation){
        try {
            System.out.println("MyInterceptor -- > intercept()");
            ActionContext actionContext = ActionContext.getContext();
            Map<String, Object> session = actionContext.getSession();
            if(session.get("userName") != null){
                Msg = invocation.invoke();
                System.out.println(Msg);
            } else {
                Msg = "error";
                HttpServletRequest request = ServletActionContext.getRequest();
                request.setAttribute("Msg", "error from MyInterceptor");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            return Msg;
        }
    }
}
