package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.User;
import com.example.final_project.Service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class MyUserController {


    private final MyUserService myUserService;


    @GetMapping("/get")
    public ResponseEntity getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.getAllUser());
    }


    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        myUserService.addAdmin(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("user is added"));
    }

    @PutMapping("/update/{username}")
    public ResponseEntity updateUser(@PathVariable String username ,@Valid @RequestBody User user){
        myUserService.updateUser(username, user);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("user is updated"));
    }
    
    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){
        myUserService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("user not found"));
    }

}
