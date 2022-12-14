package com.behnam.university.mapper;

import com.behnam.university.dto.create.CollegeCreateDto;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CollegeMapper {
    public CollegeCreateDto toCollegeDto(College college) {
        String name = college.getCollegeName();
        if (college.getCoursesInCollege() != null) {
//            List<String> courses = new ArrayList<String>();
//            for (Course course : college.getCoursesInCollege()) {
//                courses.add(course.getCourseName());
//            }
//            List<String> courses = college
//                    .getCoursesInCollege()
//                    .stream()
//                    .map(Course::getCourseName)
//                    .collect(toList());
            return new CollegeCreateDto(name);
        }
        return new CollegeCreateDto(name);
    }

    public College toCollege(CollegeCreateDto collegeCreateDto) {
        College college = new College();
        college.setCollegeName(collegeCreateDto.getCollegeName());
        return college;
    }
}
