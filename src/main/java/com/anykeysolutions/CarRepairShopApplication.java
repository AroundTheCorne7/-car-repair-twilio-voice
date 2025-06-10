package com.anykeysolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarRepairShopApplication {

    public static void main(String[] args) {
        System.out.println("Starting Car Repair Shop Application...");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("PORT environment variable: " + System.getenv("PORT"));

        SpringApplication.run(CarRepairShopApplication.class, args);

        System.out.println("Car Repair Shop Application started successfully!");
    }
}
