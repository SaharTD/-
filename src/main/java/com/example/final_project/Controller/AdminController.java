package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.Service.TaxPayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TaxPayerService taxPayerService;


    @PutMapping("/activate/{adminId}/{taxPayerId}")
    public ResponseEntity updateBusiness(@PathVariable Integer adminId , @PathVariable Integer taxPayerId ){
        taxPayerService.activateTP(adminId,taxPayerId);
        return ResponseEntity.status(200).body(new ApiResponse("the Tax Payer has been activated successfully "));
    }


}
