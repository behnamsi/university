package com.behnam.university.service.interfaces;

import com.behnam.university.dto.common.CommonCreateDto;
import com.behnam.university.dto.common.CommonDetailDto;
import com.behnam.university.dto.common.CommonListDto;
import com.behnam.university.dto.common.CommonUpdateDto;
import com.behnam.university.dto.student.StudentDetailDto;
import com.behnam.university.dto.student.StudentAddCourseDto;
import com.behnam.university.dto.student.StudentCourseScoreDto;
import com.behnam.university.service.common.CommonCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/29/2022
 */

@Service

public interface StudentService extends CommonCrudService<CommonListDto, CommonDetailDto, CommonUpdateDto, CommonCreateDto, Object> {

    StudentDetailDto getStudentByUniId(Long studentUniId);
    public Long deleteStudentByUniId(Long uniId);
    List<String> getStudentCourses(Long uniId);

    void addScoreCourse(
            Long uniId,
            String courseName,
            Double score);

    void addScoreCourse(
            Long uniId,
            StudentCourseScoreDto dto);

    void deleteStudentCourse(Long uniId, String courseName);

    Double getStudentAverage(Long uniID);

    void addCourseToEnrolledCourse(Long uniId, StudentAddCourseDto dto);
}

