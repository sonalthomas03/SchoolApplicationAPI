package com.example.schoolapp.repository;

import com.example.schoolapp.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }
    @Test
    void itShouldCheckIfStudentEmailExists() {

        //given
        String email = "sonal@gmail.com";
        Student student = new Student(
                "Sonal",
                LocalDate.of(2000, Month.JANUARY,5),
                email
        );
        underTest.save(student);

        //when
        Boolean expected = underTest.findStudentByEmail(email);

        //then

        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {

        //given
        String email = "sonal@gmail.com";

        //when
        Boolean expected = underTest.findStudentByEmail(email);

        //then

        assertThat(expected).isFalse();
    }
}