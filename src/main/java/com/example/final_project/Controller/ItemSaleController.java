package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Model.MyUser;
//import com.example.final_project.Service.ItemSaleService;
import com.example.final_project.Service.ItemSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item-sales")
public class ItemSaleController {


    private final ItemSaleService itemSaleService;

    @GetMapping
    public ResponseEntity<List<ItemSale>> getAllItemSales() {
        return ResponseEntity.status(HttpStatus.OK).body(itemSaleService.getAllItemSales());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateItemSale(@PathVariable Integer id,
                                                      @RequestBody @Valid ItemSale newItemSale) {
        itemSaleService.updateItemSale(id, newItemSale);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("ItemSale updated successfully"));
    }

    //sahar - 6
    @DeleteMapping("/remove/{itemSaleId}/{saleId}")
    public ResponseEntity<ApiResponse> removeItemFromSale(@AuthenticationPrincipal MyUser accountant, @PathVariable Integer itemSaleId, @PathVariable Integer saleId) {
        itemSaleService.removeItemFromSale(accountant.getId(),itemSaleId,saleId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("ItemSale deleted successfully"));
    }

    // authority -> Accountant
    @GetMapping("/sales/{salesId}")
    public ResponseEntity<List<ItemSale>> getItemSalesBySalesId(@AuthenticationPrincipal MyUser accountant, @PathVariable Integer salesId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemSaleService.getItemSalesBySalesId(accountant.getId(), salesId));
    }

}
