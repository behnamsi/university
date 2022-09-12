package com.behnam.university.service.interfaces;

import com.behnam.university.dto.create.CollegeCreateDto;
import com.behnam.university.dto.detail.CollegeDetailDto;
import com.behnam.university.dto.list.CollegeListDto;
import com.behnam.university.dto.update.CollegeUpdateDto;
import com.behnam.university.model.College;
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

    College addCollege(CollegeCreateDto collegeCreateDto);

    void deleteCollege(String collegeName);

    void updateCollege(Long collegeId, String collegeName);

    void updateCollege(Long collegeId, CollegeUpdateDto dto);
}
