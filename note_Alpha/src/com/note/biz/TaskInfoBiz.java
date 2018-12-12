package com.note.biz;

import com.note.entity.TaskInfo;

import java.util.List;

public interface TaskInfoBiz {
    List<TaskInfo> findByScheduled(String date);
}
