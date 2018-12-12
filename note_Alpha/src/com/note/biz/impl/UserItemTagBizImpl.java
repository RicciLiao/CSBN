package com.note.biz.impl;

import com.note.biz.UserInfoBiz;
import com.note.biz.UserItemTagBiz;
import com.note.dao.UserItemTagDao;
import com.note.entity.UserItemTag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("rawtypes")
@Transactional
public class UserItemTagBizImpl implements UserItemTagBiz {

    UserItemTagDao userItemTagDao;

    public void setUserItemTagDao(UserItemTagDao userItemTagDao) {
        this.userItemTagDao = userItemTagDao;
    }

    @Override
    public List<UserItemTag> loadItemTags(Integer userId) {
        return userItemTagDao.loadItemTags(userId);
    }
}
