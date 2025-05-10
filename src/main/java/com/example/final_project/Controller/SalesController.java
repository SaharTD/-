package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.SaleRequestDTO;
import com.example.final_project.Model.Sales;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/add-product/{salesId}/barcode/{barcode}")
    public ResponseEntity addProductToSaleByBarcode(@PathVariable Integer salesId, @PathVariable String barcode) {
        salesService.addProductToSales(salesId, barcode);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product added to sale"));
    }

    @PutMapping("/calculate/{salesId}")
    public ResponseEntity calculateAmountsForSale(@PathVariable Integer salesId) {
        salesService.calculateSalesAmounts(salesId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Amounts calculated successfully"));
    }


    @GetMapping("/sales-summary/{branchId}")
    public ResponseEntity<Map<String, Double>> getSalesByBranch(@PathVariable Integer branchId) {
        return ResponseEntity.status(200).body(salesService.getSalesSummaryByBranch(branchId));
    }

    @PostMapping("/adds/{counterBox_id}/{branch_id}")
    public ResponseEntity addSales(@PathVariable Integer counterBox_id, @PathVariable Integer branch_id, @RequestBody @Valid Sales sales) {
        Map<String, Object> result = salesService.addSales2(counterBox_id, branch_id, sales);
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/by-taxpayer/{taxPayerId}")
    public ResponseEntity getSalesByTaxPayerId(@PathVariable Integer taxPayerId) {
        List<Sales> sales = salesService.getSalesByTaxPayerId(taxPayerId);
        return ResponseEntity.status(200).body(sales);
    }




}
