package com.example.schoolapp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;


@Entity
@Table
public class Student {
    //sequence generator for id field to auto generate id
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private LocalDate dob;
    private String email;
    //transient to have it calculated in the database itself
    @Transient
    private Integer age;

    //many to one mapping since multiple students can have the same parent and teacher
    //p_id
    //set cascade type to persist to avoid deletion of parent and teacher when student is deleted
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Parent parent;
    //t_id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Teacher teacher;

    public Student() {
    }

    public Student(Long id, String name, LocalDate dob, String email, Parent parent,Teacher teacher) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.parent = parent;
        this.teacher = teacher;
    }

    public Student(String name, LocalDate dob, String email, Parent parent,Teacher teacher) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.parent = parent;
        this.teacher = teacher;

    }

    public Student(String name, LocalDate dob, String email) {
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    //getters and setters for each field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", parent=" + parent +
                ", teacher=" + teacher +
                '}';
    }
}
