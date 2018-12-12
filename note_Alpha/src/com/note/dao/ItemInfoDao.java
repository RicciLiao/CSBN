package com.note.dao;

import com.note.entity.ItemInfo;

import java.util.List;

public interface ItemInfoDao extends HibernateBaseDao{
    List<ItemInfo> findByItemPath(String itemPath);
    ItemInfo findByGuid(String guid);
    ItemInfo findDescByGuid(String guid);
    List<ItemInfo> itemSearch(ItemInfo searchCondition);
}
