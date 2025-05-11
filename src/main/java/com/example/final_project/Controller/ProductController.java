package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.Product;
import com.example.final_project.Service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }

    @PostMapping("/add/branch/{accountantId}/{branchId}")
    public ResponseEntity addProduct(@PathVariable Integer branchId,@PathVariable Integer accountantId, @RequestBody@Valid Product product){
        productService.addProduct(accountantId,branchId,product);
        return ResponseEntity.status(200).body(new ApiResponse("new product added"));
    }

    @PutMapping("/update/branch/{branchId}/product/{productId}")
    public ResponseEntity updateProduct(@PathVariable Integer branchId,@PathVariable Integer productId, @RequestBody@Valid Product product){
        productService.updateProduct(branchId, productId, product);
        return ResponseEntity.status(200).body(new ApiResponse("product updated"));
    }

    @DeleteMapping("/delete/branch/{branchId}/product/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer branchId,@PathVariable Integer productId){
        productService.deleteProduct(branchId, productId);
        return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<List<Product>> getProductsByBarcode(@PathVariable String barcode) {
        List<Product> products = productService.getProductsByBarcode(barcode);
        return ResponseEntity.ok(products);
    }



}
