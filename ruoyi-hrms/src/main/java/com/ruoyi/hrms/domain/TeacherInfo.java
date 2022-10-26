package com.ruoyi.hrms.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

public class TeacherInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long teacher_id;

    private Long examine_leader;

    private Long examine_hr;

    private String examine_remark;

    private Date expire_contract;

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Long getExamine_leader() {
        return examine_leader;
    }

    public void setExamine_leader(Long examine_leader) {
        this.examine_leader = examine_leader;
    }

    public Long getExamine_hr() {
        return examine_hr;
    }

    public void setExamine_hr(Long examine_hr) {
        this.examine_hr = examine_hr;
    }

    public String getExamine_remark() {
        return examine_remark;
    }

    public void setExamine_remark(String examine_remark) {
        this.examine_remark = examine_remark;
    }

    public Date getExpire_contract() {
        return expire_contract;
    }

    public void setExpire_contract(Date expire_contract) {
        this.expire_contract = expire_contract;
    }

    @Override
    public String toString() {
        return "TeacherInfo{" +
                "teacher_id=" + teacher_id +
                ", examine_leader=" + examine_leader +
                ", examine_hr=" + examine_hr +
                ", examine_remark='" + examine_remark + '\'' +
                ", expire_contract=" + expire_contract +
                '}';
    }
}
