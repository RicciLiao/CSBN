package com.note.dao;

public interface HibernateBaseDao {
    boolean create(Object object);
    boolean update(Object object);
    boolean delete(Object object);
    Object findById(Class clazz, Integer id);
}
