package com.example.schoolapp.controller;

import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;
    private TeacherController underTest;

    @BeforeEach
    void setUp() {
        underTest = new TeacherController(teacherService);
    }

    @Test
    void canGetAllTeachers() {
        // given
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher(
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                "ancy@gmail.com",
                "networks"
        ));

        when(teacherService.isTeacherTableEmpty()).thenReturn(false);
        when(teacherService.getTeachers()).thenReturn(teachers);

        //when
        List<Teacher> result = underTest.getTeachers();

        // then
        verify(teacherService).getTeachers();
    }

    @Test
    void registerNewTeacher() {
        // given
        Teacher teacher = new Teacher(
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                "ancy@gmail.com",
                "networks"
        );

        doNothing().when(teacherService).addNewTeacher(teacher);

        // when
        ResponseEntity<String> response = underTest.registerNewTeacher(teacher);

        // then
        verify(teacherService).addNewTeacher(teacher);
        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Teacher registered successfully").isEqualTo( response.getBody());



    }

    @Test
    void deleteTeacher() {
        //given
        long teacherId = 123L;

        // when
        ResponseEntity<String> response = underTest.deleteTeacher(teacherId);
        verify(teacherService).deleteTeacher(teacherId);

        // then
        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Teacher deleted successfully").isEqualTo( response.getBody());
    }

    @Test
    void updateTeacher() {
        // given
        Long teacherId = 123L;
        String name = "ancy";
        String email = "ancy@gmail.com";
        String subject = "algorithms";

        // when
        ResponseEntity<String> response = underTest.updateTeacher(teacherId, name, email,subject);

        //then
        verify(teacherService).updateTeacher(teacherId, name, email,subject);

        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Teacher updated successfully").isEqualTo( response.getBody());
    }
}