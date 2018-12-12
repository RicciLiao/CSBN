package com.note.biz;

import com.note.entity.UserItemTag;
import java.util.List;

public interface UserItemTagBiz {
    List<UserItemTag> loadItemTags(Integer userId);
}
