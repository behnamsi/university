package com.behnam.university.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/13/2022
 */

public class ResponseHandler {
    public static ResponseEntity<Object> globalResponse(
            String message, HttpStatus status, Object responseObject
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status);
        map.put("data", responseObject);
        return new ResponseEntity<>(map, status);
    }
}
