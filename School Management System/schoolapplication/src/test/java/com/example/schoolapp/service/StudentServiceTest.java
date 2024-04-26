package com.example.schoolapp.service;

import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.entity.Student;
import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.repository.ParentRepository;
import com.example.schoolapp.repository.StudentRepository;
import com.example.schoolapp.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    @Mock private ParentRepository parentRepository;
    @Mock private TeacherRepository teacherRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository, parentRepository, teacherRepository);
    }

    @Test
    void canGetAllStudents() {
        //when
        underTest.getStudents();
        //then
        verify(studentRepository).findAll();
    }



    @Test
    void canAddNewStudent() {

        //given
        when(parentRepository.findById(1L))
                .thenReturn(Optional.of(new Parent(1L, "thomas", LocalDate.of(2000, Month.JANUARY, 5), "thomas@gmail.com")));
        when(teacherRepository.findById(2L))
                .thenReturn(Optional.of(new Teacher(2L, "ancy", LocalDate.of(2000, Month.JANUARY, 5), "thomas@gmail.com","Algorithms")));

        Long parentId = 1L;
        Long teacherId = 2L;
        Student student = new Student(
                "Sonal",
                LocalDate.of(2000, Month.JANUARY,5),
                "sonal@gmail.com"
        );

        //when
        underTest.addNewStudent(student, parentId, teacherId);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);

    }

    @Test
    void willThrowWhenEmailIsTaken() {

        //given
        when(parentRepository.findById(1L))
                .thenReturn(Optional.of(new Parent(1L, "thomas", LocalDate.of(2000, Month.JANUARY, 5), "thomas@gmail.com")));
        when(teacherRepository.findById(2L))
                .thenReturn(Optional.of(new Teacher(2L, "ancy", LocalDate.of(2000, Month.JANUARY, 5), "thomas@gmail.com","Algorithms")));

        Long parentId = 1L;
        Long teacherId = 2L;
        Student student = new Student(
                "Sonal",
                LocalDate.of(2000, Month.JANUARY,5),
                "sonal@gmail.com"
        );


        when(studentRepository.findStudentByEmail(student.getEmail()))
                .thenReturn(true);
        //when and then
        assertThatThrownBy(() -> underTest.addNewStudent(student, parentId, teacherId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email taken");

        verify(studentRepository,never()).save(any());
    }

    @Test
    void testIsStudentTableEmptyWhenEmpty() {
        // given
        when(studentRepository.count()).thenReturn(0L);

        // when
        boolean isEmpty = underTest.isStudentTableEmpty();

        // then
        assertThat(isEmpty).isEqualTo(true);
    }

    @Test
    void testIsStudentTableEmptyWhenNotEmpty() {
        // given
        when(studentRepository.count()).thenReturn(5L); // Assuming there are 5 students

        // when
        boolean isEmpty = underTest.isStudentTableEmpty();

        // then
        assertThat(isEmpty).isEqualTo(false);
    }

    @Test
    void testDeleteStudentWhenExists() {
        // given
        when(studentRepository.existsById(1L)).thenReturn(true);

        // when
        underTest.deleteStudent(1L);

        // then
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStudentWhenNotExists() {
        // given
        when(studentRepository.existsById(1L)).thenReturn(false);

        // when
        assertThatThrownBy(() -> underTest.deleteStudent(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Student with id "+1L+" does not exist");

        // then
        verify(studentRepository, never()).deleteById(anyLong());
    }



    @Test
    void testUpdateStudent() {
        // given
        Student student = new Student(
                1L,
                "Sonal",
                LocalDate.of(2000, Month.JANUARY,5),
                "sonal@gmail.com",
                null,
                null
        );
        String newEmail = "thomas@gmail.com";
        String newName = "thomas";

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.findStudentByEmail(newEmail)).thenReturn(false);

        // when
        underTest.updateStudent(1L, newName, newEmail, null, null);

        // then
        verify(studentRepository).findById(1L);
        verify(studentRepository).findStudentByEmail(newEmail);
        verify(studentRepository).save(any(Student.class));

        assertThat(newName).isEqualTo(student.getName());
        assertThat(newEmail).isEqualTo(student.getEmail());

    }


    @Test
    void testUpdateStudentWithExistingEmail() {
        // given
        Student student = new Student(
                1L,
                "Sonal",
                LocalDate.of(2000, Month.JANUARY,5),
                "sonal04@gmail.com",
                null,
                null
        );
        when(studentRepository.findStudentByEmail("sonal@gmail.com")).thenReturn(true);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        //when
        assertThatThrownBy(() ->underTest.updateStudent(1L, "thomas", "sonal@gmail.com", null, null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email taken");

        // then
        verify(studentRepository, never()).save(any(Student.class));
    }
}

