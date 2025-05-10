package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.DTO.TaxPayerDTO;
import com.example.final_project.Service.TaxPayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/tax-payer")
@RequiredArgsConstructor
public class TaxPayerController {

    private final TaxPayerService taxPayerService;



    @GetMapping("/get-all-tax-payers/{taxPayerId}")
    public ResponseEntity getAllTaxTaxPayers (@PathVariable Integer taxPayerId){
        return ResponseEntity.status(200).body(taxPayerService.getAllTaxTaxPayers(taxPayerId));
    }

    @PostMapping("/tax-payer-register")
    public ResponseEntity register (@RequestBody @Valid TaxPayerDTO taxPayerDTO ){
        taxPayerService.register(taxPayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer is registered successfully "));
    }

    @PutMapping("/update/{taxPayerId}")
    public ResponseEntity updateTaxPayer(@PathVariable Integer taxPayerId , @RequestBody TaxPayerDTO taxPayerDTO ){
        taxPayerService.updateTaxPayer(taxPayerId,taxPayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer information has been updated successfully "));
    }

    @DeleteMapping("/delete/{taxPayerId}")
    public ResponseEntity deleteTaxPayer(@PathVariable Integer taxPayerId){
        taxPayerService.deleteTaxPayer(taxPayerId);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer has been deleted successfully "));
    }


    /// 13
    @PostMapping("/add-accountant/{taxPayerID}")
    public ResponseEntity addAccountant (@RequestBody @Valid AccountantDTO accountantDTO, @PathVariable Integer taxPayerID){
        taxPayerService.addAccountant(taxPayerID,accountantDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the accountant is added successfully "));
    }


    // Endpoint 40
    @PutMapping("/activate-accountant/tax-payer/{taxPayerId}/accountant/{accountantId}")
    public ResponseEntity activateAccountant(@PathVariable Integer taxPayerId,@PathVariable Integer accountantId){
        taxPayerService.activateAccountant(taxPayerId, accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("accountant is active"));
    }


    // Endpoint 41
    @PutMapping("/de-activate-accountant/tax-payer/{taxPayerId}/accountant/{accountantId}")
    public ResponseEntity deActivateAccountant(@PathVariable Integer taxPayerId,@PathVariable Integer accountantId){
        taxPayerService.deActivateAccountant(taxPayerId, accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("accountant is nonActive"));
    }

    @GetMapping("/tax-payers/with-accountants")
    public ResponseEntity<List<Map<String, Object>>> getTaxPayersWithAccountants() {
        return ResponseEntity.status(200).body(taxPayerService.getTaxPayersWithAccountants());
    }



    /// 15
    @PutMapping("block-inactive-accountant/{taxPayerId}/{accountantId}")
    public ResponseEntity blockInnActiveAccountant(@PathVariable Integer taxPayerId ,@PathVariable Integer accountantId ){
        taxPayerService.blockUnnActiveAccountant(taxPayerId,accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("the  accountant has been inactivated"));
    }

}
