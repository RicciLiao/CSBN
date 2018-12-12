package com.note.action;

import com.note.biz.UserItemTagBiz;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.entity.UserInfo;
import com.note.entity.UserItemTag;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@Transactional
public class UserItemTagAction extends ActionSupport implements ServletRequestAware, SessionAware {

    UserItemTag userItemTag;
    UserItemTagBiz userItemTagBiz;
    private String ajaxResult;
    HttpServletRequest request;
    Map<String, Object> session;
    private String result = Constants.SUCCESS;


    public String getAjaxResult() {
        return ajaxResult;
    }

    public void setAjaxResult(String ajaxResult) {
        this.ajaxResult = ajaxResult;
    }

    public void setUserItemTag(UserItemTag userItemTag) {
        this.userItemTag = userItemTag;
    }

    public void setUserItemTagBiz(UserItemTagBiz userItemTagBiz) {
        this.userItemTagBiz = userItemTagBiz;
    }

    public String loadItemTags(){
        Integer userId = getBySession().getId();
        Map map = new HashMap();
        try {
            map.put("itemTags", userItemTagBiz.loadItemTags(userId));
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private UserInfo getBySession() {
        String sid = "";
        UserInfo userInfo = null;
        try {
            sid = request.getSession().getId();
            userInfo = (UserInfo) session.get(sid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userInfo;
        }
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
