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

    // إضافة عنصر جديد إلى فاتورة
    @PostMapping("/add/{salesId}/product/{productId}")
    public ResponseEntity<ApiResponse> addItemSale(@RequestBody @Valid ItemSale itemSale,@PathVariable Integer salesId,@PathVariable Integer productId) {
        itemSaleService.addItemSale(itemSale, salesId, productId);
        return ResponseEntity.ok(new ApiResponse("ItemSale added successfully"));
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

    @PostMapping("/add-product/{productId}")
    public ResponseEntity addProduct(@PathVariable Integer productId){
        itemSaleService.addProduct(productId);
        return ResponseEntity.status(200).body(new ApiResponse("product added"));
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseItems(){
        itemSaleService.purchaseItems();
        return ResponseEntity.status(200).body(new ApiResponse("new sales printed"));
    }
}
