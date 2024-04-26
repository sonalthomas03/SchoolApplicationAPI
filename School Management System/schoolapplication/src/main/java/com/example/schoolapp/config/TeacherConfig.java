package com.example.schoolapp.config;

import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.repository.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class TeacherConfig {

    //commandline runner is used to initialize some data into the database or have some code run during runtime
    @Bean
    CommandLineRunner TeachercommandLineRunner(TeacherRepository repository) {
        return args -> {

            Teacher Ancy = new Teacher(
                    "Ancy",
                    LocalDate.of(1990, Month.JANUARY,5),
                    "ancy@gmail.com",
                    "Computer Networks"
            );

            Teacher Sujatha = new Teacher(
                    "Sujatha",
                    LocalDate.of(1880, Month.JANUARY,5),
                    "Sujatha@gmail.com",
                    "Algorithm Design"
            );

            repository.saveAll(
                    List.of(Ancy,Sujatha)
            );
        };
    }
}
