package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.Model.User;
import com.example.final_project.Service.TaxPayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/tax-payer")
@RequiredArgsConstructor
public class TaxPayerController {

    private final TaxPayerService taxPayerService;



    @GetMapping("/get-all-tax-payers")
    public ResponseEntity getAllTaxTaxPayers (@AuthenticationPrincipal User audit){
        return ResponseEntity.status(200).body(taxPayerService.getAllTaxTaxPayers(audit.getId()));
    }

    @PostMapping("/tax-payer-register")
    public ResponseEntity register (@RequestBody @Valid TaxPayerDTO taxPayerDTO ){
        taxPayerService.register(taxPayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer is registered successfully "));
    }

    @PutMapping("/update")
    public ResponseEntity updateTaxPayer(@AuthenticationPrincipal User taxPayer , @Valid@RequestBody TaxPayerDTO taxPayerDTO ){
        taxPayerService.updateTaxPayer(taxPayer.getId(), taxPayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer information has been updated successfully "));
    }

    @DeleteMapping("/delete/")
    public ResponseEntity deleteTaxPayer(@AuthenticationPrincipal User taxPayer){
        taxPayerService.deleteTaxPayer(taxPayer.getId());
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer has been deleted successfully "));
    }


    /// 13
    @PostMapping("/add-accountant")
    public ResponseEntity addAccountant (@RequestBody @Valid AccountantDTO accountantDTO, @AuthenticationPrincipal User taxPayer,@PathVariable Integer branchId){
        taxPayerService.addAccountant(taxPayer.getId(), branchId,accountantDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the accountant is added successfully "));
    }


    // Endpoint 40
    @PutMapping("/activate-accountant/tax-payer/accountant/{accountantId}")
    public ResponseEntity activateAccountant(@AuthenticationPrincipal User taxPayer,@PathVariable Integer accountantId){
          taxPayerService.activateAccountant(taxPayer.getId(), accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("accountant is active"));
    }


    // Endpoint 41
    @PutMapping("/de-activate-accountant/tax-payer/accountant/{accountantId}")
    public ResponseEntity deActivateAccountant(@AuthenticationPrincipal User taxPayer,@PathVariable Integer accountantId){
        taxPayerService.deActivateAccountant(taxPayer.getId(), accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("accountant is nonActive"));
    }

    @GetMapping("/taxpayers/accountants")
    public ResponseEntity<List<Map<String, Object>>> getAccountantsByTaxPayerId(@AuthenticationPrincipal User taxPayer) {
        return ResponseEntity.ok(taxPayerService.getAccountantsByTaxPayerId(taxPayer.getId()));
    }

    /// 15
    @PutMapping("block-inactive-accountant/{accountantId}")
    public ResponseEntity blockInnActiveAccountant(@AuthenticationPrincipal User taxPayer, @PathVariable Integer accountantId ){
        taxPayerService.blockUnnActiveAccountant(taxPayer.getId(),accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("the  accountant has been inactivated"));
    }






}
