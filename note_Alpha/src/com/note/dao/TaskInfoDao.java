package com.note.dao;


import com.note.entity.TaskInfo;

import java.util.List;

public interface TaskInfoDao {
    List<TaskInfo> findByDate(String date);

}
