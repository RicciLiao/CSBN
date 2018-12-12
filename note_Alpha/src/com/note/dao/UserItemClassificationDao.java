package com.note.dao;

import com.note.entity.UserItemClassification;

import java.util.List;

public interface UserItemClassificationDao extends HibernateBaseDao{
    List<UserItemClassification> loadItemClasses(Integer userId);
}
