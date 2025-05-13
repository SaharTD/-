package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.MoyasarPayment;
import com.example.final_project.Model.Payment;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Repository.TaxPayerRepository;
import com.example.final_project.Repository.TaxReportsRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MoyasarPaymentService {

    private final TaxPayerRepository taxPayerRepository;
    private final TaxReportsRepository taxReportsRepository;

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";

    public ResponseEntity<?> processPayment(Integer taxPayerId,Integer taxReportId,MoyasarPayment moyasarPayment) throws IOException {
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
        params.add("source[name]", taxPayer.getMyUser().getName());
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
        payment.setStatus("Pending");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree((JsonParser) response.getBody());

//        ObjectMapper mapper2 = new ObjectMapper();
//        JsonParser json = mapper2.getFactory().createParser((File) response.getBody()); // ✅ Correct way


        String paymentId = json.get("id").asText();
        payment.setPaymentId(paymentId);

        return ResponseEntity.status(response.getStatusCode()).body(json.get("transaction_url").asText());
    }


    public String getPaymentStatus(Integer taxReportId) throws JsonProcessingException {
        TaxReports taxReports = taxReportsRepository.findTaxReportsById(taxReportId);
        if (taxReports==null)
            throw new ApiException("The tax report not found");

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + taxReports
                        .getPayment().getPaymentId(),
                HttpMethod.GET,entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.getBody());

        if (json.get("status").asText().equals("paid"))
            taxReports.getPayment().setStatus("Paid");
        return json.get("status").asText();
    }

//*********************************************************************************************************

    public TaxReports callback(String paymentId) throws JsonProcessingException {
        // استدعاء الدفع من Moyasar
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + paymentId, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.getBody());

        String status = json.get("status").asText();
        String taxReportIdStr = json.get("metadata") != null && json.get("metadata").has("taxReportId")
                ? json.get("metadata").get("taxReportId").asText()
                : null;

        if (taxReportIdStr == null) {
            throw new ApiException("Metadata is missing taxReportId");
        }

        Integer taxReportId = Integer.parseInt(taxReportIdStr);
        TaxReports taxReport = taxReportsRepository.findTaxReportsById(taxReportId);

        if (taxReport == null) throw new ApiException("Tax Report not found");

        if (status.equalsIgnoreCase("paid")) {
            taxReport.setStatus("Paid");
            taxReport.setPaymentDate(LocalDate.now());
            if (taxReport.getPayment() != null) {
                taxReport.getPayment().setStatus("Paid");
            }
        } else {
            if (taxReport.getPayment() != null) {
                taxReport.getPayment().setStatus("Failed");
            }
        }

        taxReportsRepository.save(taxReport);
        return taxReport;
    }



    //*********************************************************************************************

    public void callback(){

    }
}
