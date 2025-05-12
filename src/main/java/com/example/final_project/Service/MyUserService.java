package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.User;
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
    public List<User> getAllUser(Integer id) {
        User admin = myUserRepository.findUserByIdAndRole(id,"ADMIN");
        return myUserRepository.findAll();
    }

    public void addAdmin(User user) {

        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole("ADMIN");
        user.setAccountant(null);
        user.setAuditor(null);
        user.setTaxPayer(null);
        myUserRepository.save(user);
    }

    public void updateUser(Integer username, User user) {
        User oldUser = myUserRepository.findUserById(username);
        if (oldUser == null) {
            throw new ApiException("user not found");
        }

        oldUser.setUsername(user.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        oldUser.setPassword(hashPassword);
        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());

        myUserRepository.save(oldUser);
    }


    public void deleteUser(Integer username) {
        User user = myUserRepository.findUserById(username);
        if (user == null) {
            throw new ApiException("user not found");
        }
        myUserRepository.delete(user);
    }

}
