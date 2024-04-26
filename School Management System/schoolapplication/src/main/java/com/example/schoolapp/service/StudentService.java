package com.example.schoolapp.service;

import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.entity.Student;
import com.example.schoolapp.entity.Teacher;
import com.example.schoolapp.repository.ParentRepository;
import com.example.schoolapp.repository.StudentRepository;
import com.example.schoolapp.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, ParentRepository parentRepository,TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.teacherRepository = teacherRepository;
    }



    //method to return all students to controller layer
    public List<Student> getStudents() {
        return studentRepository.findAll();

    }

    //to check if student table is empty
    public boolean isStudentTableEmpty() {
        return studentRepository.count() == 0;
    }

    //to add a new parent
    public void addNewStudent(Student student, Long parentId, Long teacherId) {

        //check if parent and teacher exists
        Optional<Parent> parentOptional = parentRepository.findById(parentId);
        if (parentOptional.isEmpty()) {
            throw new IllegalStateException("Parent with ID " + parentId + " not found");
        }

        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if(teacherOptional.isEmpty()) {
            throw new IllegalStateException("Teacher with ID " + teacherId + " not found");
        }


        Boolean studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());

        //throw exception if email is taken
        if(studentOptional)
        {
            throw new IllegalStateException("Email taken");
        }

        Parent parent = parentOptional.get();
        student.setParent(parent);

        Teacher teacher = teacherOptional.get();
        student.setTeacher(teacher);

        // Save the student
        studentRepository.save(student);

        //System.out.println(student);
    }

    //to delete a student
    public void deleteStudent(long studentId)
    {

        //throw exception if student doesnt exist
        boolean exists = studentRepository.existsById(studentId);
        if(!exists)
        {
            throw new IllegalStateException("Student with id "+studentId+" does not exist");
        }
        studentRepository.deleteById(studentId);

    }

    //to update parent
    @Transactional
    public void updateStudent(Long studentId, String name, String email, Parent parentId, Teacher teacherId)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id "+studentId+" does not exist"));

        //check if name,parentid,teacherid is given or not
        if(name!=null &&
                name.length()>0 &&
                !Objects.equals(student.getName(),name))
            student.setName(name);

        if(parentId!=null && !Objects.equals(student.getParent(),parentId))
            student.setParent(parentId);

        if(teacherId!=null && !Objects.equals(student.getTeacher(),teacherId))
            student.setTeacher(teacherId);


        if(email!=null &&
                email.length()>0&&
                !Objects.equals(student.getEmail(),email)) {
            Boolean studentOptional = studentRepository
                    .findStudentByEmail(email);
            if (studentOptional) {
                throw new IllegalStateException("Email taken");
            }
            student.setEmail(email);
        }

        studentRepository.save(student);

    }
}


