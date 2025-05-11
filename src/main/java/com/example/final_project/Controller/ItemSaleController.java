package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Service.ItemSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(itemSaleService.getAllItemSales());
    }


    // تعديل عنصر
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateItemSale(@PathVariable Integer id,
                                                      @RequestBody @Valid ItemSale newItemSale) {
        itemSaleService.updateItemSale(id, newItemSale);
        return ResponseEntity.ok(new ApiResponse("ItemSale updated successfully"));
    }

    // حذف عنصر
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteItemSale(@PathVariable Integer id) {
        itemSaleService.deleteItemSale(id);
        return ResponseEntity.ok(new ApiResponse("ItemSale deleted successfully"));
    }

    // جلب كل العناصر لفاتورة معينة
    @GetMapping("/sales/{salesId}")
    public ResponseEntity<List<ItemSale>> getItemSalesBySalesId(@PathVariable Integer salesId) {
        return ResponseEntity.ok(itemSaleService.getItemSalesBySalesId(salesId));
    }

}
