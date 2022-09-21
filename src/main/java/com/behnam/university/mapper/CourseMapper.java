package com.behnam.university.mapper;


import com.behnam.university.dto.course.CourseCreateDto;
import com.behnam.university.model.Course;

public class CourseMapper {
    public CourseCreateDto courseToDto(Course course) {
        String name = course.getCourseName();
        int unitNumber = course.getUnitNumber();
        String professor = String.format("%s %s",
                course.getProfessor().getFirstName(),
                course.getProfessor().getLastName());
        String collegeName=course.getCourseCollege().getCollegeName();
        return new CourseCreateDto(name, unitNumber);
    }

    public Course dtoToCourse(CourseCreateDto courseCreateDto){
        Course course=new Course();
        course.setCourseName(courseCreateDto.getCourseName());
        course.setUnitNumber(courseCreateDto.getUnitNumber());
        return course;
    }
}
