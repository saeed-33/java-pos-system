package com.saed.javapossystem.domain.usecase;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.PosRepository;
import com.saed.javapossystem.domain.repository.ProductRepository;

public class AddProductToCartUseCase {
    final private PosRepository posRepository;
    final private ProductRepository productRepository;

    public AddProductToCartUseCase(PosRepository posRepository, ProductRepository productRepository) {
        this.posRepository = posRepository;
        this.productRepository = productRepository;
    }

    public void execute(String barcode) {

        Product product = productRepository.getProduct(barcode);
        if(product == null) {
            throw new RuntimeException("Product not found");
        }
        else{
            posRepository.addProduct(product);

        }

    }
}
