package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.Model.*;
import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountantService {

    private final AccountantRepository accountantRepository;
    private final BranchRepository branchRepository;
    private final TaxPayerRepository taxPayerRepository;
    private final ProductRepository productRepository;
    private final BusinessRepository businessRepository;




/// get all accountant for 1 branch
    public List<Accountant> getBranchAccountant(Integer taxPayerID , Integer branchId) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerID);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        Branch dBranch = branchRepository.findBranchesById(branchId);

        if (dBranch==null){
            throw new ApiException("branch not found");
        }

        return accountantRepository.findAccountantByBranch(dBranch);
    }




    /// get all accountant for 1 business
    public List<Accountant> getBusinessAccountant(Integer taxPayerID , Integer businessId) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerID);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        Business business = businessRepository.findBusinessById(businessId);
        if (business == null){
            throw new ApiException("business not found");
        }

        return accountantRepository.findAccountantByBusiness(business);
    }



    public void updateAccountant(Integer id, AccountantDTO accountantDTO) {
        Accountant accountant = accountantRepository.getReferenceById(id);
        if (accountant==null){
            throw new ApiException("Accountant not found");
        }
        User user = accountant.getUser();
        user.setName(accountantDTO.getName());
        user.setUsername(accountantDTO.getUsername());
        user.setPassword(accountantDTO.getPassword());
        user.setEmail(accountantDTO.getEmail());

        accountantRepository.save(accountant);
    }


    //Delete the accountant if it is not associated with any counterBox
    public void deleteAccountant(Integer id) {
        Accountant accountant = accountantRepository.getReferenceById(id);

        if (accountant == null) {
            throw new ApiException("Accountant not found");
        }
        if (accountant.getCounterBoxes() != null && !accountant.getCounterBoxes().isEmpty()) {
            throw new ApiException("Cannot delete accountant with assigned counter boxes");
        }
        accountantRepository.delete(accountant);
    }





    // Endpoint 35
    public void restockProduct(Integer accountantId, Integer productId, Integer amount){
        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        Product product = productRepository.findProductById(productId);
        if (accountant==null)
            throw new ApiException("accountant not found");
        if (product==null)
            throw new ApiException("product not found");
        product.setStock(product.getStock()+amount);

        productRepository.save(product);
    }


    public void assignAccountantToBranch(Integer taxPayerID,Integer accountantId,Integer branchId){

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerID);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }


        Branch branch = branchRepository.findBranchesById(branchId);
        if (branch==null) {
            throw new ApiException("branch not found or does not belong to your business");
        }


        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (accountant==null){
            throw new ApiException("Accountant not found");
        }


        /// make sure that the business is active
        Business business= businessRepository.findBusinessById(branch.getBusiness().getId());
        if (!business.getIsActive()) {
            throw new ApiException("The business related to this branch is not active");
        }



        ///check if the accountant business owner is the one who made this request
        if (accountant.getBusiness().getId()!=business.getId()){
            throw new ApiException("The Accountant does not work in your business");
        }


        accountant.setBranch(branch);
        accountantRepository.save(accountant);


    }

}
