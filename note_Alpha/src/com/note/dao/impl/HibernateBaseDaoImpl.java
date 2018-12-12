package com.note.dao.impl;

import com.note.dao.HibernateBaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateBaseDaoImpl implements HibernateBaseDao {
    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean create(Object object) {
        Session session = null;
        boolean result = true;
        try {
            session = sessionFactory.getCurrentSession();
            session.save(object);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public boolean update(Object object) {
        Session session = null;
        boolean result = true;
        try {
            session = sessionFactory.getCurrentSession();
            session.update(object);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public boolean delete(Object object) {
        Session session = null;
        boolean result = true;
        try {
            session = sessionFactory.getCurrentSession();
            session.delete(object);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public Object findById(Class clazz, Integer id) {
        Session session = null;
        Object result = null;
        try {
            session = sessionFactory.getCurrentSession();
            result = session.get(clazz, id);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
