package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTOOUT.TaxReportStatusDTO;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Service.TaxReportsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/taxReports")
@RequiredArgsConstructor
public class TaxReportsController {

    private final TaxReportsService taxReportsService;

    @GetMapping("/get")
    public ResponseEntity getAllTaxReports(){
        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getAllTaxReports());
    }

    @PostMapping("/add/{auditor_id}/{business_id}")
    public ResponseEntity addTaxReports(@PathVariable Integer auditor_id,@PathVariable Integer business_id, @Valid @RequestBody TaxReports taxReports){
        taxReportsService.addTaxReports(auditor_id, business_id, taxReports);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is added!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTaxReports(@PathVariable Integer id,@Valid @RequestBody TaxReports taxReports){
        taxReportsService.updateTaxReports(id, taxReports);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is updated"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteTaxReports(@PathVariable Integer id){
        taxReportsService.deleteTaxReports(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is deleted"));
    }

    //فرض غرامة تاخير سداد بعدها بشهر
    @PutMapping("/apply-penalty/{taxReportId}")
    public ResponseEntity applyLatePaymentPenalty(@PathVariable Integer taxReportId) {
        taxReportsService.applyLatePaymentPenalty(taxReportId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Late payment penalty applied if applicable"));
    }

    //فرض غرامة تاخير سداد بعدها بشهرين
    @PutMapping("/apply-2month-penalty/{taxReportId}")
    public ResponseEntity applyTwoMonthLatePenalty(@PathVariable Integer taxReportId) {
        taxReportsService.applyTwoMonthLatePenalty(taxReportId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("10% penalty applied for 2-month late payment"));
    }

    //في حال تجاوز 90 يوم يتم اتخاذ الإجراءات القانونية
    @PutMapping("/apply-legal-action/{taxReportId}")
    public ResponseEntity applyLegalAction(@PathVariable Integer taxReportId) {
        taxReportsService.applyLegalActionForTaxEvasion(taxReportId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Legal action applied for tax evasion"));
    }



//تغيير حالة تقرير
    @PutMapping("/change-status/{taxReportId}/{auditorId}")
    public ResponseEntity changeTaxReportStatus(@PathVariable Integer auditorId,@PathVariable Integer taxReportId,@RequestBody TaxReportStatusDTO taxReportStatusDTO) {
        taxReportsService.changeTaxReportStatus(taxReportId,auditorId,taxReportStatusDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Status updated to: " + taxReportStatusDTO.getNewStatus()));
    }

// التقارير المستحقه لدفع وغير مدفوعه
    @GetMapping("/due-payment")
    public ResponseEntity getUnpaidDueTaxReports() {
        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getUnpaidDueTaxReports());
    }







}
