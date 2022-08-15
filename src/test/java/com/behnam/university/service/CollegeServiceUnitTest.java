package com.behnam.university.service;

import com.behnam.university.dto.CollegeDto;
import com.behnam.university.mapper.CollegeMapper;
import com.behnam.university.model.College;
import com.behnam.university.repository.CollegeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CollegeServiceUnitTest {
    @Mock
    CollegeRepository repository;
    @InjectMocks
    CollegeService service;
    CollegeDto collegeDto;

    @BeforeEach
    void setUp() {
        collegeDto = new CollegeDto("coputer");
    }

    @Test
    void shouldAddACollege() {
        CollegeMapper mapper = new CollegeMapper();
        College college = new College();
        college.setCollegeId(1L);
        college.setCollegeName("computer");
        when(repository.save(college)).thenReturn(college);
        College college1=service.addCollege(collegeDto);
        System.out.println(college1);
    }
}
