package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.ItemSales;
import com.example.final_project.Repository.ItemSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemSalesService {

    private final ItemSalesRepository itemSalesRepository;

    public void addItemSales(ItemSales itemSales){
        itemSalesRepository.save(itemSales);
    }

    public List getAllItemSales(){
        return itemSalesRepository.findAll();
    }

    public List getItemSales(Integer id){
        return itemSalesRepository.findItemSalesById(id);
    }

    public void updateItemSales(ItemSales itemSales,Integer id){
        ItemSales oldItemSales= (ItemSales) itemSalesRepository.findItemSalesById(id);
        if (oldItemSales==null){
            throw new ApiException("Item Sales not found");
        }

        oldItemSales.setProductName(itemSales.getProductName());
        oldItemSales.setQuantity(itemSales.getQuantity());
        oldItemSales.setPrice(itemSales.getPrice());
        oldItemSales.setTotalPrice(itemSales.getTotalPrice());
        oldItemSales.setInvoiceNumber(itemSales.getInvoiceNumber());

        itemSalesRepository.save(oldItemSales);
    }

    public void deleteItemSales(Integer id){
        ItemSales oldItemSales= (ItemSales) itemSalesRepository.findItemSalesById(id);
        if (oldItemSales==null){
            throw new ApiException("Item Sales not found");
        }
        itemSalesRepository.delete(oldItemSales);
    }


}
