package com.note.action;

import com.note.biz.ItemInfoBiz;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.entity.ItemInfo;
import com.note.entity.UserInfo;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Transactional
public class ItemInfoAction extends ActionSupport implements ServletRequestAware, SessionAware {

    ItemInfoBiz itemInfoBiz;
    ItemInfo itemInfo;
    private String ajaxResult;
    HttpServletRequest request;
    Map<String, Object> session;
    private String result = Constants.SUCCESS;
    String itemType;
    String itemName;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public void setItemInfoBiz(ItemInfoBiz itemInfoBiz) {
        this.itemInfoBiz = itemInfoBiz;
    }

    public String getAjaxResult() {
        return ajaxResult;
    }

    public void setAjaxResult(String ajaxResult) {
        this.ajaxResult = ajaxResult;
    }

    public String createItem() {
        ItemInfo unItem = null;
        String strGuid = "";
        String itemPrePath = "";
        String itemPreGuid = "";
        try {
            if (Common.isNullOrSpace(request.getParameter("itemGuid"))) {
                itemPrePath = getBySession().getUserPath();
            } else {
                itemPreGuid = request.getParameter("itemGuid");
                itemPrePath = itemInfoBiz.findByGuid(itemPreGuid).getItemPath() + Constants.ITEM_PATH_REX + itemPreGuid;
            }
            unItem = new ItemInfo();
            strGuid = Common.generateGUID();
            unItem.setItemGuid(strGuid);
            unItem.setItemCreateDate(Common.getCurrentDate());
            unItem.setItemName(request.getParameter("itemName"));
            unItem.setUserId(Integer.parseInt(request.getParameter("userId")));
            unItem.setItemType(Integer.parseInt(request.getParameter("itemType")));
            unItem.setItemPath(itemPrePath);
            ajaxResult = Common.mapToJson(itemInfoBiz.create(unItem));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String loadUserSpace() {
        Map map = null;
        String userSpace = "";
        try {
            userSpace = getBySession().getUserPath();
            map = itemInfoBiz.loadItem(userSpace, Constants.ITEM_TYPE_DIR, null);
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String loadItem() {
        Map map = null;
        String itemGuid = "";
        try {
            itemGuid = request.getParameter(Constants.ITEM_GUID);
            if (Common.isNullOrSpace(itemGuid)) {
                loadUserSpace();
            } else {
                map = itemInfoBiz.loadItemPath(itemGuid);
                ajaxResult = Common.mapToJson(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String renameItem() {
        String guid = "";
        String name = "";
        Map<String, String> map = null;
        try {
            guid = request.getParameter("itemGuid");
            name = request.getParameter("reName");
            map = itemInfoBiz.renameItem(guid, name);
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String getItemSize() {
        Map<String, String> map = null;
        String itemGuid = "";
        try {
            itemGuid = request.getParameter("itemGuid");
            if (Common.isNullOrSpace(itemGuid)) {
                map = itemInfoBiz.getItemSizes(getBySession().getUserPath());
            } else {
                ItemInfo itemInfo = itemInfoBiz.findByGuid(itemGuid);
                map = itemInfoBiz.getItemSizes(itemInfo.getItemPath() + Constants.ITEM_PATH_REX + itemInfo.getItemGuid());
            }
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String identifyItemType() {
        String itemGuid = "";
        Integer itemType = 0;
        Map<String, String> map = new HashMap<>();
        try {
            itemGuid = request.getParameter("itemGuid");
            itemType = itemInfoBiz.findByGuid(itemGuid).getItemType();
            map.put(Constants.AJAX_COMMON_RESULT, Common.convertToString(itemType));
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String checkItemEnc() {
        String itemGuid = "";
        ItemInfo itemInfo = null;
        Map<String, String> map = new HashMap<>();
        try {
            itemGuid = request.getParameter("itemGuid");
            itemInfo = itemInfoBiz.findByGuid(itemGuid);
            if (itemInfo.getItemPassword() != null) {
                map.put(Constants.AJAX_COMMON_RESULT, Constants.SUCCESS);
            } else {
                map.put(Constants.AJAX_COMMON_RESULT, "");
            }
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String itemSave() {
        String itemGuid = "";
        String newItemName = "";
        ItemInfo itemInfo = null;
        Map<String, String> map = null;
        try {
            itemGuid = request.getParameter("itemGuid");
            itemInfo = itemInfoBiz.findByGuid(itemGuid);
            if (itemInfo.getItemType().equals(Constants.ITEM_TYPE_TXT)) {
                String strContent = request.getParameter("itemContent");
                newItemName = request.getParameter("itemName");
                map = itemInfoBiz.saveTxt(newItemName, strContent, itemInfo);
                ajaxResult = Common.mapToJson(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String itemEnSave() {
        String itemPsw = "";
        String itemGuid = "";
        String result = Constants.SUCCESS;
        ItemInfo itemInfo = null;
        Map<String, String> map;
        try {
            itemPsw = request.getParameter("itemPsw");
            itemGuid = request.getParameter("itemGuid");
            itemInfo = itemInfoBiz.findByGuid(itemGuid);
            itemInfo.setItemPassword(itemPsw);
            map = itemInfoBiz.itemEnSave(itemInfo);
            if (map.get(Constants.AJAX_COMMON_RESULT).equals(Constants.ERROR)) {
                result = Constants.ERROR;
            }
        } catch (Exception e) {
            result = Constants.ERROR;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String checkItemPsw() {
        String unItemPsw = "";
        String itemGuid = "";
        Map<String, String> map = null;
        try {
            unItemPsw = request.getParameter("itemPsw");
            itemGuid = request.getParameter("itemGuid");
            map = itemInfoBiz.checkItemPsw(unItemPsw, itemGuid);
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return Constants.SUCCESS;
        }
    }

    public InputStream getDownloadItem() {
        Map map = null;
        String itemGuid = "";
        InputStream is = null;
        ItemInfo itemInfo = null;
        try {
            itemGuid = request.getParameter("itemGuid");
            itemInfo = itemInfoBiz.findByGuid(itemGuid);
            map = itemInfoBiz.downloadItem(itemInfo);
            if (map.get(Constants.AJAX_COMMON_RESULT).equals(Constants.SUCCESS)) {
                setItemType(itemInfoBiz.findDescByGuid(itemGuid).getItemTypeDesc());
                setItemName(itemInfo.getItemName());
                is = (InputStream) map.get(Constants.ITEM_CONTENT);
            } else {
                // pending
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return is;
        }
    }

    public String itemSearch(){
        String itemContent = "";
        String itemCreateDate = "";
        String itemModifyDate = "";
        String itemClass = "";
        String itemTag = "";
        String itemRange = "";
        Map map = new HashMap();
        try {
            itemContent = request.getParameter("itemContent");
            itemCreateDate = request.getParameter("itemCreateDate");
            itemModifyDate = request.getParameter("itemModifyDate");
            itemClass = request.getParameter("itemClass");
            itemTag = request.getParameter("itemTag");
            itemRange = request.getParameter("itemRange");
            map = itemInfoBiz.itemSearch(itemContent, itemCreateDate, itemModifyDate, itemClass, itemTag, itemRange, getBySession().getId());
            ajaxResult = Common.mapToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private UserInfo getBySession() {
        String sid = "";
        UserInfo userInfo = null;
        try {
            sid = request.getSession().getId();
            userInfo = (UserInfo) session.get(sid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userInfo;
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
