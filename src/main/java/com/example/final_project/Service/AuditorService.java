package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.*;
import com.example.final_project.DTO.DTOAuditor;
import com.example.final_project.Repository.*;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuditorRepository;
import com.example.final_project.Repository.BusinessRepository;
import com.example.final_project.Repository.MyUserRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditorService {

    private final AuditorRepository auditorRepository;
    private final MyUserRepository myUserRepository;
    private final BusinessRepository businessRepository;
    private final SalesRepository salesRepository;
    private final TaxReportsRepository taxReportsRepository;
    private final TaxPayerRepository taxPayerRepository;

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
        if (user == null){
            throw new ApiException("auditor not found");}
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


    // Endpoint 27
    public void createTaxReport(Integer businessId){
        Business business = businessRepository.findBusinessById(businessId);
        if (business==null)
            throw new ApiException("this business not found");
        List<Sales> sales = salesRepository.findSalesByBusinessId(businessId);
        if (sales.isEmpty())
            throw new ApiException("there are no sales for this business");
        Double totalTax=0.0;
        TaxReports taxReports = new TaxReports();
        for (Sales s: sales){
//            if (s.getTaxReports()!=null)
//                throw new ApiException("this invoice tax was paid");
            totalTax += s.getTax_amount();
            s.setTaxReports(taxReports);
        }
        taxReports.setBusiness(business);
        taxReports.setTotalTax(totalTax);
        taxReports.setEnd_date(LocalDateTime.now());
        taxReports.setStatus("Pending");

        businessRepository.save(business);
        salesRepository.saveAll(sales);
        taxReportsRepository.save(taxReports);
    }


    public void approveTaxReportStatus(Integer taxReportId,Integer auditorId) {
        TaxReports taxReport = taxReportsRepository.findTaxReportsById(taxReportId);
        if (taxReport == null) {
            throw new ApiException("Tax report not found");
        }
        Auditor auditor = auditorRepository.findAuditorsById(auditorId);
        if (auditor == null) {
            throw new ApiException("Auditor not found");
        }
        if (taxReport.getAuditor() == null || !taxReport.getAuditor().getId().equals(auditorId)) {
            throw new ApiException("This report does not belong to the specified auditor");
        }

        taxReport.setStatus("Approved");
        taxReportsRepository.save(taxReport);
    }

    public void rejectTaxReportStatus(Integer taxReportId,Integer auditorId) {
        TaxReports taxReport = taxReportsRepository.findTaxReportsById(taxReportId);
        if (taxReport == null) {
            throw new ApiException("Tax report not found");
        }
        Auditor auditor = auditorRepository.findAuditorsById(auditorId);
        if (auditor == null) {
            throw new ApiException("Auditor not found");
        }
        if (taxReport.getAuditor() == null || !taxReport.getAuditor().getId().equals(auditorId)) {
            throw new ApiException("This report does not belong to the specified auditor");
        }

        taxReport.setStatus("Rejected");
        taxReportsRepository.save(taxReport);
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
