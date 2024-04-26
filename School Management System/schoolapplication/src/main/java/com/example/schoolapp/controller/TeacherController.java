package com.example.schoolapp.controller;


import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;


    @Autowired
    public TeacherController(TeacherService teacherService)
    {
        this.teacherService = teacherService;
    }

    //this is the api end point for GET requests
    @GetMapping
    public List<Teacher> getTeachers()
    {
        if (teacherService.isTeacherTableEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No teachers found");
        }
        return teacherService.getTeachers();
    }

    //this is the api end point for POST requests
    @PostMapping
    public ResponseEntity<String> registerNewTeacher(@RequestBody Teacher teacher)
    {
        teacherService.addNewTeacher(teacher);
        return ResponseEntity.ok("Teacher registered successfully");


    }

    //this is the api end point for DELETE requests
    @DeleteMapping(path = "{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("teacherId") long id)
    {

        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Teacher deleted successfully");
    }

    //this is the api end point for PUT requests
    @PutMapping(path = "{teacherId}")
    public ResponseEntity<String> updateTeacher(
            @PathVariable("teacherId") Long teacherId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String subject)
    {
        teacherService.updateTeacher(teacherId,name,email,subject);
        return ResponseEntity.ok("Teacher updated successfully");

    }
}
