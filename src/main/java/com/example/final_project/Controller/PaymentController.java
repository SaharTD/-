package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;



    @GetMapping("getAllPayments")
    public ResponseEntity getAllPayments (){
        return ResponseEntity.status(200).body(paymentService.getAllPayments());
    }


    @DeleteMapping("delete/{paymentId}")
    public ResponseEntity deletePayment(@PathVariable Integer paymentId){
        paymentService.deletePayment(paymentId);
        return ResponseEntity.status(200).body(new ApiResponse("the payment has been deleted successfully "));
    }



}
