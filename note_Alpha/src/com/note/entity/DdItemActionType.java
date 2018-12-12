package com.note.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "dd_item_action_type", schema = "note_alphadb", catalog = "")
public class DdItemActionType {
    private Integer typeId;

    @Id
    @javax.persistence.Column(name = "TYPE_ID")
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    private Integer typeSeq;

    @Basic
    @javax.persistence.Column(name = "TYPE_SEQ")
    public Integer getTypeSeq() {
        return typeSeq;
    }

    public void setTypeSeq(Integer typeSeq) {
        this.typeSeq = typeSeq;
    }

    private String typeDesc;

    @Basic
    @javax.persistence.Column(name = "TYPE_DESC")
    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
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
        DdItemActionType that = (DdItemActionType) o;
        return Objects.equals(typeId, that.typeId) &&
                Objects.equals(typeSeq, that.typeSeq) &&
                Objects.equals(typeDesc, that.typeDesc) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(typeId, typeSeq, typeDesc, status);
    }
}
