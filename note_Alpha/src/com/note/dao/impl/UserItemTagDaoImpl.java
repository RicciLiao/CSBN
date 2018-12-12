package com.note.dao.impl;

import com.note.biz.UserInfoBiz;
import com.note.biz.UserItemTagBiz;
import com.note.dao.UserItemTagDao;
import com.note.entity.UserItemTag;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("rawtypes")
@Transactional
public class UserItemTagDaoImpl extends HibernateBaseDaoImpl implements UserItemTagDao {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserItemTag> loadItemTags(Integer userId) {
        Session session = null;
        List<UserItemTag> listUserItemTag = null;
        String hql = "from UserItemTag as model where model.userId = :userId";
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            listUserItemTag = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            return listUserItemTag;
        }

    }
}
