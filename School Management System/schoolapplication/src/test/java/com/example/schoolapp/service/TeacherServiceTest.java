package com.example.schoolapp.service;

import com.example.schoolapp.entity.Teacher;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;
    private TeacherService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TeacherService(teacherRepository);
    }


    @Test
    void canGetAllTeachers() {
        underTest.getTeachers();
        verify(teacherRepository).findAll();
    }

    @Test
    void testIsTeacherTableEmptyWhenEmpty() {
        //given
        when(teacherRepository.count()).thenReturn(0L);

        //when
        boolean isEmpty = underTest.isTeacherTableEmpty();

        //then
        assertThat(isEmpty).isEqualTo(true);
    }

    @Test
    void testIsTeacherTableEmptyWhenNotEmpty() {
        //given
        when(teacherRepository.count()).thenReturn(5L);

        //when
        boolean isEmpty = underTest.isTeacherTableEmpty();

        //then
        assertThat(isEmpty).isEqualTo(false);
    }

    @Test
    void canAddNewTeacher() {
        //given
        Teacher teacher = new Teacher(
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                "ancy@gmail.com",
                "networks"
        );

        //when
        underTest.addNewTeacher(teacher);

        //then
        ArgumentCaptor<Teacher> teacherArgumentCaptor =
                ArgumentCaptor.forClass(Teacher.class);

        verify(teacherRepository).save(teacherArgumentCaptor.capture());

        Teacher capturedTeacher = teacherArgumentCaptor.getValue();
        assertThat(capturedTeacher).isEqualTo(teacher);
    }

    @Test
    void willThrowWhenEmailIsTaken()
    {
        // given
        Teacher teacher = new Teacher(
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                "ancy@gmail.com",
                "networks"
        );

        when(teacherRepository.findTeacherByEmail(teacher.getEmail()))
                .thenReturn(true);

        // when and then
        assertThatThrownBy(()->underTest.addNewTeacher(teacher))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email taken");

        verify(teacherRepository,never()).save(any());

    }
    @Test
    void deleteTeacherWhenExists() {
        //given
        when(teacherRepository.existsById(1L)).thenReturn(true);

        //when
        underTest.deleteTeacher(1L);

        //then
        verify(teacherRepository,times(1)).deleteById(1L);

    }

    @Test
    void testUpdateTeacher() {
        // given
        Teacher teacher = new Teacher(
                1L,
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                "ancy@gmail.com",
                "networks"
        );

        String newEmail = "sujatha@gmail.com";
        String newName = "sujatha";

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findTeacherByEmail(newEmail)).thenReturn(false);

        //when
        underTest.updateTeacher(1L, newName, newEmail,null);


        //then
        verify(teacherRepository).findById(1L);
        verify(teacherRepository).findTeacherByEmail(newEmail);
        verify(teacherRepository).save(any(Teacher.class));

        assertThat(newName).isEqualTo(teacher.getName());
        assertThat(newEmail).isEqualTo(teacher.getEmail());

    }

    @Test
    void testUpdateTeacherWithExistingEmail() {
        //given
        Teacher teacher = new Teacher(
                1L,
                "ancy",
                LocalDate.of(2000, Month.JANUARY,5),
                "ancy09@gmail.com",
                "networks"
        );

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findTeacherByEmail("ancy@gmail.com")).thenReturn(true);

        //when then
        assertThatThrownBy(()->underTest.updateTeacher(1L,"sujatha","ancy@gmail.com",null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email taken");

        verify(teacherRepository, never()).save(any(Teacher.class));
    }

    @Test
    void deleteTeacherThrowsExceptionWhenTeacherDoesNotExist() {
        // Given
        long nonExistentTeacherId = 100L;
        when(teacherRepository.existsById(nonExistentTeacherId)).thenReturn(false);

        // when and Then
        assertThatThrownBy(()->underTest.deleteTeacher(nonExistentTeacherId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Teacher with id " + nonExistentTeacherId + " does not exist");

        verify(teacherRepository, never()).deleteById(anyLong());
    }
}