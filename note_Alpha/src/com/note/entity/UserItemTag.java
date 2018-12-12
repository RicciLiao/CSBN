package com.note.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_item_tag", schema = "note_alphadb", catalog = "")
public class UserItemTag {
    private Integer tagId;
    private Integer tagSeq;
    private Integer userId;
    private String tagName;
    private String tagDesc;
    private Date tagCreateDate;
    private Integer status;

    @Id
    @Column(name = "TAG_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "TAG_SEQ")
    public Integer getTagSeq() {
        return tagSeq;
    }

    public void setTagSeq(Integer tagSeq) {
        this.tagSeq = tagSeq;
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
    @Column(name = "TAG_NAME")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Basic
    @Column(name = "TAG_DESC")
    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    @Basic
    @Column(name = "TAG_CREATE_DATE")
    public Date getTagCreateDate() {
        return tagCreateDate;
    }

    public void setTagCreateDate(Date tagCreateDate) {
        this.tagCreateDate = tagCreateDate;
    }

    @Basic
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
