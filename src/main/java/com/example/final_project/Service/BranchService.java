package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTOOUT.SalesDTO;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.Sales;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.BusinessRepository;
import com.example.final_project.Repository.SalesRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BranchService {


    private final BranchRepository branchRepository;
    private final BusinessRepository businessRepository;
    private final SalesRepository salesRepository;
    private final TaxPayerRepository taxPayerRepository;


    public List<Branch> getAllBranches(){
        return branchRepository.findAll();
    }


    public void addBranch(Integer taxPayerID,Integer businessId, Branch branch){


        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerID);

        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        Business business = businessRepository.findBusinessById(businessId);
        if (business==null)
            throw new ApiException("business not found");



        if (branchRepository.findBranchesById(branch.getId())!=null){
            throw new ApiException("The branch already exist");

        }
        /// if business is not active
        if (!business.getIsActive()) {
            throw new ApiException("The business related to this branch is not active");
        }

        branch.setBusiness(business);
//        business.getBranches().add(branch);
//        businessRepository.save(business);
        branchRepository.save(branch);
    }


    public void updateBranch(Integer businessId, Integer branchId, Branch branch){
        Business business = businessRepository.findBusinessById(businessId);
        Branch oldBranch = branchRepository.findBranchesById(branchId);
        if (business==null)
            throw new ApiException("business not found");
        if (oldBranch==null)
            throw new ApiException("branch not found");

        oldBranch.setBranchNumber(branch.getBranchNumber());
        oldBranch.setCity(branch.getCity());
        oldBranch.setRegion(branch.getRegion());


        branchRepository.save(branch);
    }

    public void deleteBranch(Integer businessId, Integer branchId){
        Business business = businessRepository.findBusinessById(businessId);
        Branch oldBranch = branchRepository.findBranchesById(branchId);
        if (business==null)
            throw new ApiException("business not found");
        if (oldBranch==null)
            throw new ApiException("branch not found");

        business.getBranches().remove(oldBranch);
        branchRepository.delete(oldBranch);
        businessRepository.save(business);
    }


    public Map<String,Double> getSalesSummaryByBranch(Integer taxPayerID, Integer branchId) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerID);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        Branch branch=branchRepository.findBranchesById(branchId);
        if (branch==null){
            throw new ApiException("branch not found");
        }

        double totalBeforeTax = 0.0;


        for (Sales sale : branch.getSales()) {
            if (sale.getTotal_amount() != null)
                totalBeforeTax += sale.getTotal_amount();
        }

        double taxAmount = totalBeforeTax * 0.15;
        double grandTotal = totalBeforeTax + taxAmount;

        Map<String, Double> result = new HashMap<>();
        result.put("total", totalBeforeTax);
        result.put("total tax Amount is:", taxAmount);
        result.put("grand total is:", grandTotal);

        return result;
    }

     public List<Branch> getTaxPayerBranches(Integer taxPayerId){
        List<Branch> branches = branchRepository.findBranchesByBusinessTaxPayerId(taxPayerId);
        if (branches.isEmpty())
            throw new ApiException("no branches belong to this tax payer");
        return branches;
     }

}
