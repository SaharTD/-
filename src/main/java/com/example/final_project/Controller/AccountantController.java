package com.example.final_project.Controller;


import com.example.final_project.Service.AccountantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accountant")
public class AccountantController {

    private final AccountantService accountantService;
}
