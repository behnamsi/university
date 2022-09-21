package com.behnam.university.controller.user;

import com.behnam.university.dto.appUser.AppUserListDto;
import com.behnam.university.response.ResponseHandler;
import com.behnam.university.response.ResponseModel;
import com.behnam.university.security.auth.AppUserService;
import com.behnam.university.service.interfaces.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/18/2022
 */

@RestController
@RequestMapping("/management/users")
public class UserController {

    private final AppUserService service;
    private final ResponseHandler responseHandler;

    @Autowired
    public UserController(AppUserService service, ResponseHandler responseHandler) {
        this.service = service;
        this.responseHandler = responseHandler;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseModel> getAllUsers(
            @SortDefault(value = "username", direction = ASC)
            @PageableDefault Pageable pageable) {
        try {
            String message = "get the data successfully";
            List<AppUserListDto> results = service.getAllUsers(pageable);
            return responseHandler.globalResponse(message, OK, results);
        } catch (Exception e) {
            return responseHandler.globalResponse(e.getMessage(), BAD_REQUEST, null);
        }
    }
}
