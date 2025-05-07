package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Product;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public void addProduct(Integer branchId, Product product){
        Branch branch = branchRepository.findBranchesById(branchId);
        if (branch==null)
            throw new ApiException("branch not found");
        Product newProduct = new Product();
        newProduct.setBranch(branch);
        branch.getProducts().add(newProduct);
        branchRepository.save(branch);
        productRepository.save(product);
    }

    public void updateProduct(Integer branchId, Integer productId, Product product){
        Branch branch = branchRepository.findBranchesById(branchId);
        Product oldProduct = productRepository.findProductById(productId);
        if (branch==null)
            throw new ApiException("branch not found");
        if (oldProduct==null)
            throw new ApiException("product not found");
        oldProduct.setName(product.getName());
        oldProduct.setStock(product.getStock());
        oldProduct.setPrice(product.getPrice());

        productRepository.save(oldProduct);
    }

    public void deleteProduct(Integer branchId,Integer productId){
        Branch branch = branchRepository.findBranchesById(branchId);
        Product product = productRepository.findProductById(productId);
        if (branch==null)
            throw new ApiException("branch not found");
        if (product==null)
            throw new ApiException("product not found");

        branch.getProducts().remove(product);
        branchRepository.save(branch);
        productRepository.delete(product);
    }


}
