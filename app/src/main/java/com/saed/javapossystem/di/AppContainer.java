package com.saed.javapossystem.di;

import android.content.Context;

import com.saed.javapossystem.data.repository.BillRepositoryImpl;
import com.saed.javapossystem.data.repository.BillRowRepositoryImpl;
import com.saed.javapossystem.data.repository.PosRepositoryImpl;
import com.saed.javapossystem.data.repository.ProductRepositoryImpl;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.BillRepository;
import com.saed.javapossystem.domain.repository.BillRowRepository;
import com.saed.javapossystem.domain.repository.PosRepository;
import com.saed.javapossystem.domain.repository.ProductRepository;
import com.saed.javapossystem.domain.usecase.AddProductToCartUseCase;
import com.saed.javapossystem.domain.usecase.ChangeQtyOfProductUseCase;
import com.saed.javapossystem.domain.usecase.ClearCartUseCase;
import com.saed.javapossystem.domain.usecase.GetAllCartItemsUseCase;
import com.saed.javapossystem.domain.usecase.GetAllProductsUseCase;
import com.saed.javapossystem.domain.usecase.GetCartTotalPriceUseCase;
import com.saed.javapossystem.domain.usecase.GetCartTotalQtyUseCase;
import com.saed.javapossystem.domain.usecase.PayBillUseCase;
import com.saed.javapossystem.domain.usecase.RemoveProductFromCartUseCase;
import com.saed.javapossystem.framework.db.BillDaoSQLite;
import com.saed.javapossystem.framework.db.BillRowDaoSQLite;
import com.saed.javapossystem.framework.db.DBHelper;
import com.saed.javapossystem.framework.db.ProductDaoSQLite;

import java.util.Collections;
import java.util.List;

public class AppContainer {
    //data
    private final DBHelper dbHelper;
    private final BillRowDaoSQLite billRowDaoSQLite;
    private final BillDaoSQLite billDaoSQLite;
    private final ProductDaoSQLite productDaoSQLite;

    // repository
    public final PosRepository cartRepository;
    public final ProductRepository productRepository;
    public final BillRepository billRepository;
    public final BillRowRepository billRowRepository;

    // Use cases
    public final AddProductToCartUseCase addProductToCartUseCase;
    public final GetAllCartItemsUseCase getAllCartItemsUseCase;
    public final RemoveProductFromCartUseCase removeProductFromCartUseCase;
    public final ClearCartUseCase clearCartUseCase;
    public final GetCartTotalPriceUseCase getCartTotalPriceUseCase;
    public final GetCartTotalQtyUseCase getCartTotalQtyUseCase;
    public final ChangeQtyOfProductUseCase changeQtyOfProductUseCase;
    public final PayBillUseCase payBillUseCase;
    public final GetAllProductsUseCase getAllProductsUseCase;


    public AppContainer(Context context) {
        //data
        dbHelper = new DBHelper(context);
        billRowDaoSQLite = new BillRowDaoSQLite(dbHelper.getWritableDatabase());
        billDaoSQLite = new BillDaoSQLite(dbHelper.getWritableDatabase());
        productDaoSQLite = new ProductDaoSQLite(dbHelper.getWritableDatabase());

        //repository
        cartRepository = new PosRepositoryImpl();
        billRepository = new BillRepositoryImpl(context, billDaoSQLite);
        billRowRepository = new BillRowRepositoryImpl(context, billRowDaoSQLite);
        productRepository = new ProductRepositoryImpl(context, productDaoSQLite);

        //usecases
        addProductToCartUseCase = new AddProductToCartUseCase(cartRepository, productRepository);
        removeProductFromCartUseCase = new RemoveProductFromCartUseCase(cartRepository, productRepository);
        getAllCartItemsUseCase = new GetAllCartItemsUseCase(cartRepository);
        clearCartUseCase = new ClearCartUseCase(cartRepository);
        getCartTotalQtyUseCase = new GetCartTotalQtyUseCase(cartRepository);
        getCartTotalPriceUseCase = new GetCartTotalPriceUseCase(cartRepository);
        changeQtyOfProductUseCase = new ChangeQtyOfProductUseCase(cartRepository);
        payBillUseCase = new PayBillUseCase(cartRepository, productRepository, billRepository, billRowRepository);
        getAllProductsUseCase = new GetAllProductsUseCase(productRepository);
    }


}