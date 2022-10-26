package com.ruoyi.web.controller.hrms;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hrms.domain.TeacherInfoWithAnonymousId;
import com.ruoyi.hrms.service.ITeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
@Anonymous
public class TeacherInfoController extends BaseController {

    @Autowired
    private ITeacherInfoService teacherInfoService;

    @GetMapping("/list")
    public AjaxResult list(@RequestParam Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("total", teacherInfoService.selectTeacherCount());
        result.put("rows", teacherInfoService.selectTeacherInfoList(params));
        return AjaxResult.success(result);
    }

//    @GetMapping("/{teacherId}")
//    public AjaxResult getTeacherById(@PathVariable Long teacherId) {
//        return AjaxResult.success(teacherInfoService.selectTeacherInfoById(teacherId));
//    }
//
//    @PostMapping()
//    public AjaxResult add(@RequestBody TeacherInfo teacherInfo) {
//        int code = teacherInfoService.insertTeacherInfo(teacherInfo);
//        if(code == 0) {
//            return AjaxResult.error("用户已存在");
//        }
//        return AjaxResult.success();
//    }
//
//    @PutMapping
//    public AjaxResult edit(@RequestBody TeacherInfo teacherInfo) {
//        return toAjax(teacherInfoService.updateTeacherInfo(teacherInfo));
//    }
//
//    @DeleteMapping("/{teacherIds}")
//    public AjaxResult remove(@PathVariable Long[] teacherIds){
//        return toAjax(teacherInfoService.deleteTeacherInfoByIds(teacherIds));
//    }

    @GetMapping("/major")
    public AjaxResult getMajorCode(String name) {
        return AjaxResult.success(teacherInfoService.searchMajorCodeByName(name));
    }

    @GetMapping("/getAnonymousId")
    @PreAuthorize("@ss.hasPermi('teacher:delete')")
    public AjaxResult getAnonymousId() {
        return AjaxResult.success(teacherInfoService.generateAnonymousId());
    }

    @PostMapping("/addTeacherInfoByAnonymousId")
    public AjaxResult InsertDataByAnonymousId(@RequestBody TeacherInfoWithAnonymousId teacherInfoWithAnonymousId) {
        return toAjax(teacherInfoService.insertDataByAnonymousId(teacherInfoWithAnonymousId));
    }
}
