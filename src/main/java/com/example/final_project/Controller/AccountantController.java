package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Service.AccountantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accountant")
public class AccountantController {

    private final AccountantService accountantService;

    @PutMapping("/restock-product/accountant/{accountantId}/product/{productId}/amount/{amount}")
    public ResponseEntity restockProduct(@PathVariable Integer accountantId,@PathVariable Integer productId,@PathVariable Integer amount){
        accountantService.restockProduct(accountantId, productId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("product restocked successfully"));
    }

}
