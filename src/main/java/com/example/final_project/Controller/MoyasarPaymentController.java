package com.example.final_project.Controller;

import com.example.final_project.Model.MoyasarPayment;
import com.example.final_project.Service.MoyasarPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/moyasar-payment")
@RequiredArgsConstructor
public class MoyasarPaymentController {

    private final MoyasarPaymentService moyasarPaymentService;
    @PostMapping("/pay-tax/tax-payer/{taxPayerId}/tax-report/{taxReportId}")
    public ResponseEntity<?> processPayment(@PathVariable Integer taxPayerId,@PathVariable Integer taxReportId,@RequestBody @Valid MoyasarPayment moyasarPayment) {
        return ResponseEntity.status(200).body(moyasarPaymentService.processPayment(taxPayerId, taxReportId, moyasarPayment));
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity getPaymentStatus(@PathVariable String id) {
        return ResponseEntity.ok().body(moyasarPaymentService.getPaymentStatus(id));
    }

    @GetMapping("/callback")
    public ResponseEntity callbackUrl(){
        return ResponseEntity.status(200).body("Paid !!");
    }

}
