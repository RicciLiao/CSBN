package com.note.common;


import com.note.dao.TaskInfoDao;
import com.note.dao.impl.TaskInfoDaoImpl;
import com.note.entity.TaskInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


public class ScheduledUtil {
    Date date = null;
    String strDate = "";
    List<TaskInfo> list = new ArrayList<>();
    TaskInfoDao taskInfoDao;

    public void setTaskInfoDao(TaskInfoDao taskInfoDao) {
        this.taskInfoDao = taskInfoDao;
    }

    public void test() {
        date = new Date(Common.getCurrentDate().getTime() + (long) 60 * 1000);
        strDate = Common.dateToString3(date);
        System.out.println(strDate);
        list = taskInfoDao.findByDate(strDate + "%");
        WebSocketUtil.receiveMessage(buildMsg(list));
    }

    private Map buildMsg(List<TaskInfo> list){
        Map<Integer, Object> map = new HashMap();
        for(TaskInfo taskInfo : list){
            map.put(taskInfo.getUserId(), taskInfo);
        }
        return map;
    }
}
