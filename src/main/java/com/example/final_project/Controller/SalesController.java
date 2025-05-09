package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Sales;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/get")
    public ResponseEntity getAllSales(){
        return ResponseEntity.status(HttpStatus.OK).body(salesService.getAllSales());
    }

    @PostMapping("/add/{counterBox_id}/{branch_id}")
    public ResponseEntity addTaxReports(@PathVariable Integer counterBox_id,@PathVariable Integer branch_id,  @Valid @RequestBody Sales sales){
        salesService.addSales(counterBox_id,branch_id,sales);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales is added!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSales(@PathVariable Integer id,@Valid @RequestBody  Sales sales){
        salesService.updateSales(id, sales);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales  is updated"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteSales(@PathVariable Integer id){
        salesService.deleteSales(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales is deleted"));
    }
}
