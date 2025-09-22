package com.fintrak.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;


@SpringBootApplication
public class UserServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    // Add this bean to log the test secret on startup
    @Bean
    @Profile("prod") // Ensure this only runs in the 'prod' profile
    CommandLineRunner commandLineRunner(@Value("${debug.retrieved.password:SECRET_NOT_FOUND}") String retrievedPassword) {
        return args -> {
            logger.info("====================== DEBUGGING SECRET ======================");
            logger.info("Attempting to use password retrieved from Secret Manager: [{}]", retrievedPassword);
            logger.info("==============================================================");
        };
    }

    // Add this bean to log the test secret on startup
//    @Bean
//    CommandLineRunner commandLineRunner(@Value("${fintrack.test.secret:SECRET_NOT_FOUND}") String testSecret) {
//        return args -> {
//            logger.info(">>>>>>>>>>>>> FINTRAK TEST SECRET <<<<<<<<<<<<<");
//            logger.info("Successfully fetched test secret: {}", testSecret);
//            logger.info(">>>>>>>>>>>>> FINTRAK TEST SECRET <<<<<<<<<<<<<");
//        };
//    }
}