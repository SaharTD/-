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

    @PostMapping("/add/{businessId}")
    public ResponseEntity addBranch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId, @RequestBody @Valid Branch branch){
        branchService.addBranch(businessId, myUser.getId(), branch);
        return ResponseEntity.status(200).body(new ApiResponse("new branch added"));
    }

    @PutMapping("/update/{businessId}/branch/{branchId}")
    public ResponseEntity updateBranch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId, @PathVariable Integer branchId, @RequestBody @Valid Branch branch){
        branchService.updateBranch(businessId, branchId, branch);
        return ResponseEntity.status(200).body(new ApiResponse("branch updated"));
    }

    @DeleteMapping("/delete/{businessId}/branch/{branchId}")
    public ResponseEntity deleteProduct(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer businessId, @PathVariable Integer branchId){
        branchService.deleteBranch(businessId, branchId);
        return ResponseEntity.status(200).body(new ApiResponse("branch deleted"));
    }

//    /// 3
//    @GetMapping("branch-sales/{branchId}")
//    public ResponseEntity salesOperationOnBranch(@AuthenticationPrincipal User user,@PathVariable Integer branchId) {
//        List<SalesDTO> sales = branchService.salesOperationOnBranch(user.getId(), branchId);
//        return ResponseEntity.status(200).body(new ApiResponse("The total number of sales operations: \n " + sales.size()
//                + branchService.salesOperationOnBranch(user.getId(), branchId)));
//    }

    @GetMapping("/sales-summary/{branchId}")
    //    public ResponseEntity<Map<String, Double>> getSalesByBranch(@AuthenticationPrincipal User taxPayer, @PathVariable Integer branchId) {
    public ResponseEntity<Map<String, Double>> getSalesByBranch(@AuthenticationPrincipal MyUser taxPayer, @PathVariable Integer branchId) {
        return ResponseEntity.status(200).body(branchService.getSalesSummaryByBranch(taxPayer.getId(),branchId));
    }

    // Endpoint 12
    @GetMapping("/get-tax-payer-branches")
    public ResponseEntity getAllTaxPayerBranches(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(branchService.getTaxPayerBranches(myUser.getId()));
    }



}
