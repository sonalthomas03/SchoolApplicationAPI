package com.example.schoolapp.repository;

import com.example.schoolapp.entity.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository underTest;

    @AfterEach
    void tearDown()
    {
        underTest.deleteAll();
    }
    @Test
    void itShouldCheckIfTeacherEmailExists() {

        //given
        String email = "ancy@gmail.com";
        Teacher teacher = new Teacher(
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                email,
                "Networks"
        );
        underTest.save(teacher);

        //when
        Boolean expected = underTest.findTeacherByEmail(email);

        //then

        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfTeacherEmailDoesNotExists() {

        //given
        String email = "ancy@gmail.com";

        //when
        Boolean expected = underTest.findTeacherByEmail(email);

        //then

        assertThat(expected).isFalse();
    }


}