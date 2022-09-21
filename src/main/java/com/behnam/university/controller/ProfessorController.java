package com.behnam.university.controller;

import com.behnam.university.dto.professor.ProfessorCreateDto;
import com.behnam.university.dto.professor.ProfessorDetailDto;
import com.behnam.university.dto.professor.ProfessorListDto;
import com.behnam.university.dto.professor.ProfessorUpdateDto;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.response.ResponseModel;
import com.behnam.university.service.interfaces.ProfessorService;
import com.behnam.university.validation.annotations.ValidName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/professors")
@Validated
public class ProfessorController {

    private final ProfessorService service;
    private final ResponseHandler responseHandler;

    @Autowired
    public ProfessorController(
            @Qualifier("professorServiceImp") ProfessorService service, ResponseHandler responseHandler) {
        this.service = service;
        this.responseHandler = responseHandler;
    }


    // methods:

    // GET methods:

    //get All Professors
    @GetMapping
    @PreAuthorize("hasAuthority('professor:read')")
    public ResponseEntity<ResponseModel> getAllProfessors(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "lastName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        try {
            List<ProfessorListDto> result = service.getAllProfessors(pageable);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{profId}")
    @PreAuthorize("hasAuthority('professor:read')")
    public ResponseEntity<ResponseModel> getProfessor(
            @PathVariable("profId") @Min(1) Long profId
    ) {
        try {
            ProfessorDetailDto result = service.getProfessor(profId);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    // get all students that belong to a professor
    @GetMapping(path = "{professorId}/students")
    @PreAuthorize("hasAuthority('professor:read')")
    public ResponseEntity<ResponseModel> getProfessorStudents(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorStudents(professorId);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    // get all students averages that belong to a professor
    @GetMapping(path = "{professorId}/students/averages")
    @PreAuthorize("hasAuthority('professor:read')")
    public ResponseEntity<ResponseModel> getProfessorStudentsAverages(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorStudentsAverages(professorId);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    // get courses of a professor
    @GetMapping(path = "{professorId}/courses")
    @PreAuthorize("hasAuthority('professor:read')")
    public ResponseEntity<ResponseModel> getProfessorsCourses(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorsCourses(professorId);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    // get students of a course of professor
    @GetMapping(path = "{professorId}/courses/{courseName}/students")
    @PreAuthorize("hasAuthority('professor:read')")
    public ResponseEntity<ResponseModel> getProfessorStudentsByCourse(
            @PathVariable("courseName") @ValidName String courseName,
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorStudentsByCourse(professorId, courseName);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{professorId}/courses/{courseName}/students/averages")
    @PreAuthorize("hasAuthority('professor:read')")
    public ResponseEntity<ResponseModel> getProfessorStudentsAverageByCourse(
            @PathVariable("courseName") @ValidName String courseName,
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorStudentsAverageByCourse(professorId, courseName);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }


    //POST methods:

    @PostMapping
    @PreAuthorize("hasAuthority('professor:write')")
    public ResponseEntity<ResponseModel> addProfessor(
            @Valid @RequestBody ProfessorCreateDto professorCreateDto,
            @RequestParam() @Min(1) Long collegeId
    ) {
        try {
            ProfessorCreateDto result = service.addProfessor(professorCreateDto, collegeId);
            String message = "create the data successfully";
            return responseHandler.globalResponse(message, CREATED, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "{profId}")
    @PreAuthorize("hasAuthority('professor:write')")
    public ResponseEntity<ResponseModel> deleteProfessor(
            @PathVariable("profId") @Min(1) Long id
    ) {
        try {
            Long result = service.deleteProfessor(id);
            String message = "delete the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @PutMapping("{profId}")
    @PreAuthorize("hasAuthority('professor:write')")
    public ResponseEntity<ResponseModel> updateProfessor(
            @PathVariable("profId") Long profId,
            @Valid @RequestBody ProfessorUpdateDto dto
    ) {
        try {
            ProfessorUpdateDto result = service.updateProfessor(profId, dto);
            String message = "updated the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
}
