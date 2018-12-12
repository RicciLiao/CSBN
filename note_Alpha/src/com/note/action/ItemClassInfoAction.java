package com.note.action;

import com.note.biz.ItemClassInfoBiz;
import com.note.common.Constants;
import com.note.entity.ItemClassInfo;
import com.note.entity.UserInfo;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Transactional
public class ItemClassInfoAction extends ActionSupport implements ServletRequestAware, SessionAware {
    private String ajaxResult;
    HttpServletRequest request;
    Map<String, Object> session;
    ItemClassInfoBiz itemClassInfoBiz;
    private String result = Constants.SUCCESS;
    ItemClassInfo itemClassInfo;

    public void setItemClassInfoBiz(ItemClassInfoBiz itemClassInfoBiz) {
        this.itemClassInfoBiz = itemClassInfoBiz;
    }

    public String getAjaxResult() {
        return ajaxResult;
    }

    public void setAjaxResult(String ajaxResult) {
        this.ajaxResult = ajaxResult;
    }

    public void setItemClassInfo(ItemClassInfo itemClassInfo) {
        this.itemClassInfo = itemClassInfo;
    }

    public String loadItemClass() {
        String itemGuid = "";
        Map<String, String> map = null;
        try {
            itemGuid = request.getParameter("itemGuid");
            map = itemClassInfoBiz.loadItemClass(itemGuid);
            request.setAttribute(Constants.ITEM_CLASS, map);
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
