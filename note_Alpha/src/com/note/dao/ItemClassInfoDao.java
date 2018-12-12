package com.note.dao;

import com.note.entity.ItemClassInfo;

public interface ItemClassInfoDao extends HibernateBaseDao{
    ItemClassInfo findByItemId(String itemGuid);
}
