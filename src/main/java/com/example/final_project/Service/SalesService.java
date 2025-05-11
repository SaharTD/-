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
import com.example.final_project.DTO.ProductDTO;
import com.example.final_project.Model.*;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final CounterBoxRepository counterBoxRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final AccountantRepository accountantRepository;


    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }
//
//    public void addSales(Integer accountantId, Integer counterBox_id, Integer branch_id, Sales sales) {
//
//
//        CounterBox counterBox = counterBoxRepository.findCounterBoxById(counterBox_id);
//        Branch branch = branchRepository.findBranchesById(branch_id);
//
//        if (counterBox_id == null && branch == null) {
//            throw new ApiException("Branch or Counter Box not found ");
//        }
//
//
//        Accountant accountant = accountantRepository.findAccountantByIdAndBranch(accountantId, branch);
//        if (accountant == null) {
//            throw new ApiException("accountant is not found or does not belong to the mentioned branch");
//        }
//
//
//        if (!accountant.getCounterBoxes().contains(counterBox)) {
//            throw new ApiException("The counter  does not belong to the accountant");
//        }
//
//
//        if (!accountant.getCounterBoxes().contains(counterBox) && !counterBox.getCloseDatetime().isBefore(LocalDateTime.now()) || counterBox.getCloseDatetime() == null) {
//            throw new ApiException("The counter box is closed");
//        }
//
//
//        sales.setBranch(branch);
//        sales.setCounterBox(counterBox);
//        salesRepository.save(sales);
//    }
//
//
//    public void addProductInSale(Integer accountantId, Integer saleId, ProductDTO productDTO) {
//
//        Accountant accountant = accountantRepository.findAccountantById(accountantId);
//        if (accountant == null) {
//            throw new ApiException("accountant is not found or does not belong to the mentioned branch");
//        }
//
//        Sales thisSales=salesRepository.findSalesById(saleId);
//        Sales currentSale = salesRepository.findSalesByIdAndCounterBox_Accountant(thisSales.getSale_invoice(), accountantId);
//        if (currentSale == null) {
//            throw new ApiException("the sale is not found or does not belong to this accountant");
//        }
//
//
//        Product product = productRepository.findProductByName(productDTO.getName());
//        if (product==null){
//            throw new ApiException("Sorry the product is not found . check the  product name again ");
//
//        }
//
//        if(product.getStock()< productDTO.getQuantity()){
//            throw new ApiException("Sorry the product is out of stock");
//
//        }
//
//
//
//        product.setQuantity(productDTO.getQuantity());
//        product.setStock(product.getStock() - productDTO.getQuantity());
//
//        productRepository.save(product);
//
//
//        Product saleProduct=new Product();
//        saleProduct.setName(productDTO.getName());
//        saleProduct.setQuantity(productDTO.getQuantity());
//        currentSale.getProducts().add(saleProduct);
//        productRepository.save(saleProduct);
//
//        salesRepository.save(currentSale);
//
//
//    }
//
//
//    public byte[] printInvoice(Integer accountantId, Integer saleId) {
//
//
//        Accountant accountant = accountantRepository.findAccountantById(accountantId);
//        if (accountant == null) {
//            throw new ApiException("accountant is not found or does not belong to the mentioned branch");
//        }
//
//        Sales currentSale = salesRepository.findSalesByIdAndCounterBox_Accountant(saleId,accountant);
//        if (currentSale == null) {
//            throw new ApiException("the Invoice is not found ");
//        }
//
//
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            Document document = new Document();
//            PdfWriter.getInstance(document, baos);
//            document.open();
//
//
//            try {
//                InputStream is = getClass().getResourceAsStream("/logo.png");
//                if (is != null) {
//                    Image logo = Image.getInstance(is.readAllBytes());
//                    logo.scaleToFit(120, 120);
//                    logo.setAlignment(Element.ALIGN_CENTER);
//                    document.add(logo);
//                    document.add(Chunk.NEWLINE);
//                }
//            } catch (Exception e) {
//                System.out.println("Logo not found or failed to load.");
//            }
//
//
//            Paragraph title = new Paragraph("SALE INVOICE", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//            document.add(Chunk.NEWLINE);
//
//
//            document.add(new Paragraph("Generated on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//
//            document.add(new Paragraph("------------------------------------------------------------"));
//
//            document.add(new Paragraph("INVOICE ID: " + currentSale.getId()));
//            document.add(new Paragraph("Date: " + currentSale.getSaleDate() ));
//            document.add(new Paragraph("Branch: " + currentSale.getBranch()));
//            document.add(new Paragraph("------------------------------------------------------------"));
//
//
//            document.add(new Paragraph("Products:"));
//            PdfPTable table = new PdfPTable(4);
//            table. setWidthPercentage(100);
//            table.addCell("Product Name:");
//            table.addCell(" Quantity:");
//            table.addCell("Unit Price:");
//            table.addCell("Total Price:");
//
//            Double subTotal = 0.0;
//            for (Product p : currentSale.getProducts()) {
//
//                table.addCell(p.getName());
//                table.addCell(String.valueOf(p.getQuantity()));
//                table.addCell(String.valueOf(p.getPrice()));
//                Double total=p.getQuantity() * p.getPrice();
//                table.addCell(String.valueOf(total));
//                subTotal+=total;
//
//            }
//            document.add(table);
//
//
//
//            currentSale.setTax_amount(subTotal * 0.15);
//            currentSale.setGrand_amount(subTotal + currentSale.getTax_amount());
//
//
//            /// the total prices tax +grand total after tax
//            document.add(new Paragraph("------------------------------------------------------------"));
//            document.add(new Paragraph("Subtotal :" + String.valueOf(subTotal)));
//            document.add(new Paragraph("Tax (15%) :" + String.valueOf(currentSale.getTax_amount())));
//            document.add(new Paragraph("Total :" + String.valueOf(currentSale.getGrand_amount())));
//            document.add(new Paragraph("------------------------------------------------------------"));
//            document.add(Chunk.NEWLINE);
//
//
//            document.add(new Paragraph("Thank you for shopping with us!"));
//            document.add(Chunk.NEWLINE);
//            document.add(new Paragraph("Mohasil Team", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));
//
//            document.close();
//
//
//            currentSale.setSaleDate(LocalDateTime.now());
//            salesRepository.save(currentSale);
//
//
//            return baos.toByteArray();
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate PDF", e);
//        }
//
//
//
//
//    }
//
//


    public void addSales(Integer counterBox_id,Integer branch_id){
        CounterBox counterBox=counterBoxRepository.findCounterBoxById(counterBox_id);
        Branch branch=branchRepository.findBranchesById(branch_id);
//        sales.setDate(LocalDateTime.now());


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

//    public void addProductToSales(Integer salesId, String barcode) {
//        Sales sales = salesRepository.findSalesById(salesId);
//        if (sales == null) {
//            throw new ApiException("Sales not found");
//        }
//
//        Product product = productRepository.findProductByBarcode(barcode);
//        if (product==null) {
//            throw new ApiException("Product not found with barcode: " + barcode);
//        }
//
//        sales.getProducts().add(product);
//        productRepository.save(product);
//        salesRepository.save(sales);
//    }


//    public void calculateSalesAmounts(Integer salesId) {
//        Sales sales = salesRepository.findSalesById(salesId);
//        if (sales == null) {
//            throw new ApiException("Sales not found");
//        }
//
//        double total = 0;
//        for (Product product : sales.getProducts()) {
//            total += product.getPrice();
//        }
//
//        double tax = total * 0.15;
//        double grand = total + tax;
//
//        sales.setTotal_amount(total);
//        sales.setTax_amount(tax);
//        sales.setGrand_amount(grand);
//
//        salesRepository.save(sales);
//    }


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
