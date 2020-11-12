package com.crud.product.services;

import com.crud.product.entities.Product;
import com.crud.product.repositories.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getProducts() {
        return productsRepository.findAll();
    }

    public Product getProduct(Long id) {
        Optional<Product> productOptional = productsRepository.findById(id);
        return productOptional.get();
    }

    public Product guardar(Product product) {
        return productsRepository.save(product);
    }

    public void delete(Long id) {
        productsRepository.deleteById(id);
    }
}
