package com.nagarro.pointofsaleapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nagarro.pointofsaleapplication.utils.HibernateUtility;

@SpringBootApplication
public class PointOfSaleApplication {

    // main method that will load the spring boot application
    public static void main(String[] args) {
        HibernateUtility.getSessionFactory();
        SpringApplication.run(PointOfSaleApplication.class, args);
    }

}
