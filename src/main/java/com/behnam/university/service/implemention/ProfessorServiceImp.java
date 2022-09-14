package com.behnam.university.service.implemention;

import com.behnam.university.dto.create.ProfessorCreateDto;
import com.behnam.university.dto.detail.ProfessorDetailDto;
import com.behnam.university.dto.list.ProfessorListDto;
import com.behnam.university.dto.update.ProfessorUpdateDto;
import com.behnam.university.mapper.ProfessorMapper;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.repository.ProfessorRepository;
import com.behnam.university.repository.StudentRepository;
import com.behnam.university.service.interfaces.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.behnam.university.mapper.static_mapper.StaticMapper.mapper;
import static java.util.stream.Collectors.toList;

/**
 * @author Behnam Si
 */

@Service("professorServiceImp")
@Primary
public class ProfessorServiceImp implements ProfessorService {

    private final ProfessorRepository repository;
    private final StudentRepository studentRepository;
    private final CollegeRepository collegeRepository;

    @Autowired
    public ProfessorServiceImp(ProfessorRepository repository, StudentRepository studentRepository, CollegeRepository collegeRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.collegeRepository = collegeRepository;
    }

    @Override
    public List<ProfessorCreateDto> getAllProfessors(Integer page, Integer limit) {
        // limit and paging filter
        if (limit == null) limit = 3;
        if (page == null) page = 0;
        else page -= 1;
        if (limit > 100) throw new IllegalStateException("limit can not be more than 100");
        // paging and sorting data
        Pageable professorPageable = PageRequest.of(page, limit, Sort.by("lastName").ascending());
        Page<Professor> professorPage = repository.findAll(professorPageable);
        // mapping to dto
        ProfessorMapper mapper = new ProfessorMapper();
        return professorPage
                .getContent()
                .stream()
                .map(mapper::professorToDto)
                .collect(toList());
    }

