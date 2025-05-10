package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.MoyasarPayment;
import com.example.final_project.Model.Payment;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Repository.TaxPayerRepository;
import com.example.final_project.Repository.TaxReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MoyasarPaymentService {

    private final TaxPayerRepository taxPayerRepository;
    private final TaxReportsRepository taxReportsRepository;

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";

    public ResponseEntity<?> processPayment(Integer taxPayerId,Integer taxReportId,MoyasarPayment moyasarPayment){
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        TaxReports taxReports = taxReportsRepository.findTaxReportsById(taxReportId);
        if (taxPayer==null)
            throw new ApiException("The TaxPayer not found");
        if (taxReports==null)
            throw new ApiException("The taxReport not found");
        if (taxReports.getStatus().equals("Paid"))
            throw new ApiException("This TaxReport is already paid");

        int amount = (int) (taxReports.getTotalTax()*100);
        String stAmount = String.valueOf(amount);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(apiKey, "");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("amount", stAmount); // in halalas = 100 SAR
        params.add("currency", "SAR");
        params.add("description", moyasarPayment.getDescription());
        params.add("callback_url", "http://localhost:5000/api/v1/moyasar-payment/callback");
        params.add("source[type]", "card"); // or "sadad", "mada", "applepay" "creditcard"
        params.add("source[name]", taxPayer.getUser().getName());
        params.add("source[number]", moyasarPayment.getNumber());
        params.add("source[month]", moyasarPayment.getMonth());
        params.add("source[year]", moyasarPayment.getYear());
        params.add("source[cvc]", moyasarPayment.getCvc());

        String url = "https://api.moyasar.com/v1/payments";
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity response = restTemplate.postForEntity(url, request, String.class);

        Payment payment = new Payment();
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTaxPayer(taxPayer);
        payment.setTaxReports(taxReports);
        payment.setName(moyasarPayment.getName());

//        response.getBody().
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }


    public String getPaymentStatus(String payment_id){

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + payment_id,
                HttpMethod.GET,entity, String.class);

        return response.getBody();
    }
}
