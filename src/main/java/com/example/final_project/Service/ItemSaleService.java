package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Repository.AccountantRepository;
import com.example.final_project.Repository.ItemSaleRepository;
import com.example.final_project.Repository.ProductRepository;
import com.example.final_project.Repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemSaleService {

    private final ItemSaleRepository itemSaleRepository;
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;
    private final SalesService salesService;
    private final AccountantRepository accountantRepository;


    public List<ItemSale> getAllItemSales() {
        return itemSaleRepository.findAll();
    }


    public void updateItemSale(Integer id, ItemSale newItemSale) {
        ItemSale itemSale = itemSaleRepository.findItemSaleById(id);
                if(itemSale==null){
                   throw  new ApiException("ItemSale not found");
                }

        itemSale.setQuantity(newItemSale.getQuantity());
        itemSale.setUnitPrice(itemSale.getProduct().getPrice()); // always based on product price
        itemSale.setTotalPrice(itemSale.getUnitPrice() * itemSale.getQuantity());

        itemSaleRepository.save(itemSale);
    }

    public void removeItemFromSale(Integer accounterId, Integer itemId,Integer saleId) {

        Accountant accountant = accountantRepository.findAccountantById(accounterId);
        if (accountant == null) {
            throw new ApiException("Accountant not found");
        }
        ItemSale itemSale = itemSaleRepository.findItemSaleById(itemId);
        if (itemSale == null) {
            throw new ApiException("ItemSale not found");
        }
        Sales currentSale = salesRepository.findSalesById(saleId);
        if (currentSale == null) {
            throw new ApiException("Sale not found");
        }
        itemSaleRepository.delete(itemSale);

        Product product1 = itemSale.getProduct();

        if (currentSale.getSalesStatus().equals("CONFIRMED")) {
            throw new ApiException("Cannot remove item from a confirmed invoice");
        }


        product1.setStock(product1.getStock()+itemSale.getQuantity());
        salesService.updateCalculations(saleId);
        productRepository.save(product1);
        itemSaleRepository.delete(itemSale);
        salesRepository.save(currentSale);

    }

    public List<ItemSale> getItemSalesBySalesId(Integer salesId) {
        return itemSaleRepository.findItemSaleBySalesId(salesId);
    }

}
