package com.behnam.university.service.common.course;

import com.behnam.university.dto.common.CommonDetailDto;
import com.behnam.university.model.Course;
import com.behnam.university.repository.CourseRepository;
import com.behnam.university.service.common.CommonCrudServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/27/2022
 */

@Service
public class CourseCrudServiceImpl
        extends CommonCrudServiceImpl<CourseRepository, Course> {
    public CourseCrudServiceImpl(CourseRepository repository) {
        super(repository);
    }
}
