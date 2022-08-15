package com.behnam.university.mapper;


import com.behnam.university.dto.CourseDto;
import com.behnam.university.model.Course;

public class CourseMapper {
    public CourseDto courseToDto(Course course) {
        String name = course.getCourseName();
        int unitNumber = course.getUnitNumber();
        String professor = String.format("%s %s",
                course.getProfessor().getFirstName(),
                course.getProfessor().getLastName());
        String collegeName=course.getCourseCollege().getCollegeName();
        return new CourseDto(name, unitNumber, professor,collegeName);
    }

    public Course dtoToCourse(CourseDto courseDto){
        Course course=new Course();
        course.setCourseName(courseDto.getName());
        course.setUnitNumber(courseDto.getUnitNumber());
        return course;
    }
}
