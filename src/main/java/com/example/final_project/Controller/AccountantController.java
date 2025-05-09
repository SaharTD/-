package com.example.final_project.Controller;


import com.example.final_project.DTO.AccountantDTO;
import com.example.final_project.Service.AccountantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accountant")
public class AccountantController {

    private final AccountantService accountantService;


    @PostMapping("/add")
    public ResponseEntity createAccountant(@RequestBody @Valid AccountantDTO accountantDTO) {
        accountantService.createAccountant(accountantDTO);
        return ResponseEntity.ok("Accountant created successfully");
    }


    @GetMapping("/getall")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(accountantService.getAllAccountants());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody @Valid AccountantDTO accountantDTO) {
        accountantService.updateAccountant(id, accountantDTO);
        return ResponseEntity.status(200).body("Updated Successfully");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAccountant(@PathVariable Integer id) {
        accountantService.deleteAccountant(id);
        return ResponseEntity.status(200).body("Accountant deleted successfully");
    }






}
