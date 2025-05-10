package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.DTOAuditor;
import com.example.final_project.Service.AuditorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auditor")
@RequiredArgsConstructor
public class AuditorController {

    private final AuditorService auditorService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(auditorService.getAllAuditors());
    }

    @PostMapping("/add")
    public ResponseEntity addBranch(@RequestBody@Valid DTOAuditor dtoAuditor){
        auditorService.addAuditor(dtoAuditor);
        return ResponseEntity.status(200).body(new ApiResponse("new auditor added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAuditor(@PathVariable Integer id,@RequestBody@Valid DTOAuditor dtoAuditor){
        auditorService.updateAuditor(id, dtoAuditor);
        return ResponseEntity.status(200).body(new ApiResponse("auditor updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAuditor(@PathVariable Integer id){
        auditorService.deleteAuditor(id);
        return ResponseEntity.status(200).body(new ApiResponse("auditor deleted"));
    }


    @PutMapping("activate-business/{auditId}/{taxPayerId}/{businessId}")
    public ResponseEntity activateBusiness(@PathVariable Integer auditId,@PathVariable Integer taxPayerId,@PathVariable Integer businessId){
        auditorService.activateBusiness(auditId,taxPayerId,businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The business is activated successfully "));
    }


    @PutMapping("block-business/{auditId}/{taxPayerId}/{businessId}")
    public ResponseEntity blockBusiness(@PathVariable Integer auditId,@PathVariable Integer taxPayerId,@PathVariable Integer businessId){
        auditorService.blockBusiness(auditId,taxPayerId,businessId);
        return ResponseEntity.status(200).body(new ApiResponse("The business is blocked successfully "));
    }




}
