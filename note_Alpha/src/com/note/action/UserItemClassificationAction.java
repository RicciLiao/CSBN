package com.note.action;

import com.note.biz.ItemClassInfoBiz;
import com.note.biz.UserItemClassificationBiz;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.entity.ItemClassInfo;
import com.note.entity.UserInfo;
import com.note.entity.UserItemClassification;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Transactional
public class UserItemClassificationAction extends ActionSupport implements ServletRequestAware, SessionAware {
    private String ajaxResult;
    HttpServletRequest request;
    Map<String, Object> session;
    UserItemClassification userItemClassification;
    UserItemClassificationBiz userItemClassificationBiz;
    private String result = Constants.SUCCESS;

    public String getAjaxResult() {
        return ajaxResult;
    }

    public void setAjaxResult(String ajaxResult) {
        this.ajaxResult = ajaxResult;
    }

    public void setUserItemClassification(UserItemClassification userItemClassification) {
        this.userItemClassification = userItemClassification;
    }

    public void setUserItemClassificationBiz(UserItemClassificationBiz userItemClassificationBiz) {
        this.userItemClassificationBiz = userItemClassificationBiz;
    }

    public String loadItemClasses() {
        Integer userId = null;
        ItemClassInfo itemClassInfo = null;
        Map<String, String> map = new HashMap<>();

        try {
            userId = getBySession().getId();
            if (request.getAttribute(Constants.ITEM_CLASS) != null) {
                map.putAll((Map)request.getAttribute(Constants.ITEM_CLASS));
            }
            map.putAll(userItemClassificationBiz.loadItemClasses(userId));
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
