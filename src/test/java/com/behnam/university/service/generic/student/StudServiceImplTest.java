package com.behnam.university.service.generic.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
class StudServiceImplTest {

    @Autowired

//    StudServiceImpl service;

//    Pageable pageable;

    @BeforeEach
//    void setUp() {
//        pageable = PageRequest.of(0, 4);
//    }

    @Test
    void getInstance() throws InstantiationException, IllegalAccessException {

    }

    @Test
    void getAll() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
//        System.out.println("service.getAll(pageable) = " + service.getAll(pageable));
    }
    @Test
    void get() throws Throwable {
//        System.out.println("service.get(9024006L) = " + service.get(9024006L));
    }

    @Test
    void testGet() {
    }
}