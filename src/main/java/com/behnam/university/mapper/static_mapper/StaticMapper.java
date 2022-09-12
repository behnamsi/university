package com.behnam.university.mapper.static_mapper;

import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/9/2022
 */

public class StaticMapper {

    /**
     * @param prototype
     * @param converting
     * @implNote map any kind of class to another with the same field names
     * @implSpec getter and setter for all fields
     */
    public static <K, T> void mapper(T prototype, K converting) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // init
        Class<?> prototypeClass = prototype.getClass();
        Class<?> convertingClass = converting.getClass();
        switch (prototypeClass.getName()) {
            case "com.behnam.university.model.Student":
            case "com.behnam.university.model.Professor":
                if (convertingClass.getSimpleName().equals("StudentListDto") || convertingClass.getSimpleName().equals("ProfessorListDto")) {
                    unknownTypeMapper(prototype, converting);
                } else studentOrProfessorEntityToDetailDto(prototype, converting);
                return;
            case "com.behnam.university.dto.create.StudentCreateDto":
            case "com.behnam.university.dto.create.ProfessorCreateDto":
            case "com.behnam.university.dto.create.CourseCreateDto":
            case "com.behnam.university.dto.create.CollegeCreateDto":
            case "com.behnam.university.model.College":
                if (convertingClass.getSimpleName().equals("CollegeListDto"))
                    unknownTypeMapper(prototype, converting);
                else collegeEntityToDetailDto(prototype, converting);
                return;
            case "com.behnam.university.model.Course":
                if (convertingClass.getSimpleName().equals("CourseListDto"))
                    unknownTypeMapper(prototype, converting);
                else courseEntityTODetailDto(prototype, converting);
                return;
            default:
                unknownTypeMapper(prototype, converting);
        }
    }

    private static <T, K> void collegeEntityToDetailDto(T prototype, K converting) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        unknownTypeMapper(prototype, converting);
        Class<?> prototypeClass = prototype.getClass();
        Class<?> convertingClass = converting.getClass();
        Method getProfessor = prototypeClass.getDeclaredMethod("getProfessorsInCollege");
        Method getStudents = prototypeClass.getDeclaredMethod("getStudentsInCollege");
        Method getCourses = prototypeClass.getDeclaredMethod("getCoursesInCollege");
        Method[] allConvertingMethods = convertingClass.getDeclaredMethods();
        List<String> professorNames = new ArrayList<>();
        List<String> studentNames = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();
        for (Method m :
                allConvertingMethods) {
            switch (m.getName()) {
                case "setProfessors":
                    List<Professor> professors = (List<Professor>) getProfessor.invoke(prototype);
                    for (Professor p :
                            professors) {
                        professorNames.add(p.getFirstName() + " " + p.getLastName());
                    }
                    m.invoke(converting, professorNames);
                    continue;
                case "setStudents":
                    List<Student> students = (List<Student>) getStudents.invoke(prototype);
                    for (Student s :
                            students) {
                        studentNames.add(s.getFirstName() + " " + s.getLastName());
                    }
                    m.invoke(converting, studentNames);
                    continue;
                case "setCourses":
                    List<Course> courses = (List<Course>) getCourses.invoke(prototype);
                    for (Course c :
                            courses) {
                        courseNames.add(c.getCourseName());
                    }
                    m.invoke(converting, courseNames);
                    continue;
                default:
            }
        }
    }

    private static <T, K> void studentOrProfessorEntityToDetailDto(T prototype, K converting) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // initialize the same name fields
        unknownTypeMapper(prototype, converting);
        // customize it
        Class<?> prototypeClass = prototype.getClass();
        Class<?> convertingClass = converting.getClass();

        Method[] allConvertingMethods = convertingClass.getDeclaredMethods();
        Method getCollegeGetter;
        if (prototypeClass.getSimpleName().equals("Professor")) {
            getCollegeGetter = prototypeClass.getDeclaredMethod("getProfessorCollege");
        } else {
            getCollegeGetter = prototypeClass.getDeclaredMethod("getStudentCollege");
        }
        College college = (College) getCollegeGetter.invoke(prototype);
        for (Method m :
                allConvertingMethods) {
            if (m.getName().equals("setCollegeName")) {
                m.invoke(converting, college.getCollegeName());
                break;
            }
        }


    }

    private static <T, K> void courseEntityTODetailDto(T prototype, K converting) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        unknownTypeMapper(prototype, converting);
        Class<?> prototypeClass = prototype.getClass();
        Class<?> convertingClass = converting.getClass();
        Method prototypeProfessorGetter = prototypeClass.getDeclaredMethod("getProfessor");
        Method prototypeCollegeGetter = prototypeClass.getDeclaredMethod("getCourseCollege");
        Professor professor = (Professor) prototypeProfessorGetter.invoke(prototype);
        College college = (College) prototypeCollegeGetter.invoke(prototype);
        Method[] allConvertingMethods = convertingClass.getDeclaredMethods();
        for (Method m :
                allConvertingMethods) {
            if (m.getName().equals("setCollegeName")) {
                m.invoke(converting, college.getCollegeName());
            } else if (m.getName().equals("setProfessorOfCourse")) {
                m.invoke(converting, professor.getFirstName() + " " + professor.getLastName());
            }
        }


    }

    /**
     * @implNote get the same fields and fill the converting fields with data
     */
    private static <T, K> void unknownTypeMapper(T prototype, K converting) throws InvocationTargetException, IllegalAccessException {
        Class<?> prototypeClass = prototype.getClass();
        Class<?> convertingClass = converting.getClass();

        // get prototype methods
        Method[] prototypeMethods = prototypeClass.getDeclaredMethods();
        List<Method> prototypeSetters = new ArrayList<>();
        List<Method> prototypeGetters = new ArrayList<>();
        findGetterSetters(prototypeMethods, prototypeGetters, prototypeSetters);

        // get the converting methods
        Method[] convertingMethods = convertingClass.getDeclaredMethods();
        List<Method> convertingSetters = new ArrayList<>();
        List<Method> convertingGetters = new ArrayList<>();
        findGetterSetters(convertingMethods, convertingGetters, convertingSetters);

        // set the data for equal fields
        for (Method m1 :
                prototypeGetters) {
            for (Method m2 :
                    convertingSetters) {
                if (m1.getName().replace("get", "").equals(m2.getName().replace("set", ""))
                        || m1.getName().replace("is", "").equals(m2.getName().replace("set", ""))) {

                    m2.invoke(converting, m1.invoke(prototype));
                }
            }
        }
    }

    /**
     * @implNote find the getter setter methods
     */
    private static void findGetterSetters(Method[] methods, List<Method> getters, List<Method> setters) {
        for (Method m :
                methods) {
            if (m.getName().contains("set")) {
                setters.add(m);
            } else if (m.getName().contains("get") || m.getName().contains("is")) {
                getters.add(m);
            }
        }
    }

}
