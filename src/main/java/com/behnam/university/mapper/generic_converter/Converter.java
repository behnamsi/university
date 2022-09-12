package com.behnam.university.mapper.generic_converter;

import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @param <T>
 * @param <K>
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @implNote the first param is the prototype and the second param is converted type.
 * @since 8/23/2022 - 8:50 PM
 */
@Service
public class Converter<T, K> {

    /**
     * @param prototype
     * @param converted
     * @implNote convert the first param type to sec param type.
     * works with filling the same fields names Careful working.
     */
    public void convert(T prototype, K converted) {
        // initializing
        Class<?> prototypeClass = prototype.getClass();
        Class<?> convertedClass = converted.getClass();
        // switch for different types:
        switch (prototypeClass.getName()) {
            // Entities
            case "com.behnam.university.model.Student":
            case "com.behnam.university.model.Professor":
                convertStudentOrProfessorEntityToDto(prototype, converted, prototypeClass, convertedClass);
                return;
            case "com.behnam.university.model.Course":
                convertCourseEntityToDto(prototype, converted, prototypeClass, convertedClass);
                return;
            case "com.behnam.university.model.College":
                convertCollegeEntityToDto(prototype, converted, prototypeClass, convertedClass);
                return;
            // DTOs
            case "com.behnam.university.dto.create.StudentDto":
            case "com.behnam.university.dto.create.ProfessorDto":
                convertStudentOrProfessorDtoToEntity(prototype, converted, prototypeClass, convertedClass);
                return;
            case "com.behnam.university.dto.create.CourseDto":
                convertCourseDtoToEntity(prototype, converted, prototypeClass, convertedClass);
                return;
            case "com.behnam.university.dto.create.CollegeDto":
                convertCollegeDtoToEntity(prototype, converted, prototypeClass, convertedClass);
                return;
            default:
                convertUnknownType(prototype, converted, prototypeClass, convertedClass);

        }
    }

    /**
     * @implNote for converting student and professor entity to dto
     */

