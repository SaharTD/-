package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.SaleDTO;
import com.example.final_project.DTO.SaleRequestDTO;
import com.example.final_project.DTO.ProductDTO;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ItemSaleService;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final ItemSaleService itemSaleService;

    @GetMapping("/get")
    public ResponseEntity getAllSales(){
        return ResponseEntity.status(HttpStatus.OK).body(salesService.getAllSales());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSales(@AuthenticationPrincipal User user, @PathVariable Integer id, @Valid @RequestBody  Sales sales){
        salesService.updateSales(user.getId(),id, sales);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales  is updated"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteSales(@AuthenticationPrincipal User user, @PathVariable Integer id){
        salesService.deleteSales(user.getId(),id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" Sales is deleted"));
    }




//    @GetMapping("/sales-summary/{branchId}")
//    public ResponseEntity<Map<String, Double>> getSalesByBranch(@AuthenticationPrincipal User accountant) {
//        return ResponseEntity.status(200).body(salesService.getSalesSummaryByBranch(accountant.getId()));
//    }



    @PostMapping("/adds/{counterBoxId}/{branch_id}")
    public ResponseEntity addSales(@AuthenticationPrincipal User accountant, @PathVariable Integer counterBoxId, @PathVariable Integer branch_id, @RequestBody @Valid SaleDTO saleDTO ) {
        salesService.addSales(accountant.getId(),counterBoxId,branch_id,saleDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Sale made successfully"));
    }

    /// Integer accountantId, Integer saleId, ProductDTO productDTO
    @PutMapping("/add-product-in-sale/{saleId}")
    public ResponseEntity addProductInSale(@AuthenticationPrincipal User accountant,@PathVariable Integer saleId,@Valid @RequestBody ProductDTO product){
        salesService.addProductInSale(accountant.getId(), saleId,product);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(" Product is added to invoice successfully"));
    }

    @GetMapping("/by-taxpayer/{taxPayerId}")
    public ResponseEntity getSalesByTaxPayerId(@AuthenticationPrincipal User taxPayer) {
        List<Sales> sales = salesService.getSalesByTaxPayerId(taxPayer.getId());
        return ResponseEntity.status(200).body(sales);
    }

    @PutMapping("/confirm-sale/{saleId}")
    public ResponseEntity confirmSale(@AuthenticationPrincipal User accountant,@PathVariable Integer saleId){
        salesService.confirmSale(accountant.getId(), saleId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException(" The sales confirmed successfully"));
    }



    @GetMapping("/print-sale/{saleId}")
    public ResponseEntity<byte[]> printInvoice(@AuthenticationPrincipal User accountant,@PathVariable Integer saleId) {
        byte[] pdf = salesService.printInvoice(accountant.getId(),saleId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tax-report-" + saleId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    @PutMapping("/update-product-quantity/{accountantId}/{itemSaleId}/{quantity}")
    public ResponseEntity updateProductQuantity(@PathVariable Integer accountantId, @PathVariable Integer itemSaleId, @PathVariable Integer quantity) {

        ItemSale updatedItemSale = salesService.updateProductQuantity(accountantId, itemSaleId, quantity);
        return ResponseEntity.status(200).body(updatedItemSale);
    }


}
