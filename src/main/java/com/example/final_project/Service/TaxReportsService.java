package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTOOUT.TaxReportStatusDTO;
import com.example.final_project.Model.Auditor;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Repository.AuditorRepository;
import com.example.final_project.Repository.BusinessRepository;
import com.example.final_project.Repository.SalesRepository;
import com.example.final_project.Repository.TaxReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxReportsService {

    private final TaxReportsRepository taxReportsRepository;
    private final BusinessRepository businessRepository;
    private final AuditorRepository auditorRepository;
    private final SalesRepository salesRepository;



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

    public void applyLatePaymentPenalty(Integer taxReportId) {
        TaxReports taxReport = taxReportsRepository.findTaxReportsById(taxReportId);

        if (taxReport == null) {
            throw new ApiException("Tax report not found");
        }

        if (!taxReport.getStatus().equals("Approved")) {
            throw new ApiException("Penalty applies only to approved tax reports");
        }

        if (taxReport.getPaymentDate() == null) {
            throw new ApiException("Payment date is missing");
        }


        LocalDate approvalDate = taxReport.getEnd_date().toLocalDate();
        LocalDate dueDate = approvalDate.plusMonths(1);
        LocalDate today = LocalDate.now();

        if (today.isAfter(dueDate)) {

            Double penalty = taxReport.getTotalTax() * 0.05;
            taxReport.setTotalTax(taxReport.getTotalTax() + penalty);
            taxReportsRepository.save(taxReport);
        } else {
            throw new ApiException("No penalty: payment is still within grace period");
        }
    }


    public void applyTwoMonthLatePenalty(Integer taxReportId) {
        TaxReports taxReport = taxReportsRepository.findTaxReportsById(taxReportId);

        if (taxReport == null) {
            throw new ApiException("Tax report not found");
        }

        if (!taxReport.getStatus().equals("Approved")) {
            throw new ApiException("Penalty applies only to approved tax reports");
        }

        if (taxReport.getPaymentDate() == null || taxReport.getEnd_date() == null) {
            throw new ApiException("Missing payment or approval date");
        }

        LocalDate endDate = taxReport.getEnd_date().toLocalDate();
        LocalDate twoMonthDue = endDate.plusMonths(2);
        LocalDate today = LocalDate.now();

        if (today.isBefore(twoMonthDue)) {
            throw new ApiException("No penalty: Two months have not passed since end date");
        }

        double originalTax = taxReport.getTotalTax();
        double penalty = originalTax * 0.10;
        taxReport.setTotalTax(originalTax + penalty);

        taxReportsRepository.save(taxReport);
    }


    public void applyLegalActionForTaxEvasion(Integer taxReportId) {
        TaxReports taxReport = taxReportsRepository.findTaxReportsById(taxReportId);

        if (taxReport == null) {
            throw new ApiException("Tax report not found");
        }

        if (!taxReport.getStatus().equals("Approved")) {
            throw new ApiException("Legal action applies only to approved tax reports");
        }

        if (taxReport.getEnd_date() == null) {
            throw new ApiException("Missing end date");
        }

        LocalDate endDate = taxReport.getEnd_date().toLocalDate();
        LocalDate legalDeadline = endDate.plusDays(90);
        LocalDate today = LocalDate.now();

        boolean unpaid = (taxReport.getPaymentDate() == null || taxReport.getPaymentDate().isAfter(legalDeadline));

        if (today.isAfter(legalDeadline) && unpaid) {
            taxReport.setStatus("Under Legal Action");
            taxReportsRepository.save(taxReport);
        } else {
            throw new ApiException("Conditions for legal action not met");
        }
    }


    public void changeTaxReportStatus(Integer taxReportId,Integer auditorId, TaxReportStatusDTO taxReportStatusDTO) {
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

        List<String> allowedStatuses = List.of("Pending", "Approved", "Paid", "Under Legal Action", "Rejected");
        if (!allowedStatuses.contains(taxReportStatusDTO.getNewStatus())) {
            throw new ApiException("Invalid status value");
        }

        taxReport.setStatus(taxReportStatusDTO.getNewStatus());
        taxReportsRepository.save(taxReport);
    }



    public List<TaxReports> getUnpaidDueTaxReports() {
        return taxReportsRepository.findAllByPaymentDateIsNotNullAndStatusNot("Paid");
    }







}
