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

import java.util.List;

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

}
