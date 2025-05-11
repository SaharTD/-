package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Service.ItemSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add/{salesId}/product/{productId}")
    public ResponseEntity<ApiResponse> addItemSale(@RequestBody @Valid ItemSale itemSale,@PathVariable Integer salesId,@PathVariable Integer productId) {
        itemSaleService.addItemSale(itemSale, salesId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("ItemSale added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateItemSale(@PathVariable Integer id,
                                                      @RequestBody @Valid ItemSale newItemSale) {
        itemSaleService.updateItemSale(id, newItemSale);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("ItemSale updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteItemSale(@PathVariable Integer id) {
        itemSaleService.deleteItemSale(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("ItemSale deleted successfully"));
    }

    @GetMapping("/sales/{salesId}")
    public ResponseEntity<List<ItemSale>> getItemSalesBySalesId(@PathVariable Integer salesId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemSaleService.getItemSalesBySalesId(salesId));
    }
}
