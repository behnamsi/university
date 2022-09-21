package com.behnam.university.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/13/2022
 */
@Component
public class ResponseHandler {
    public ResponseEntity<ResponseModel> globalResponse(
            String message, HttpStatus status, Object responseObject
    ) {
        ResponseModel response = new ResponseModel(
                message,
                status,
                responseObject);
        return new ResponseEntity<>(response, status);
    }

    //    public static ResponseEntity<Object> globalResponse(
//            String message, HttpStatus status, Object responseObject
//    ) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("message", message);
//        map.put("status", status);
//        map.put("data", responseObject);
//        return new ResponseEntity<>(map, status);
//    }
}
