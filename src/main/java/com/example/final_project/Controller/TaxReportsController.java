//package com.example.final_project.Controller;
//
//import com.example.final_project.Api.ApiException;
//import com.example.final_project.Api.ApiResponse;
//import com.example.final_project.DTOOUT.TaxReportStatusDTO;
//import com.example.final_project.Model.TaxReports;
//import com.example.final_project.Model.User;
//import com.example.final_project.Service.TaxReportsService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/taxReports")
//@RequiredArgsConstructor
//public class TaxReportsController {
//
//    private final TaxReportsService taxReportsService;
//
//    @GetMapping("/get")
//    public ResponseEntity getAllTaxReports(@AuthenticationPrincipal User user){
//        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getAllTaxReports(user.getId()));
//    }
//
//    @PostMapping("/add/{business_id}")
//    public ResponseEntity addTaxReports(@AuthenticationPrincipal User user, @PathVariable Integer business_id, @Valid @RequestBody TaxReports taxReports){
//        taxReportsService.addTaxReports(user.getId(), business_id, taxReports);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is added!"));
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity updateTaxReports(@AuthenticationPrincipal User user,@PathVariable Integer id,@Valid @RequestBody TaxReports taxReports){
//        taxReportsService.updateTaxReports(user.getId(),id, taxReports);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is updated"));
//    }
//
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity deleteTaxReports(@AuthenticationPrincipal User user,@PathVariable Integer id){
//        taxReportsService.deleteTaxReports(user.getId(),id);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Tax Reports is deleted"));
//    }
//
//    //فرض غرامة تاخير سداد بعدها بشهر
//    @PutMapping("/apply-penalty/{taxReportId}")
//    public ResponseEntity applyLatePaymentPenalty(@AuthenticationPrincipal User user,@PathVariable Integer taxReportId) {
//        taxReportsService.applyLatePaymentPenalty(user.getId(),taxReportId);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Late payment penalty applied if applicable"));
//    }
//
//    //فرض غرامة تاخير سداد بعدها بشهرين
//    @PutMapping("/apply-2month-penalty/{taxReportId}")
//    public ResponseEntity applyTwoMonthLatePenalty(@AuthenticationPrincipal User user,@PathVariable Integer taxReportId) {
//        taxReportsService.applyTwoMonthLatePenalty(user.getId(),taxReportId);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("10% penalty applied for 2-month late payment"));
//    }
//
//    //في حال تجاوز 90 يوم يتم اتخاذ الإجراءات القانونية
//    @PutMapping("/apply-legal-action/{taxReportId}")
//    public ResponseEntity applyLegalAction(@AuthenticationPrincipal User user,@PathVariable Integer taxReportId) {
//        taxReportsService.applyLegalActionForTaxEvasion(user.getId(),taxReportId);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Legal action applied for tax evasion"));
//    }
//
//
//
////################################
//// التقارير المستحقه لدفع وغير مدفوعه
//    @GetMapping("/due-payment")
//    public ResponseEntity getUnpaidDueTaxReports(@AuthenticationPrincipal User user) {
//        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getUnpaidDueTaxReports(user.getId()));
//    }
//
//
//
//    @GetMapping("/reports/{auditorId}")
//    public ResponseEntity getReportsByAuditor(@AuthenticationPrincipal User user,@PathVariable Integer auditorId) {
//        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getReportsByAuditor(user.getId(),auditorId));
//    }
//
//    // 2. Get report count by status for an auditor
//    @GetMapping("/report-count/{auditorId}/{status}")
//    public ResponseEntity getReportCountByStatus(@AuthenticationPrincipal User user,@PathVariable Integer auditorId, @PathVariable String status) {
//        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getReportCountByStatus(user.getId(),auditorId, status));
//    }
//
//    // 4. Get approval rate for an auditor
//    @GetMapping("/approval-rate/{auditorId}")
//    public ResponseEntity getApprovalRate(@AuthenticationPrincipal User user,@PathVariable Integer auditorId) {
//        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getApprovalRate(user.getId(),auditorId));
//    }
//
//    // 5. Bulk approve reports for an auditor
//    @PutMapping("/bulk-approve/{auditorId}")
//    public ResponseEntity bulkApproveReports(@AuthenticationPrincipal User user,@PathVariable Integer auditorId, @RequestBody List<Integer> reportIds) {
//        taxReportsService.bulkApproveReports(user.getId(),auditorId, reportIds);
//        return ResponseEntity.status(HttpStatus.OK).body(new ApiException("Reports approved successfully"));
//    }
//
//    // 7. Get latest report reviewed by auditor
//    @GetMapping("/latest-report/{auditorId}")
//    public ResponseEntity getLatestReport(@AuthenticationPrincipal User user,@PathVariable Integer auditorId) {
//        return ResponseEntity.status(HttpStatus.OK).body(taxReportsService.getLatestReportByAuditor(user.getId(),auditorId));
//    }
//
//    @GetMapping("/unapproved")
//    public ResponseEntity getUnapprovedTaxReports(@AuthenticationPrincipal User user) {
//        return ResponseEntity.status(200).body(taxReportsService.getUnapprovedTaxReports(user.getId()));
//    }
//
//    @GetMapping("/{reportId}/payment-status")
//    public ResponseEntity getPaymentStatus(@AuthenticationPrincipal User user,@PathVariable Integer reportId) {
//        return ResponseEntity.status(200).body(taxReportsService.getPaymentStatusByReportId(user.getId(),reportId));
//    }
//
//    @GetMapping("/notify-upcoming-payment")
//    public ResponseEntity<String> notifyUpcomingPayments() {
//        taxReportsService.notifyUpcomingPayments();
//        return ResponseEntity.status(200).body("Notifications sent to taxpayers with near-due reports.");
//    }
//
//    // Endpoint 37
//    @GetMapping("/get-tax-reports/tax-payer/{taxPayerId}")
//    public ResponseEntity printTaxReportForEveryBusinesses(@AuthenticationPrincipal User user,@PathVariable Integer taxPayerId){
//        return ResponseEntity.status(200).body(taxReportsService.printTaxReportForEveryBusinesses(user.getId(),taxPayerId));
//    }
//
//    @GetMapping("/print/{reportId}")
//    public ResponseEntity<byte[]> printTaxReportAsPdf(@AuthenticationPrincipal User user,@PathVariable Integer reportId) {
//        byte[] pdf = taxReportsService.getTaxReportAsPdf(user.getId(),reportId);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tax-report-" + reportId + ".pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdf);
//    }
//
//}
