package com.example.schoolapp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;


@Entity
@Table
public class Parent {
    //sequence generator for id field to auto generate id
    @Id
    @SequenceGenerator(
            name = "parent_sequence",
            sequenceName = "parent_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "parent_sequence"
    )
    private Long p_id;
    private String name;
    private LocalDate dob;
    private String email;
    //transient to have it calculated in the database itself
    @Transient
    private Integer age;

    public Parent() {
    }

    public Parent(Long p_id, String name, LocalDate dob, String email) {
        this.p_id = p_id;
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public Parent(String name, LocalDate dob, String email) {
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    //getters and setters for each field
    public Long getId() {
        return p_id;
    }

    public void setId(Long id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + p_id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }


}
