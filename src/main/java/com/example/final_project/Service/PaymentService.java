package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.Payment;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Repository.PaymentRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import com.example.final_project.Repository.TaxReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TaxPayerRepository taxPayerRepository;
    private final TaxReportsRepository taxReportsRepository;

    /// الصلاحية لم تحدد
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /// الصلاحية لم تحدد
    public void deletePayment(Integer paymentId){
        Payment payment = paymentRepository.findPaymentById(paymentId);

    }

    public void paymentSuccess(Integer taxPayerId,String paymentId){
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer==null)
            throw new ApiException("tax payer not found");
        List<TaxReports> taxReports = taxReportsRepository.findTaxReportsByTaxPayer(taxPayerId);
        if (taxReports.isEmpty())
            throw new ApiException("Tax Payer doesn't have tax reports to paid");
        for (TaxReports t:taxReports){
            if (t.getPaymentDate().isAfter(LocalDate.now().minusDays(1))){
                Payment payment = new Payment();
                payment.setPaymentDate(LocalDateTime.now());
                payment.setName(taxPayer.getMyUser().getName());
                payment.setStatus("Paid");
                payment.setTaxPayer(taxPayer);
                payment.setTaxReports(t);
                payment.setPaymentId(paymentId);
                paymentRepository.save(payment);
                return;
            }
        }
        throw new ApiException("ERROR");
    }

}
