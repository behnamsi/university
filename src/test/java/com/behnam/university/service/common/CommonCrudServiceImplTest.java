package com.behnam.university.service.common;

import com.behnam.university.dto.college.CollegeCreateDto;
import com.behnam.university.dto.college.CollegeUpdateDto;
import com.behnam.university.model.Student;
import com.behnam.university.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.xml.ws.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommonCrudServiceImplTest {
    @Autowired
    @Qualifier("collegeCrudServiceImpl")
    CommonCrudService service;

    Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 2);
    }

    @Test
    void getAll() {
        System.out.println("service.getAll() = " + service.getAll(pageable));
    }

    @Test
    void get() {
        System.out.println("get " + service.get(1L));
    }

    @Test
    void add() {
        CollegeCreateDto dto = new CollegeCreateDto("accounting");
        System.out.println("service.add(dto) = " + service.add(dto));
    }

    @Test
    void update() {
        CollegeUpdateDto dto = new CollegeUpdateDto("management");
        System.out.println("service.update(4L, dto) = " + service.update(4L, dto));
    }

    @Test
    void delete() {
        System.out.println("service.delete(4L) = " + service.delete(4L));
    }
}