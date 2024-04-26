package com.example.schoolapp.config;


import com.example.schoolapp.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    //commandline runner is used to initialize some data into the database or have some code run during runtime
    @Bean
    CommandLineRunner StudentcommandLineRunner(StudentRepository repository) {
        return args -> {

            /*Student sonal = new Student(
                    "Sonal",
                    LocalDate.of(2000, Month.JANUARY,5),
                    "sonal@gmail.com",
                    1,
                    1
            );

            Student thomas = new Student(
                    "thomas",
                    LocalDate.of(2004, Month.JANUARY,5),
                    "thomas@gmail.com",
                    2
            );

            repository.saveAll(
                    List.of(sonal,thomas)
            ); */
        };
    }
}
