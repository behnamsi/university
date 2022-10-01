package com.behnam.university;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class UniversityApplicationTests {

    @Test
    void contextLoads() {
        Long o=1L;
        System.out.println("o.getClass().getTypeName() = " + o.getClass().getTypeName());
    }

}
