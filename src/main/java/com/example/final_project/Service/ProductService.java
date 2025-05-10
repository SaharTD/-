package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Accountant;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Product;
import com.example.final_project.Model.Sales;
import com.example.final_project.Notification.NotificationService;
import com.example.final_project.Repository.AccountantRepository;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.ProductRepository;
import com.example.final_project.Repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final AccountantRepository accountantRepository;
    private final NotificationService notificationService;
    private final SalesRepository salesRepository;

    public List<Product> getAllProduct() {
        notificationService.sendEmail("aa.ll.ii.pp5@gmail.com", "test", "dear customer");
        return productRepository.findAll();
    }

    public void addProduct(Integer branchId, Product product) {
        Branch branch = branchRepository.findBranchesById(branchId);
        if (branch == null) {
            throw new ApiException("branch not found");
        }

/// if business is not active
        if (!branch.getBusiness().getIsActive()) {
            throw new ApiException("The business that this branch belong to is not activated");

        }

//        Product newProduct = new Product();
//        newProduct.setBranch(branch);
//        branch.getProducts().add(newProduct);


        product.setBranch(branch);
        branchRepository.save(branch);
        productRepository.save(product);
    }


    public void updateProduct(Integer branchId, Integer productId, Product product) {
        Branch branch = branchRepository.findBranchesById(branchId);
        Product oldProduct = productRepository.findProductById(productId);
        if (branch == null)
            throw new ApiException("branch not found");
        if (oldProduct == null)
            throw new ApiException("product not found");
        oldProduct.setName(product.getName());
        oldProduct.setStock(product.getStock());
        oldProduct.setPrice(product.getPrice());

        productRepository.save(oldProduct);
    }

    public void deleteProduct(Integer branchId, Integer productId) {
        Branch branch = branchRepository.findBranchesById(branchId);
        Product product = productRepository.findProductById(productId);
        if (branch == null)
            throw new ApiException("branch not found");
        if (product == null)
            throw new ApiException("product not found");

        branch.getProducts().remove(product);
        branchRepository.save(branch);
        productRepository.delete(product);
    }


    public Product getProductsByBarcode(String barcode) {
        Product products = productRepository.findProductByBarcode(barcode);
        if (products==null) {
            throw new RuntimeException("No products found with barcode: " + barcode);
        }
        return products;
    }



    //by accountant
    public void addProductToBranch(Integer accountantId, Integer branchId, Product product) {
        Accountant accountant = accountantRepository.getReferenceById(accountantId);
        if (accountant == null) throw new ApiException("Accountant not found");

        Branch branch = branchRepository.getReferenceById(branchId);
        if (branch == null) throw new ApiException("Branch not found");

        Product existingProduct = productRepository.findByNameAndBranchId(product.getName(), branchId);
        if (existingProduct != null)
            throw new ApiException("The product is already exist you can edit it");

        product.setBranch(branch);
        productRepository.save(product);
    }



}
