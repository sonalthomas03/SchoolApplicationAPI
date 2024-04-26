package com.example.schoolapp.config;

import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.repository.ParentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@Configuration
public class ParentConfig {
    //commandline runner is used to initialize some data into the database or have some code run during runtime
    @Bean
    CommandLineRunner ParentcommandLineRunner(ParentRepository repository) {
        return args -> {

            Parent antony = new Parent(
                    "antony",
                    LocalDate.of(2000, Month.JANUARY,5),
                    "antony@gmail.com"
            );

            Parent mark = new Parent(
                    "mark",
                    LocalDate.of(2004, Month.JANUARY,5),
                    "mark@gmail.com"
            );

            repository.saveAll(
                    List.of(antony,mark)
            );
        };
    }
}
