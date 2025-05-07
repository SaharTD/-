package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.ItemSales;
import com.example.final_project.Service.ItemSalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/itemsales")
public class ItemSalesController {

    private final ItemSalesService itemSalesService;


    @PostMapping
    public ResponseEntity addIteamSelas(@Valid @RequestBody ItemSales itemSales){
        itemSalesService.addItemSales(itemSales);
        return ResponseEntity.status(200).body(new ApiResponse("item sales added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(itemSalesService.getAllItemSales());
    }

    @GetMapping("get/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(itemSalesService.getItemSales(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody ItemSales itemSales,@PathVariable Integer id){
        itemSalesService.updateItemSales(itemSales,id);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        itemSalesService.deleteItemSales(id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted Successfully"));
    }
}
