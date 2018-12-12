package com.note.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_item_classification", schema = "note_alphadb", catalog = "")
public class UserItemClassification {
    private Integer classId;
    private Integer classSeq;
    private Integer userId;
    private String className;
    private String classDesc;
    private Date classCreateDate;
    private Integer status;

    @Id
    @Column(name = "CLASS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "CLASS_SEQ")
    public Integer getClassSeq() {
        return classSeq;
    }

    public void setClassSeq(Integer classSeq) {
        this.classSeq = classSeq;
    }

    @Basic
    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "CLASS_NAME")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "CLASS_DESC")
    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    @Basic
    @Column(name = "CLASS_CREATE_DATE")
    public Date getClassCreateDate() {
        return classCreateDate;
    }

    public void setClassCreateDate(Date classCreateDate) {
        this.classCreateDate = classCreateDate;
    }

    @Basic
    @Column(name = "STATUS")
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
        UserItemClassification that = (UserItemClassification) o;
        return Objects.equals(classId, that.classId) &&
                Objects.equals(classSeq, that.classSeq) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(className, that.className) &&
                Objects.equals(classDesc, that.classDesc) &&
                Objects.equals(classCreateDate, that.classCreateDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(classId, classSeq, userId, className, classDesc, classCreateDate, status);
    }
}
