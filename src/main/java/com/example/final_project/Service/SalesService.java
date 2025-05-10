package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.CounterBox;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.CounterBoxRepository;
import com.example.final_project.Repository.ProductRepository;
import com.example.final_project.Repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void addSales(Integer counterBox_id,Integer branch_id,Sales sales){
        CounterBox counterBox=counterBoxRepository.findCounterBoxById(counterBox_id);
        Branch branch=branchRepository.findBranchesById(branch_id);

        if(counterBox_id==null&&branch==null){
            throw new ApiException("Branch or Counter Box not found ");
        }
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

    // Endpoint
    public void printSale(Integer saleId){
        Sales sales = salesRepository.findSalesById(saleId);
    }


    // Endpoint 33
    public void calcuateTaxBySaleNumber(Integer salesNumber){

    }

}
