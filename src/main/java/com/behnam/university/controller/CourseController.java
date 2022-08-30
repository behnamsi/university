package com.behnam.university.controller;


import com.behnam.university.dto.CourseDto;
import com.behnam.university.service.interfaces.CourseService;
import com.behnam.university.validation.annotations.ValidName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "api/courses")
@Validated
public class CourseController {
    private final CourseService service;

    @Autowired
    public CourseController(
            @Qualifier("courseServiceImp")CourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<CourseDto> getAllCourses(
            @RequestParam(required = false) @Min(1) Integer page,
            @RequestParam(required = false) @Min(1) Integer limit
    ) {
        return service.getAllCourses(page, limit);
    }

    @GetMapping(path = "{courseId}")
    public CourseDto getCourse(
            @PathVariable("courseId") @Min(1) Long courseId
    ) {
        return service.getCourse(courseId);
    }

    @PostMapping
    public void addCourse(@Valid @RequestBody CourseDto courseDto,
                          @RequestParam() @Min(1) Long professorPersonalId,
                          @RequestParam() @NotNull @ValidName String collegeName
    ) {
        service.addCourse(courseDto, professorPersonalId, collegeName);
    }

    @DeleteMapping("{courseName}")
    public void deleteCourseByName(
            @PathVariable("courseName") @ValidName String courseName
    ) {
        service.deleteCourseByName(courseName);
    }

    @PutMapping(path = "{courseId}")
    public void updateCourse(
            @PathVariable("courseId") @Min(1) Long courseId,
            @RequestParam(required = false) @ValidName String courseName,
            @RequestParam(required = false) @Min(1) @Max(3) Integer unitNumber,
            @RequestParam(required = false) Long professorId
    ) {
        service.updateCourse(courseId, courseName, unitNumber, professorId);
    }
}
