package com.note.biz;

import com.note.entity.ItemInfo;

import java.util.List;
import java.util.Map;

public interface ItemInfoBiz {
    Map<String, String> create(ItemInfo itemInfo);
    Map loadItem(String path, Integer loadType, ItemInfo itemInfo);
    ItemInfo findByGuid(String guid);
    Map loadItemPath(String guid);
    Map<String, String> renameItem(String guid, String name);
    Map<String, String> getItemSizes(String path);
    Map<String, String> saveTxt(String newItemName, String txtContent, ItemInfo itemInfo);
    Map<String, String> itemEnSave(ItemInfo itemInfo);
    Map<String, String> checkItemPsw(String unItemPsw, String itemGuid);
    Map downloadItem(ItemInfo itemInfo);
    ItemInfo findDescByGuid(String itemGuid);
    Map itemSearch(String itemContent, String itemCreateDate, String itemModifyDate, String itemClass, String itemTag, String itemRange, Integer userId);
}
