package com.note.dao;

import com.note.entity.UserInfo;

import java.util.List;

/**************************************************************************
 * MODIFICATION HISTORY
 * Name             Date                     Description
 * ==========  ==============  =======================================
 * Ricci               30-Apr-2018       Initial Version
 **************************************************************************/

public interface UserInfoDao extends HibernateBaseDao{
    UserInfo findByName(String userName);
    //boolean create(UserInfo userInfo);
}
