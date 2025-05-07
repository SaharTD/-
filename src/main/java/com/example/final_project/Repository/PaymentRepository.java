package com.example.final_project.Repository;

import com.example.final_project.Model.Business;
import com.example.final_project.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {


    Payment findPaymentById(Integer id);



}
