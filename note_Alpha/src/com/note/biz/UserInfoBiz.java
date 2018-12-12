package com.note.biz;

import com.note.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**************************************************************************
 * MODIFICATION HISTORY
 * Name             Date                     Description
 * ==========  ==============  =======================================
 * Ricci               30-Apr-2018       Initial Version
 **************************************************************************/

public interface UserInfoBiz {
    Map<String, Object> login(UserInfo userInfo);
    Map<String, String> signUp(UserInfo userInfo);
    UserInfo findByName(String userName);
    boolean createUser(UserInfo userInfo);
    boolean createUserSpace(String userPath);
}
