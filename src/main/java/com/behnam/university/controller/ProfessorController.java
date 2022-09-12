package com.behnam.university.controller;

import com.behnam.university.dto.create.ProfessorCreateDto;
import com.behnam.university.dto.detail.ProfessorDetailDto;
import com.behnam.university.dto.list.ProfessorListDto;
import com.behnam.university.dto.update.ProfessorUpdateDto;
import com.behnam.university.service.interfaces.ProfessorService;
import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidNationalId;
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
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

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
    public List<ProfessorListDto> getAllProfessors(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "lastName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        return service.getAllProfessors(pageable);
    }

    @GetMapping(path = "{profId}")
    public ProfessorDetailDto getProfessor(
            @PathVariable("profId") @Min(1) Long profId
    ) {
        return service.getProfessor(profId);
    }

    // get all students that belong to a professor
    @GetMapping(path = "{professorId}/students")
    public List<String> getProfessorStudents(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        return service.getProfessorStudents(professorId);
    }

    // get all students averages that belong to a professor
    @GetMapping(path = "{professorId}/students/averages")
    public List<String> getProfessorStudentsAverages(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        return service.getProfessorStudentsAverages(professorId);
    }

    // get courses of a professor
    @GetMapping(path = "{professorId}/courses")
    public List<String> getProfessorsCourses(
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        return service.getProfessorsCourses(professorId);
    }

    // get students of a course of professor
    @GetMapping(path = "{professorId}/courses/{courseName}/students")
    public List<String> getProfessorStudentsByCourse(
            @PathVariable("courseName") @ValidName String courseName,
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        return service.getProfessorStudentsByCourse(professorId, courseName);
    }

    @GetMapping(path = "{professorId}/courses/{courseName}/students/averages")
    public List<String> getProfessorStudentsAverageByCourse(
            @PathVariable("courseName") @ValidName String courseName,
            @PathVariable("professorId") @Min(1) Long professorId
    ) {
        return service.getProfessorStudentsAverageByCourse(professorId, courseName);
    }


    //POST methods:

    @PostMapping
    public void addProfessor(
            @Valid @RequestBody ProfessorCreateDto professorCreateDto,
            @RequestParam() @Min(1) Long collegeId
    ) {
        service.addProfessor(professorCreateDto, collegeId);
    }

    @DeleteMapping(path = "{profId}")
    public void deleteProfessor(
            @PathVariable("profId") @Min(1) Long id
    ) {
        service.deleteProfessor(id);
    }

    //    @PutMapping(path = "{profId}")
//    public void updateProfessor(
//            @PathVariable("profId") @Min(1) Long id,
//            @RequestParam(required = false) @ValidName String first_name,
//            @RequestParam(required = false) @ValidName String last_name,
//            @RequestParam(required = false) @ValidNationalId Long nationalId,
//            @RequestParam(required = false) @ValidSevenDigits Long personalId
//    ) {
//        service.updateProfessor(id, first_name, last_name, nationalId, personalId);
//    }
    @PutMapping("{profId}")
    public void updateProfessor(
            @PathVariable("profId") Long profId,
            @Valid @RequestBody ProfessorUpdateDto dto
    ) {
        service.updateProfessor(profId, dto);
    }
}
