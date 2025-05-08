package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.Product;
import com.example.final_project.Service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }

    @PostMapping("/add/{branchId}")
    public ResponseEntity addProduct(@PathVariable Integer branchId, @RequestBody@Valid Product product){
        productService.addProduct(branchId, product);
        return ResponseEntity.status(200).body(new ApiResponse("new product added"));
    }

    @PutMapping("/update/{branchId}/product/{productId}")
    public ResponseEntity updateProduct(@PathVariable Integer branchId,@PathVariable Integer productId, @RequestBody@Valid Product product){
        productService.updateProduct(branchId, productId, product);
        return ResponseEntity.status(200).body(new ApiResponse("product updated"));
    }

    @DeleteMapping("/delete/{branchId}/product/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer branchId,@PathVariable Integer productId){
        productService.deleteProduct(branchId, productId);
        return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
    }

}
