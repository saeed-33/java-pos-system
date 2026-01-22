package com.saed.javapossystem.domain.usecase;

import android.content.Context;
import com.saed.javapossystem.R;

import com.saed.javapossystem.domain.repository.PosRepository;

public class ChangeQtyOfProductUseCase {
    private final PosRepository repository;

    public ChangeQtyOfProductUseCase(PosRepository repository) {
        this.repository = repository;
    }

    public void execute(int id, int newQty, Context context) {
        boolean success = repository.changeQtyOfProduct(id, newQty);
        if (!success) {
            throw new RuntimeException(context.getString(R.string.product_not_found));
        }
    }
}
