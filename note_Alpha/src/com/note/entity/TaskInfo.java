package com.note.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Entity
@javax.persistence.Table(name = "task_info", schema = "note_alphadb", catalog = "")
public class TaskInfo {

    private Integer taskId;
    private Integer userId;
    private Integer itemId;
    private String taskName;
    private String taskDesc;
    private Date taskCreateDate;
    private Date taskDate;
    private Integer taskLvl;
    private Integer status;
    private String itemGuid;
    private String itemName;
    private String strTaskDate;
    private String strTaskCreateDate;


    @Id
    @javax.persistence.Column(name = "TASK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }


    @Basic
    @javax.persistence.Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_ID")
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }


    @Basic
    @javax.persistence.Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    @Basic
    @javax.persistence.Column(name = "TASK_DESC")
    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }


    @Basic
    @javax.persistence.Column(name = "TASK_CREATE_DATE")
    public Date getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(Date taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }


    @Basic
    @javax.persistence.Column(name = "TASK_DATE")
    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }


    @Basic
    @javax.persistence.Column(name = "TASK_LVL")
    public Integer getTaskLvl() {
        return taskLvl;
    }

    public void setTaskLvl(Integer taskLvl) {
        this.taskLvl = taskLvl;
    }


    @Basic
    @javax.persistence.Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Transient
    public String getItemGuid() {
        return itemGuid;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
    }


    @Transient
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Transient
    public String getStrTaskDate() {
        return strTaskDate;
    }

    public void setStrTaskDate(String strTaskDate) {
        this.strTaskDate = strTaskDate;
    }

    @Transient
    public String getStrTaskCreateDate() {
        return strTaskCreateDate;
    }

    public void setStrTaskCreateDate(String strTaskCreateDate) {
        this.strTaskCreateDate = strTaskCreateDate;
    }
}
