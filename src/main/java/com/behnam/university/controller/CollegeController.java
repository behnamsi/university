package com.behnam.university.controller;


import com.behnam.university.dto.create.CollegeCreateDto;
import com.behnam.university.dto.detail.CollegeDetailDto;
import com.behnam.university.dto.list.CollegeListDto;
import com.behnam.university.dto.update.CollegeUpdateDto;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.service.interfaces.CollegeService;
import com.behnam.university.validation.annotations.ValidName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> getAllColleges(
            @RequestParam(required = false) @Min(0) Integer page,
            @RequestParam(required = false) @Min(1) @Max(50) Integer size,
            @SortDefault(value = "collegeName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        try {
            List<CollegeListDto> result = service.getAllColleges(pageable);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{collegeName}")
    public ResponseEntity<Object> getCollege(@PathVariable("collegeName") @ValidName String collegeName) {
        try {
            CollegeDetailDto result = service.getCollege(collegeName);
            String message = "get the data successfully";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addCollege(@Valid @RequestBody CollegeCreateDto collegeCreateDto) {
        try {
            CollegeCreateDto result = service.addCollege(collegeCreateDto);
            String message = "college successfully added";
            return globalResponse(message, CREATED, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }


    @DeleteMapping(path = "{collegeName}")
    public ResponseEntity<Object> deleteCollegeByName(
            @PathVariable("collegeName") @ValidName String collegeName) {
        try {
            String result = service.deleteCollege(collegeName);
            String message = "college successfully deleted";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @PutMapping("{collegeId}")
    public ResponseEntity<Object> updateCollege(
            @PathVariable("collegeId") Long collegeId,
            @Valid @RequestBody CollegeUpdateDto dto
    ) {

        try {
            CollegeUpdateDto result = service.updateCollege(collegeId, dto);
            String message = "college successfully updated";
            return globalResponse(message, OK, result);
        } catch (Exception e) {
            return globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
}
