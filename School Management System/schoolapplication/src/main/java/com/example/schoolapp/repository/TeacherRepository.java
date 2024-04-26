package com.example.schoolapp.repository;

import com.example.schoolapp.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//repository class for accessing query methods provided by JpaRepository. Eg: findAll()
@Repository
public interface TeacherRepository
        extends JpaRepository<Teacher,Long> {

    //user defined method to fetch true or false if an email is taken or not
    //select * from teacher where email = ?
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Teacher s WHERE s.email = ?1")
    Boolean findTeacherByEmail(String email);
}