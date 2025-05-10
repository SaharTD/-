package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.SaleRequestDTO;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.CounterBox;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.CounterBoxRepository;
import com.example.final_project.Repository.ProductRepository;
import com.example.final_project.Repository.SalesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final CounterBoxRepository  counterBoxRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;


    public List<Sales> getAllSales(){
        return salesRepository.findAll();
    }

    public void addSales(Integer counterBox_id,Integer branch_id){
        CounterBox counterBox=counterBoxRepository.findCounterBoxById(counterBox_id);
        Branch branch=branchRepository.findBranchesById(branch_id);
        sales.setDate(LocalDateTime.now());


        if(counterBox_id==null&&branch==null){
            throw new ApiException("Branch or Counter Box not found ");
        }
        Sales sales = new Sales();
//        sales.setSale_invoice();
        salesRepository.save(sales);
    }

    public void updateSales(Integer id,Sales sales){
        Sales oldSales=salesRepository.findSalesById(id);

        if(oldSales==null){

        }
        oldSales.setSale_invoice(sales.getSale_invoice());
        oldSales.setTax_amount(sales.getTax_amount());
        oldSales.setTotal_amount(sales.getTotal_amount());
        oldSales.setGrand_amount(sales.getGrand_amount());

        salesRepository.save(oldSales);
    }

    public void deleteSales(Integer id){
        Sales sales=salesRepository.findSalesById(id);

        if(sales==null){
            throw new ApiException("Sales not found");
        }

        salesRepository.delete(sales);
    }


    public Product getSingleProductByBarcode(String barcode) {
        Product product = productRepository.findProductByBarcode(barcode);
        if (product==null) {
            throw new ApiException("No product found with barcode: " + barcode);
        }
        return product;
    }

    public void addProductToSales(Integer salesId, String barcode) {
        Sales sales = salesRepository.findSalesById(salesId);
        if (sales == null) {
            throw new ApiException("Sales not found");
        }

        Product product = productRepository.findProductByBarcode(barcode);
        if (product==null) {
            throw new ApiException("Product not found with barcode: " + barcode);
        }

        sales.getProducts().add(product);
        productRepository.save(product);
        salesRepository.save(sales);
    }


    public void calculateSalesAmounts(Integer salesId) {
        Sales sales = salesRepository.findSalesById(salesId);
        if (sales == null) {
            throw new ApiException("Sales not found");
        }

        double total = 0;
        for (Product product : sales.getProducts()) {
            total += product.getPrice();
        }

        double tax = total * 0.15;
        double grand = total + tax;

        sales.setTotal_amount(total);
        sales.setTax_amount(tax);
        sales.setGrand_amount(grand);

        salesRepository.save(sales);
    }


    public Map<String, Double> getSalesSummaryByBranch(Integer branchId) {
        List<Sales> salesList = salesRepository.findSalesByBranch(branchId);
        double totalBeforeTax = 0.0;

        for (Sales sale : salesList) {
            if (sale.getTotal_amount() != null)
                totalBeforeTax += sale.getTotal_amount();
        }

        double taxRate = 0.15;
        double taxAmount = totalBeforeTax * taxRate;
        double grandTotal = totalBeforeTax + taxAmount;

        Map<String, Double> result = new HashMap<>();
        result.put("total", totalBeforeTax);
        result.put("total tax Amount is:", taxAmount);
        result.put("grand total is:", grandTotal);
        return result;
    }

    public Map<String, Object> addSales2(Integer counterBox_id, Integer branch_id, Sales sales) {
        CounterBox counterBox = counterBoxRepository.findCounterBoxById(counterBox_id);
        Branch branch = branchRepository.findBranchesById(branch_id);

        if (counterBox == null || branch == null) {
            throw new ApiException("Branch or Counter Box not found ");
        }



        sales.setDate(LocalDateTime.now());
        LocalDate today = LocalDate.now();

        List<LocalDate> discountDates = List.of(
                LocalDate.of(2025, 5, 10),
                LocalDate.of(2025, 5, 20)
        );


        double originalAmount = sales.getTotal_amount();
        double finalAmount = originalAmount;
        double discountPercentage = 0.20;
        double discountAmount = 0.0;
        boolean discountApplied = false;

        if (discountDates.contains(today)) {
            discountAmount = originalAmount * discountPercentage;
            finalAmount = originalAmount - discountAmount;
            discountApplied = true;
        }


        double tax = finalAmount * 0.15;
        double grand = finalAmount + tax;

        sales.setTotal_amount(finalAmount);
        sales.setGrand_amount(grand);
        salesRepository.save(sales);

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("original_price", originalAmount);

        if (discountApplied) {
            result.put("discount_percentage", discountPercentage * 100 + "%");
            result.put("discount_amount", discountAmount);
            result.put("price_after_discount", finalAmount);
        }

        result.put("tax", tax);
        result.put("grand_total", grand);

        return result;
    }

    public List<Sales> getSalesByTaxPayerId(Integer taxPayerId) {
        return salesRepository.findSalesByTaxPayerId(taxPayerId);
    }






    // Endpoint
    public void printSale(Integer saleId){
        Sales sales = salesRepository.findSalesById(saleId);
    }


    // Endpoint 33
    public void calcuateTaxBySaleNumber(Integer salesNumber){

    }

}
