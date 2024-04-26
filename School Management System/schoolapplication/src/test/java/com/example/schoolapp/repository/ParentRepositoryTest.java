package com.example.schoolapp.repository;

import com.example.schoolapp.entity.Parent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ParentRepositoryTest {

    @Autowired
    private ParentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }


    @Test
    void itShouldCheckIfParentEmailExists() {

        //given
        String email = "thomas@gmail.com";
        Parent parent = new Parent(
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                email
        );
        underTest.save(parent);

        //when
        Boolean expected = underTest.findParentByEmail(email);

        //then

        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfParentEmailDoesNotExists() {

        //given
        String email = "thomas@gmail.com";

        //when
        Boolean expected = underTest.findParentByEmail(email);

        //then

        assertThat(expected).isFalse();
    }

}