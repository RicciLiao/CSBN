package com.note.biz.impl;

import com.note.biz.ItemInfoBiz;
import com.note.common.AESUtil;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.common.MD5Util;
import com.note.dao.ItemInfoDao;
import com.note.entity.ItemInfo;
import com.sun.xml.internal.ws.api.message.HeaderList;
import org.apache.struts2.ServletActionContext;
import org.jgroups.protocols.FILE_PING;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Transactional
public class ItemInfoBizImpl implements ItemInfoBiz {
    ItemInfoDao itemInfoDao;

    public void setItemInfoDao(ItemInfoDao itemInfoDao) {
        this.itemInfoDao = itemInfoDao;
    }

    @Override
    public Map<String, String> create(ItemInfo itemInfo) {
        File file = null;
        boolean flag = false;
        Map<String, String> map = new HashMap<>();
        String itemRealPath = itemInfo.getItemPath() + Constants.ITEM_PATH_REX + itemInfo.getItemGuid();
        try {
            file = new File(itemRealPath);
            if (itemInfo.getItemType() == Constants.ITEM_TYPE_DIR) {
                if (file.mkdir()) {
                    flag = true;
                }
            } else if (itemInfo.getItemType() == Constants.ITEM_TYPE_TXT) {
                if (file.createNewFile()) {
                    flag = true;
                }
            }
            if (flag) {
                if (itemInfoDao.create(itemInfo)) {
                    map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
                } else {
                    Common.deleteItem(itemRealPath);
                    map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
                }
            } else {
                map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public Map loadItem(String path, Integer loadType, ItemInfo itemInfo) {
        Map map = null;
        List<ItemInfo> listItemInfo = null;
        try {
            if (loadType.equals(Constants.ITEM_TYPE_DIR)) {
                listItemInfo = itemInfoDao.findByItemPath(path);
                map = buildItemInfo(listItemInfo);
                map.put("loadType", Constants.ITEM_TYPE_DIR);
            } else if (loadType.equals(Constants.ITEM_TYPE_TXT)) {
                map = buildItemContent(itemInfo);
                map.put("loadType", Constants.ITEM_TYPE_TXT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public ItemInfo findByGuid(String guid) {
        ItemInfo itemInfo = null;
        try {
            itemInfo = itemInfoDao.findByGuid(guid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemInfo;
        }
    }

    @Override
    public Map loadItemPath(String guid) {
        ItemInfo itemInfo = null;
        Map map = null;
        try {
            itemInfo = findByGuid(guid);
            map = loadItem(itemInfo.getItemPath() + Constants.ITEM_PATH_REX + itemInfo.getItemGuid(), itemInfo.getItemType(), itemInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public Map<String, String> renameItem(String guid, String name) {
        ItemInfo itemInfo = null;
        Map<String, String> map = new HashMap<>();
        try {
            itemInfo = itemInfoDao.findByGuid(guid);
            itemInfo.setItemName(name);
            itemInfoDao.update(itemInfo);
            map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
        } catch (Exception e) {
            map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public Map<String, String> getItemSizes(String path) {
        File file = null;
        Map<String, String> map = new HashMap<>();
        try {
            file = new File(path);
            long itemSize = Common.getItemSize(file);
            String strItemSize = Common.FormatFileSize(itemSize);
            map.put("itemSize", String.valueOf(itemSize));
            map.put("strItemSize", strItemSize);
        } catch (Exception e) {
            map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public Map<String, String> saveTxt(String newItemName, String txtContent, ItemInfo itemInfo) {
        File tarFile = null;
        File bakFile = new File(Constants.SERVER_TEMP_BAK_PATH + itemInfo.getItemGuid());
        Map<String, String> map = new HashMap<>();
        try {
            tarFile = new File(itemInfo.getItemPath() + Constants.ITEM_PATH_REX + itemInfo.getItemGuid());
            if (Common.copyItem(tarFile, bakFile)) {
                // AES
                String strEnContent = AESUtil.AESEncode(Constants.SERVER_AES_KEY_DEF, txtContent);
                FileOutputStream fos = new FileOutputStream(tarFile);
                fos.write(strEnContent.getBytes());
                fos.close();
                itemInfo.setItemName(newItemName);
                itemInfo.setItemLastModifiedDate(Common.getCurrentDate());
                if (itemInfoDao.update(itemInfo)) {
                    Common.deleteItem(bakFile.getPath());
                    map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
                } else {
                    map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
                }
            } else {
                map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            }
        } catch (Exception e) {
            map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public Map<String, String> itemEnSave(ItemInfo itemInfo) {
        String itemPsw = null;
        String strMD5 = null;
        Map<String, String> map = new HashMap<>();
        try {
            itemPsw = itemInfo.getItemPassword();
            strMD5 = MD5Util.Encryption(itemPsw);
            itemInfo.setItemPassword(strMD5);
            itemInfoDao.update(itemInfo);
            map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
        } catch (Exception e) {
            map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public Map<String, String> checkItemPsw(String unItemPsw, String itemGuid) {
        String unMD5 = "";
        ItemInfo itemInfo = null;
        Map<String, String> map = new HashMap<>();
        try {
            unMD5 = MD5Util.Encryption(unItemPsw);
            itemInfo = findByGuid(itemGuid);
            if (unMD5.equals(itemInfo.getItemPassword())) {
                map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
            } else {
                map.put(Constants.AJAX_COMMON_RESULT, Constants.PASSWORD_ERR);
            }
        } catch (Exception e) {
            map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    @Override
    public Map downloadItem(ItemInfo itemInfo) {
        String c;
        File tarFile = null;
        File tempFile = null;
        InputStream is = null;
        FileOutputStream fos = null;
        InputStreamReader isr = null;
        Map map = new HashMap<>();
        StringBuffer enItemContent = new StringBuffer();
        try {
            tarFile = new File(itemInfo.getItemPath() + Constants.ITEM_PATH_REX + itemInfo.getItemGuid());
            tempFile = new File(Constants.SERVER_TEMP_DL_PATH + itemInfo.getItemGuid());
            if (tempFile.exists()) {
                Common.deleteItem(tempFile.getPath());
            }
            if (tempFile.createNewFile()) {
                isr = new InputStreamReader(new FileInputStream(tarFile), "gbk");
                BufferedReader rf = new BufferedReader(isr);
                while ((c = rf.readLine()) != null) {
                    enItemContent.append(c + Constants.ITEM_CONTENT_LINE_REX);
                }
                String dnItemContent = AESUtil.AESDncode(Constants.SERVER_AES_KEY_DEF, enItemContent.toString());
                String content = dnItemContent.replace("\n", "\r\n");
                fos = new FileOutputStream(tempFile);
                fos.write(content.getBytes());
                //is = ServletActionContext.getServletContext().getResourceAsStream(tempFile.getPath());
                is = new FileInputStream(tempFile.getPath());
                map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
                map.put(Constants.ITEM_CONTENT, is);
                isr.close();
                fos.flush();
                fos.close();
            } else {
                map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            }
        } catch (Exception e) {
            map.put(Constants.AJAX_COMMON_RESULT, Constants.ERROR);
            e.printStackTrace();
        } finally {
            return map;
        }

    }

    @Override
    public ItemInfo findDescByGuid(String itemGuid) {
        return itemInfoDao.findDescByGuid(itemGuid);
    }

    @Override
    public Map itemSearch(String itemContent, String itemCreateDate, String itemModifyDate, String itemClass, String itemTag, String itemRange, Integer userId) {
        ItemInfo searchCondition = new ItemInfo();
        String[] itemTags = itemTag.split(",");
        List<Integer> listTag = new ArrayList<>();
        List<ItemInfo> listItemInfo = null;
        List<ItemInfo> listResult = null;
        Map map = new HashMap();
        try {
            for (int i = 0; i < itemTags.length; i++) {
                if (!Common.isNullOrSpace(itemTags[i])) {
                    listTag.add(Common.convertToInteger(itemTags[i]));
                }
            }
            searchCondition.setUserId(userId);
            searchCondition.setItemCreateDate(Common.stringToDate(itemCreateDate));
            searchCondition.setItemLastModifiedDate(Common.stringToDate(itemModifyDate));
            searchCondition.setItemClass(Common.convertToInteger(itemClass));
            searchCondition.setItemTag(listTag);
            if (itemRange.equals("2")) {
                searchCondition.setItemName(itemContent);
            }
            listItemInfo = itemInfoDao.itemSearch(searchCondition);
            if (itemRange.equals("1") || itemRange.equals("3")) {
                listResult = new ArrayList<>();
                List<ItemInfo> list = itemContentSearch(listItemInfo, itemContent);
                if (itemRange.equals("1")) {
                    for (ItemInfo itemInfo : list) {
                        if (!Common.isNullOrSpace(itemInfo.getItemContent())) {
                            listResult.add(itemInfo);
                        }
                    }
                } else {
                    for (ItemInfo itemInfo : list) {
                        if (!Common.isNullOrSpace(itemInfo.getItemContent()) || itemInfo.getItemName().matches(".*" + itemContent + ".*")) {
                            listResult.add(itemInfo);
                        }
                    }
                }
            } else {
                listResult = listItemInfo;
            }
            map.put(Constants.AJAX_COMMON_RESULT, listResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }

    }

    private List<ItemInfo> itemContentSearch(List<ItemInfo> listItemInfo, String itemContentCondition) {
        Map map = null;
        String itemContent = "";
        StringBuffer itemMatchContent = null;
        Integer matchIndex = 0;
        List<ItemInfo> listResult = new ArrayList<>();
        try {
            for (ItemInfo itemInfo : listItemInfo) {
                map = buildItemContent(itemInfo);
                itemContent = Common.convertToString(map.get(Constants.ITEM_CONTENT));
                if (!Common.isNullOrSpace(itemContentCondition)) {
                    itemContent = itemContent.replace("\n", "");
                    if (itemContent.matches(".*" + itemContentCondition + ".*")) {
                        matchIndex = itemContent.indexOf(itemContentCondition);
                        itemMatchContent = new StringBuffer();
                        if (matchIndex >= 30) {
                            itemMatchContent.append(itemContent.substring(matchIndex - 30, matchIndex));
                        } else {
                            itemMatchContent.append(itemContent.substring(0, matchIndex));
                        }
                        itemMatchContent.append("<span class='item_search_result_sign'>");
                        itemMatchContent.append(itemContentCondition);
                        itemMatchContent.append("</span>");
                        if (itemContent.length() >= matchIndex + 31) {
                            itemMatchContent.append(itemContent.substring(matchIndex + itemContentCondition.length(), matchIndex + 31));
                        } else {
                            itemMatchContent.append(itemContent.substring(matchIndex + itemContentCondition.length(), itemContent.length()));
                        }
                        itemInfo.setItemContent(itemMatchContent.toString());
                    }
                }
                itemInfo.setItemCreateDateStr(Common.convertToString(map.get(Constants.ITEM_CREATE_DATE)));
                itemInfo.setItemLastModifiedDateStr(Common.convertToString(map.get(Constants.ITEM_LAST_MODIFY_DATE)));
                itemInfo.setItemSize(Common.convertToString(map.get(Constants.ITEM_SIZE)));
                listResult.add(itemInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return listResult;
        }
    }

    private Map buildItemInfo(List<ItemInfo> listItemInfo) {
        Map map = null;
        List<Map> listMap = new ArrayList<>();
        for (ItemInfo itemInfo : listItemInfo) {
            map = new HashMap();
            map.put(Constants.ITEM_NAME, itemInfo.getItemName());
            map.put(Constants.ITEM_TYPE_DESC, itemInfo.getItemTypeDesc());
            map.put(Constants.ITEM_PASSWORD, itemInfo.getItemPassword());
            map.put(Constants.ITEM_GUID, itemInfo.getItemGuid());
            map.put(Constants.ITEM_GUID, itemInfo.getItemGuid());
            map.put(Constants.ITEM_TYPE, Common.convertToString(itemInfo.getItemType()));
            listMap.add(map);
        }
        map = new HashMap();
        map.put(Constants.ITEM_INFO, listMap);
        return map;
    }

    private Map buildItemContent(ItemInfo itemInfo) {
        String c;
        File file = null;
        Map map = null;
        InputStreamReader isr = null;
        StringBuilder itemContent = new StringBuilder();
        try {
            file = new File(itemInfo.getItemPath() + Constants.ITEM_PATH_REX + itemInfo.getItemGuid());
            isr = new InputStreamReader(new FileInputStream(file), "gbk");
            BufferedReader rf = new BufferedReader(isr);
            while ((c = rf.readLine()) != null) {
                itemContent.append(c + Constants.ITEM_CONTENT_LINE_REX);
            }
            map = new HashMap();
            String strDnContent = AESUtil.AESDncode(Constants.SERVER_AES_KEY_DEF, itemContent.toString());
            map.put(Constants.ITEM_CONTENT, strDnContent);
            map.put(Constants.ITEM_SIZE, Common.FormatFileSize(file.length()));
            map.put(Constants.ITEM_NAME, itemInfo.getItemName());
            map.put(Constants.ITEM_CREATE_DATE, Common.dateToString(itemInfo.getItemCreateDate()));
            map.put(Constants.ITEM_LAST_MODIFY_DATE, Common.dateToString(itemInfo.getItemLastModifiedDate()));
            map.put(Constants.ITEM_GUID, itemInfo.getItemGuid());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (isr != null)
                    isr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;
        }
    }

}
