package com.note.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "item_info", schema = "note_alphadb", catalog = "")
public class ItemInfo {
    private Integer id;
    private String itemGuid;
    private Integer userId;
    private String itemName;
    private String itemPath;
    private Integer itemType;
    private String itemPassword;
    private Date itemCreateDate;
    private Date itemLastModifiedDate;
    private String itemAnnotation;
    private Integer status;
    private String itemTypeDesc;
    private String itemPrePath;
    private List<Integer> itemTag;
    private Integer itemClass;
    private String itemClassDesc;
    private String itemTagDesc;
    private String itemContent;
    private String itemCreateDateStr;
    private String itemLastModifiedDateStr;
    private String itemSize;

    @Id
    @javax.persistence.Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_GUID")
    public String getItemGuid() {
        return itemGuid;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
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
    @javax.persistence.Column(name = "ITEM_NAME")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_PATH")
    public String getItemPath() {
        return itemPath;
    }

    public void setItemPath(String itemPath) {
        this.itemPath = itemPath;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_TYPE")
    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_PASSWORD")
    public String getItemPassword() {
        return itemPassword;
    }

    public void setItemPassword(String itemPassword) {
        this.itemPassword = itemPassword;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_CREATE_DATE")
    public Date getItemCreateDate() {
        return itemCreateDate;
    }

    public void setItemCreateDate(Date itemCreateDate) {
        this.itemCreateDate = itemCreateDate;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_LAST_MODIFIED_DATE")
    public Date getItemLastModifiedDate() {
        return itemLastModifiedDate;
    }

    public void setItemLastModifiedDate(Date itemLastModifiedDate) {
        this.itemLastModifiedDate = itemLastModifiedDate;
    }


    @Basic
    @javax.persistence.Column(name = "ITEM_ANNOTATION")
    public String getItemAnnotation() {
        return itemAnnotation;
    }

    public void setItemAnnotation(String itemAnnotation) {
        this.itemAnnotation = itemAnnotation;
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
    public String getItemTypeDesc() {
        return itemTypeDesc;
    }

    public void setItemTypeDesc(String itemTypeDesc) {
        this.itemTypeDesc = itemTypeDesc;
    }

    @Transient
    public String getItemPrePath() {
        return itemPrePath;
    }

    public void setItemPrePath(String itemPrePath) {
        this.itemPrePath = itemPrePath;
    }

    @Transient
    public List<Integer> getItemTag() {
        return itemTag;
    }

    public void setItemTag(List<Integer> itemTag) {
        this.itemTag = itemTag;
    }

    @Transient
    public Integer getItemClass() {
        return itemClass;
    }

    public void setItemClass(Integer itemClass) {
        this.itemClass = itemClass;
    }

    @Transient
    public String getItemClassDesc() {
        return itemClassDesc;
    }

    public void setItemClassDesc(String itemClassDesc) {
        this.itemClassDesc = itemClassDesc;
    }

    @Transient
    public String getItemTagDesc() {
        return itemTagDesc;
    }

    public void setItemTagDesc(String itemTagDesc) {
        this.itemTagDesc = itemTagDesc;
    }

    @Transient
    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    @Transient
    public String getItemCreateDateStr() {
        return itemCreateDateStr;
    }

    public void setItemCreateDateStr(String itemCreateDateStr) {
        this.itemCreateDateStr = itemCreateDateStr;
    }

    @Transient
    public String getItemLastModifiedDateStr() {
        return itemLastModifiedDateStr;
    }

    public void setItemLastModifiedDateStr(String itemLastModifiedDateStr) {
        this.itemLastModifiedDateStr = itemLastModifiedDateStr;
    }

    @Transient
    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }
}
