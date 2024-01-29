package com.ecommerce.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class ServiceApplicationTests {

    @Test
    void contextLoads() {
        String result = initTest();
        Assertions.assertEquals("init", result, "기대한 값");
    }

    private String initTest() {
        return "init";
    }

}
