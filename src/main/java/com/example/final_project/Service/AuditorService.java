package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Auditor;
import com.example.final_project.DTO.DTOAuditor;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuditorRepository;
import com.example.final_project.Repository.BusinessRepository;
import com.example.final_project.Repository.MyUserRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditorService {

    private final AuditorRepository auditorRepository;
    private final MyUserRepository myUserRepository;
    private final TaxPayerRepository taxPayerRepository;
    private final BusinessRepository businessRepository;

    public List<Auditor> getAllAuditors() {
        return auditorRepository.findAll();
    }

    public void addAuditor(DTOAuditor dtoAuditor) {
        User user = new User();
        user.setName(dtoAuditor.getName());
        user.setUsername(dtoAuditor.getUsername());
        user.setPassword(dtoAuditor.getPassword());
        user.setEmail(dtoAuditor.getEmail());
        user.setRole("AUDITOR");
        Auditor auditor = new Auditor(null, dtoAuditor.getSOCPA(), user, null);
        user.setAuditor(auditor);
        myUserRepository.save(user);
        auditorRepository.save(auditor);
    }

    public void updateAuditor(Integer auditorId, DTOAuditor dtoAuditor) {
        User user = myUserRepository.findUserById(auditorId);
        if (user == null)
            throw new ApiException("auditor not found");
        user.setName(dtoAuditor.getName());
        user.setUsername(dtoAuditor.getUsername());
        user.setPassword(dtoAuditor.getPassword());
        user.setEmail(dtoAuditor.getEmail());
        user.getAuditor().setSOCPA(dtoAuditor.getSOCPA());

        myUserRepository.save(user);
//        auditorRepository.save(auditor);
    }


    public void deleteAuditor(Integer auditorId) {
        User user = myUserRepository.findUserById(auditorId);
        if (user == null)
            throw new ApiException("auditor not found");
        myUserRepository.delete(user);
//        auditorRepository.delete(auditor);
    }


    public void activateBusiness(Integer auditId, Integer taxPayerId, Integer businessId) {

        Auditor auditor = auditorRepository.findAuditorsById(auditId);
        if (auditor == null) {
            throw new ApiException("auditor not found");
        }

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);

        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }


        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessId, taxPayer);
        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }


        if (business.getIsActive()) {
            throw new ApiException("The business is already activated");

        }


        business.setIsActive(true);
        businessRepository.save(business);

    }


    public void blockBusiness(Integer auditId, Integer taxPayerId, Integer businessId) {

        Auditor auditor = auditorRepository.findAuditorsById(auditId);
        if (auditor == null) {
            throw new ApiException("auditor not found");
        }

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);

        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }


        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessId, taxPayer);
        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }


        if (!business.getIsActive()) {
            throw new ApiException("The business is already inactive");

        }

        business.setIsActive(false);
        businessRepository.save(business);

    }


}
