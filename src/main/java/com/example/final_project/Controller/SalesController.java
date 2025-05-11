package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.ProductDTO;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/get")
    public ResponseEntity getAllSales(){
        return ResponseEntity.status(HttpStatus.OK).body(salesService.getAllSales());
    }

    @PostMapping("/add/{accountantId}/{counterBox_id}/{branch_id}")
    public ResponseEntity addTaxReports(@PathVariable Integer accountantId,@PathVariable Integer counterBox_id,@PathVariable Integer branch_id,  @Valid @RequestBody Sales sales){
        salesService.addSales(accountantId,counterBox_id,branch_id,sales);
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
//    }
//
//    @PutMapping("/add-product-in-sale/{accountantId}/{saleId}")
//    public ResponseEntity addProductInSale(@PathVariable Integer accountantId,@PathVariable Integer saleId,@Valid @RequestBody ProductDTO product){
//        salesService.addProductInSale(accountantId, saleId,product);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Product is added to invoice successfully"));
//    }

//
//    @GetMapping("/print-sale/{accountantId}/{saleId}")
//    public ResponseEntity<byte[]> printInvoice(@PathVariable Integer accountantId,@PathVariable Integer saleId) {
//        byte[] pdf = salesService.printInvoice(accountantId,saleId);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tax-report-" + saleId + ".pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdf);
//    }


}
