package com.note.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "item_class_info", schema = "note_alphadb", catalog = "")
public class ItemClassInfo {
    private Integer id;

    @Id
    @javax.persistence.Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer userId;

    @Basic
    @javax.persistence.Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer itemId;

    @Basic
    @javax.persistence.Column(name = "ITEM_ID")
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    private Integer classId;

    @Basic
    @javax.persistence.Column(name = "CLASS_ID")
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemClassInfo that = (ItemClassInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(classId, that.classId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, itemId, classId);
    }
}
