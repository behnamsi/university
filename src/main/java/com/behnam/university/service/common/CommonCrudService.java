package com.behnam.university.service.common;

import com.behnam.university.dto.common.CommonListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/26/2022
 */

public interface CommonCrudService<LISTDTO, DETAIL_DTO, UPDATE_DTO, CREATE_DTO, GET_MODEL_KEY> {

    List<LISTDTO> getAll(Pageable pageable);

    DETAIL_DTO get(GET_MODEL_KEY key);

    CREATE_DTO add(CREATE_DTO createDto, Object... objects);

    UPDATE_DTO update(GET_MODEL_KEY key, UPDATE_DTO updateDto);

    GET_MODEL_KEY delete(GET_MODEL_KEY key);


}
