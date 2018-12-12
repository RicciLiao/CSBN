package com.note.dao.impl;

/**************************************************************************
 * MODIFICATION HISTORY
 * Name             Date                     Description
 * ==========  ==============  =======================================
 * Ricci               30-Apr-2018       Initial Version
 **************************************************************************/

import com.note.dao.UserInfoDao;
import com.note.entity.UserInfo;
import org.hibernate.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("rawtypes")
@Transactional
public class UserInfoDaoImpl extends HibernateBaseDaoImpl implements UserInfoDao{
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserInfo findByName(String userName) {
        Session session = null;
        UserInfo userInfo = null;
        String hql = "from UserInfo userInfo where userInfo.userName = ? ";
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, userName);
            List list = query.list();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                userInfo = (UserInfo) iterator.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userInfo;
        }
    }

/*    @Override
    public boolean create(UserInfo userInfo) {
        Session session = null;
        boolean result = true;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(userInfo);
        } catch (HibernateException e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }*/

}
