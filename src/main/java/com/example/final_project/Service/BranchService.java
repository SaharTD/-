package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {


    private final BranchRepository branchRepository;
    private final BusinessRepository businessRepository;


    public List<Branch> getAllBranches(){
        return branchRepository.findAll();
    }


    public void addBranch(Integer businessId, Branch branch){
        Business business = businessRepository.findBusinessById(businessId);
        if (business==null)
            throw new ApiException("business not found");
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
        oldBranch.setAddress(branch.getAddress());

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


}
