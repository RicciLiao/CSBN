package com.note.biz.impl;

import com.note.biz.ItemClassInfoBiz;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.dao.ItemClassInfoDao;
import com.note.entity.ItemClassInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Transactional
public class ItemClassInfoBizImpl implements ItemClassInfoBiz {

    ItemClassInfoDao itemClassInfoDao;

    public void setItemClassInfoDao(ItemClassInfoDao itemClassInfoDao) {
        this.itemClassInfoDao = itemClassInfoDao;
    }

    @Override
    public Map<String, String> loadItemClass(String itemGuid) {
        Map<String, String> map = new HashMap<>();
        ItemClassInfo itemClassInfo = null;
        try {
            itemClassInfo =itemClassInfoDao.findByItemId(itemGuid);
            if(itemClassInfo == null){
                map.put(Constants.ITEM_CLASS, " ");
            } else {
                map.put(Constants.ITEM_CLASS, Common.convertToString(itemClassInfo.getClassId()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }
}
