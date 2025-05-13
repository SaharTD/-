package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/get")
    public ResponseEntity getAll(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(branchService.getAllBranches());
    }

    // authority -> TaxPayer
    @PostMapping("/add/{businessId}")
    public ResponseEntity addBranch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId, @RequestBody @Valid Branch branch){
        branchService.addBranch( myUser.getId(),businessId, branch);
        return ResponseEntity.status(200).body(new ApiResponse("new branch added"));
    }

    // authority -> TaxPayer
    @PutMapping("/update/{businessId}/branch/{branchId}")
    public ResponseEntity updateBranch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId, @PathVariable Integer branchId, @RequestBody @Valid Branch branch){
        branchService.updateBranch(businessId, branchId, branch);
        return ResponseEntity.status(200).body(new ApiResponse("branch updated"));
    }

    // authority -> TaxPayer
    @DeleteMapping("/delete/{businessId}/branch/{branchId}")
    public ResponseEntity deleteBranch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId, @PathVariable Integer branchId){
        branchService.deleteBranch(businessId, branchId);
        return ResponseEntity.status(200).body(new ApiResponse("branch deleted"));
    }


    @GetMapping("branch-revenue/{branchId}")
    //sahar - 13
    public ResponseEntity branchRevenue(@AuthenticationPrincipal MyUser user,@PathVariable Integer branchId) {
        return ResponseEntity.status(200).body(new ApiResponse("The total revenue of the business: "
                + branchService.branchRevenue(user.getId(), branchId)));
    }

    // authority -> TaxPayer
    @GetMapping("/sales-summary/{branchId}")
    //    public ResponseEntity<Map<String, Double>> getSalesByBranch(@AuthenticationPrincipal User taxPayer, @PathVariable Integer branchId) {
    public ResponseEntity<Map<String, Double>> getSalesByBranch(MyUser taxPayer, @PathVariable Integer branchId) {
        return ResponseEntity.status(200).body(branchService.getSalesSummaryByBranch(taxPayer.getId(),branchId));
    }

    // authority -> TaxPayer
    // Endpoint 12
    @GetMapping("/get-tax-payer-branches")
    public ResponseEntity getAllTaxPayerBranches(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(branchService.getTaxPayerBranches(myUser.getId()));
    }



}
