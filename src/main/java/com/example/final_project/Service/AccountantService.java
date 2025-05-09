package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Product;
import com.example.final_project.Repository.AccountantRepository;
import com.example.final_project.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountantService {

    private final AccountantRepository accountantRepository;
    private final ProductRepository productRepository;

    // Endpoint 35
    public void restockProduct(Integer accountantId, Integer productId, Integer amount){
        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        Product product = productRepository.findProductById(productId);
        if (accountant==null)
            throw new ApiException("accountant not found");
        if (product==null)
            throw new ApiException("product not found");
        product.setStock(product.getStock()+amount);

        productRepository.save(product);
    }

}
