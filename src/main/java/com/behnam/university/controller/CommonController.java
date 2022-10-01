package com.behnam.university.controller;

import com.behnam.university.dto.common.CommonCreateDto;
import com.behnam.university.dto.common.CommonDetailDto;
import com.behnam.university.dto.common.CommonListDto;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.response.ResponseModel;
import com.behnam.university.service.common.CommonCrudService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/26/2022
 */

public class CommonController<SERVICE extends CommonCrudService> {
    private final SERVICE service;
    protected final ResponseHandler responseHandler;

    public CommonController(SERVICE service, ResponseHandler responseHandler) {
        this.service = service;
        this.responseHandler = responseHandler;
    }

    //TODO write the 5 common methods
    public ResponseEntity<ResponseModel> getAll(Pageable pageable) {
        try {
            List<CommonListDto> result = service.getAll(pageable);
            String message = "get All data successfully";
            return responseHandler.globalResponse(message,
                    OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    public ResponseEntity<ResponseModel> get(Object key) {
        try {
            CommonDetailDto result = (CommonDetailDto) service.get(key);
            String message = "get data successfully";
            return responseHandler.globalResponse(message,
                    OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }

    public ResponseEntity<ResponseModel> add(CommonCreateDto dto) {
        try {
            CommonCreateDto result = (CommonCreateDto) service.add(dto);
            String message = "create data successfully";
            return responseHandler.globalResponse(message,
                    OK, result);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
}
