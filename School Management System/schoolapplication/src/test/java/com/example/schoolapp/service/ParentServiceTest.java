package com.example.schoolapp.service;

import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.repository.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ParentServiceTest {

    @Mock
    private ParentRepository parentRepository;
    private ParentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ParentService(parentRepository);
    }

    @Test
    void canGetAllParents() {
        // when
        underTest.getParents();
        // then
        verify(parentRepository).findAll();
    }

    @Test
    void testIsParentTableEmptyWhenEmpty() {
        // given
        when(parentRepository.count()).thenReturn(0L);

        // when
        boolean isEmpty = underTest.isParentTableEmpty();

        // then
        assertThat(isEmpty).isEqualTo(true);
    }

    @Test
    void testIsParentTableEmptyWhenNotEmpty() {
        // given
        when(parentRepository.count()).thenReturn(5L); // Assuming there are 5 students

        // when
        boolean isEmpty = underTest.isParentTableEmpty();

        // then
        assertThat(isEmpty).isEqualTo(false);
    }

    @Test
    void canAddNewParent() {
        // given
        Parent parent = new Parent(
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                "thomas@gmail.com"
        );

        // when
        underTest.addNewParent(parent);

        //then
        ArgumentCaptor<Parent> parentArgumentCaptor =
                ArgumentCaptor.forClass(Parent.class);

        verify(parentRepository).save(parentArgumentCaptor.capture());

        Parent capturedParent = parentArgumentCaptor.getValue();
        assertThat(capturedParent).isEqualTo(parent);
    }

    @Test
    void willThrowWhenEmailIsTaken()
    {
        // given
        Parent parent = new Parent(
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                "thomas@gmail.com"
        );

        when(parentRepository.findParentByEmail(parent.getEmail()))
                .thenReturn(true);

        // when and then

        assertThatThrownBy(()->underTest.addNewParent(parent))
        .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email taken");

        verify(parentRepository,never()).save(any());

    }
    @Test
    void deleteParentWhenExists() {

        //mocking - given
        when(parentRepository.existsById(1L)).thenReturn(true);

        //when
        underTest.deleteParent(1L);

        //then
        verify(parentRepository,times(1)).deleteById(1L);

    }

    @Test
    void testUpdateParent() {
        //given
        Parent parent = new Parent(
                1L,
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                "thomas@gmail.com"
        );
        String newEmail = "antony@gmail.com";
        String newName = "antony";

        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(parentRepository.findParentByEmail(newEmail)).thenReturn(false);

        //when
        underTest.updateParent(1L, newName, newEmail);

        //then
        verify(parentRepository).findById(1L);
        verify(parentRepository).findParentByEmail(newEmail);
        verify(parentRepository).save(any(Parent.class));

        assertThat(newName).isEqualTo(parent.getName());
        assertThat(newEmail).isEqualTo(parent.getEmail());
    }

    @Test
    void testUpdateParentWithExistingEmail() {
        //given
        Parent parent = new Parent(
                1L,
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                "thomas06@gmail.com"
        );

        when(parentRepository.findById(1L)).thenReturn(Optional.of(parent));
        when(parentRepository.findParentByEmail("thomas@gmail.com")).thenReturn(true);

        //when then

        assertThatThrownBy(()->underTest.updateParent(1L,"antony","thomas@gmail.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Email taken");

        verify(parentRepository, never()).save(any(Parent.class));
    }

    @Test
    void deleteParentThrowsExceptionWhenParentDoesNotExist() {
        // Given
        long nonExistentParentId = 100L;
        when(parentRepository.existsById(nonExistentParentId)).thenReturn(false);

        // When and Then
        assertThatThrownBy(()->underTest.deleteParent(nonExistentParentId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Parent with id " + nonExistentParentId + " does not exist");

        verify(parentRepository, never()).deleteById(anyLong());
    }
}