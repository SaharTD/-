package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Model.Product;
import com.example.final_project.Service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // authority -> Accountant
    @PostMapping("/add/{branchId}")
    public ResponseEntity addProduct(@AuthenticationPrincipal MyUser accountant ,@PathVariable Integer branchId, @RequestBody@Valid Product product){
        productService.addProduct(accountant.getId(),branchId,product);
        return ResponseEntity.status(200).body(new ApiResponse("new product added"));
    }

    // authority -> Accountant
    /// ?????????????????????????????????
    @PutMapping("/update/branch/{branchId}/product/{productId}")
    public ResponseEntity updateProduct(@PathVariable Integer branchId,@PathVariable Integer productId, @RequestBody@Valid Product product){
        productService.updateProduct(branchId, productId, product);
        return ResponseEntity.status(200).body(new ApiResponse("product updated"));
    }

    // authority -> Accountant
    /// ?????????????????????????????????
    @DeleteMapping("/delete/branch/{branchId}/product/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer branchId,@PathVariable Integer productId){
        productService.deleteProduct(branchId, productId);
        return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
    }

    /// ?????????????????????????????????
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<?> getProductsByBarcode(@PathVariable String barcode) {
        Product products = productService.getProductsByBarcode(barcode);
        return ResponseEntity.ok(products);
    }





    //accountant add product to branch
    /// ?????????????????????????????????
    @PostMapping("/add-to-branch/{accountantId}/{branchId}")
    public ResponseEntity<?> addProductToBranch(@PathVariable Integer accountantId, @PathVariable Integer branchId, @RequestBody Product product) {
        productService.addProductToBranch(accountantId, branchId, product);
        return ResponseEntity.status(200).body("Product added successfully");
    }




}
