package com.behnam.university.controller;


import com.behnam.university.dto.create.CollegeCreateDto;
import com.behnam.university.dto.detail.CollegeDetailDto;
import com.behnam.university.dto.list.CollegeListDto;
import com.behnam.university.dto.update.CollegeUpdateDto;
import com.behnam.university.service.interfaces.CollegeService;
import com.behnam.university.validation.annotations.ValidName;
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
@RequestMapping(path = "api/colleges")
@Validated
public class CollegeController {
    private final CollegeService service;


    @Autowired
    public CollegeController(
            @Qualifier("collegeServiceImp") CollegeService service) {
        this.service = service;
    }

    @GetMapping
    public List<CollegeListDto> getAllColleges(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "collegeName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        return service.getAllColleges(pageable);
    }

    @GetMapping(path = "{collegeName}")
    public CollegeDetailDto getCollege(@PathVariable("collegeName") @ValidName String collegeName) {
        return service.getCollege(collegeName);
    }

    @PostMapping
    public void addCollege(@Valid @RequestBody CollegeCreateDto collegeCreateDto) {
        service.addCollege(collegeCreateDto);
    }


    @DeleteMapping(path = "{collegeName}")
    public void deleteCollegeByName(
            @PathVariable("collegeName") @ValidName String collegeName) {
        service.deleteCollege(collegeName);
    }

//    @PutMapping(path = "{collegeId}")
//    public void updateCollege(
//            @PathVariable("collegeId") @Min(1) Long collegeId,
//            @RequestParam(required = false) @ValidName String collegeName
//    ) {
//        service.updateCollege(collegeId, collegeName);
//    }
    @PutMapping("{collegeId}")
    public void updateCollege(
            @PathVariable("collegeId") Long collegeId ,
            @Valid @RequestBody CollegeUpdateDto dto
            ){
        service.updateCollege(collegeId,dto);
    }
}
