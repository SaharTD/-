package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService {


    private MyUserRepository myUserRepository;


    public List<User> getAllUser(){
       return myUserRepository.findAll();
    }

    public void addAdmin(User user){
        user.setRole("ADMIN");
        myUserRepository.save(user);
    }


    public void deleteUser(Integer id){
        User user=myUserRepository.findUserById(id);

        if(user==null){
            throw new ApiException("user not found");
        }
        myUserRepository.delete(user);
    }
    
}
