package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.SaleDTO;
import com.example.final_project.DTO.SaleRequestDTO;
import com.example.final_project.DTO.ProductDTO;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Model.User;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
//
//    @PostMapping("/add/{counterBox_id}/{branch_id}")
//    public ResponseEntity addTaxReports(@PathVariable Integer counterBox_id,@PathVariable Integer branch_id) {
//        salesService.addSales(counterBox_id, branch_id);
//        return ResponseEntity.status(200).body(new ApiResponse("new tax report added"));
//    }
//
//    @PostMapping("/add/{accountantId}/{counterBox_id}/{branch_id}")
//    public ResponseEntity addTaxReports(@PathVariable Integer accountantId,@PathVariable Integer counterBox_id,@PathVariable Integer branch_id,  @Valid @RequestBody Sales sales){
//        salesService.addSales(accountantId,counterBox_id);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales is added!"));
//    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity updateSales(@AuthenticationPrincipal User user, @PathVariable Integer id, @Valid @RequestBody  Sales sales){
//        salesService.updateSales(user.getId(),id, sales);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales  is updated"));
//    }
//
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity deleteSales(@AuthenticationPrincipal User user,@PathVariable Integer id){
//        salesService.deleteSales(user.getId(),id);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales is deleted"));
//    }

//    @PostMapping("/add-product/{salesId}/barcode/{barcode}")
//    public ResponseEntity addProductToSaleByBarcode(@PathVariable Integer salesId, @PathVariable String barcode) {
//        salesService.addProductToSales(salesId, barcode);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Product added to sale"));
//    }


//    @PutMapping("/calculate/{salesId}")
//    public ResponseEntity calculateAmountsForSale(@PathVariable Integer salesId) {
//        salesService.calculateSalesAmounts(salesId);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Amounts calculated successfully"));
//    }


    @GetMapping("/sales-summary/{branchId}")
    public ResponseEntity<Map<String, Double>> getSalesByBranch(@PathVariable Integer branchId) {
        return ResponseEntity.status(200).body(salesService.getSalesSummaryByBranch(branchId));
    }


    @PostMapping("/adds/{accountantId}/{counterBoxId}/{branch_id}")
    public ResponseEntity addSales(@PathVariable Integer accountantId, @PathVariable Integer counterBoxId, @PathVariable Integer branch_id, @RequestBody @Valid SaleDTO saleDTO ) {
        salesService.addSales(accountantId,counterBoxId,branch_id,saleDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Sale made successfully"));
    }

    /// Integer accountantId, Integer saleId, ProductDTO productDTO
    @PutMapping("/add-product-in-sale/{accountantId}/{saleId}")
    public ResponseEntity addProductInSale(@PathVariable Integer accountantId,@PathVariable Integer saleId,@Valid @RequestBody ProductDTO product){
        salesService.addProductInSale(accountantId, saleId,product);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Product is added to invoice successfully"));
    }

    @GetMapping("/by-taxpayer/{taxPayerId}")
    public ResponseEntity getSalesByTaxPayerId(@PathVariable Integer taxPayerId) {
        List<Sales> sales = salesService.getSalesByTaxPayerId(taxPayerId);
        return ResponseEntity.status(200).body(sales);
    }

    @PutMapping("/confirm-sale/{accountantId}/{saleId}")
    public ResponseEntity confirmSale(@PathVariable Integer accountantId,@PathVariable Integer saleId){
        salesService.confirmSale(accountantId, saleId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" The sales confirmed successfully"));
    }


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
