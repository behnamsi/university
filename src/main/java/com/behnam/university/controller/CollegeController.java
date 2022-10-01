package com.behnam.university.controller;


import com.behnam.university.dto.college.CollegeCreateDto;
import com.behnam.university.dto.college.CollegeDetailDto;
import com.behnam.university.dto.college.CollegeListDto;
import com.behnam.university.dto.college.CollegeUpdateDto;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.response.ResponseModel;
import com.behnam.university.service.interfaces.CollegeService;
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
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/colleges")
@Validated
public class CollegeController extends CommonController{
    private final CollegeService service;
    private final ResponseHandler responseHandler;


    @Autowired
    public CollegeController(
            @Qualifier("collegeServiceImp") CollegeService service, ResponseHandler responseHandler) {
        super(service, responseHandler);
        this.service = service;
        this.responseHandler = responseHandler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('college:read')")
    public ResponseEntity<ResponseModel> getAllColleges(
            @SortDefault(value = "collegeName", direction = ASC)
            @PageableDefault(value = 10, page = 0) Pageable pageable
    ) {
        try {
            List<CollegeListDto> result = service.getAllColleges(pageable);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{collegeName}")
    @PreAuthorize("hasAuthority('college:read')")
    public ResponseEntity<ResponseModel> getCollege(@PathVariable("collegeName") @ValidName String collegeName) {
        try {
            CollegeDetailDto result = service.getCollege(collegeName);
            String message = "get the data successfully";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('college:write')")
    public ResponseEntity<ResponseModel> addCollege(@Valid @RequestBody CollegeCreateDto collegeCreateDto) {
        try {
            CollegeCreateDto result = service.addCollege(collegeCreateDto);
            String message = "college successfully added";
            return responseHandler.globalResponse(message, CREATED, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }


    @DeleteMapping(path = "{collegeName}")
    @PreAuthorize("hasAuthority('college:write')")
    public ResponseEntity<ResponseModel> deleteCollegeByName(
            @PathVariable("collegeName") @ValidName String collegeName) {
        try {
            String result = service.deleteCollege(collegeName);
            String message = "college successfully deleted";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    @PutMapping("{collegeId}")
    @PreAuthorize("hasAuthority('college:write')")
    public ResponseEntity<ResponseModel> updateCollege(
            @PathVariable("collegeId") Long collegeId,
            @Valid @RequestBody CollegeUpdateDto dto
    ) {

        try {
            CollegeUpdateDto result = service.updateCollege(collegeId, dto);
            String message = "college successfully updated";
            return responseHandler.globalResponse(message, OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
}
