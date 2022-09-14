package com.behnam.university.controller;

import com.behnam.university.dto.create.StudentCreateDto;
import com.behnam.university.dto.detail.StudentDetailDto;
import com.behnam.university.dto.list.StudentListDto;
import com.behnam.university.dto.update.StudentUpdateDto;
import com.behnam.university.service.interfaces.StudentService;
import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidSevenDigits;
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

import static com.behnam.university.response.ResponseHandler.globalResponse;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.*;

// end imports

/**
 * @author Behnam Si
 */
@RestController
@RequestMapping(path = "api/students")
@Validated
public class StudentController {
    private final StudentService service;

    //constructor
    @Autowired
    public StudentController(
            @Qualifier("studentServiceImp") StudentService service) {
        this.service = service;
    }

    // GET methods

    // get the all of students
    @GetMapping
    public ResponseEntity<Object> getAllStudents(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "lastName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        try {
            List<StudentListDto> result = service.getAllStudents(pageable);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    @GetMapping(path = "{studentUniId}")
    public ResponseEntity<Object> getStudent(
            @PathVariable("studentUniId") Long studentUniId
    ) {
        try {
            StudentDetailDto result = service.getStudent(studentUniId);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    //get student courses using university id
    @GetMapping(path = "{uniID}/courses")
    public ResponseEntity<Object> getStudentCourses(
            @PathVariable("uniID") @ValidSevenDigits Long uniID
    ) {
        try {
            List<String> result = service.getStudentCourses(uniID);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    // get student average
    @GetMapping(path = "{uniID}/averages")
    public ResponseEntity<Object> getStudentAverage(@PathVariable("uniID") @ValidSevenDigits Long uniID) {
        try {
            Double result = service.getStudentAverage(uniID);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }


    //POST methods
    @PostMapping
    public ResponseEntity<Object> addStudent(
            @Valid @RequestBody StudentCreateDto student,
            @RequestParam @NotNull @ValidName String collegeName
    ) {
        try {
            StudentCreateDto result = service.addStudent(student, collegeName);
            String message = "create the data successfully";
            return globalResponse(message, CREATED, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    @DeleteMapping(path = "{uniId}")
    public ResponseEntity<Object> deleteStudentByUniId(
            @PathVariable("uniId") @ValidSevenDigits Long uniId
    ) {

        try {
            Long result = service.deleteStudentByUniId(uniId);
            String message = "deleted the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    @PutMapping("{uniId}")
    public ResponseEntity<Object> updateStudent(@PathVariable("uniId") Long uniId,
                                                @Valid @RequestBody StudentUpdateDto dto) {
        try {
            StudentUpdateDto result = service.updateStudent(uniId, dto);
            String message = "deleted the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    // add course for student
    @PutMapping(path = "{uniId}/courses/{courseName}/scores")
    public ResponseEntity<Object> addScoreCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @PathVariable("courseName") @ValidName String courseName,
            @RequestParam @Min(0) @Max(20) Double score
    ) {
        try {
            service.addScoreCourse(uniId, courseName, score);
            String message = "score: " + score + " added to the course: " + courseName + " successfully";
            return globalResponse(message, OK, null);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    // delete course for student
    @PutMapping(path = "{uniId}/courses/delete")
    public ResponseEntity<Object> deleteStudentCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @RequestParam @ValidName String courseName
    ) {
        try {
            service.deleteStudentCourse(uniId, courseName);
            String message = "course: " + courseName + " deleted successfully";
            return globalResponse(message, OK, null);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }
}
