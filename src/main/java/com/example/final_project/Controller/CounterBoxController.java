package com.example.final_project.Controller;


import com.example.final_project.Api.ApiResponse;
import com.example.final_project.DTO.CounterBoxDTO;
import com.example.final_project.DTO.SaleRequestDTO;
import com.example.final_project.Model.CounterBox;
import com.example.final_project.Repository.SalesRepository;
import com.example.final_project.Service.CounterBoxService;
import com.example.final_project.Service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/counterbox")
public class CounterBoxController {


    private final CounterBoxService counterBoxService;
    private final SalesService salesService;

    //كرييت مع المحاسب
    @PostMapping("/create")
    public ResponseEntity<?> createCounterBox(@RequestBody @Valid CounterBoxDTO counterBoxDTO) {
        counterBoxService.createCounterBox(counterBoxDTO);
        return ResponseEntity.ok("Counter box created successfully");
    }


    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(counterBoxService.getAllCounterBoxes());
    }

    @GetMapping("get/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(counterBoxService.getCounterBox(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody @Valid CounterBox counterBox, @PathVariable Integer id){
        counterBoxService.updateCounterBox(counterBox, id);
        return ResponseEntity.status(200).body(new ApiResponse("Updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        counterBoxService.deleteCounterBox(id);
        return ResponseEntity.status(200).body(new ApiResponse("Deleted successfully"));
    }

    //--------------------------------------------

    //open  the counter box
    @PatchMapping("/open/{boxId}/{accountantId}")
    public ResponseEntity<?> openCounterBox(@PathVariable Integer boxId, @PathVariable Integer accountantId) {
        counterBoxService.openCounterBox(boxId, accountantId);
        return ResponseEntity.status(200).body("CounterBox opened successfully");
    }
    @PostMapping("/create-box")
    public ResponseEntity createCounterBox2(@RequestBody @Valid CounterBoxDTO counterBoxDTO) {
//        counterBoxService.createCounterBox2(counterBoxDTO);
        return ResponseEntity.status(200).body("Counter box created successfully");
    }


    //close the counter box
    @PostMapping("/close/{id}/{accountantId}")
    public ResponseEntity<String> closeCounterBox(@PathVariable Integer id, @PathVariable Integer accountantId) {
        String result = counterBoxService.closeCounterBox(id, accountantId);
        return ResponseEntity.status(200).body(result);
    }


    // Endpoint 7
    @PutMapping("/close-opened-counter-box")
    public ResponseEntity closeCounterBoxAuto(){
        counterBoxService.closeCounterBoxAuto();
        return ResponseEntity.status(200).body(new ApiResponse("counter Box is closed"));
    }

}
