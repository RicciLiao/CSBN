package com.note.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "item_tag_info", schema = "note_alphadb", catalog = "")
public class ItemTagInfo {
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

    private Integer tagId;

    @Basic
    @javax.persistence.Column(name = "TAG_ID")
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemTagInfo that = (ItemTagInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, itemId, tagId);
    }
}
