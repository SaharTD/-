package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.DTOAuditor;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.AuditorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auditor")
@RequiredArgsConstructor
public class AuditorController {

    private final AuditorService auditorService;

    // authority -> ADMIN
    @GetMapping("/get")
    public ResponseEntity getAll(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(auditorService.getAllAuditors(myUser.getId()));
    }

    // authority -> ADMIN
    @PostMapping("/add")
    public ResponseEntity addAuditor(@AuthenticationPrincipal MyUser myUser, @RequestBody@Valid DTOAuditor dtoAuditor){
        auditorService.addAuditor(myUser.getId(),dtoAuditor);
        return ResponseEntity.status(200).body(new ApiResponse("new auditor added"));
    }

    // authority -> Auditor
    @PutMapping("/update")
    public ResponseEntity updateAuditor(@AuthenticationPrincipal MyUser myUser, @RequestBody@Valid DTOAuditor dtoAuditor){
        auditorService.updateAuditor(myUser.getId(), dtoAuditor);
        return ResponseEntity.status(200).body(new ApiResponse("auditor updated"));
    }

    // authority -> ADMIN
    @DeleteMapping("/delete")
    public ResponseEntity deleteAuditor(@AuthenticationPrincipal MyUser myUser){
        auditorService.deleteAuditor(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("auditor deleted"));
    }

    // authority -> Auditor
    // Endpoint 27
    @PostMapping("/create-tax-report/{businessId}")
    public ResponseEntity createTaxReport(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId){
        auditorService.createTaxReport(myUser.getId(),businessId);
        return ResponseEntity.status(200).body(new ApiResponse("tax created"));
    }

    // authority -> Auditor
    @PutMapping("activate-business/{taxPayerId}/{businessId}")
    public ResponseEntity activateBusiness(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer taxPayerId, @PathVariable Integer businessId){
        auditorService.activateBusiness(myUser.getId(),taxPayerId,businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The business is activated successfully "));
    }

    // authority -> Auditor
    @PutMapping("block-business/{taxPayerId}/{businessId}")
    public ResponseEntity blockBusiness(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer taxPayerId, @PathVariable Integer businessId){
        auditorService.blockBusiness(myUser.getId(), taxPayerId,businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The business is blocked successfully "));
    }


    //اعتماد التقرير
    @PutMapping("/approve-tax-report/{taxReportId}")
    public ResponseEntity approveTaxReport(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer taxReportId) {
        auditorService.approveTaxReportStatus(taxReportId, myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Status updated to: " ));
    }

    @PutMapping("/reject-tax-report/{taxReportId}/{auditorId}")
    public ResponseEntity rejectTaxReport(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer taxReportId) {
        auditorService.rejectTaxReportStatus(taxReportId, myUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Status updated to: " ));
    }

}
