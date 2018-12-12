package com.note.dao.impl;

import com.note.dao.ItemClassInfoDao;
import com.note.entity.ItemClassInfo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("rawtypes")
@Transactional
public class ItemClassInfoDaoImpl extends HibernateBaseDaoImpl implements ItemClassInfoDao {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ItemClassInfo findByItemId(String itemGuid) {
        Session session = null;
        ItemClassInfo itemClassInfo = null;
        String hql = "select ici from ItemClassInfo ici, ItemInfo ii where ici.itemId = ii.id and ii.itemGuid = ?";
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, itemGuid);
            itemClassInfo = (ItemClassInfo)query.list().get(0);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            return itemClassInfo;
        }

    }
}
