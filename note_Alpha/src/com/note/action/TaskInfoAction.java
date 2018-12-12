package com.note.action;

import com.note.biz.TaskInfoBiz;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings("serial")
@Transactional
public class TaskInfoAction extends ActionSupport  implements ServletRequestAware, SessionAware {
    private TaskInfoBiz taskInfoBiz;
    private String ajaxResult;
    HttpServletRequest request;
    Map<String, Object> session;

    public String getAjaxResult() {
        return ajaxResult;
    }

    public void setAjaxResult(String ajaxResult) {
        this.ajaxResult = ajaxResult;
    }

    public void setTaskInfoBiz(TaskInfoBiz taskInfoBiz) {
        this.taskInfoBiz = taskInfoBiz;
    }




    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
