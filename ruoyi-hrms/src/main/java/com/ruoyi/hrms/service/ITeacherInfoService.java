package com.ruoyi.hrms.service;

import com.ruoyi.hrms.domain.TeacherInfoWithAnonymousId;

import java.util.List;
import java.util.Map;

public interface ITeacherInfoService {

    public Integer selectTeacherCount() ;

    public Integer selectDynamicTableCount();

    public Integer selectDynamicDicTableCount() ;

    public Integer selectDynamicValidateCount() ;

    public List<Map<String, Object>> selectTeacherInfoList(Map<String, String> dynamicTeacherInfo);

//    public TeacherInfo selectTeacherInfoById(Long id);

//    public int insertTeacherInfo(TeacherInfo teacherInfo);
//
//    public int updateTeacherInfo(TeacherInfo teacherInfo);
//
//    public int deleteTeacherInfoByIds(Long[] teacherIds);

    public String generateAnonymousId();

    public int insertDataByAnonymousId(TeacherInfoWithAnonymousId teacherInfoWithAnonymousId);

    public String searchMajorCodeByName(String name);
}
