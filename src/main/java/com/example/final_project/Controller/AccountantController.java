package com.example.final_project.Controller;


import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Service.AccountantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accountant")
public class AccountantController {

    private final AccountantService accountantService;




    /// Auth ->
    @GetMapping("/get-accountant-by-branch/{branchId}")
    public ResponseEntity getBranchAccountant(@AuthenticationPrincipal MyUser taxPayer,@PathVariable Integer branchId){
        return ResponseEntity.status(200).body(accountantService.getBranchAccountant(taxPayer.getId(),branchId));
    }


    /// Auth -> taxPayer
    @GetMapping("/get-accountant-by-business/{businessId}")
    public ResponseEntity getBusinessAccountant(@AuthenticationPrincipal MyUser taxPayer, @PathVariable Integer businessId){
        return ResponseEntity.status(200).body(accountantService.getBusinessAccountant(taxPayer.getId(),businessId));
    }





    /// Auth -> Accountant
    @PutMapping("/update/{id}")
    public ResponseEntity update(@AuthenticationPrincipal MyUser accountant, @RequestBody @Valid AccountantDTO accountantDTO) {
        accountantService.updateAccountant(accountant.getId(), accountantDTO);
        return ResponseEntity.status(200).body("Updated Successfully");
    }

    /// Auth-> Accountant
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccountant(@AuthenticationPrincipal MyUser accountant) {
        accountantService.deleteAccountant(accountant.getId());
        return ResponseEntity.status(200).body("Accountant deleted successfully");
    }





    /// Auth-> Taxpayer
    @PutMapping("assign-accountant-to-branch/accountant/{accountantId}/{branchId}")
    public ResponseEntity assignAccountantToBranch(@AuthenticationPrincipal MyUser TaxPayer, @PathVariable Integer accountantId, @PathVariable Integer branchId){
        accountantService.assignAccountantToBranch(TaxPayer.getId(),accountantId,branchId);
        return ResponseEntity.status(200).body(new ApiResponse("The accountant has been assign to the branch successfully "));
    }

    /// Auth-> Accountant
    @PutMapping("/restock-product/accountant/{accountantId}/product/{productId}/amount/{amount}")
    public ResponseEntity restockProduct(@AuthenticationPrincipal MyUser accountant , @PathVariable Integer productId, @PathVariable Integer amount){
        accountantService.restockProduct(accountant.getId(), productId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("product restocked successfully"));
    }



}
