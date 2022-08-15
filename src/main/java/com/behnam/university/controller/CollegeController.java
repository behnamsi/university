package com.behnam.university.controller;


import com.behnam.university.dto.CollegeDto;
import com.behnam.university.service.CollegeService;
import com.behnam.university.validation.annotations.ValidName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "api/colleges")
@Validated
public class CollegeController {
    private final CollegeService service;


    @Autowired
    public CollegeController(CollegeService service) {
        this.service = service;
    }

    @GetMapping
    public List<CollegeDto> getAllColleges(
            @RequestParam(required = false) @Min(1) Integer page,
            @RequestParam(required = false) @Min(1) Integer limit
    ) {
        return service.getAllColleges(page, limit);
    }

    @PostMapping
    public void addCollege(@Valid @RequestBody CollegeDto collegeDto) {
        service.addCollege(collegeDto);
    }


    @DeleteMapping(path = "{collegeName}")
    public void deleteCollegeByName(
            @PathVariable("collegeName") @ValidName String collegeName) {
        service.deleteCollege(collegeName);
    }

    @PutMapping(path = "{collegeId}")
    public void updateCollege(
            @PathVariable("collegeId") @Min(1) Long collegeId,
            @RequestParam(required = false) @ValidName String collegeName
    ) {
        service.updateCollege(collegeId, collegeName);
    }
}
