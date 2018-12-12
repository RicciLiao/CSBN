package com.note.dao;

import com.note.entity.UserItemTag;

import java.util.List;

public interface UserItemTagDao extends  HibernateBaseDao{
    List<UserItemTag> loadItemTags(Integer userId);
}
