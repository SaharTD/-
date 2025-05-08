package com.example.final_project.Service;

import com.example.final_project.Model.Business;
import com.example.final_project.Model.Payment;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Repository.PaymentRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

/// الصلاحية لم تحدد
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /// الصلاحية لم تحدد
    public void deletePayment(Integer paymentId){
        Payment payment = paymentRepository.findPaymentById(paymentId);

    }



}
