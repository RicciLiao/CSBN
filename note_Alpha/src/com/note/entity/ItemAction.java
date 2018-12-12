package com.note.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "item_action", schema = "note_alphadb", catalog = "")
public class ItemAction {
    private Integer actionId;

    @Id
    @javax.persistence.Column(name = "ACTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
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

    private Date actionDate;

    @Basic
    @javax.persistence.Column(name = "ACTION_DATE")
    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    private Integer actionType;

    @Basic
    @javax.persistence.Column(name = "ACTION_TYPE")
    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    private String comment;

    @Basic
    @javax.persistence.Column(name = "COMMENT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private Integer status;

    @Basic
    @javax.persistence.Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemAction that = (ItemAction) o;
        return Objects.equals(actionId, that.actionId) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(actionDate, that.actionDate) &&
                Objects.equals(actionType, that.actionType) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(actionId, itemId, actionDate, actionType, comment, status);
    }
}
