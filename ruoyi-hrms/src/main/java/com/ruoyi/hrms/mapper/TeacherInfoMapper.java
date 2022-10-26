package com.ruoyi.hrms.mapper;

import com.ruoyi.hrms.domain.TeacherInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeacherInfoMapper {

    public Integer selectTeacherCount();

    public Integer selectDynamicTableCount();

    public Integer selectDynamicDicTableCount();

    public Integer selectDynamicValidateCount();

    public List<Map<String, Object>> selectTeacherInfoList(@Param("params") Map<String, String> dynamicTeacherInfo);

//    public TeacherInfo selectTeacherInfoById(Long id);

//    public int insertTeacherInfo(TeacherInfo teacherInfo);

    public int updateTeacherInfo(TeacherInfo teacherInfo);

    public int deleteTeacherInfoByIds(Long[] teacherIds);
}
