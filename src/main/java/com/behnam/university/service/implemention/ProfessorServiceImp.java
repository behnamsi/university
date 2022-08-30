package com.behnam.university.service.implemention;

import com.behnam.university.dto.ProfessorDto;
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
import java.util.*;

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
    public List<ProfessorDto> getAllProfessors(Integer page, Integer limit) {
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
    public void addProfessor(ProfessorDto professorDto, Long collegeId) {
        if (collegeId != null) {
            College college = collegeRepository.findById(collegeId).orElseThrow(() ->
                    new IllegalStateException("invalid college id"));
            // mapping to entity
            Professor professor;
            ProfessorMapper mapper = new ProfessorMapper();
            professor = mapper.dtoTOProfessor(professorDto);
            // check for national and personal id
            if (repository.existsProfessorByNationalId(professorDto.getNationalId())
                    || studentRepository.existsStudentByNationalId(professorDto.getNationalId())) {
                throw new IllegalStateException("national id has taken");
            }
            if (repository.existsProfessorByPersonalId(professorDto.getPersonalId())) {
                throw new IllegalStateException("personal id has taken");
            }
            professor.setProfessorCollege(college);
            repository.save(professor);
        } else {
            throw new IllegalStateException("college id must be present.");
        }
    }

    @Override
    public void deleteProfessor(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("invalid id to delete professor.");
        }
        repository.deleteById(id);
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
    public ProfessorDto getProfessor(Long profId) {
        Professor professor = repository.findById(profId).orElseThrow(
                () -> new IllegalStateException("invalid professor id")
        );
        ProfessorMapper mapper = new ProfessorMapper();
        return mapper.professorToDto(professor);
    }
}
