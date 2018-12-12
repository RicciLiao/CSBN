package com.note.biz.impl;

import com.note.biz.TaskInfoBiz;
import com.note.dao.TaskInfoDao;
import com.note.entity.TaskInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("serial")
@Transactional
public class TaskInfoBizImpl implements TaskInfoBiz {
    private TaskInfoDao taskInfoDao;

    public void setTaskInfoDao(TaskInfoDao taskInfoDao) {
        this.taskInfoDao = taskInfoDao;
    }

    @Override
    public List<TaskInfo> findByScheduled(String date) {

        return taskInfoDao.findByDate(date);
    }
}
