package com.example.final_project.Controller;

import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Model.MoyasarPayment;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Model.MyUser;
import com.example.final_project.Service.MoyasarPaymentService;
import com.example.final_project.Service.PaymentService;
import com.example.final_project.Service.TaxReportsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/moyasar-payment")
@RequiredArgsConstructor
public class MoyasarPaymentController {

    private final MoyasarPaymentService moyasarPaymentService;
    private final TaxReportsService taxReportsService;
    private final PaymentService paymentService;

    @Value("${moyasar.api.key}")
    private String apiKey;


    // authority -> TaxPayer
    @PostMapping("/pay-tax/tax-report/{taxReportId}")
    public ResponseEntity<?> processPayment(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer taxReportId, @RequestBody @Valid MoyasarPayment moyasarPayment) throws IOException {
        return ResponseEntity.status(200).body(moyasarPaymentService.processPayment(myUser.getId(), taxReportId, moyasarPayment));
    }


    @GetMapping("/get-status/{id}")
    public ResponseEntity getPaymentStatus(@PathVariable Integer id) throws JsonProcessingException {
        return ResponseEntity.ok().body(moyasarPaymentService.getPaymentStatus(id));
    }


    @RequestMapping(value = "/callback",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity callbackHandler(HttpServletRequest request) {
        String paymentId = request.getParameter("id");
        String taxPayerIdStr = request.getParameter("taxPayerId");
        if (paymentId==null||taxPayerIdStr==null)
            return ResponseEntity.status(400).body(new ApiResponse("Missing paymetn ID or taxpayer ID"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "https://api.moyasar.com/v1/payments/" + paymentId;
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET,entity,Map.class);

        String status = (String) response.getBody().get("status");

        if ("paid".equalsIgnoreCase(status)) {
            Integer taxPayerId = Integer.parseInt(taxPayerIdStr);
            paymentService.paymentSuccess(taxPayerId,paymentId);
            return ResponseEntity.ok("Tax Report successfully paid");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed."); // يرجع التقرير مع حالته سواء دفعت أو لم تدفع
    }

    @GetMapping("/callback2")
    public ResponseEntity callbackUrl(){
        return ResponseEntity.status(200).body("Paid !!");
    }

}
