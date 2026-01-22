package com.saed.javapossystem.presentation.screens;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.PosApplication;
import com.saed.javapossystem.R;
import com.saed.javapossystem.di.AppContainer;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.AddProductToCartUseCase;
import com.saed.javapossystem.domain.usecase.GetAllProductsUseCase;
import com.saed.javapossystem.presentation.resources.SearchProductListAdapter;

import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    private GetAllProductsUseCase getAllProductsUseCase;
    private AddProductToCartUseCase addProductToCartUseCase;
    private AppContainer container;
    private SearchProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        container = ((PosApplication) getApplication()).appContainer;
        getAllProductsUseCase = container.getAllProductsUseCase;
        addProductToCartUseCase = container.addProductToCartUseCase;
        initCloseButton();
        initList();
        initSearchBar();
    }

    private void initCloseButton() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void initList() {
        View header = findViewById(R.id.headerLayout);
        header.setBackgroundColor(Color.parseColor(getString(R.string.app_bar_color)));
        ((TextView) header.findViewById(R.id.txtName)).setText(R.string.nameTxt);
        ((TextView) header.findViewById(R.id.txtBarcode)).setText(R.string.barcodeTxt);
        ((TextView) header.findViewById(R.id.txtPrice)).setText(R.string.priceTxt);
        ((TextView) header.findViewById(R.id.txtQty)).setText(R.string.qty);

        RecyclerView rv = findViewById(R.id.searchRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.indented_divider)));
        rv.addItemDecoration(decoration);
        List<Product> products = getAllProductsUseCase.execute(); // Your data source
        adapter = new SearchProductListAdapter(products);
        rv.setAdapter(adapter);
        adapter.setOnProductClickListener(product -> {
            addProductToCartUseCase.execute(product.getBarcode(),this);
            finish();
        });

    }

    private void initSearchBar() {
        EditText searchBox = findViewById(R.id.searchEditText);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int x, int y, int z) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}