    @Override
    public List<ProfessorListDto> getAllProfessors(Pageable pageable) {
        List<Professor> professors = repository.findAll(pageable).getContent();
        List<ProfessorListDto> professorDetailDTOS = new ArrayList<>();
        for (Professor p :
                professors) {
            ProfessorListDto dto = new ProfessorListDto();
            try {
                mapper(p, dto);
                professorDetailDTOS.add(dto);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return professorDetailDTOS;
    }

    @Override
    public ProfessorCreateDto addProfessor(ProfessorCreateDto professorCreateDto, Long collegeId) {
        if (collegeId != null) {
            College college = collegeRepository.findById(collegeId).orElseThrow(() ->
                    new IllegalStateException("invalid college id"));
            // mapping to entity
            Professor professor;
            ProfessorMapper mapper = new ProfessorMapper();
            professor = mapper.dtoTOProfessor(professorCreateDto);
            // check for national and personal id
            if (repository.existsProfessorByNationalId(professorCreateDto.getNationalId())
                    || studentRepository.existsStudentByNationalId(professorCreateDto.getNationalId())) {
                throw new IllegalStateException("national id has taken");
            }
            if (repository.existsProfessorByPersonalId(professorCreateDto.getPersonalId())) {
                throw new IllegalStateException("personal id has taken");
            }
            professor.setProfessorCollege(college);
            repository.save(professor);
        } else {
            throw new IllegalStateException("college id must be present.");
        }
        return professorCreateDto;
    }

    @Override
    public Long deleteProfessor(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("invalid id to delete professor.");
        }
        repository.deleteById(id);
        return id;
    }

    @Override
    @Transactional
    public void updateProfessor(Long id, String first_name,
                                String last_name, Long nationalId, Long personalId) {
        Professor professor = repository.findById(id).orElseThrow(() -> new
                IllegalStateException("invalid id to update professor."));

        if (repository.existsProfessorByNationalId(nationalId)
                || studentRepository.existsStudentByNationalId(nationalId)) {
            throw new IllegalStateException("national id has owner");
        }
        if (repository.existsProfessorByPersonalId(personalId)) {
            throw new IllegalStateException("personal id is taken.");
        }
        if (first_name != null && first_name.length() > 0 &&
                !Objects.equals(professor.getFirstName(), first_name)) {
            professor.setFirstName(first_name);
        }

        if (last_name != null && last_name.length() > 0 &&
                !Objects.equals(professor.getLastName(), last_name)) {
            professor.setLastName(last_name);
        }
        if (nationalId != null && !nationalId.equals(professor.getNationalId())) {
            professor.setNationalId(nationalId);
        }
        if (personalId != null && !personalId.equals(professor.getPersonalId())) {
            professor.setPersonalId(personalId);
        }
    }


    @Override
    @Transactional
    public ProfessorUpdateDto updateProfessor(Long profId, ProfessorUpdateDto dto) {
        Professor professor = repository.findById(profId).orElseThrow(() -> new
                IllegalStateException("invalid id to update professor."));

        if (repository.existsProfessorByNationalId(dto.getNationalId())
                || studentRepository.existsStudentByNationalId(dto.getNationalId())) {
            throw new IllegalStateException("national id has owner");
        }
        if (dto.getFirstName() != null) {
            professor.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            professor.setLastName(dto.getLastName());
        }
        if (dto.getNationalId() != null) {
            professor.setNationalId(dto.getNationalId());
        }
        return dto;
    }


    @Override
    @Transactional
    public List<String> getProfessorStudents(Long professorId) {
        Professor professor = repository.findById(professorId).orElseThrow(
                () -> new IllegalStateException("invalid professor id")
        );
        List<String> studentsName = new ArrayList<>();
        for (Student student : professor.getStudentsOfProfessor()) {
            studentsName.add(student.getFirstName() + " " + student.getLastName());
        }
        return studentsName;
    }

    @Override
    @Transactional
    public List<String> getProfessorStudentsAverages(Long professorId) {
        Professor professor = repository.findById(professorId).orElseThrow(
                () -> new IllegalStateException("invalid professor id")
        );
        List<String> studentsAverageList = new ArrayList<>();
        for (Student student : professor.getStudentsOfProfessor()) {
            List<Course> courses = student.getStudentCourses();
            Map<String, Double> scores = student.getScores();
            if (courses.size() > scores.size()) {
                throw new IllegalStateException("all course`s results must be present.");
            }
            int numOfUnits = 0;
            double sum = 0, result;
            for (Course course : courses) {
                sum += scores.get(course.getCourseName()) * course.getUnitNumber();
                numOfUnits += course.getUnitNumber();
            }
            result = sum / numOfUnits;
            studentsAverageList.add(student.getFirstName() + " " + student.getLastName()
                    + " : " + result);
        }
        return studentsAverageList;
    }

    @Override
    @Transactional
    public List<String> getProfessorsCourses(Long professorId) {

        Professor professor = repository.findById(professorId).orElseThrow(
                () -> new IllegalStateException("invalid professor id")
        );
        List<String> professorCourses = new ArrayList<>();
        for (Course course : professor.getCourses()) {
            professorCourses.add(course.getCourseName());
        }
        return professorCourses;

    }

    @Override
    @Transactional
    public List<String> getProfessorStudentsByCourse(Long professorId, String courseName) {

        Professor professor = repository.findById(professorId).orElseThrow(
                () -> new IllegalStateException("invalid professor id")
        );
        boolean courseExists = false;
        List<Student> students = new ArrayList<>();
        List<String> studentsNames = new ArrayList<>();
        for (Course course : professor.getCourses()) {
            if (course.getCourseName().equals(courseName)) {
                courseExists = true;
                students.addAll(course.getEnrolled_students());
                break;
            }
        }
        if (!courseExists) {
            throw new IllegalStateException("invalid course");
        }
        for (Student student : students) {
            studentsNames.add(student.getFirstName() + " " + student.getLastName());
        }
        return studentsNames;

    }

    @Override
    @Transactional
    public List<String> getProfessorStudentsAverageByCourse(Long professorId, String courseName) {
        Professor professor = repository.findById(professorId).orElseThrow(
                () -> new IllegalStateException("invalid professor id")
        );
        boolean courseExists = false;
        List<Student> students = new ArrayList<>();
        for (Course course : professor.getCourses()) {
            if (course.getCourseName().equals(courseName)) {
                courseExists = true;
                students.addAll(course.getEnrolled_students());
                break;
            }
        }
        if (!courseExists) {
            throw new IllegalStateException("invalid course");
        }
        List<String> studentAverages = new ArrayList<>();
        for (Student student : students) {
            List<Course> courses = student.getStudentCourses();
            Map<String, Double> scores = student.getScores();
            if (courses.size() == 0) {
                throw new IllegalStateException("no courses taken");
            }
            if (courses.size() > scores.size()) {
                throw new IllegalStateException("all course`s results must be present.");
            }
            int numOfUnits = 0;
            double sum = 0, result;
            for (Course course : courses) {
                sum += scores.get(course.getCourseName()) * course.getUnitNumber();
                numOfUnits += course.getUnitNumber();
            }
            result = sum / numOfUnits;
            studentAverages.add(student.getFirstName() + " " + student.getLastName() + " : " +
                    result);
        }
        return studentAverages;
    }

    @Override
    public ProfessorDetailDto getProfessor(Long profId) {
        Professor professor = repository.findById(profId).orElseThrow(
                () -> new IllegalStateException("invalid professor id")
        );
        ProfessorDetailDto dto = new ProfessorDetailDto();
        try {
            mapper(professor, dto);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return dto;
    }
}
