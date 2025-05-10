package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.DTOAuditor;
import com.example.final_project.Service.AuditorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    // Endpoint 27
    @PostMapping("/create-tax-report/{businessId}")
    public ResponseEntity createTaxReport(@PathVariable Integer businessId){
        auditorService.createTaxReport(businessId);
        return ResponseEntity.status(200).body(new ApiResponse("tax created"));
    }



    //اعتماد التقرير
    @PutMapping("/approve-tax-report/{taxReportId}/{auditorId}")
    public ResponseEntity approveTaxReport(@PathVariable Integer auditorId,@PathVariable Integer taxReportId) {
        auditorService.approveTaxReportStatus(taxReportId,auditorId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Status updated to: " ));
    }

    @PutMapping("/reject-tax-report/{taxReportId}/{auditorId}")
    public ResponseEntity rejectTaxReport(@PathVariable Integer auditorId,@PathVariable Integer taxReportId) {
        auditorService.rejectTaxReportStatus(taxReportId,auditorId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Status updated to: " ));
    }

}
