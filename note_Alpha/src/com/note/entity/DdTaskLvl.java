package com.note.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "dd_task_lvl", schema = "note_alphadb", catalog = "")
public class DdTaskLvl {
    private Integer lvlId;

    @Id
    @javax.persistence.Column(name = "LVL_ID")
    public Integer getLvlId() {
        return lvlId;
    }

    public void setLvlId(Integer lvlId) {
        this.lvlId = lvlId;
    }

    private Integer lvlSeq;

    @Basic
    @javax.persistence.Column(name = "LVL_SEQ")
    public Integer getLvlSeq() {
        return lvlSeq;
    }

    public void setLvlSeq(Integer lvlSeq) {
        this.lvlSeq = lvlSeq;
    }

    private String lvlName;

    @Basic
    @javax.persistence.Column(name = "LVL_NAME")
    public String getLvlName() {
        return lvlName;
    }

    public void setLvlName(String lvlName) {
        this.lvlName = lvlName;
    }

    private String lvlDesc;

    @Basic
    @javax.persistence.Column(name = "LVL_DESC")
    public String getLvlDesc() {
        return lvlDesc;
    }

    public void setLvlDesc(String lvlDesc) {
        this.lvlDesc = lvlDesc;
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
        DdTaskLvl ddTaskLvl = (DdTaskLvl) o;
        return Objects.equals(lvlId, ddTaskLvl.lvlId) &&
                Objects.equals(lvlSeq, ddTaskLvl.lvlSeq) &&
                Objects.equals(lvlName, ddTaskLvl.lvlName) &&
                Objects.equals(lvlDesc, ddTaskLvl.lvlDesc) &&
                Objects.equals(status, ddTaskLvl.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lvlId, lvlSeq, lvlName, lvlDesc, status);
    }
}
