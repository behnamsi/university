package com.behnam.university.controller;


import com.behnam.university.dto.course.CourseCreateDto;
import com.behnam.university.dto.course.CourseDetailDto;
import com.behnam.university.dto.course.CourseListDto;
import com.behnam.university.dto.course.CourseUpdateDto;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.response.ResponseModel;
import com.behnam.university.service.course.CourseService;
import com.behnam.university.validation.annotations.ValidName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/courses")
@Validated
public class CourseController {
    private final CourseService service;
    private final ResponseHandler responseHandler;

    @Autowired
    public CourseController(
            @Qualifier("courseServiceImp") CourseService service, ResponseHandler responseHandler) {
        this.service = service;
        this.responseHandler = responseHandler;
    }

    @GetMapping
    public ResponseEntity<ResponseModel> getAllCourses(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "courseName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        try {
            List<CourseListDto> result = service.getAllCourses(pageable);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{courseId}")
    public ResponseEntity<ResponseModel> getCourse(
            @PathVariable("courseId") @Min(1) Long courseId
    ) {
        try {
            CourseDetailDto result = service.getCourse(courseId);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseModel> addCourse(@Valid @RequestBody CourseCreateDto courseCreateDto,
                                            @RequestParam("profId") @Min(1) Long professorPersonalId,
                                            @RequestParam("collegeName") @NotNull @ValidName String collegeName
    ) {
        try {
            CourseCreateDto result = service.addCourse(courseCreateDto, professorPersonalId, collegeName);
            String message = "make the data successfully";
            return responseHandler.globalResponse(message, CREATED, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @DeleteMapping("{courseName}")
    public ResponseEntity<ResponseModel> deleteCourseByName(
            @PathVariable("courseName") @ValidName String courseName
    ) {
        try {
            String result = service.deleteCourseByName(courseName);
            String message = "deleted the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @PutMapping("{courseId}")
    public ResponseEntity<ResponseModel> updateCourse(@PathVariable("courseId") Long courseId,
                                                      @Valid @RequestBody CourseUpdateDto dto) {
        try {
            CourseUpdateDto result = service.updateCourse(courseId, dto);
            String message = "updated the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

}
