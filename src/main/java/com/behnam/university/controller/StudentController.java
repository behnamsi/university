package com.behnam.university.controller;

import com.behnam.university.dto.common.CommonListDto;
import com.behnam.university.dto.student.*;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.response.ResponseModel;
import com.behnam.university.service.interfaces.StudentService;
import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidSevenDigits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private final ResponseHandler responseHandler;
    private final StudentService service;

    //constructor
    @Autowired
    public StudentController(
            ResponseHandler responseHandler,
            StudentService service) {
        this.responseHandler = responseHandler;

        this.service = service;
    }

    // GET methods

    // get the all of students

    /**
     * @param pageable return page of content in database
     * @return return the content of page witch is student lists
     */
    @GetMapping
    @PreAuthorize("hasAuthority('student:read')")
    public ResponseEntity<ResponseModel> getAllStudents(
            @SortDefault(value = "lastName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        try {
            List<CommonListDto> result = service.getAll(pageable);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param studentUniId to find the specific student
     * @return details of the student
     */
    @GetMapping(path = "{studentUniId}")
    @PreAuthorize("hasAuthority('student:read')")
    public ResponseEntity<ResponseModel> getStudent(
            @PathVariable("studentUniId") Long studentUniId
    ) {
        try {
            StudentDetailDto result = (StudentDetailDto) service.getStudentByUniId(studentUniId);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    //get student courses using university id

    /**
     * @param uniID find specific student and get the courses
     * @return courses of the student
     */
    @GetMapping(path = "{uniID}/courses")
    @PreAuthorize("hasAuthority('student:read')")
    public ResponseEntity<ResponseModel> getStudentCourses(
            @PathVariable("uniID") @ValidSevenDigits Long uniID
    ) {
        try {
            List<String> result = service.getStudentCourses(uniID);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    // get student average
    @GetMapping(path = "{uniID}/averages")
    @PreAuthorize("hasAuthority('student:read')")
    public ResponseEntity<ResponseModel> getStudentAverage(@PathVariable("uniID") @ValidSevenDigits Long uniID) {
        try {
            Double result = service.getStudentAverage(uniID);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }


    //POST methods

    /**
     * @param student get the info of the student that wants to register in university
     * @return student creation body
     */
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<ResponseModel> addStudent(
            @Valid @RequestBody StudentCreateDto student,
            @RequestParam @NotNull @ValidName String collegeName
    ) {
        try {
//            StudentCreateDto result = (StudentCreateDto) service.addStudent(student, collegeName);
            String message = "create the data successfully";
            return responseHandler.globalResponse(message, CREATED, null);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param uniId find specific student
     * @return uni id of fired student
     */
    @DeleteMapping(path = "{uniId}")
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<ResponseModel> deleteStudentByUniId(
            @PathVariable("uniId") @ValidSevenDigits Long uniId
    ) {

        try {
            Long result = service.deleteStudentByUniId(uniId);
            String message = "deleted the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param uniId find the student
     * @param dto   get the new info that wants to change and adding courses for enrolling
     * @return the body of changing data
     */
    @PutMapping("{uniId}")
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<ResponseModel> updateStudent(@PathVariable("uniId") Long uniId,
                                                       @Valid @RequestBody StudentUpdateDto dto) {
        try {
//            StudentUpdateDto result = (StudentUpdateDto) service.updateStudent(uniId, dto);
            String message = "updated the data successfully";
            return responseHandler.globalResponse(message, OK, null);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param uniId find the student
     * @param dto   get the course name to add it to enrolled courses
     * @return nothing but status
     */

    @PutMapping("{uniId}/courses")
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<ResponseModel> addCourseToEnrolledCourse(
            @PathVariable Long uniId,
            @Valid @RequestBody StudentAddCourseDto dto
    ) {

        try {
            service.addCourseToEnrolledCourse(uniId, dto);
            String message = "added the course to enrolled courses successfully";
            return responseHandler.globalResponse(message, OK, null);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    /**
     * @param uniId find the student
     * @param dto   get the course name and its score
     * @return nothing to return but status
     */
    @PutMapping(path = "{uniId}/courses/scores")
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<ResponseModel> addScoreCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @Valid @RequestBody StudentCourseScoreDto dto
    ) {

        try {
            service.addScoreCourse(uniId, dto);
            String message = "score: " + dto.getScore()
                    + " added to the course: " + dto.getCourseName() + " successfully";
            return responseHandler.globalResponse(message, OK, null);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }


    // delete course for student

    /**
     * @param uniId      find student
     * @param courseName deleting student course name
     * @return nothing but status
     */
    @PutMapping(path = "{uniId}/courses/delete/{courseName}")
    @PreAuthorize("hasAuthority('student:write')")
    public ResponseEntity<ResponseModel> deleteStudentCourse(
            @PathVariable("uniId") @ValidSevenDigits Long uniId,
            @PathVariable @ValidName String courseName
    ) {
        try {
            service.deleteStudentCourse(uniId, courseName);
            String message = "course: " + courseName + " deleted successfully";
            return responseHandler.globalResponse(message, OK, null);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
}
