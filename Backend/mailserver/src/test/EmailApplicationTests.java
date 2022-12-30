package com.example.mailserver;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

@SpringBootTest
class EmailApplicationTests {
    public static void main(String[] args) {
        Result result1 = JUnitCore.runClasses(FunctionTester.class, SignUpLogInTester.class);


        for (Failure failure1 : result1.getFailures()) {
            System.out.println(failure1.toString());
        }

        System.out.println("there is no error in the main functions: " + result1.wasSuccessful());
    }

}

