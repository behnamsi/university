package com.behnam.university.service.common.college;

import com.behnam.university.dto.common.CommonUpdateDto;
import com.behnam.university.model.College;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.service.common.CommonCrudServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/28/2022
 */

@Service
public class CollegeCrudServiceImpl extends CommonCrudServiceImpl<CollegeRepository, College> {
    public CollegeCrudServiceImpl(CollegeRepository repository) {
        super(repository);
    }

}
