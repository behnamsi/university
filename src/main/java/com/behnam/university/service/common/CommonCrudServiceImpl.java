package com.behnam.university.service.common;

import com.behnam.university.dto.college.CollegeDetailDto;
import com.behnam.university.dto.college.CollegeListDto;
import com.behnam.university.dto.college.CollegeUpdateDto;
import com.behnam.university.dto.common.CommonCreateDto;
import com.behnam.university.dto.common.CommonDetailDto;
import com.behnam.university.dto.common.CommonListDto;
import com.behnam.university.dto.common.CommonUpdateDto;
import com.behnam.university.dto.course.CourseDetailDto;
import com.behnam.university.dto.course.CourseListDto;
import com.behnam.university.dto.course.CourseUpdateDto;
import com.behnam.university.dto.professor.ProfessorDetailDto;
import com.behnam.university.dto.professor.ProfessorListDto;
import com.behnam.university.dto.professor.ProfessorUpdateDto;
import com.behnam.university.dto.student.StudentDetailDto;
import com.behnam.university.dto.student.StudentListDto;
import com.behnam.university.dto.student.StudentUpdateDto;
import com.behnam.university.mapper.static_mapper.StaticMapper;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import com.behnam.university.model.common.CommonModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.behnam.university.mapper.static_mapper.StaticMapper.mapper;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/26/2022
 */


public class CommonCrudServiceImpl<REPO extends JpaRepository, MODEL extends CommonModel> implements CommonCrudService<CommonListDto, CommonDetailDto, CommonUpdateDto, CommonCreateDto, Object> {

    private final REPO repository;

    public CommonCrudServiceImpl(REPO repository) {
        this.repository = repository;
    }

    @Override
    public List<CommonListDto> getAll(Pageable pageable) {

        List<MODEL> records = repository.findAll(pageable).getContent();
        List<CommonListDto> listDtos = new ArrayList<>();
        for (MODEL model : records) {

            try {
                CommonListDto dto = getListDto().newInstance();
                mapper(model, dto);
                listDtos.add(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listDtos;
    }

    @Override
    @Transactional
    public CommonDetailDto get(Object o) {
        try {
            if (!o.getClass().getTypeName().equals("java.lang.Long")) {
                throw new IllegalStateException("key must be long type");
            }
            MODEL model = (MODEL) repository.findById((Long) o)
                    .orElseThrow(() -> new IllegalAccessException("invalid id"));
            CommonDetailDto dto = getDetailDto().newInstance();
            mapper(model, dto);
            return dto;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("could not find the model");

    }


    @Override
    public CommonCreateDto add(CommonCreateDto commonCreateDto, Object... objects) {

        try {
            CommonModel model = getModel().newInstance();
            mapper(commonCreateDto, model);
            repository.save(model);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return commonCreateDto;
    }

    @Override
    @Transactional
    public CommonUpdateDto update(Object key, CommonUpdateDto commonUpdateDto) {
        if (!key.getClass().getTypeName().equals("java.lang.Long")) {
            throw new IllegalStateException("key must be long type");
        }
        try {
            MODEL model = (MODEL) repository.findById((Long) key)
                    .orElseThrow(() -> new IllegalAccessException("invalid id"));
            StaticMapper.mapper(commonUpdateDto, model);

        } catch (Throwable e) {
            throw new IllegalStateException(e.getMessage());
        }
        return commonUpdateDto;
    }

    @Override
    @Transactional
    public Object delete(Object o) {
        if (!o.getClass().getTypeName().equals("java.lang.Long")) {
            throw new IllegalStateException("key must be long type");
        }
        if (!repository.existsById((Long)o)){
            throw new IllegalStateException("invalid id");
        }
        repository.deleteById((Long) o);
        return o;
    }

    private Class<? extends CommonListDto> getListDto() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        System.out.println("paramType = " + paramType);
        if (paramType.getActualTypeArguments()[0].getTypeName().contains("Student")) {
            return StudentListDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Professor")) {
            return ProfessorListDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Course")) {
            return CourseListDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("College")) {
            return CollegeListDto.class;
        } else throw new IllegalStateException("invalid model");
    }

    private Class<? extends CommonDetailDto> getDetailDto() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        System.out.println("paramType = " + paramType);
        if (paramType.getActualTypeArguments()[0].getTypeName().contains("Student")) {
            return StudentDetailDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Professor")) {
            return ProfessorDetailDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Course")) {
            return CourseDetailDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("College")) {
            return CollegeDetailDto.class;
        } else throw new IllegalStateException("invalid model");
    }

    private Class<? extends CommonModel> getModel() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        System.out.println("paramType = " + paramType);
        if (paramType.getActualTypeArguments()[0].getTypeName().contains("Student")) {
            return Student.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Professor")) {
            return Professor.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Course")) {
            return Course.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("College")) {
            return College.class;
        } else throw new IllegalStateException("invalid model");
    }

    private Class<? extends CommonUpdateDto> getUpdateDto() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        System.out.println("paramType = " + paramType);
        if (paramType.getActualTypeArguments()[0].getTypeName().contains("Student")) {
            return StudentUpdateDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Professor")) {
            return ProfessorUpdateDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("Course")) {
            return CourseUpdateDto.class;
        } else if (paramType.getActualTypeArguments()[0].getTypeName().contains("College")) {
            return CollegeUpdateDto.class;
        } else throw new IllegalStateException("invalid model");
    }
}
