package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Repository.ItemSaleRepository;
import com.example.final_project.Repository.ProductRepository;
import com.example.final_project.Repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemSaleService {

    private final ItemSaleRepository itemSaleRepository;
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    public List<ItemSale> getAllItemSales() {
        return itemSaleRepository.findAll();
    }

    public void addItemSale(ItemSale itemSale, Integer salesId, Integer productId) {
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new ApiException("Sales not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException("Product not found"));

        itemSale.setSales(sales);
        itemSale.setProduct(product);
        itemSale.setUnitPrice(product.getPrice());
        itemSale.setTotalPrice(product.getPrice() * itemSale.getQuantity());

        itemSaleRepository.save(itemSale);
    }

    public void updateItemSale(Integer id, ItemSale newItemSale) {
        ItemSale itemSale = itemSaleRepository.findById(id)
                .orElseThrow(() -> new ApiException("ItemSale not found"));

        itemSale.setQuantity(newItemSale.getQuantity());
        itemSale.setUnitPrice(itemSale.getProduct().getPrice()); // always based on product price
        itemSale.setTotalPrice(itemSale.getUnitPrice() * itemSale.getQuantity());

        itemSaleRepository.save(itemSale);
    }

    public void deleteItemSale(Integer id) {
        ItemSale itemSale = itemSaleRepository.findById(id)
                .orElseThrow(() -> new ApiException("ItemSale not found"));
        itemSaleRepository.delete(itemSale);
    }

    public List<ItemSale> getItemSalesBySalesId(Integer salesId) {
        return itemSaleRepository.findBySalesId(salesId);
    }

    Set<ItemSale> itemSales;

    public void addProduct(Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null)
            throw new ApiException("product not found");

        ItemSale itemSale = new ItemSale();
        for (ItemSale i: itemSales){
            if (i.getProduct().equals(product)){
                i.setQuantity(i.getQuantity()+1);
                i.setTotalPrice(i.getUnitPrice()*i.getQuantity());
                return;
            }
        }
        itemSale.setProduct(product);
        itemSale.setUnitPrice(product.getPrice());
        itemSale.setTotalPrice(product.getPrice());
        itemSales.add(itemSale);
    }

    Integer counter=1000;
    public void purchaseItems(){
        counter++;
//        Set<ItemSale> itemSales1 = itemSales;
        Sales sales = new Sales();
        sales.setSale_invoice(counter);
        double totalPrice = 0.0;
        double tax;
        double grandPrice=0.0;
        for (ItemSale i: itemSales){
            totalPrice += i.getTotalPrice();
//            sales.getItemSales().add(i);
        }
        tax = totalPrice*0.15;
        grandPrice = tax+totalPrice;
        sales.setGrand_amount(grandPrice);
        sales.setTax_amount(tax);
        sales.setTotal_amount(totalPrice);
        sales.setInvoiceDate(LocalDateTime.now());
        sales.setItemSales(itemSales);
        salesRepository.save(sales);
        itemSaleRepository.saveAll(itemSales);
        itemSales=null;
    }

}
