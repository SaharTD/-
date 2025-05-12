package com.example.final_project.Controller;


import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Service.AccountantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
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




    //
    @GetMapping("/get-accountant-by-branch/{taxPayerId}/{branchId}")
    public ResponseEntity getBranchAccountant(@PathVariable Integer taxPayerId,@PathVariable Integer branchId){
        return ResponseEntity.status(200).body(accountantService.getBranchAccountant(taxPayerId,branchId));
    }



    @GetMapping("/get-accountant-by-business/{taxPayerId}/{businessId}")
    //    public ResponseEntity getBusinessAccountant(@AuthenticationPrincipal TaxPayer taxPayer, @PathVariable Integer taxPayerId, @PathVariable Integer businessId){
    public ResponseEntity getBusinessAccountant( TaxPayer taxPayer, @PathVariable Integer taxPayerId, @PathVariable Integer businessId){
        return ResponseEntity.status(200).body(accountantService.getBusinessAccountant(taxPayerId,businessId));
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






    @PutMapping("assign-accountant-to-branch/accountant/{taxPayerId}/{accountantId}/{branchId}")
    public ResponseEntity assignAccountantToBranch(@PathVariable Integer taxPayerId,@PathVariable Integer accountantId,@PathVariable Integer branchId){
        accountantService.assignAccountantToBranch(taxPayerId,accountantId,branchId);
        return ResponseEntity.status(200).body(new ApiResponse("The accountant has been assign to the branch successfully "));
    }


    @PutMapping("/restock-product/accountant/{accountantId}/product/{productId}/amount/{amount}")
    public ResponseEntity restockProduct(@PathVariable Integer accountantId,@PathVariable Integer productId,@PathVariable Integer amount){
        accountantService.restockProduct(accountantId, productId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("product restocked successfully"));
    }



}
