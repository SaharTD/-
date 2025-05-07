package com.example.final_project.Service;

import com.example.final_project.Repository.AccountantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountantService {

    private final AccountantRepository accountantRepository;
}
