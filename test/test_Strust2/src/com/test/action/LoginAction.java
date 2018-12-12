package com.test.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginAction extends ActionSupport {
    public String login(){
        System.out.println("LoginAction -- > login()");
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        String userName = request.getParameter("userName");
        session.put("userName", userName);
        return SUCCESS;
    }
    public String init(){
        System.out.println("LoginAction -- > init()");
        return SUCCESS;
    }
}
