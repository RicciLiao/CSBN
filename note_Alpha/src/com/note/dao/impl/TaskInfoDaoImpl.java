package com.note.dao.impl;

import com.note.common.Common;
import com.note.dao.TaskInfoDao;
import com.note.entity.TaskInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Transactional
public class TaskInfoDaoImpl  extends HibernateBaseDaoImpl implements TaskInfoDao {
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TaskInfo> findByDate(String date) {
        Session session = null;
        List<TaskInfo> listTaskInfo = new ArrayList<>();
        String sql = "select ii.item_guid, ii.item_name, ti.* from task_info ti left join item_info ii on ii.id = ti.item_id where ti.task_date like ? and ti.status = 0;";
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, date);
            List<Object[]> list = query.list();
            TaskInfo taskInfo = null;
            for(Object[] object : list){
                taskInfo = new TaskInfo();
                taskInfo.setItemGuid(Common.convertToString(object[0]));
                taskInfo.setItemName(Common.convertToString(object[1]));
                taskInfo.setTaskId(Common.convertToInteger(object[2]));
                taskInfo.setUserId(Common.convertToInteger(object[3]));
                taskInfo.setItemId(Common.convertToInteger(object[4]));
                taskInfo.setTaskName(Common.convertToString(object[5]));
                taskInfo.setTaskDesc(Common.convertToString(object[6]));
                taskInfo.setStrTaskCreateDate(Common.dateToString(Common.objectToDate(object[7])));
                taskInfo.setStrTaskDate(Common.dateToString(Common.objectToDate(object[8])));
                taskInfo.setTaskLvl(Common.convertToInteger(object[9]));
                taskInfo.setStatus(Common.convertToInteger(object[10]));
                listTaskInfo.add(taskInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return listTaskInfo;
        }
    }
}
