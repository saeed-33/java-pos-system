package com.saed.javapossystem.screens;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.R;
import com.saed.javapossystem.entities.Product;
import com.saed.javapossystem.resources.ButtonModel;
import com.saed.javapossystem.resources.PosButtonAdapter;
import com.saed.javapossystem.resources.PosListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PosActivity extends AppCompatActivity {
    RecyclerView productRecyclerList, buttonRecyclerGrid;
    PosListAdapter productAdapter;
    PosButtonAdapter buttonAdapter;
    EditText barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        barcode = findViewById(R.id.barcodeInput);
        initListView();
        initProductList();
    }

    private void initListView() {
        productRecyclerList = findViewById(R.id.recyclerView);
        productRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        List<Product> products = new ArrayList<>();
        productAdapter = new PosListAdapter(products);
        productRecyclerList.setAdapter(productAdapter);
    }

    private void initProductList() {
        buttonRecyclerGrid = findViewById(R.id.buttonGridRecycler); // Add this ID to your XML
        buttonRecyclerGrid.setLayoutManager(new GridLayoutManager(this, 3));
        List<ButtonModel> buttonList = new ArrayList<>();
        buttonList.add(initPayButton());
        buttonList.add(initRemoveButton());
        buttonList.add(initEnterButton());
        buttonList.add(initChangeQtyButton());
        buttonList.add(initCancelAllButton());
        buttonList.add(initSearchButton());

        buttonAdapter = new PosButtonAdapter(buttonList);
        buttonRecyclerGrid.setAdapter(buttonAdapter);
    }

    private ButtonModel initPayButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {

        };
        return new ButtonModel(getString(R.string.pay_button), () -> {
        });
    }

    private ButtonModel initEnterButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {

        };
        return new ButtonModel(getString(R.string.enter_button), () -> {
        });
    }

    private ButtonModel initChangeQtyButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {

        };
        return new ButtonModel(getString(R.string.change_qty_button), () -> {
        });
    }

    private ButtonModel initRemoveButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {

        };
        return new ButtonModel(getString(R.string.remove_button), () -> {
        });
    }

    private ButtonModel initCancelAllButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {

        };
        return new ButtonModel(getString(R.string.cancel_all_button), () -> {
        });
    }

    private ButtonModel initSearchButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {

        };
        return new ButtonModel(getString(R.string.search_button), () -> {
        });
    }


}