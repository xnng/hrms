package com.ruoyi.hrms.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class TeacherInfoDynamic extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String type;

    private String name;

    private Long teacherId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeacher_id() {
        return teacherId;
    }

    public void setTeacher_id(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "TeacherInfoDynamic{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", teacherId=" + teacherId +
                '}';
    }
}
