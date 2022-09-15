package com.behnam.university.controller;

import com.behnam.university.dto.create.StudentCreateDto;
import com.behnam.university.dto.detail.StudentDetailDto;
import com.behnam.university.dto.list.StudentListDto;
import com.behnam.university.dto.studentCourses.StudentAddCourseDto;
import com.behnam.university.dto.studentCourses.StudentCourseScoreDto;
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

    /**
     * @param page     optional param for getting the page By default is first page
     * @param size     optional param for getting the size By default is 10
     * @param pageable return page of content in database
     * @return return the content of page witch is student lists
     */
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
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param studentUniId to find the specific student
     * @return details of the student
     */
    @GetMapping(path = "{studentUniId}")
    public ResponseEntity<Object> getStudent(
            @PathVariable("studentUniId") Long studentUniId
    ) {
        try {
            StudentDetailDto result = service.getStudent(studentUniId);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    //get student courses using university id

    /**
     * @param uniID find specific student and get the courses
     * @return courses of the student
     */
    @GetMapping(path = "{uniID}/courses")
    public ResponseEntity<Object> getStudentCourses(
            @PathVariable("uniID") @ValidSevenDigits Long uniID
    ) {
        try {
            List<String> result = service.getStudentCourses(uniID);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
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
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }


    //POST methods

    /**
     * @param student     get the info of the student that wants to register in university
     * @param collegeName Highlighting the college that the student want to educate.
     * @return student creation body
     */
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
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param uniId find specific student
     * @return uni id of fired student
     */
    @DeleteMapping(path = "{uniId}")
    public ResponseEntity<Object> deleteStudentByUniId(
            @PathVariable("uniId") @ValidSevenDigits Long uniId
    ) {

        try {
            Long result = service.deleteStudentByUniId(uniId);
            String message = "deleted the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param uniId find the student
     * @param dto   get the new info that wants to change and adding courses for enrolling
     * @return the body of changing data
     */
    @PutMapping("{uniId}")
    public ResponseEntity<Object> updateStudent(@PathVariable("uniId") Long uniId,
                                                @Valid @RequestBody StudentUpdateDto dto) {
        try {
            StudentUpdateDto result = service.updateStudent(uniId, dto);
            String message = "updated the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     *
     * @param uniId find the student
     * @param dto get the course name to add it to enrolled courses
     * @return nothing but status
     */

    @PutMapping("{uniId}/courses")
    public ResponseEntity<Object> addCourseToEnrolledCourse(
            @PathVariable Long uniId,
            @Valid @RequestBody StudentAddCourseDto dto
            ) {

        try {
            service.addCourseToEnrolledCourse(uniId, dto);
            String message = "added the course to enrolled courses successfully";
            return globalResponse(message, OK, null);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
    /**
     * @param uniId      find the student
     * @param dto get the course name and its score
     * @return nothing to return but status
     */
    @PutMapping(path = "{uniId}/courses/scores")
    public ResponseEntity<Object> addScoreCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @Valid @RequestBody StudentCourseScoreDto dto
    ) {

        try {
            service.addScoreCourse(uniId, dto);
            String message = "score: " + dto.getScore()
                    + " added to the course: " + dto.getCourseName() + " successfully";
            return globalResponse(message, OK, null);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }


    // delete course for student

    /**
     * @param uniId      find student
     * @param courseName deleting student course name
     * @return nothing but status
     */
    @PutMapping(path = "{uniId}/courses/delete/{courseName}")
    public ResponseEntity<Object> deleteStudentCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @PathVariable @ValidName String courseName
    ) {
        try {
            service.deleteStudentCourse(uniId, courseName);
            String message = "course: " + courseName + " deleted successfully";
            return globalResponse(message, OK, null);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
}
