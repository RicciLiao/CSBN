package com.note.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "dd_user_type", schema = "note_alphadb", catalog = "")
public class DdUserType {
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

    private String typeName;

    @Basic
    @javax.persistence.Column(name = "TYPE_NAME")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    private BigDecimal typePrice;

    @Basic
    @javax.persistence.Column(name = "TYPE_PRICE")
    public BigDecimal getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(BigDecimal typePrice) {
        this.typePrice = typePrice;
    }

    private BigDecimal typeDiscount;

    @Basic
    @javax.persistence.Column(name = "TYPE_DISCOUNT")
    public BigDecimal getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(BigDecimal typeDiscount) {
        this.typeDiscount = typeDiscount;
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
        DdUserType that = (DdUserType) o;
        return Objects.equals(typeId, that.typeId) &&
                Objects.equals(typeSeq, that.typeSeq) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(typeDesc, that.typeDesc) &&
                Objects.equals(typePrice, that.typePrice) &&
                Objects.equals(typeDiscount, that.typeDiscount) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(typeId, typeSeq, typeName, typeDesc, typePrice, typeDiscount, status);
    }
}
