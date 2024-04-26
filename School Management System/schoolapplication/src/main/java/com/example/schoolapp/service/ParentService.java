package com.example.schoolapp.service;

import com.example.schoolapp.entity.Parent;
import com.example.schoolapp.repository.ParentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ParentService {


    private final ParentRepository parentRepository;


    @Autowired
    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }


    //method to return all parents to controller layer
    public List<Parent> getParents() {
        return parentRepository.findAll();

    }

    //to check if parent table is empty
    public boolean isParentTableEmpty() {
        return parentRepository.count() == 0;
    }


    //to add a new parent
    public void addNewParent(Parent parent) {

        Boolean parentOptional = parentRepository
                .findParentByEmail(parent.getEmail());

        //throw exception if email is taken
        if(parentOptional)
        {
            throw new IllegalStateException("Email taken");
        }
        parentRepository.save(parent);

    }

    //to delete a parent
    public void deleteParent(long parentId)
    {

        //throw exception if parent doesnt exists
        boolean exists = parentRepository.existsById(parentId);
        if(!exists)
        {
            throw new IllegalStateException("Parent with id "+parentId+" does not exist");
        }
        parentRepository.deleteById(parentId);

    }

    //to update parent
    @Transactional
    public void updateParent(Long parentId, String name, String email)
    {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Parent with id "+parentId+" does not exist"));

        //check if name is given or not
        if(name!=null &&
                name.length()>0 &&
                !Objects.equals(parent.getName(),name))
            parent.setName(name);

        if(email!=null && email.length()>0 && !Objects.equals(parent.getEmail(),email)){
            Boolean parentOptional = parentRepository.findParentByEmail(email);//findParentByEmail(email);
            if(parentOptional) {
                throw new IllegalStateException("Email taken");
            }
            parent.setEmail(email);
        }
        parentRepository.save(parent);
    }
}