    private void convertStudentOrProfessorEntityToDto(
            T prototype,
            K converted,
            Class<?> prototypeClass,
            Class<?> convertedClass) {
        try {
            // getting the student fields
            Field studentCollegeField = null;
            if (prototypeClass.getSimpleName().equals("Professor")) {
                studentCollegeField = prototypeClass
                        .getDeclaredField("professorCollege");
            } else {
                studentCollegeField = prototypeClass
                        .getDeclaredField("studentCollege");
            }

            Field firstNameField = prototypeClass
                    .getDeclaredField("firstName");
            Field lastNameField = prototypeClass
                    .getDeclaredField("lastName");
            Field nationalIdField = prototypeClass
                    .getDeclaredField("nationalId");
            Field universityIdField = null;
            if (prototypeClass.getSimpleName().equals("Professor")) {
                universityIdField = prototypeClass
                        .getDeclaredField("personalId");
            } else {
                universityIdField = prototypeClass
                        .getDeclaredField("universityId");
            }


            // getting the accessibility to the above fields.


            studentCollegeField.setAccessible(true);
            firstNameField.setAccessible(true);
            lastNameField.setAccessible(true);
            nationalIdField.setAccessible(true);
            universityIdField.setAccessible(true);


            // getting the needed data for converting to student dto


            College college = (College) studentCollegeField.get(prototype);
            String collegeName = college.getCollegeName();
            String firstName = (String) firstNameField.get(prototype);
            String lastName = (String) lastNameField.get(prototype);
            Long nationalId = (Long) nationalIdField.get(prototype);
            Long universityId = (Long) universityIdField.get(prototype);


            // getting the student dto fields

            Field studentCollegeFieldDto = convertedClass
                    .getDeclaredField("collegeName");
            Field firstNameFieldDto = convertedClass
                    .getDeclaredField("firstName");
            Field lastNameFieldDto = convertedClass
                    .getDeclaredField("lastName");
            Field nationalIdFieldDto = convertedClass
                    .getDeclaredField("nationalId");
            Field universityIdFieldDto;
            if (prototypeClass.getSimpleName().equals("Professor")) {
                universityIdFieldDto = convertedClass
                        .getDeclaredField("personalId");
            } else {
                universityIdFieldDto = convertedClass
                        .getDeclaredField("universityId");
            }

            // set the accessibility for student dto fields

            studentCollegeFieldDto.setAccessible(true);
            firstNameFieldDto.setAccessible(true);
            lastNameFieldDto.setAccessible(true);
            nationalIdFieldDto.setAccessible(true);
            universityIdFieldDto.setAccessible(true);

            // set the data to student dto

            studentCollegeFieldDto.set(converted, collegeName);
            firstNameFieldDto.set(converted, firstName);
            lastNameFieldDto.set(converted, lastName);
            nationalIdFieldDto.set(converted, nationalId);
            universityIdFieldDto.set(converted, universityId);

            // end of try body
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // the end of method and return student dto
    }

    /**
     * @implNote for converting student and professor dto to entity
     */

    private void convertStudentOrProfessorDtoToEntity(
            T prototype,
            K converted,
            Class<?> prototypeClass,
            Class<?> convertedClass
    ) {

        try {
            // getting the student dto fields


            Field firstNameFieldDto = prototypeClass
                    .getDeclaredField("firstName");
            Field lastNameFieldDto = prototypeClass
                    .getDeclaredField("lastName");
            Field nationalIdFieldDto = prototypeClass
                    .getDeclaredField("nationalId");
            Field universityIdFieldDto;
            if (prototypeClass.getSimpleName().equals("ProfessorDto")) {
                universityIdFieldDto = prototypeClass
                        .getDeclaredField("personalId");
            } else {
                universityIdFieldDto = prototypeClass
                        .getDeclaredField("universityId");
            }

            // set the accessibility for student dto fields

            firstNameFieldDto.setAccessible(true);
            lastNameFieldDto.setAccessible(true);
            nationalIdFieldDto.setAccessible(true);
            universityIdFieldDto.setAccessible(true);

            String firstName = (String) firstNameFieldDto.get(prototype);
            String lastName = (String) lastNameFieldDto.get(prototype);
            Long nationalId = (Long) nationalIdFieldDto.get(prototype);
            Long universityId = (Long) universityIdFieldDto.get(prototype);

            Field firstNameField = convertedClass
                    .getDeclaredField("firstName");
            Field lastNameField = convertedClass
                    .getDeclaredField("lastName");
            Field nationalIdField = convertedClass
                    .getDeclaredField("nationalId");
            Field universityIdField;
            if (prototypeClass.getSimpleName().equals("ProfessorDto")) {
                universityIdField = convertedClass
                        .getDeclaredField("personalId");
            } else {
                universityIdField = convertedClass
                        .getDeclaredField("universityId");
            }


            // getting the accessibility to the above fields.


            firstNameField.setAccessible(true);
            lastNameField.setAccessible(true);
            nationalIdField.setAccessible(true);
            universityIdField.setAccessible(true);

            // set the data to student
            firstNameField.set(converted, firstName);
            lastNameField.set(converted, lastName);
            nationalIdField.set(converted, nationalId);
            universityIdField.set(converted, universityId);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    /**
     * @implNote for converting course entity to dto
     */

    private void convertCourseEntityToDto(
            T prototype,
            K converted,
            Class<?> prototypeClass,
            Class<?> convertedClass
    ) {
        try {
            // get the course fields.
            Field courseNameField = prototypeClass.getDeclaredField("courseName");
            Field unitNumberField = prototypeClass.getDeclaredField("unitNumber");
            Field professorField = prototypeClass.getDeclaredField("professor");
            Field courseCollegeField = prototypeClass.getDeclaredField("courseCollege");
            // set the accessibility.
            courseCollegeField.setAccessible(true);
            unitNumberField.setAccessible(true);
            professorField.setAccessible(true);
            courseNameField.setAccessible(true);
            // get the data of course entity
            String courseName = (String) courseNameField.get(prototype);
            Integer unitNumber = (Integer) unitNumberField.get(prototype);
            Professor professor = (Professor) professorField.get(prototype);
            String professorName = String.format("%s %s",
                    professor.getFirstName(),
                    professor.getLastName());
            College college = (College) courseCollegeField.get(prototype);
            String collegeName = college.getCollegeName();
            // get the course dto fields.
            Field courseNameDtoField = convertedClass.getDeclaredField("courseName");
            Field unitNumberDtoField = convertedClass.getDeclaredField("unitNumber");
            Field professorDtoField = convertedClass.getDeclaredField("professorOfCourse");
            Field collegeNameDtoField = convertedClass.getDeclaredField("collegeName");
            // set the accessibility
            courseNameDtoField.setAccessible(true);
            unitNumberDtoField.setAccessible(true);
            professorDtoField.setAccessible(true);
            collegeNameDtoField.setAccessible(true);
            // set the dta to course dto
            courseNameDtoField.set(converted, courseName);
            unitNumberDtoField.set(converted, unitNumber);
            professorDtoField.set(converted, professorName);
            collegeNameDtoField.set(converted, collegeName);
            // end of try body
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @implNote converting course dto to entity
     */

    private void convertCourseDtoToEntity(
            T prototype,
            K converted,
            Class<?> prototypeClass,
            Class<?> convertedClass
    ) {
        // get the course dto fields.
        Field courseNameFieldDto = null;
        try {
            courseNameFieldDto = prototypeClass.getDeclaredField("courseName");
            Field unitNumberFieldDto = prototypeClass.getDeclaredField("unitNumber");
            Field professorFieldDto = prototypeClass.getDeclaredField("professorOfCourse");
            Field courseCollegeFieldDto = prototypeClass.getDeclaredField("collegeName");
            // set the accessibility.
            courseCollegeFieldDto.setAccessible(true);
            unitNumberFieldDto.setAccessible(true);
            professorFieldDto.setAccessible(true);
            courseNameFieldDto.setAccessible(true);
            // get the data of course entity
            String courseName = (String) courseNameFieldDto.get(prototype);
            Integer unitNumber = (Integer) unitNumberFieldDto.get(prototype);
            // get the course fields
            Field courseNameField = convertedClass.getDeclaredField("courseName");
            Field unitNumberField = convertedClass.getDeclaredField("unitNumber");
            // set the accessibility
            courseNameField.setAccessible(true);
            unitNumberField.setAccessible(true);
            // set the data to course entity
            courseNameField.set(converted, courseName);
            unitNumberField.set(converted, unitNumber);
            // end try body

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * @implNote converting college entity to dto
     */
    private void convertCollegeEntityToDto(
            T prototype,
            K converted,
            Class<?> prototypeClass,
            Class<?> convertedClass) {

        try {
            // get the college fields
            Field collegeNameField = prototypeClass.getDeclaredField("collegeName");
            Field collegeCoursesField = prototypeClass.getDeclaredField("coursesInCollege");
            // set the accessibility
            collegeNameField.setAccessible(true);
            collegeCoursesField.setAccessible(true);
            // set the college data
            String collegeName = (String) collegeNameField.get(prototype);
            List<Course> courses = (List<Course>) collegeCoursesField.get(prototype);

            List<String> courseNames = courses.stream()
                    .map(Course::getCourseName)
                    .collect(Collectors.toList());
            // get the college dto fields
            Field collegeNameDto = convertedClass.getDeclaredField("collegeName");
            Field coursesFieldDto = convertedClass.getDeclaredField("courses");
            // set the accessibility
            collegeNameDto.setAccessible(true);
            coursesFieldDto.setAccessible(true);
            // set the data
            collegeNameDto.set(converted, collegeName);
            coursesFieldDto.set(converted, courseNames);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * @implNote converting college dto to entity
     */
    private void convertCollegeDtoToEntity(
            T prototype,
            K converted,
            Class<?> prototypeClass,
            Class<?> convertedClass
    ) {
        try {

            // get the dto fields
            Field collegeNameFieldDto = prototypeClass.getDeclaredField("collegeName");
            // set the accessibility
            collegeNameFieldDto.setAccessible(true);
            // get the data
            String name = (String) collegeNameFieldDto.get(prototype);
            // get the entity fields
            Field collegeNameField = convertedClass
                    .getDeclaredField("collegeName");
            // set the accessibility
            collegeNameField.setAccessible(true);
            // set the data
            collegeNameField.set(converted, name);
            // end the try body
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * @implNote convert unknown types
     */

    private void convertUnknownType(
            T prototype,
            K converted,
            Class<?> prototypeClass,
            Class<?> convertedClass) {


        // ------------- METHOD STARTS -------------

        // initializing

        Field[] prototypeFields = prototypeClass.getDeclaredFields();
        Field[] convertedFields = convertedClass.getDeclaredFields();
        List<String> prototypeFieldsName = new ArrayList<>();
        List<String> convertedFieldsName = new ArrayList<>();

        // get the names of prototype fields
        for (Field prototypeField :
                prototypeFields) {
            prototypeFieldsName.add(prototypeField.getName());
        }
        // get the names of converted fields
        for (Field convertedField :
                convertedFields) {
            convertedFieldsName.add(convertedField.getName());
        }

        try {
            // get the equal fields
            List<String> equalFields = new ArrayList<>();
            for (String fieldName :
                    convertedFieldsName) {
                for (String prototypeFieldName :
                        prototypeFieldsName) {
                    if (fieldName.equals(prototypeFieldName)) {
                        equalFields.add(fieldName);
                    }
                }
            }
            try {
                for (String fieldName
                        : equalFields
                ) {
                    // setting the data
                    Field convertedClassDeclaredField = convertedClass.getDeclaredField(fieldName);
                    convertedClassDeclaredField.setAccessible(true);
                    Field prototypeClassDeclaredField = prototypeClass.getDeclaredField(fieldName);
                    prototypeClassDeclaredField.setAccessible(true);
                    convertedClassDeclaredField.set(converted, prototypeClassDeclaredField.get(prototype));
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

