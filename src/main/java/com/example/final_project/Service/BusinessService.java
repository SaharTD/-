package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.BusinessRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {


    private final TaxPayerRepository taxPayerRepository;
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;


    public List<Business> getAllBusiness(Integer AuditId) {
        return businessRepository.findAll();
    }

    /// get all business details for one taxpayer
    public List<Business> getMyBusinesses(Integer taxPayerId) {
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        return businessRepository.findBusinessByTaxPayer(taxPayer);
    }



    public void addBusiness(Integer taxPayerId, BusinessDTO businessDTO) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("Tax Payer is not found");

        }

        Business business=new Business();
        business.setBusinessName(businessDTO.getBusinessName());
        business.setBusinessCategory(businessDTO.getBusinessCategory());
        business.setCommercialRegistration(businessDTO.getCommercialRegistration());
        business.setTaxNumber(businessDTO.getTaxNumber());
        business.setCity(businessDTO.getCity());
        business.setRegion(businessDTO.getRegion());


        business.setTaxPayer(taxPayer);
        business.setIsActive(false);


        businessRepository.save(business);



    }





    public void updateBusiness(Integer taxPayerId,Integer businessId ,BusinessDTO businessDTO) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("Tax Payer is not found");

        }
        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessId,taxPayer);

        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }


        business.setBusinessName(businessDTO.getBusinessName());
        business.setBusinessCategory(businessDTO.getBusinessCategory());
        business.setCity(businessDTO.getCity());
        business.setRegion(businessDTO.getRegion());

        businessRepository.save(business);

    }






    public void deleteBusiness(Integer taxPayerId, Integer businessID) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("Tax Payer is not found");

        }
        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessID, taxPayer);


        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }




    }






}
