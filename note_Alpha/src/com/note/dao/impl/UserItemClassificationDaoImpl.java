package com.note.dao.impl;

import com.note.dao.UserItemClassificationDao;
import com.note.entity.UserItemClassification;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
@Transactional
public class UserItemClassificationDaoImpl extends HibernateBaseDaoImpl implements UserItemClassificationDao {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserItemClassification> loadItemClasses(Integer userId) {
        Session session = null;
        List<UserItemClassification> list = new ArrayList<>();
        String hql = "from UserItemClassification as model where model.userId = ?";
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, userId);
            list = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }
}
