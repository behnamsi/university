package com.behnam.university.service.interfaces;

import com.behnam.university.dto.CollegeDto;
import com.behnam.university.model.College;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/30/2022
 */

public interface CollegeService {
    List<CollegeDto> getAllColleges(Integer page, Integer limit);

    College addCollege(CollegeDto collegeDto);

    void deleteCollege(String collegeName);

    void updateCollege(Long collegeId, String collegeName);
}
