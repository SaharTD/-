package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.ProductDTO;
import com.example.final_project.Model.*;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final CounterBoxRepository counterBoxRepository;
    private final BranchRepository branchRepository;
    private final AccountantRepository accountantRepository;
    private final ProductRepository productRepository;


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









}
