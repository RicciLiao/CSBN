package com.note.dao.impl;

import com.note.common.Common;
import com.note.dao.ItemInfoDao;
import com.note.entity.ItemInfo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
@Transactional
public class ItemInfoDaoImpl extends HibernateBaseDaoImpl implements ItemInfoDao {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<ItemInfo> findByItemPath(String itemPath) {
        Session session = null;
        ItemInfo itemInfo = null;
        List<ItemInfo> listResult = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select ii.item_guid, ii.item_name, ( case when ii.item_password is null then null else '1' end ) as psw, dd_it.type_name, ii.item_type \n")
                .append("from item_info ii \n").append("left join dd_item_type dd_it on dd_it.type_id = ii.item_type \n")
                .append("where ii.item_path = ?");
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(sql.toString());
            query.setParameter(0, itemPath);
            List<Object[]> list = query.list();
            for (Object[] object : list) {
                itemInfo = new ItemInfo();
                itemInfo.setItemGuid(Common.convertToString(object[0]));
                itemInfo.setItemName(Common.convertToString(object[1]));
                itemInfo.setItemPassword(Common.convertToString(object[2]));
                itemInfo.setItemTypeDesc(Common.convertToString(object[3]));
                itemInfo.setItemType(Common.convertToInteger(object[4]));
                listResult.add(itemInfo);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            return listResult;
        }
    }

    @Override
    public ItemInfo findByGuid(String guid) {
        Session session = null;
        ItemInfo itemInfo = null;
        String hql = "from ItemInfo as model where model.itemGuid = ?";
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, guid);
            itemInfo = (ItemInfo) query.list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemInfo;
        }
    }

    @Override
    public ItemInfo findDescByGuid(String guid) {
        Session session = null;
        ItemInfo itemInfo = null;
        String sql = "select dd_it.type_name, 1 from item_info ii left join dd_item_type dd_it on dd_it.type_id = item_type where ii.item_guid = ?";
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, guid);
            Object[] object = (Object[]) query.list().get(0);
            itemInfo = new ItemInfo();
            itemInfo.setItemTypeDesc(Common.convertToString(object[0]));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemInfo;
        }
    }

    @Override
    public List<ItemInfo> itemSearch(ItemInfo searchCondition) {
        Session session = null;
        StringBuffer sql = new StringBuffer();
        List<ItemInfo> listItemInfo = new ArrayList<>();
        sql.append("select ii.item_guid, ii.item_name, ii.item_path, (case when ii.item_password is not null then '1' else ' ' end) as psw, ")
                .append("ii.item_create_date, ii.item_last_modified_date, uic.class_name, group_concat(uit.tag_name separator ',') as tag_names \n")
                .append("from item_info ii \n").append("left join item_class_info ici \n").append("on ici.item_id = ii.id \n").append("left join item_tag_info iti \n")
                .append("on iti.item_id = ii.id \n").append("left join user_item_classification uic \n").append("on uic.user_id = ii.user_id ").append("and uic.class_id = ici.class_id \n")
                .append(" left join user_item_tag uit \n").append("on uit.tag_id = iti.tag_id \n").append("where ii.user_id = :user_id and ii.item_type = 2 \n");
        if ( !Common.isNullOrSpace(searchCondition.getItemName())) {
            sql.append("and ii.item_name = :item_name \n");
        }
        if (searchCondition.getItemCreateDate() != null) {
            sql.append("and Date(ii.item_create_date) = :create_date \n");
        }
        if(searchCondition.getItemLastModifiedDate() != null){
            sql.append("and Date(ii.item_last_modified_date) = :modified_date \n");
        }
        if(searchCondition.getItemClass() != null){
            sql.append("and ici.class_id = :class_id  \n");
        }
        if(searchCondition.getItemTag() != null && searchCondition.getItemTag().size() > 0){
            sql.append("and uit.tag_id in ( :tag_id )  \n");
        }
        sql.append(" group by ii.item_guid");
        try {
            session = sessionFactory.getCurrentSession();
            Query query = session.createSQLQuery(sql.toString());
            query.setParameter("user_id", searchCondition.getUserId());
            if ( !Common.isNullOrSpace(searchCondition.getItemName())) {
                query.setParameter("item_name", searchCondition.getItemName());
            }
            if (searchCondition.getItemCreateDate() != null) {
                query.setParameter("create_date", Common.dateToString2(searchCondition.getItemCreateDate()));
            }
            if(searchCondition.getItemLastModifiedDate() != null){
                query.setParameter("modified_date", Common.dateToString2(searchCondition.getItemLastModifiedDate()));
            }
            if(searchCondition.getItemClass() != null){
                query.setParameter("class_id", searchCondition.getItemClass());
            }
            if(searchCondition.getItemTag() != null && searchCondition.getItemTag().size() > 0l){
                query.setParameterList("tag_id", searchCondition.getItemTag().toArray());
            }
            List<Object[]> list1 = query.list();
            ItemInfo itemInfo = null;
            for(Object[] object : list1){
                itemInfo = new ItemInfo();
                itemInfo.setItemGuid(Common.convertToString(object[0]));
                itemInfo.setItemName(Common.convertToString(object[1]));
                itemInfo.setItemPath(Common.convertToString(object[2]));
                itemInfo.setItemPassword(Common.convertToString(object[3]));
                itemInfo.setItemCreateDate(Common.objectToDate(object[4]));
                itemInfo.setItemLastModifiedDate(Common.objectToDate(object[5]));
                itemInfo.setItemClassDesc(Common.convertToString(object[6]));
                itemInfo.setItemTagDesc(Common.convertToString(object[7]));
                listItemInfo.add(itemInfo);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            return listItemInfo;
        }

    }

}




