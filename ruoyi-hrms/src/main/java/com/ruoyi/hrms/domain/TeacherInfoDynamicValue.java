package com.ruoyi.hrms.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class TeacherInfoDynamicValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String value;

    private Long teacherInfoDynamicId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTeacher_info_dynamic_id() {
        return teacherInfoDynamicId;
    }

    public void setTeacher_info_dynamic_id(Long teacherInfoDynamicId) {
        this.teacherInfoDynamicId = teacherInfoDynamicId;
    }

    @Override
    public String toString() {
        return "TeacherInfoDynamicValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", teacherInfoDynamicId=" + teacherInfoDynamicId +
                '}';
    }
}
