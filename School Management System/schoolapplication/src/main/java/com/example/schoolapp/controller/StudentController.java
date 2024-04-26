package com.example.schoolapp.controller;


import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.entity.Student;
import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    //this is the api end point for GET requests
    @GetMapping
    public List<Student> getStudents() {
        if (studentService.isStudentTableEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found");
        }
        return studentService.getStudents();
    }

    //this is the api end point for POST requests
    @PostMapping(path = {"{ParentId}/{TeacherId}",""})
    public ResponseEntity<String> registerNewStudent(@RequestBody Student student, @PathVariable Long ParentId, @PathVariable Long TeacherId) {

        studentService.addNewStudent(student,ParentId,TeacherId);
        // Return success response
        return ResponseEntity.ok("Student registered successfully");
    }

    //this is the api end point for DELETE requests
    @DeleteMapping(path = {"{studentId}",""})
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") long id)
    {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");

    }

    //this is the api end point for PUT requests
    @PutMapping(path = "{studentId}")
    public ResponseEntity<String> updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Parent parentId,
            @RequestParam(required = false) Teacher TeacherId)
    {
        studentService.updateStudent(studentId,name,email,parentId,TeacherId);
        return ResponseEntity.ok("Student updated successfully");

    }

}
