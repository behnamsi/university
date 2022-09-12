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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

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
    public List<StudentListDto> getAllStudents(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "lastName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        return service.getAllStudents(pageable);
    }

    @GetMapping(path = "{studentUniId}")
    public StudentDetailDto getStudent(
            @PathVariable("studentUniId") Long studentUniId
    ) {
        return service.getStudent(studentUniId);
    }

    //get student courses using university id
    @GetMapping(path = "{uniID}/courses")
    public List<String> getStudentCourses(
            @PathVariable("uniID") @ValidSevenDigits Long uniID
    ) {
        return service.getStudentCourses(uniID);
    }

    // get student average
    @GetMapping(path = "{uniID}/averages")
    public Double getStudentAverage(@PathVariable("uniID") @ValidSevenDigits Long uniID) {
        return service.getStudentAverage(uniID);
    }


    //POST methods
    @PostMapping
    public void addStudent(
            @Valid @RequestBody StudentCreateDto student,
            @RequestParam @NotNull @ValidName String collegeName
    ) {
        service.addStudent(student, collegeName);
    }


//    @DeleteMapping(path = "{id}/delete")
//    public void deleteStudent(
//            @PathVariable("id") @Min(1) Long id
//    ) {
//        service.deleteStudent(id);
//    }

    @DeleteMapping(path = "{uniId}")
    public void deleteStudentByUniId(
            @PathVariable("uniId") @ValidSevenDigits Long uniId
    ) {

        service.deleteStudentByUniId(uniId);
    }

    // update with university id
//    @PutMapping(path = "{uniId}")
//    public void updateStudent(
//            @PathVariable("uniId") @ValidSevenDigits Long uniId,
//            @RequestParam(required = false)  String first_name,
//            @RequestParam(required = false)  String last_name,
//            @RequestParam(required = false) List<String> courses,
//            @RequestParam(required = false)  Long nationalId
//    ) {
//        service.updateStudent(uniId, first_name, last_name, courses, nationalId);
//    }
    @PutMapping("{uniId}")
    public void updateStudent(@PathVariable("uniId") Long uniId,
                              @Valid @RequestBody StudentUpdateDto dto) {
        service.updateStudent(uniId,dto);
    }

    // add course for student
    @PutMapping(path = "{uniId}/courses/{courseName}/scores")
    public void addScoreCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @PathVariable("courseName") @ValidName String courseName,
            @RequestParam @Min(0) @Max(20) Double score
    ) {
        service.addScoreCourse(uniId, courseName, score);
    }

    // delete course for student
    @PutMapping(path = "{uniId}/courses/delete")
    public void deleteStudentCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @RequestParam @ValidName String courseName
    ) {
        service.deleteStudentCourse(uniId, courseName);
    }
}
