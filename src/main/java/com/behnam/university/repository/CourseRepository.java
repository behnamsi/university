package com.behnam.university.repository;


import com.behnam.university.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseByCourseName(String courseName);
    @Modifying
    void deleteCourseByCourseName(String courseNme);

    boolean existsCourseByCourseName(String courseNme);


}
