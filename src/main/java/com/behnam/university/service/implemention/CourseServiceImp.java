package com.behnam.university.service.implemention;

import com.behnam.university.dto.course.CourseCreateDto;
import com.behnam.university.dto.course.CourseDetailDto;
import com.behnam.university.dto.course.CourseListDto;
import com.behnam.university.dto.course.CourseUpdateDto;
import com.behnam.university.mapper.CourseMapper;
import com.behnam.university.mapper.static_mapper.StaticMapper;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.repository.CourseRepository;
import com.behnam.university.repository.ProfessorRepository;
import com.behnam.university.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.behnam.university.mapper.static_mapper.StaticMapper.mapper;
import static java.util.stream.Collectors.toList;

/**
 * @author Behnam Si
 */
@Service("courseServiceImp")
@Primary
public class CourseServiceImp implements CourseService {

    private final CourseRepository repository;
    private final CollegeRepository collegeRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public CourseServiceImp(CourseRepository repository,
                            CollegeRepository collegeRepository,
                            ProfessorRepository professorRepository) {
        this.repository = repository;
        this.collegeRepository = collegeRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public List<CourseCreateDto> getAllCourses(Integer page, Integer limit) {
        CourseMapper mapper = new CourseMapper();
        // limit and paging filters
        if (limit == null) limit = 3;
        if (page == null) page = 0;
        else page -= 1;
        if (limit > 100) throw new IllegalStateException("limit can not be more than 100");
        // paging and sorting data
        Pageable coursePageable = PageRequest.of(page, limit, Sort.by("courseName").ascending());
        Page<Course> coursePage = repository.findAll(coursePageable);
        return coursePage
                .getContent()
                .stream()
                .map(mapper::courseToDto)
                .collect(toList());
    }

    @Override
    public List<CourseListDto> getAllCourses(Pageable pageable) {
        List<Course> courses = repository.findAll(pageable).getContent();
        List<CourseListDto> courseDetailDTOS = new ArrayList<>();
        for (Course c :
                courses) {
            CourseListDto dto = new CourseListDto();
            try {
                StaticMapper.mapper(c, dto);
                courseDetailDTOS.add(dto);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return courseDetailDTOS;
    }

    @Override
    public CourseCreateDto addCourse(CourseCreateDto courseCreateDto, Long professorPersonalId, String collegeName) {
        if (professorPersonalId != null && collegeName != null) {
            if (!professorRepository
                    .existsProfessorByPersonalId(professorPersonalId)) {
                throw new IllegalStateException("invalid professor personal id");
            }
            if (!collegeRepository.existsCollegeByCollegeName(collegeName)) {
                throw new IllegalStateException("invalid college name");
            }
            Professor professor =
                    professorRepository
                            .findProfessorByPersonalId(professorPersonalId);
            College college =
                    collegeRepository
                            .findCollegeByCollegeName(collegeName);
            // mapping to entity
            CourseMapper mapper = new CourseMapper();
            Course course;
            course = mapper.dtoToCourse(courseCreateDto);
            course.setProfessor(professor);
            course.setCourseCollege(college);
            repository.save(course);
        } else {
            throw new IllegalStateException("could not add a course without college and professor");
        }
        return courseCreateDto;
    }

    @Override
    @Transactional
    public String deleteCourseByName(String courseName) {
        if (!repository.existsCourseByCourseName(courseName)) {
            throw new IllegalStateException("this course name is invalid");
        }
        repository.deleteCourseByCourseName(courseName);
        return courseName;
    }

//    @Transactional
//    public void deleteCourseById(Long courseId) {
//        if (!repository.existsById(courseId)) {
//            throw new IllegalStateException("this course id is invalid");
//        }
//        repository.deleteById(courseId);
//    }

    @Override
    @Transactional
    public void updateCourse(Long courseId, String courseName, Integer unitNumber, Long professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalStateException("invalid professor to add into course"));
        Course course = repository.findById(courseId).orElseThrow(() -> new IllegalStateException("invalid" +
                "course id to update"));
        if (courseName != null && !Objects.equals(courseName, course.getCourseName())
                && courseName.length() > 0) {
            course.setCourseName(courseName);
        }
        if (unitNumber != null && !Objects.equals(unitNumber, course.getUnitNumber())
                && unitNumber > 0) {
            course.setUnitNumber(unitNumber);
        }
        if (course.getProfessor() != null) {
            if (!Objects.equals(professorId, course.getProfessor().getProfessorId())
                    && professorId > 0) {
                course.setProfessor(professor);
            }
        } else {
            course.setProfessor(professor);
        }

    }


    @Override
    @Transactional
    public CourseUpdateDto updateCourse(Long courseId, CourseUpdateDto dto) {
        Course course = repository.findById(courseId).orElseThrow(() -> new IllegalStateException("invalid" +
                "course id to update"));
        if (dto.getProfessorPersonalId() != null) {
            if (!professorRepository.existsProfessorByPersonalId(dto.getProfessorPersonalId())) {
                throw new IllegalStateException("professor with " + dto.getProfessorPersonalId() + " id is invalid");
            }
            Professor professor = professorRepository.findProfessorByPersonalId(dto.getProfessorPersonalId());
            course.setProfessor(professor);
        }
        if (dto.getCourseName() != null) {
            course.setCourseName(dto.getCourseName());
        }
        if (dto.getUnitNumber() != null) {
            course.setUnitNumber(dto.getUnitNumber());
        }
        return dto;
    }


    @Override
    public CourseDetailDto getCourse(Long courseId) {
        Course course = repository.findById(courseId).orElseThrow(
                () -> new IllegalStateException("invalid course id")
        );
        CourseDetailDto dto = new CourseDetailDto();
        try {
            mapper(course, dto);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return dto;
    }
}

