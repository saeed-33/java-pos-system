package com.saed.javapossystem.di;

import android.content.Context;

import com.saed.javapossystem.data.repository.PosRepositoryImpl;
import com.saed.javapossystem.data.repository.ProductRepositoryImpl;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.PosRepository;
import com.saed.javapossystem.domain.repository.ProductRepository;
import com.saed.javapossystem.domain.usecase.AddProductToCartUseCase;
import com.saed.javapossystem.domain.usecase.ChangeQtyOfProductUseCase;
import com.saed.javapossystem.domain.usecase.ClearCartUseCase;
import com.saed.javapossystem.domain.usecase.GetAllCartItemsUseCase;
import com.saed.javapossystem.domain.usecase.GetCartTotalPriceUseCase;
import com.saed.javapossystem.domain.usecase.GetCartTotalQtyUseCase;
import com.saed.javapossystem.domain.usecase.RemoveProductFromCartUseCase;

import java.util.Collections;
import java.util.List;

public class AppContainer {
    // repository
    public final PosRepository cartRepository;
    public final ProductRepository productRepository;

    // Use cases
    public final AddProductToCartUseCase addProductToCartUseCase;
    public final GetAllCartItemsUseCase getAllCartItemsUseCase;
    public final RemoveProductFromCartUseCase removeProductFromCartUseCase;
    public final ClearCartUseCase clearCartUseCase;
    public final GetCartTotalPriceUseCase getCartTotalPriceUseCase;
    public final GetCartTotalQtyUseCase getCartTotalQtyUseCase;
    public final ChangeQtyOfProductUseCase changeQtyOfProductUseCase;


    // You can add more services here (e.g., Database, PrintService)
    public AppContainer(Context context) {
        cartRepository = new PosRepositoryImpl(context);
        productRepository = new ProductRepositoryImpl(context);
        addProductToCartUseCase = new AddProductToCartUseCase(cartRepository, productRepository);
        removeProductFromCartUseCase = new RemoveProductFromCartUseCase(cartRepository, productRepository);
        getAllCartItemsUseCase = new GetAllCartItemsUseCase(cartRepository);
        clearCartUseCase = new ClearCartUseCase(cartRepository);
        getCartTotalQtyUseCase = new GetCartTotalQtyUseCase(cartRepository);
        getCartTotalPriceUseCase = new GetCartTotalPriceUseCase(cartRepository);
        changeQtyOfProductUseCase = new ChangeQtyOfProductUseCase(cartRepository);

    }


}