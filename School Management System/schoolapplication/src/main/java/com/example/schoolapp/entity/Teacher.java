package com.example.schoolapp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;


@Entity
@Table
public class Teacher {
    //sequence generator for id field to auto generate id
    @Id
    @SequenceGenerator(
            name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teacher_sequence"
    )
    private Long t_id;
    private String name;
    private LocalDate dob;
    private String email;
    private String subject;
    //transient to have it calculated in the database itself
    @Transient
    private Integer age;

    public Teacher() {
    }

    public Teacher(Long p_id, String name, LocalDate dob, String email,String subject) {
        this.t_id = p_id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.subject = subject;
    }

    public Teacher(String name, LocalDate dob, String email,String subject) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.subject = subject;

    }

    //getters and setters for each field
    public Long getId() {
        return t_id;
    }

    public void setId(Long id) {
        this.t_id = t_id;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "t_id=" + t_id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", age=" + age +
                '}';
    }
}
