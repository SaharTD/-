package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Auditor;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Repository.AuditorRepository;
import com.example.final_project.Repository.BusinessRepository;
import com.example.final_project.Repository.TaxReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxReportsService {

    private final TaxReportsRepository taxReportsRepository;
    private final BusinessRepository businessRepository;
    private final AuditorRepository auditorRepository;



    public List<TaxReports> getAllTaxReports(){
        return taxReportsRepository.findAll();
    }

    public void addTaxReports(Integer auditor_id,Integer business_id,TaxReports taxReports){
        Business business=businessRepository.findBusinessById(business_id);
         Auditor auditor =auditorRepository.findAuditorsById(auditor_id);

         if(auditor==null&&business==null){
             throw new ApiException("auditor or business not found");
         }
         taxReports.setStatus("Pending");
         taxReportsRepository.save(taxReports);
    }

    public void updateTaxReports(Integer id,TaxReports taxReports){
        TaxReports oldTaxReports=taxReportsRepository.findTaxReportsById(id);

        if(oldTaxReports==null){
            throw new ApiException("TaxReports not found");
        }

        oldTaxReports.setTotalTax(taxReports.getTotalTax());
        oldTaxReports.setStart_date(taxReports.getStart_date());
        oldTaxReports.setEnd_date(taxReports.getEnd_date());
        oldTaxReports.setStatus(taxReports.getStatus());
        oldTaxReports.setPaymentDate(taxReports.getPaymentDate());

        taxReportsRepository.save(oldTaxReports);
    }


    public void deleteTaxReports(Integer id){
        TaxReports taxReports=taxReportsRepository.findTaxReportsById(id);
        if(taxReports==null){
            throw new ApiException("Tax Reports not found");
        }
        taxReportsRepository.delete(taxReports);
    }

}
