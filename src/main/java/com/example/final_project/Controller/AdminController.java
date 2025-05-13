package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.TaxPayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TaxPayerService taxPayerService;



    //sahar - 14

    @PutMapping("/activate/{taxPayerId}")
    public ResponseEntity updateBusiness(@AuthenticationPrincipal MyUser admin, @PathVariable Integer taxPayerId ){
        taxPayerService.activateTP(admin.getId(), taxPayerId);
        return ResponseEntity.status(200).body(new ApiResponse("the Tax Payer has been activated successfully "));
    }


}
