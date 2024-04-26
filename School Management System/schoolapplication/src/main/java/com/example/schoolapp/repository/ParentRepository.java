package com.example.schoolapp.repository;

import com.example.schoolapp.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//repository class for accessing query methods provided by JpaRepository. Eg: findAll()
@Repository
public interface ParentRepository
        extends JpaRepository<Parent,Long> {

    //user defined method to fetch true or false if an email is taken or not
    //select * from parent where email = ?
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Parent s WHERE s.email = ?1")
    Boolean findParentByEmail(String email);
}