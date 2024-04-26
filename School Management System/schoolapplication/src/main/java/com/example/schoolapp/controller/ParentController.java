package com.example.schoolapp.controller;


import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/parent")
public class ParentController {

    private final ParentService parentService;


    @Autowired
    public ParentController(ParentService parentService)
    {
        this.parentService = parentService;
    }


    //this is the api end point for GET requests
    @GetMapping
    public List<Parent> getParents() {

        if (parentService.isParentTableEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No parents found");
        }
        return parentService.getParents();
    }

    //this is the api end point for POST requests
    @PostMapping
    public ResponseEntity<String> registerNewParent(@RequestBody Parent parent)
    {
        parentService.addNewParent(parent);
        return ResponseEntity.ok("Parent registered successfully");

    }

    //this is the api end point for DELETE requests
    @DeleteMapping(path = "{parentId}")
    public ResponseEntity<String> deleteParent(@PathVariable("parentId") long id)
    {
        parentService.deleteParent(id);
        return ResponseEntity.ok("Parent deleted successfully");

    }

    //this is the api end point for PUT requests
    @PutMapping(path = "{parentId}")
    public ResponseEntity<String> updateParent(
            @PathVariable("parentId") Long parentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
    {
        parentService.updateParent(parentId,name,email);
        return ResponseEntity.ok("Parent updated successfully");
    }
}
