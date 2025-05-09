package com.example.final_project.Controller;


import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Service.AccountantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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





    @GetMapping("/getall")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(accountantService.getAllAccountants());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody @Valid AccountantDTO accountantDTO) {
        accountantService.updateAccountant(id, accountantDTO);
        return ResponseEntity.status(200).body("Updated Successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccountant(@PathVariable Integer id) {
        accountantService.deleteAccountant(id);
        return ResponseEntity.status(200).body("Accountant deleted successfully");
    }







    @PutMapping("/restock-product/accountant/{accountantId}/product/{productId}/amount/{amount}")
    public ResponseEntity restockProduct(@PathVariable Integer accountantId,@PathVariable Integer productId,@PathVariable Integer amount){
        accountantService.restockProduct(accountantId, productId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("product restocked successfully"));
    }

}
