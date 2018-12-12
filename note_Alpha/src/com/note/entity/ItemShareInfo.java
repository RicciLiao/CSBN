package com.note.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "item_share_info", schema = "note_alphadb", catalog = "")
public class ItemShareInfo {
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

    private Integer itemId;

    @Basic
    @javax.persistence.Column(name = "ITEM_ID")
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    private Integer ownUserId;

    @Basic
    @javax.persistence.Column(name = "OWN_USER_ID")
    public Integer getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(Integer ownUserId) {
        this.ownUserId = ownUserId;
    }

    private Integer shareUserId;

    @Basic
    @javax.persistence.Column(name = "SHARE_USER_ID")
    public Integer getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Integer shareUserId) {
        this.shareUserId = shareUserId;
    }

    private Date shareDate;

    @Basic
    @javax.persistence.Column(name = "SHARE_DATE")
    public Date getShareDate() {
        return shareDate;
    }

    public void setShareDate(Date shareDate) {
        this.shareDate = shareDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemShareInfo that = (ItemShareInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(ownUserId, that.ownUserId) &&
                Objects.equals(shareUserId, that.shareUserId) &&
                Objects.equals(shareDate, that.shareDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, itemId, ownUserId, shareUserId, shareDate);
    }
}
