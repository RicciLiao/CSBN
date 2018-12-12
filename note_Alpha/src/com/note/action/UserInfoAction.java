package com.note.action;

/**************************************************************************
 * MODIFICATION HISTORY
 * Name             Date                     Description
 * ==========  ==============  =======================================
 * Ricci               30-Apr-2018       Initial Version
 **************************************************************************/

import com.note.biz.UserInfoBiz;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.entity.UserInfo;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("serial")
@Transactional
public class UserInfoAction extends ActionSupport implements ServletRequestAware, SessionAware {

    private UserInfo userInfo;
    private UserInfoBiz userInfoBiz;
    private String ajaxResult;
    HttpServletRequest request;
    Map<String, Object> session;

    // getter and setter
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getAjaxResult() {
        return ajaxResult;
    }

    public void setAjaxResult(String ajaxResult) {
        this.ajaxResult = ajaxResult;
    }

    // 依赖注入 UserInfoBiz
    public void setUserInfoBiz(UserInfoBiz userInfoBiz) {
        this.userInfoBiz = userInfoBiz;
    }

    public String login() {
        Map<String, Object> map = null;
        try {
            UserInfo unUserInfo = new UserInfo();
            unUserInfo.setUserName(request.getParameter("userName"));
            unUserInfo.setUserPassword(request.getParameter("userPassword"));
            map = userInfoBiz.login(unUserInfo);
            if (map.get(Constants.SUCCESS) != null) {
                String sid = request.getSession().getId();
                session.put(sid, map.get(Constants.SUCCESS));
                map = new HashMap<>();
                map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
                map.put(Constants.J_SESSION_ID, sid);
                ajaxResult = Common.mapToJson(map);
            } else if (map.get(Constants.AJAX_COMMON_RESULT) != null) {
                ajaxResult = Common.mapToJson(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Constants.SUCCESS;
        }
    }

    public String loginSuccess(){
        String sid = request.getParameter(Constants.J_SESSION_ID);
        if(session.get(sid) != null){
            return Constants.SUCCESS;
        } else {
            return Constants.ERROR;
        }
    }

    public String signUp() {
        String result = "";
        boolean flag = true;
        Map<String, String> map = null;
        try {
            UserInfo unUserInfo = new UserInfo();
            unUserInfo.setUserEmail(request.getParameter("userEmail"));
            unUserInfo.setUserPhone(request.getParameter("userPhone"));
            unUserInfo.setUserName(request.getParameter("userName"));
            unUserInfo.setUserPassword(request.getParameter("userPassword"));
            unUserInfo.setUserRePassword(request.getParameter("rePassword"));
            map = userInfoBiz.signUp(unUserInfo);
            for (String key : map.keySet()) {
                if (map.get(key).equals(Constants.SUCCESS)) {
                    continue;
                } else {
                    ajaxResult = Common.mapToJson(map);
                    flag = false;
                    break;
                }
            }
            result = flag ? Constants.SUCCESS : Constants.ERROR;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String createUser() {
        String result = Constants.SUCCESS;
        UserInfo newUserInfo = new UserInfo();
        Map<String, String> map = new HashMap<>();
        try {
            newUserInfo.setUserEmail(request.getParameter("userEmail"));
            newUserInfo.setUserPhone(request.getParameter("userPhone"));
            newUserInfo.setUserName(request.getParameter("userName"));
            newUserInfo.setUserPassword(request.getParameter("userPassword"));
            if (userInfoBiz.createUser(newUserInfo)) {
                request.setAttribute("userPath", newUserInfo.getUserPath());
                map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
            } else {
                map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
                ajaxResult = Common.mapToJson(map);
            }
        } catch (Exception e) {
            map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            ajaxResult = Common.mapToJson(map);
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String createUserSpace() {
        String result = "";
        String userPath = "";
        Map<String, String> map = null;
        try {
            userPath = Common.convertToString(request.getAttribute("userPath"));
            if (!Common.isNullOrSpace(userPath)) {
                if (userInfoBiz.createUserSpace(userPath)) {
                    map = new HashMap<>();
                    map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
                    ajaxResult = Common.mapToJson(map);
                    result = Constants.SUCCESS;
                } else {
                    result = Constants.ERROR;
                    // pending
                }
            } else {
                result = Constants.ERROR;
                // pending
            }
        } catch (Exception e) {
            result = Constants.ERROR;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String checkByUserName() {
        Map<String, String> map = null;
        try {
            if (request.getParameter("regName") != null) {
                map = new HashMap<>();
                UserInfo userInfo = userInfoBiz.findByName(request.getParameter("regName"));
                if (userInfo != null) {
                    map.put(Constants.NAME_INFO, Constants.EXISTED);
                } else {
                    map.put(Constants.NAME_INFO, Constants.SUCCESS);
                }
                ajaxResult = Common.mapToJson(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Constants.SUCCESS;
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
