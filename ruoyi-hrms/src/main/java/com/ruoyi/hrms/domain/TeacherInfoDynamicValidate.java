package com.ruoyi.hrms.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class TeacherInfoDynamicValidate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Boolean required;

    private Long maxLength;

    private String datetimeFormat;

    private Long teacherInfoDynamicId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }

    public String getDatetimeFormat() {
        return datetimeFormat;
    }

    public void setDatetimeFormat(String datetimeFormat) {
        this.datetimeFormat = datetimeFormat;
    }

    public Long getTeacherInfoDynamicId() {
        return teacherInfoDynamicId;
    }

    public void setTeacherInfoDynamicId(Long teacherInfoDynamicId) {
        this.teacherInfoDynamicId = teacherInfoDynamicId;
    }

    @Override
    public String toString() {
        return "TeacherInfoDynamicValidate{" +
                "id=" + id +
                ", required=" + required +
                ", maxLength=" + maxLength +
                ", datetimeFormat='" + datetimeFormat + '\'' +
                ", teacherInfoDynamicId=" + teacherInfoDynamicId +
                '}';
    }
}
