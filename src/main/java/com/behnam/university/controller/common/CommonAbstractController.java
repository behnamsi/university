package com.behnam.university.controller.common;

import com.behnam.university.service.common.CommonCrudAbstractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/20/2022
 */

@RestController
@RequestMapping()
public class CommonAbstractController {

    private final CommonCrudAbstractService service;

    public CommonAbstractController(CommonCrudAbstractService service) {
        this.service = service;
    }
}
