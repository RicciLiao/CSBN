package com.note.biz.impl;

import com.note.biz.UserItemClassificationBiz;
import com.note.common.Common;
import com.note.common.Constants;
import com.note.dao.UserItemClassificationDao;
import com.note.dao.impl.UserItemClassificationDaoImpl;
import com.note.entity.UserItemClassification;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Transactional
public class UserItemClassificationBizImpl implements UserItemClassificationBiz {

    UserItemClassificationDao userItemClassificationDao;

    public void setUserItemClassificationDao(UserItemClassificationDaoImpl userItemClassificationDao) {
        this.userItemClassificationDao = userItemClassificationDao;
    }

    @Override
    public Map loadItemClasses(Integer userId) {
        Map map = null;
        List<UserItemClassification> list=null;
        List<Map<String, String>> listMap = new ArrayList<>();
        try {
            list = userItemClassificationDao.loadItemClasses(userId);
            for(UserItemClassification userItemClassification : list){
                map = new HashMap<>();
                map.put(Constants.CLASS_ID, Common.convertToString(userItemClassification.getClassId()));
                map.put(Constants.CLASS_NAME, userItemClassification.getClassName());
                map.put(Constants.CLASS_DESC, userItemClassification.getClassDesc());
                listMap.add(map);
            }
            map = new HashMap<>();
            map.put(Constants.ITEM_CLASSES, listMap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }
}
