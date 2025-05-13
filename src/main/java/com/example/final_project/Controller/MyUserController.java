package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.MyUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class MyUserController {

    private final MyUserService myUserService;

    // authority -> ADMIN
    @GetMapping("/get")
    public ResponseEntity getAllUser(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(HttpStatus.OK).body(myUserService.getAllUser(myUser.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody MyUser myUser){
        myUserService.addAdmin(myUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("user is added"));
    }

    // authority -> any
    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal MyUser myUser, @Valid @RequestBody MyUser myUser2){
        myUserService.updateUser(myUser.getId(), myUser2);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("user is updated"));
    }

    // authority -> ADMIN
    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteUser(@AuthenticationPrincipal MyUser myUser, @PathVariable String username){
        myUserService.deleteUser(myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("user not found"));
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body(new ApiResponse("log out"));
    }

}
