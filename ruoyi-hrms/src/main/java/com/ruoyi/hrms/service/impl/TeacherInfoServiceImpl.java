package com.ruoyi.hrms.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.hrms.domain.MajorDic;
import com.ruoyi.hrms.domain.TeacherInfoWithAnonymousId;
import com.ruoyi.hrms.mapper.MajorDicMapper;
import com.ruoyi.hrms.mapper.TeacherInfoMapper;
import com.ruoyi.hrms.service.ITeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TeacherInfoServiceImpl implements ITeacherInfoService {

    private final static String SYS_TEACHER_TABLE_ID = "hrms:teacher";

    @Autowired
    private MajorDicMapper majorDicMapper;

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Integer selectTeacherCount() {
        return teacherInfoMapper.selectTeacherCount();
    }

    @Override
    public Integer selectDynamicTableCount() {
        return teacherInfoMapper.selectDynamicTableCount();
    }

    @Override
    public Integer selectDynamicDicTableCount() {
        return teacherInfoMapper.selectDynamicDicTableCount();
    }

    @Override
    public Integer selectDynamicValidateCount() {
        return teacherInfoMapper.selectDynamicValidateCount();
    }

    @Override
    public List<Map<String, Object>> selectTeacherInfoList(Map<String, String> dynamicTeacherInfo) {
        // 分页参数
        if (dynamicTeacherInfo.get("mPageNum") != null && dynamicTeacherInfo.get("mPageSize") != null) {
            int mPageNum = Integer.parseInt(dynamicTeacherInfo.get("mPageNum"));
            int mPageSize = Integer.parseInt(dynamicTeacherInfo.get("mPageSize"));
            dynamicTeacherInfo.put("mPageSize", String.valueOf(mPageSize));
            dynamicTeacherInfo.put("mOffset", String.valueOf((mPageNum - 1) * mPageSize));
        } else {
            dynamicTeacherInfo.put("mPageSize", "10");
            dynamicTeacherInfo.put("mOffset", "0");
        }

        // 查询列表数据
        List<Map<String, Object>> teacherInfoList = teacherInfoMapper.selectTeacherInfoList(dynamicTeacherInfo);
        List<Map<String, Object>> result = new ArrayList<>();

        // 第一层分组
        List<List<Map<String, Object>>> lists = new ArrayList<>(teacherInfoList.stream().collect(Collectors.groupingBy(item -> item.get("teacherId"))).values());
        for (List<Map<String, Object>> list : lists) {
            Map<String, Object> teacherInfoMap = new HashMap<>();
            teacherInfoMap.put("teacherId", list.get(0).get("teacherId"));
            teacherInfoMap.put("examineLeader", list.get(0).get("examineLeader"));
            teacherInfoMap.put("createTime", list.get(0).get("createTime"));
            teacherInfoMap.put("examineHr", list.get(0).get("examineHr"));
            List<Map<String, Object>> itemList = new ArrayList<>();
            for (Map<String, Object> item : list) {
                if (item.get("dynamicId") != null) {
                    itemList.add(item);
                }
            }
            // 第二层分组
            List<Map<String, Object>> dynamicChildrenList = new ArrayList<>();
            List<List<Map<String, Object>>> dynamicLists = new ArrayList<>(itemList.stream().collect(Collectors.groupingBy(item -> item.get("dynamicId"))).values());
            for (List<Map<String, Object>> dynamicList : dynamicLists) {
                Map<String, Object> dynamicMap = new HashMap<>();
                dynamicMap.put("id", dynamicList.get(0).get("dynamicId"));
                dynamicMap.put("type", dynamicList.get(0).get("dynamicType"));
                dynamicMap.put("name", dynamicList.get(0).get("dynamicName"));
                for (Map<String, Object> dynamicItem : dynamicList) {
                    if (dynamicItem.get("valTeacherInfoDynamicId") == dynamicItem.get("dynamicId") && dynamicItem.get("valTeacherInfoId") == dynamicItem.get("teacherId")) {
                        dynamicMap.put("value", dynamicList.get(0).get("value"));
                    } else {
                        dynamicMap.put("value", "");
                    }
                    if (dynamicItem.get("validateTeacherInfoDynamicId") == dynamicItem.get("dynamicId")) {
                        Map<String, Object> validateMap = new HashMap<>();
                        validateMap.put("id", dynamicItem.get("validateId"));
                        validateMap.put("required", dynamicItem.get("validateRequired"));
                        validateMap.put("maxLength", dynamicItem.get("validateMaxLength"));
                        validateMap.put("dateTimeFormat", dynamicItem.get("validateDateTimeFormat"));
                        dynamicMap.put("validate", validateMap);
                    }
                }
                if (dynamicList.get(0).get("dicTeacherInfoDynamicId") != null) {
                    List<Map<String, Object>> dicList = new ArrayList<>();
                    for (Map<String, Object> dicItem : dynamicList) {
                        Map<String, Object> dicMap = new HashMap<>();
                        dicMap.put("id", dicItem.get("dicId"));
                        dicMap.put("label", dicItem.get("dicLabel"));
                        dicMap.put("value", dicItem.get("dicValue"));
                        dicList.add(dicMap);
                    }
                    dynamicMap.put("dicList", dicList);
                }
                dynamicChildrenList.add(dynamicMap);
            }
            teacherInfoMap.put("children", dynamicChildrenList);
            result.add(teacherInfoMap);
        }
        return result;
    }

//    @Override
//    public TeacherInfo selectTeacherInfoById(Long id) {
//        return teacherInfoMapper.selectTeacherInfoById(id);
//    }

//    @Override
//    public int insertTeacherInfo(TeacherInfo teacherInfo) {
//        TeacherInfo teacher = new TeacherInfo();
//        teacher.setIdCard(teacherInfo.getIdCard());
//        List<TeacherInfo> teacherInfoList = teacherInfoMapper.selectTeacherInfoList(teacher);
//        if (!teacherInfoList.isEmpty()) {
//            return 0;
//        }
//        return teacherInfoMapper.insertTeacherInfo(teacherInfo);
//    }
//
//    @Override
//    public int updateTeacherInfo(TeacherInfo teacherInfo) {
//        return teacherInfoMapper.updateTeacherInfo(teacherInfo);
//    }
//
//    @Override
//    public int deleteTeacherInfoByIds(Long[] teacherIds) {
//        return teacherInfoMapper.deleteTeacherInfoByIds(teacherIds);
//    }

    @Override
    public String generateAnonymousId() {
        String uuid = IdUtils.simpleUUID();
        redisCache.setCacheObject(SYS_TEACHER_TABLE_ID + ":" + uuid, uuid, 1, TimeUnit.DAYS);
        return uuid;
    }

    @Override
    public int insertDataByAnonymousId(TeacherInfoWithAnonymousId teacherInfoWithAnonymousId) {
        String cacheId = redisCache.getCacheObject(SYS_TEACHER_TABLE_ID + ":" + teacherInfoWithAnonymousId.getAnonymousId());
        if (!Objects.equals(cacheId, null)) {
//            teacherInfoMapper.insertTeacherInfo(teacherInfoWithAnonymousId);
            redisCache.deleteObject(SYS_TEACHER_TABLE_ID + ":" + teacherInfoWithAnonymousId.getAnonymousId());
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String searchMajorCodeByName(String name) {
        MajorDic majorDic = majorDicMapper.selectMajorDicByName(name);
        if (!Objects.equals(majorDic, null)) {
            return majorDic.getMajorCode();
        } else {
            return "";
        }
    }
}
