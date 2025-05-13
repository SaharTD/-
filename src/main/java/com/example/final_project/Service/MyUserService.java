package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService {


    private final MyUserRepository myUserRepository;

    // authority -> ADMIN
    public List<MyUser> getAllUser(Integer id) {
        MyUser admin = myUserRepository.findUserByIdAndRole(id,"ADMIN");
        return myUserRepository.findAll();
    }

    public void addAdmin(MyUser myUser) {

        String hashPassword = new BCryptPasswordEncoder().encode(myUser.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setRole("ADMIN");
        myUser.setAccountant(null);
        myUser.setAuditor(null);
        myUser.setTaxPayer(null);
        myUserRepository.save(myUser);
    }

    public void updateUser(Integer username, MyUser myUser) {
        MyUser oldMyUser = myUserRepository.findUserById(username);
        if (oldMyUser == null) {
            throw new ApiException("user not found");
        }

        oldMyUser.setUsername(myUser.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(myUser.getPassword());
        oldMyUser.setPassword(hashPassword);
        oldMyUser.setEmail(myUser.getEmail());
        oldMyUser.setName(myUser.getName());

        myUserRepository.save(oldMyUser);
    }


    public void deleteUser(Integer username) {
        MyUser myUser = myUserRepository.findUserById(username);
        if (myUser == null) {
            throw new ApiException("user not found");
        }
        myUserRepository.delete(myUser);
    }

}
