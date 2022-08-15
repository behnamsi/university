package com.behnam.university.mapper;

import com.behnam.university.dto.CollegeDto;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CollegeMapper {
    public CollegeDto toCollegeDto(College college) {
        String name = college.getCollegeName();
        if (college.getCoursesInCollege() != null) {
//            List<String> courses = new ArrayList<String>();
//            for (Course course : college.getCoursesInCollege()) {
//                courses.add(course.getCourseName());
//            }
            List<String> courses = college
                    .getCoursesInCollege()
                    .stream()
                    .map(Course::getCourseName)
                    .collect(toList());
            return new CollegeDto(name, courses);
        }
        return new CollegeDto(name);
    }

    public College toCollege(CollegeDto collegeDto) {
        College college = new College();
        college.setCollegeName(collegeDto.getName());
        return college;
    }
}
