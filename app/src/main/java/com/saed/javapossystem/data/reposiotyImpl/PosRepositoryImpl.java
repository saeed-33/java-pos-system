package com.saed.javapossystem.data.reposiotyImpl;

import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.PosRepository;

import java.util.ArrayList;
import java.util.List;

public class PosRepositoryImpl implements PosRepository {

    // 1. THE LIST: This is the primary data source for your modern view
    private final List<Product> cartList = new ArrayList<>();


    public PosRepositoryImpl() {
    }


    @Override
    public void addProduct(Product product) {
        if (cartList.contains(product)) {
            int index = cartList.indexOf(product);
            Product existingProduct = cartList.get(index);
            existingProduct.setQty(existingProduct.getSelldQuantity() + 1);
            cartList.set(index, existingProduct);
        } else {
            cartList.add(product);
        }
    }

    @Override
    public void removeProduct(Product product) {
        System.out.println(product);
        if (cartList.contains(product)) {
            int index = cartList.indexOf(product);
            Product existingProduct = cartList.get(index);
            if (existingProduct.isRefunded()) {
                cartList.set(index, existingProduct.copyWith(existingProduct.getSelldQuantity() - 1));
            } else {
                cartList.remove(product);
            }
        } else {
            cartList.add(product.copyWith(-1));

        }
    }

    @Override
    public List<Product> getAllProducts() {
        return cartList;
    }

    @Override
    public void clearCart() {
        this.cartList.clear();
    }

    @Override
    public double getTotalPrice() {
        double total = 0;
        for (Product p : cartList) {
            total += p.getTotalPrice();
        }
        return total;
    }

    @Override
    public int getTotalQty() {
        int total = 0;
        for (Product p : cartList) {
            total += Math.abs(p.getSelldQuantity());
        }
        return total;
    }

    @Override
    public boolean changeQtyOfProduct(int id, int newQty) {
        Product product = cartList.stream().filter((p) -> p.getId() == id).findFirst().orElse(null);
        if (product == null) return false;
        int index = cartList.indexOf(product);
        Product existingProduct = cartList.get(index);
        existingProduct.setQty(newQty);
        cartList.set(index, existingProduct);
        return true;
    }

}