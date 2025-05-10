package com.example.final_project.Controller;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTOOUT.TaxReportStatusDTO;
import com.example.final_project.Model.TaxReports;
import com.example.final_project.Service.TaxReportsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @GetMapping("/reports/{auditorId}")
    public ResponseEntity getReportsByAuditor(@PathVariable Integer auditorId) {
        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getReportsByAuditor(auditorId));
    }

    // 2. Get report count by status for an auditor
    @GetMapping("/report-count/{auditorId}/{status}")
    public ResponseEntity getReportCountByStatus(@PathVariable Integer auditorId, @PathVariable String status) {
        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getReportCountByStatus(auditorId, status));
    }

    // 4. Get approval rate for an auditor
    @GetMapping("/approval-rate/{auditorId}")
    public ResponseEntity getApprovalRate(@PathVariable Integer auditorId) {
        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getApprovalRate(auditorId));
    }

    // 5. Bulk approve reports for an auditor
    @PutMapping("/bulk-approve/{auditorId}")
    public ResponseEntity bulkApproveReports(@PathVariable Integer auditorId, @RequestBody List<Integer> reportIds) {
        taxReportsService.bulkApproveReports(auditorId, reportIds);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Reports approved successfully"));
    }

    // 7. Get latest report reviewed by auditor
    @GetMapping("/latest-report/{auditorId}")
    public ResponseEntity getLatestReport(@PathVariable Integer auditorId) {
        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getLatestReportByAuditor(auditorId));
    }


    // Endpoint 37
    @GetMapping("/get-tax-reports/tax-payer/{taxPayerId}")
    public ResponseEntity printTaxReportForEveryBusinesses(@PathVariable Integer taxPayerId){
        return ResponseEntity.status(200).body(taxReportsService.printTaxReportForEveryBusinesses(taxPayerId));
    }


}
