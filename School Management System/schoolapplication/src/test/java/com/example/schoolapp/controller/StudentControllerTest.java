package com.example.schoolapp.controller;

import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.service.StudentService;
import com.example.schoolapp.entity.Student;
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
class StudentControllerTest {
    @Mock private StudentService studentService;
    private StudentController underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentController(studentService);
    }

    @Test
    void canGetAllStudents() {
        // given
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "Sonal",
                LocalDate.of(2000, Month.JANUARY,5),
                "sonal@gmail.com"
        ));

        when(studentService.isStudentTableEmpty()).thenReturn(false);
        when(studentService.getStudents()).thenReturn(students);

        //when
        List<Student> result = underTest.getStudents();

        // then
        verify(studentService).getStudents();
    }

    @Test
    void registerNewStudent() {
        // given
        Student student = new Student(
                "Sonal",
                LocalDate.of(2000, Month.JANUARY,5),
                "sonal@gmail.com"
        );
        Long parentId = 123L;
        Long teacherId = 456L;

        doNothing().when(studentService).addNewStudent(student, parentId, teacherId);

        // when
        ResponseEntity<String> response = underTest.registerNewStudent(student, parentId, teacherId);

        // then
        verify(studentService).addNewStudent(student, parentId, teacherId);
        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Student registered successfully").isEqualTo( response.getBody());
    }

    @Test
    void deleteStudent() {
        //given
        long studentId = 123L;

        // when
        ResponseEntity<String> response = underTest.deleteStudent(studentId);
        verify(studentService).deleteStudent(studentId);

        // then
        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Student deleted successfully").isEqualTo( response.getBody());
    }

    @Test
    void updateStudent() {

        // given
        Long studentId = 123L;
        String name = "sonal";
        String email = "sonal@gmail.com";
        Parent parent = new Parent(
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                "thomas@gmail.com");
        Teacher teacher = new Teacher(
                1L,
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                "ancy@gmail.com",
                "networks");
        // when
        ResponseEntity<String> response = underTest.updateStudent(studentId, name, email, parent, teacher);

        //then
        verify(studentService).updateStudent(studentId, name, email, parent, teacher);

        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Student updated successfully").isEqualTo( response.getBody());

    }
}