package com.behnam.university.service.common.student;

import com.behnam.university.model.Student;
import com.behnam.university.repository.StudentRepository;
import com.behnam.university.service.common.CommonCrudServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/26/2022
 */

@Service
public class StudentCrudServiceImpl extends CommonCrudServiceImpl<StudentRepository, Student> {
    public StudentCrudServiceImpl(StudentRepository repository) {
        super(repository);
    }


}
