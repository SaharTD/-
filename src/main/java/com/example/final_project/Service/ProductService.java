package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.ProductDTO;
import com.example.final_project.Model.*;
import com.example.final_project.Notification.NotificationService;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final AccountantRepository accountantRepository;
    private final BusinessRepository businessRepository;




    public void addProductToBranch(Integer accountantId, Integer branchId, ProductDTO product) {

        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (accountant == null) throw new ApiException("Accountant not found");

        Branch branch = branchRepository.findBranchesById(branchId);
        if (branch == null) throw new ApiException("Branch not found");

        Product existingProduct = productRepository.findByNameAndBranchId(product.getName(), branchId);
        if (existingProduct != null)
            throw new ApiException("The product is already exist you can edit it");



        Product product1=new Product();
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setStock(product.getStock());
        product1.setBarcode(product.getBarcode());



        product1.setBranch(branch);
        productRepository.save(product1);
    }







    public List<Product> getAllProductForBusiness(Integer accountantId) {
        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (accountant == null) throw new ApiException("Accountant not found");
        Business b= businessRepository.findBusinessById(accountant.getBusiness().getId());

        List<Product>productList=productRepository.findProductByBusiness(b.getId());
        if(productList.isEmpty()){
            throw new ApiException("no products found ");
        }
        return productList;
    }


    public void updateProduct(Integer accountantId,Integer branchId, Integer productId, Product product) {

        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (accountant == null) throw new ApiException("Accountant not found");

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

    public void deleteProduct(Integer accountantId,Integer branchId, Integer productId) {
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



    public Product getProductsByBarcode(Integer accountantId,String barcode) {

        Accountant accountant = accountantRepository.findAccountantById(accountantId);
        if (accountant == null) throw new ApiException("Accountant not found");

        Product products = productRepository.findProductByBarcode(barcode);
        if (products==null) {
            throw new RuntimeException("No products found with barcode: " + barcode);
        }
        return products;
    }






}
