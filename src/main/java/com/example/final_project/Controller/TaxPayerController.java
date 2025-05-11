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
    public ResponseEntity updateTaxPayer(@PathVariable Integer taxPayerId , @Valid@RequestBody TaxPayerDTO taxPayerDTO ){
        taxPayerService.updateTaxPayer(taxPayerId,taxPayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer information has been updated successfully "));
    }

    @DeleteMapping("/delete/{taxPayerId}")
    public ResponseEntity deleteTaxPayer(@PathVariable Integer taxPayerId){
        taxPayerService.deleteTaxPayer(taxPayerId);
        return ResponseEntity.status(200).body(new ApiResponse("the tax payer has been deleted successfully "));
    }


    /// 13
    @PostMapping("add-accountant/{taxPayerID}")
    public ResponseEntity addAccountant (@RequestBody @Valid AccountantDTO accountantDTO, @PathVariable Integer taxPayerID){
        taxPayerService.addAccountant(taxPayerID,accountantDTO);
        return ResponseEntity.status(200).body(new ApiResponse("the accountant is added successfully "));
    }



    /// 15
    @PutMapping("block-inactive-accountant/{taxPayerId}/{accountantId}")
    public ResponseEntity blockInnActiveAccountant(@PathVariable Integer taxPayerId ,@PathVariable Integer accountantId ){
        taxPayerService.blockUnnActiveAccountant(taxPayerId,accountantId);
        return ResponseEntity.status(200).body(new ApiResponse("the  accountant has been inactivated"));
    }

}
