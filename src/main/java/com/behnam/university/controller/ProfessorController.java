package com.behnam.university.controller;

import com.behnam.university.dto.create.ProfessorCreateDto;
import com.behnam.university.dto.detail.ProfessorDetailDto;
import com.behnam.university.dto.list.ProfessorListDto;
import com.behnam.university.dto.update.ProfessorUpdateDto;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.service.interfaces.ProfessorService;
import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidNationalId;
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
import java.util.List;

import static com.behnam.university.response.ResponseHandler.globalResponse;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/professors")
@Validated
public class ProfessorController {

    private final ProfessorService service;

    @Autowired
    public ProfessorController(
            @Qualifier("professorServiceImp") ProfessorService service) {
        this.service = service;
    }


    // methods:

    // GET methods:

    //get All Professors
    @GetMapping
    public ResponseEntity<Object> getAllProfessors(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "lastName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
       try {
           List<ProfessorListDto> result = service.getAllProfessors(pageable);
           String message = "get the data successfully";
           return globalResponse(message, OK, result);
       }catch (Exception e) {
           return globalResponse(e.getMessage(), MULTI_STATUS, null);
       }
    }

    @GetMapping(path = "{profId}")
    public ResponseEntity<Object> getProfessor(
            @PathVariable("profId") @Min(1) Long profId
    ) {
        try {
            ProfessorDetailDto result = service.getProfessor(profId);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        }catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    // get all students that belong to a professor
    @GetMapping(path = "{professorId}/students")
    public ResponseEntity<Object> getProfessorStudents(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorStudents(professorId);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    // get all students averages that belong to a professor
    @GetMapping(path = "{professorId}/students/averages")
    public ResponseEntity<Object> getProfessorStudentsAverages(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorStudentsAverages(professorId);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        }catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    // get courses of a professor
    @GetMapping(path = "{professorId}/courses")
    public ResponseEntity<Object> getProfessorsCourses(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try {
            List<String> result = service.getProfessorsCourses(professorId);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        }catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    // get students of a course of professor
    @GetMapping(path = "{professorId}/courses/{courseName}/students")
    public ResponseEntity<Object> getProfessorStudentsByCourse(
            @PathVariable("courseName") @ValidName String courseName,
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
       try {
           List<String> result = service.getProfessorStudentsByCourse(professorId, courseName);
           String message = "get the data successfully";
           return globalResponse(message, OK, result);
       }catch (Exception e) {
           return globalResponse(e.getMessage(), MULTI_STATUS, null);
       }
    }

    @GetMapping(path = "{professorId}/courses/{courseName}/students/averages")
    public ResponseEntity<Object> getProfessorStudentsAverageByCourse(
            @PathVariable("courseName") @ValidName String courseName,
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        try{
            List<String> result = service.getProfessorStudentsAverageByCourse(professorId, courseName);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        }catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }


    //POST methods:

    @PostMapping
    public ResponseEntity<Object> addProfessor(
            @Valid @RequestBody ProfessorCreateDto professorCreateDto,
            @RequestParam() @Min(1) Long collegeId
    ) {
        try {
            ProfessorCreateDto result = service.addProfessor(professorCreateDto, collegeId);
            String message = "create the data successfully";
            return globalResponse(message, CREATED, result);
        }catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    @DeleteMapping(path = "{profId}")
    public ResponseEntity<Object> deleteProfessor(
            @PathVariable("profId") @Min(1) Long id
    ) {
        try {
            Long result = service.deleteProfessor(id);
            String message = "delete the data successfully";
            return globalResponse(message, CREATED, result);
        }catch (Exception e) {
            return globalResponse(e.getMessage(), MULTI_STATUS, null);
        }
    }

    @PutMapping("{profId}")
    public ResponseEntity<Object> updateProfessor(
            @PathVariable("profId") Long profId,
            @Valid @RequestBody ProfessorUpdateDto dto
    ) {
       try {
           ProfessorUpdateDto result = service.updateProfessor(profId, dto);
           String message = "updated the data successfully";
           return globalResponse(message, CREATED, result);
       }catch (Exception e) {
           return globalResponse(e.getMessage(), MULTI_STATUS, null);
       }
    }
}
