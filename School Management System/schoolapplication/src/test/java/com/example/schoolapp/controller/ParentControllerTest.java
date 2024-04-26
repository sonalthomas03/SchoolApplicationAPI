package com.example.schoolapp.controller;

import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.service.ParentService;
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
class ParentControllerTest {
    @Mock private ParentService parentService;
    private ParentController underTest;

    @BeforeEach
    void setUp() {
        underTest = new ParentController(parentService);
    }
    @Test
    void canGetAllParents() {
        // given
        List<Parent> parents = new ArrayList<>();
        parents.add(new Parent(
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                "thomas@gmail.com"
        ));

        when(parentService.isParentTableEmpty()).thenReturn(false);
        when(parentService.getParents()).thenReturn(parents);

        //when
        List<Parent> result = underTest.getParents();

        // then
        verify(parentService).getParents();
    }

    @Test
    void registerNewParent() {
        // given
        Parent parent = new Parent(
                "thomas",
                LocalDate.of(2000, Month.JANUARY,5),
                "thomas@gmail.com"
        );


        doNothing().when(parentService).addNewParent(parent);

        // when
        ResponseEntity<String> response = underTest.registerNewParent(parent);

        // then
        verify(parentService).addNewParent(parent);
        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Parent registered successfully").isEqualTo( response.getBody());
    }

    @Test
    void deleteParent() {
        //given
        long parentId = 123L;

        // when
        ResponseEntity<String> response = underTest.deleteParent(parentId);
        verify(parentService).deleteParent(parentId);

        // then
        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Parent deleted successfully").isEqualTo( response.getBody());
    }


    @Test
    void updateParent() {
        // given
        Long parentId = 123L;
        String name = "thomas";
        String email = "thomas@gmail.com";

        // when
        ResponseEntity<String> response = underTest.updateParent(parentId, name, email);

        //then
        verify(parentService).updateParent(parentId, name, email);

        assertThat(HttpStatus.OK).isEqualTo( response.getStatusCode());
        assertThat("Parent updated successfully").isEqualTo( response.getBody());
    }
}