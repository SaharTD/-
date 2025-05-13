package com.example.final_project.Controller;

import com.example.final_project.Model.MoyasarPayment;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.MoyasarPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/moyasar-payment")
@RequiredArgsConstructor
public class MoyasarPaymentController {

    private final MoyasarPaymentService moyasarPaymentService;

    @PostMapping("/pay-tax/tax-report/{taxReportId}")
    public ResponseEntity<?> processPayment(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer taxReportId, @RequestBody @Valid MoyasarPayment moyasarPayment) throws IOException {
        return ResponseEntity.status(200).body(moyasarPaymentService.processPayment(myUser.getId(), taxReportId, moyasarPayment));
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity getPaymentStatus(@PathVariable Integer id) throws JsonProcessingException {
        return ResponseEntity.ok().body(moyasarPaymentService.getPaymentStatus(id));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callbackHandler(@RequestParam String id) throws JsonProcessingException {
        TaxReports taxReport = moyasarPaymentService.callback(id);
        return ResponseEntity.ok(taxReport); // يرجع التقرير مع حالته سواء دفعت أو لم تدفع
    }

    @GetMapping("/callback2")
    public ResponseEntity callbackUrl(){
        return ResponseEntity.status(200).body("Paid !!");
    }

}
