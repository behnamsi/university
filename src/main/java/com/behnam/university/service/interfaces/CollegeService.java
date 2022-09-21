package com.behnam.university.service.interfaces;

import com.behnam.university.dto.college.CollegeCreateDto;
import com.behnam.university.dto.college.CollegeDetailDto;
import com.behnam.university.dto.college.CollegeListDto;
import com.behnam.university.dto.college.CollegeUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/30/2022
 */

@Service
public interface CollegeService {
    List<CollegeCreateDto> getAllColleges(Integer page, Integer limit);

    List<CollegeListDto> getAllColleges(Pageable pageable);

    CollegeDetailDto getCollege(String collegeName);

    CollegeCreateDto addCollege(CollegeCreateDto collegeCreateDto);

    String deleteCollege(String collegeName);

    void updateCollege(Long collegeId, String collegeName);

    CollegeUpdateDto updateCollege(Long collegeId, CollegeUpdateDto dto);
}
