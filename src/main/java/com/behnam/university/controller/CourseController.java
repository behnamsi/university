package com.behnam.university.controller;


import com.behnam.university.dto.create.CourseCreateDto;
import com.behnam.university.dto.detail.CourseDetailDto;
import com.behnam.university.dto.list.CourseListDto;
import com.behnam.university.dto.update.CourseUpdateDto;
import com.behnam.university.service.interfaces.CourseService;
import com.behnam.university.validation.annotations.ValidName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping(path = "api/courses")
@Validated
public class CourseController {
    private final CourseService service;

    @Autowired
    public CourseController(
            @Qualifier("courseServiceImp") CourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<CourseListDto> getAllCourses(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "courseName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        return service.getAllCourses(pageable);
    }

    @GetMapping(path = "{courseId}")
    public CourseDetailDto getCourse(
            @PathVariable("courseId") @Min(1) Long courseId
    ) {
        return service.getCourse(courseId);
    }

    @PostMapping
    public void addCourse(@Valid @RequestBody CourseCreateDto courseCreateDto,
                          @RequestParam() @Min(1) Long professorPersonalId,
                          @RequestParam() @NotNull @ValidName String collegeName
    ) {
        service.addCourse(courseCreateDto, professorPersonalId, collegeName);
    }

    @DeleteMapping("{courseName}")
    public void deleteCourseByName(
            @PathVariable("courseName") @ValidName String courseName
    ) {
        service.deleteCourseByName(courseName);
    }

    //    @PutMapping(path = "{courseId}")
//    public void updateCourse(
//            @PathVariable("courseId") @Min(1) Long courseId,
//            @RequestParam(required = false) @ValidName String courseName,
//            @RequestParam(required = false) @Min(1) @Max(3) Integer unitNumber,
//            @RequestParam(required = false) Long professorId
//    ) {
//        service.updateCourse(courseId, courseName, unitNumber, professorId);
//    }
    @PutMapping("{courseId}")
    public void updateCourse(@PathVariable("courseId") Long courseId,
                             @Valid @RequestBody CourseUpdateDto dto) {
        service.updateCourse(courseId, dto);
    }

}
