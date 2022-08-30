package com.behnam.university.service;


import com.behnam.university.dto.CourseDto;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.repository.CourseRepository;
import com.behnam.university.service.implemention.CourseServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceImpUnitTest {

    @Mock
    CourseRepository repository;
    @InjectMocks
    CourseServiceImp courseServiceImp;

    Course course1;
    Course course2;
    CourseDto courseDto1;
    CourseDto courseDto2;
    College college;
    Professor professor;

    @BeforeEach
    void setUp() {
        courseServiceImp = new CourseServiceImp(repository,null,null);
        college = new College();
        college.setCollegeId(1L);
        college.setCollegeName("computer");
        professor = new Professor();
        professor.setProfessorId(1L);
        professor.setFirstName("beni");
        professor.setLastName("saqari");
        professor.setNationalId(1234567890L);
        professor.setProfessorId(1234567L);
        course1 = new Course();
        course2 = new Course();
        course1.setCourseName("java");
        course2.setCourseName("cpp");
        course1.setCourseId(1L);
        course2.setCourseId(2L);
        course1.setUnitNumber(3);
        course2.setUnitNumber(3);
        courseDto2 = new CourseDto();
        course1.setProfessor(professor);
        course2.setProfessor(professor);
        course1.setCourseCollege(college);
        course2.setCourseCollege(college);
    }

//    @Test
//    void getAllCourses() {
//        CourseMapper mapper = new CourseMapper();
//        Pageable coursePageable = PageRequest.of(0, 3, Sort.by("courseName").ascending());
//        List<Course> courseList = new ArrayList<Course>(List.of(course1, course2));
//        Page<Course> coursePage = new PageImpl<>(courseList);
//        List<CourseDto> courseDtoListExpected = courseList
//                .stream()
//                .map(mapper::courseToDto)
//                .toList();
//        when(repository.findAll(coursePageable))
//                .thenReturn(coursePage);
//        List<CourseDto> courseDtoListActual = courseService.getAllCourses(1, 3);
//        System.out.println("courseDtoListActual = " + courseDtoListActual);
//        System.out.println("courseDtoListExpected = " + courseDtoListExpected);
//    }
}