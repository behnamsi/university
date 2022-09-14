package com.behnam.university.service;



import com.behnam.university.dto.create.CollegeCreateDto;
import com.behnam.university.model.College;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.service.implemention.CollegeServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollegeServiceImpUnitTest {

    @Mock
    CollegeRepository repository;
    @InjectMocks
    CollegeServiceImp service;

    College college1;
    College college2;
    College college3;
    CollegeCreateDto collegeCreateDto1;
    CollegeCreateDto collegeCreateDto2;
    CollegeCreateDto collegeCreateDto3;

    @BeforeEach
    void setUp() {
        service = new CollegeServiceImp(repository);
        college1 = new College();
        college2 = new College();
        college3 = new College();
        collegeCreateDto1 = new CollegeCreateDto("computer");
        collegeCreateDto2 = new CollegeCreateDto("electronic");
        collegeCreateDto3 = new CollegeCreateDto("math");
        college1.setCollegeId(1L);
        college2.setCollegeId(2L);
        college3.setCollegeId(3L);
        college1.setCollegeName("computer");
        college2.setCollegeName("electronic");
        college3.setCollegeName("math");

    }

//    @Test
//    void getAllColleges() {
//        Pageable collegePageable = PageRequest.of(0, 3, Sort.by("collegeName").ascending());
//        Page<College> collegeList = new PageImpl<College>(List.of(college1, college2,college3));
//        when(repository.findAll(collegePageable))
//                .thenReturn(collegeList);
//        List<CollegeDto> collegeDtoListExpected = new ArrayList<>(List.of(collegeDto1, collegeDto2, collegeDto3));
//        List<CollegeDto> collegeDTOListActual = service.getAllColleges(1, 3);
//        assertThat(collegeDTOListActual.size()).isEqualTo(collegeDtoListExpected.size());
//        System.out.println("collegeDTOListActual = " + collegeDTOListActual);
//        System.out.println("collegeDtoListExpected = " + collegeDtoListExpected);
//        assertThat(collegeDTOListActual.get(0).getName()).isEqualTo(collegeDtoListExpected.get(0).getName());
//    }

    @Test
    void addCollege() {
//        collegeCreateDto college = service.addCollege(collegeCreateDto1);
//        assertThat(college.getCollegeName()).isEqualTo("computer");
//        assertThat(college).isInstanceOf(College.class);
    }

    @Test
    void deleteCollegeByCollegeName() {
        when(repository.existsCollegeByCollegeName(college1.getCollegeName()))
                .thenReturn(true);
        service.deleteCollege(college1.getCollegeName());
    }

    @Test
    void updateCollege() {
        when(repository.findById(college1.getCollegeId()))
                .thenReturn(Optional.ofNullable(college1));
        service.updateCollege(1L, "comp science");
        assertThat(college1.getCollegeName()).isEqualTo("comp science");
    }

    // ----------------- NEW DTO TESTING -----------------

//    @Test
//    void shouldGetAllCollegesWithNewDto() {
//        Pageable collegePageable = PageRequest.of(0, 3, Sort.by("collegeName").ascending());
//        List<College> colleges=new ArrayList<>(List.of(college1, college2,college3));
//        Page<College> collegeList = new PageImpl<>(colleges);
//        when(repository.findAll(collegePageable))
//                .thenReturn(collegeList);
//
//        List<CollegeDto> collegeDtoListExpected = new ArrayList<>(List.of(collegeDto1, collegeDto2, collegeDto3));
//        List<CollegeDto> collegeDTOListActual = newService.getAllColleges(1, 3);
//        assertThat(collegeDTOListActual.size()).isEqualTo(collegeDtoListExpected.size());
//        System.out.println("collegeDTOListActual = " + collegeDTOListActual);
//        System.out.println("collegeDtoListExpected = " + collegeDtoListExpected);
//        assertThat(collegeDTOListActual.get(0).getName()).isEqualTo(collegeDtoListExpected.get(0).getName());
//    }

//    @Test
//    void shouldAddCollegeWithNewDto() {
//        College college = newService.addCollege(collegeDto1);
//        assertThat(college.getCollegeName()).isEqualTo("computer");
//    }


}