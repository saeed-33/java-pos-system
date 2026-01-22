package com.saed.javapossystem.domain.usecase;

import android.content.Context;

import com.saed.javapossystem.R;
import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.BillRepository;
import com.saed.javapossystem.domain.repository.BillRowRepository;
import com.saed.javapossystem.domain.repository.PosRepository;
import com.saed.javapossystem.domain.repository.ProductRepository;

public class PayBillUseCase {
    private final PosRepository posRepository;
    private final ProductRepository productRepository;
    private final BillRepository billRepository;
    private final BillRowRepository billRowRepository;

    public PayBillUseCase(PosRepository posRepository, ProductRepository productRepository, BillRepository billRepository, BillRowRepository billRowRepository) {
        this.posRepository = posRepository;
        this.productRepository = productRepository;
        this.billRepository = billRepository;
        this.billRowRepository = billRowRepository;
    }

    public void execute(Context context) {
        try {
            Bill bill = new Bill(posRepository.getTotalPrice());
            int billId = billRepository.createBill(bill);
            for (Product p : posRepository.getAllProducts()) {
                BillRow row = new BillRow(billId, p.getId(), p.getSelldQuantity(), p.getPrice());
                billRowRepository.createBillRow(row);
                productRepository.updateProduct(p);
            }
            posRepository.clearCart();
        } catch (Exception e) {
            throw new RuntimeException(context.getString(R.string.error_while_pay_the_bill));
        }

    }
}
