package com.saed.javapossystem.presentation.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.PosApplication;
import com.saed.javapossystem.R;
import com.saed.javapossystem.data.datasource.local.ProductLocalDataSource;
import com.saed.javapossystem.data.reposiotyImpl.ProductRepositoryImpl;
import com.saed.javapossystem.di.AppContainer;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.repository.ProductRepository;
import com.saed.javapossystem.domain.usecase.GetAllProductsUseCase;
import com.saed.javapossystem.framework.db.DBHelper;
import com.saed.javapossystem.framework.db.ProductDaoSQLite;
import com.saed.javapossystem.presentation.resources.PosListAdapter;
import com.saed.javapossystem.presentation.resources.ProductsListAdapter;

import java.util.List;


public class ProductListActivity extends AppCompatActivity {
    RecyclerView productRecyclerList;
    Button addProduct;
    ListView l;
    List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        productRecyclerList = findViewById(R.id.recyclerView);
        addProduct = findViewById(R.id.addProduct);
        DBHelper helper = new DBHelper(this);
        ProductDaoSQLite dao = new ProductDaoSQLite(helper.getWritableDatabase());
        ProductLocalDataSource localDataSource = new ProductLocalDataSource(dao);
        ProductRepository repository = new ProductRepositoryImpl(localDataSource);

        GetAllProductsUseCase getAllProductsUseCase = new GetAllProductsUseCase(repository);

        products = getAllProductsUseCase.execute();


        productRecyclerList = findViewById(R.id.recyclerView);
        productRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        AppContainer container;
        container = ((PosApplication) getApplication()).appContainer;
        Intent intent = new Intent(ProductListActivity.this, ProductActivity.class);
        ProductsListAdapter productAdapter = new ProductsListAdapter(products, (id) -> {
            intent.putExtra("ProductId", id);
            startActivity(intent);
        });
        productRecyclerList.setAdapter(productAdapter);
        addProduct.setOnClickListener((v) -> {
            Intent addProductIntent = new Intent(ProductListActivity.this, ProductActivity.class);
            addProductIntent.putExtra("ProductId", -1);
            startActivity(intent);
        });


    }

    @Override
    protected void onStart(){
        AppContainer container = ((PosApplication) getApplication()).appContainer;
        GetAllProductsUseCase getAllProductsUseCase = new GetAllProductsUseCase(container.productRepository);
        products = getAllProductsUseCase.execute();

        Intent intent = new Intent(ProductListActivity.this, ProductActivity.class);

        ProductsListAdapter productAdapter = new ProductsListAdapter(products, (id) -> {
            intent.putExtra("ProductId", id);
            startActivity(intent);
        });
        productRecyclerList.setAdapter(productAdapter);
        super.onStart();
    }


}
