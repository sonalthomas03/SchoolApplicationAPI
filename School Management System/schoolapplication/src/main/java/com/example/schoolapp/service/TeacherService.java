package com.example.schoolapp.service;

import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TeacherService {


    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    //method to return all teachers to controller layer
    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();

    }

    //to check if teacher table is empty
    public boolean isTeacherTableEmpty() {
        return teacherRepository.count() == 0;
    }

    //to add a new teacher
    public void addNewTeacher(Teacher teacher) {

        Boolean teacherOptional = teacherRepository
                .findTeacherByEmail(teacher.getEmail());

        //throw exception if email is taken
        if(teacherOptional)
        {
            throw new IllegalStateException("Email taken");
        }
        teacherRepository.save(teacher);

    }

    //to delete a parent
    public void deleteTeacher(long teacherId)
    {

        //throw exception if teacher doesnt exists
        boolean exists = teacherRepository.existsById(teacherId);
        if(!exists)
        {
            throw new IllegalStateException("Teacher with id "+teacherId+" does not exist");
        }
        teacherRepository.deleteById(teacherId);

    }

    //to update teacher
    @Transactional
    public void updateTeacher(Long teacherId, String name, String email, String subject)
    {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalStateException(
                        "Teacher with id "+teacherId+" does not exist"));

        //check if name and subject is given or not
        if(name!=null &&
                name.length()>0 &&
                !Objects.equals(teacher.getName(),name))
            teacher.setName(name);

        if(email!=null && email.length()>0 && !Objects.equals(teacher.getEmail(),email)){
            Boolean teacherOptional = teacherRepository.findTeacherByEmail(email);
            if(teacherOptional) {
                throw new IllegalStateException("Email taken");
            }
            teacher.setEmail(email);
        }
        if(subject!=null &&
                subject.length()>0 &&
                !Objects.equals(teacher.getSubject(),subject))
            teacher.setSubject(subject);
        teacherRepository.save(teacher);
    }

}


