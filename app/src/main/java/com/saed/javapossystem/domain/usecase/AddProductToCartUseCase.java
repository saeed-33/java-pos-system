package com.saed.javapossystem.domain.usecase;

import android.content.Context;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.PosRepository;
import com.saed.javapossystem.domain.repository.ProductRepository;
import com.saed.javapossystem.R;

public class AddProductToCartUseCase {
    final private PosRepository posRepository;
    final private ProductRepository productRepository;

    public AddProductToCartUseCase(PosRepository posRepository, ProductRepository productRepository) {
        this.posRepository = posRepository;
        this.productRepository = productRepository;
    }

    public void execute(String barcode, Context context) {

        Product product = productRepository.getProduct(barcode);
        if(product == null) {
            throw new RuntimeException(context.getString(R.string.product_not_found));
        }
        else{
            posRepository.addProduct(product);

        }

    }
}
