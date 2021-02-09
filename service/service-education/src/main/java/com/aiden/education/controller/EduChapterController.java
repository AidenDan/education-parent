package com.aiden.education.controller;

import com.aiden.commenUtils.CommonResult;
import com.aiden.education.entity.EduChapter;
import com.aiden.education.entity.chapter.ChapterVo;
import com.aiden.education.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Aiden
 * @since 2021-01-10
 */

@Api(description = "课程章节管理接口")
@RestController
@CrossOrigin
@RequestMapping("/education/edu-chapter")
public class EduChapterController {
    @Autowired
    EduChapterService chapterService;

    @ApiOperation(value = "根据课程id获取课程章节信息")
    @GetMapping("/getChapterInfoByCourseId/{courseId}")
    public CommonResult getChapterInfoByCourseId(@PathVariable("courseId") String courseId) {
        List<ChapterVo> chapterInfoList = chapterService.getChapterInfoByCourseId(courseId);
        return CommonResult.success().data("chapterInfoList", chapterInfoList);
    }

    @ApiOperation(value = "添加课程章信息")
    @PostMapping("/addChapterInfo")
    public CommonResult addChapterInfo(@RequestBody EduChapter eduChapter) {
        boolean b = chapterService.addChapterInfo(eduChapter);
        return b ? CommonResult.success() : CommonResult.fail();
    }

    @ApiOperation(value = "修改课程章信息")
    @PutMapping("/updateChapterInfo")
    public CommonResult updateChapterInfo(@RequestBody EduChapter eduChapter) {
        boolean b = chapterService.updateChapterInfo(eduChapter);
        return b ? CommonResult.success() : CommonResult.fail();
    }

    @ApiOperation(value = "删除课程章信息")
    @DeleteMapping("/deleteChapterInfo/{id}")
    public CommonResult deleteChapterInfo(@PathVariable("id") String id) {
        boolean b = chapterService.deleteChapterInfoById(id);
        return b ? CommonResult.success() : CommonResult.fail();
    }

    @ApiOperation(value = "查询课程章信息")
    @DeleteMapping("/getChapterInfo/{id}")
    public CommonResult getChapterInfo(@PathVariable("id") String id) {
        EduChapter chapterInfo = chapterService.getChapterInfo(id);
        return CommonResult.success().data("chapterInfo", chapterInfo);
    }
}

