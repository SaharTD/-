package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Service.TaxReportsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/taxReports")
@RequiredArgsConstructor
public class TaxReportsController {

    private final TaxReportsService taxReportsService;

    @GetMapping("/get")
    public ResponseEntity getAllTaxReports(){
        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getAllTaxReports());
    }

    @PostMapping("/add/{auditor_id}/{business_id}")
    public ResponseEntity addTaxReports(@PathVariable Integer auditor_id,@PathVariable Integer business_id, @Valid @RequestBody TaxReports taxReports){
        taxReportsService.addTaxReports(auditor_id, business_id, taxReports);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is added!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTaxReports(@PathVariable Integer id,@Valid @RequestBody TaxReports taxReports){
        taxReportsService.updateTaxReports(id, taxReports);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is updated"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteTaxReports(@PathVariable Integer id){
        taxReportsService.deleteTaxReports(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is deleted"));
    }
}
