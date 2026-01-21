package com.saed.javapossystem.presentation.screens;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.PosApplication;
import com.saed.javapossystem.R;
import com.saed.javapossystem.di.AppContainer;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.AddProductToCartUseCase;
import com.saed.javapossystem.domain.usecase.ChangeQtyOfProductUseCase;
import com.saed.javapossystem.domain.usecase.ClearCartUseCase;
import com.saed.javapossystem.domain.usecase.GetAllCartItemsUseCase;
import com.saed.javapossystem.domain.usecase.PayBillUseCase;
import com.saed.javapossystem.domain.usecase.RemoveProductFromCartUseCase;
import com.saed.javapossystem.presentation.resources.ButtonModel;
import com.saed.javapossystem.presentation.resources.PosButtonAdapter;
import com.saed.javapossystem.presentation.resources.PosListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PosActivity extends AppCompatActivity {
    RecyclerView productRecyclerList, buttonRecyclerGrid;
    PosListAdapter productAdapter;
    PosButtonAdapter buttonAdapter;
    EditText barcode;
    AppContainer container;
    private Product selectedProduct = null;
    ImageButton btnClear ;

    //usecases
    private AddProductToCartUseCase addProductToCartUseCase;
    private RemoveProductFromCartUseCase removeProductFromCartUseCase;
    private GetAllCartItemsUseCase getAllCartItemsUseCase;
    private ClearCartUseCase clearCartUseCase;
    private ChangeQtyOfProductUseCase changeQtyOfProductUseCase;
    private PayBillUseCase payBillUseCase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        container = ((PosApplication) getApplication()).appContainer;
        initBarcode();
        initListView();
        initProductList();
        initUseCases();

    }

    private void initBarcode(){
        barcode = findViewById(R.id.barcodeInput);
        btnClear= findViewById(R.id.btnClear);
        btnClear.setOnClickListener(v -> {
            barcode.setText("");
            barcode.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(barcode, InputMethodManager.SHOW_IMPLICIT);
        });
        barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btnClear.setVisibility(View.VISIBLE);
                } else {
                    btnClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void initListView() {
        productRecyclerList = findViewById(R.id.recyclerView);
        productRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new PosListAdapter(container.getAllCartItemsUseCase.execute(), container.getCartTotalQtyUseCase, container.getCartTotalPriceUseCase);
        productRecyclerList.setAdapter(productAdapter);
        productAdapter.setOnProductSelectedListener(product -> {
            this.selectedProduct = product;
        });
    }

    private void initUseCases() {
        addProductToCartUseCase = container.addProductToCartUseCase;
        removeProductFromCartUseCase = container.removeProductFromCartUseCase;
        clearCartUseCase = container.clearCartUseCase;
        getAllCartItemsUseCase = container.getAllCartItemsUseCase;
        changeQtyOfProductUseCase = container.changeQtyOfProductUseCase;
        payBillUseCase = container.payBillUseCase;

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
        payBillUseCase.execute();
        productAdapter.updateData(getAllCartItemsUseCase.execute());
        };
        return new ButtonModel(getString(R.string.pay_button), onPress);
    }

    private ButtonModel initEnterButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {
            String barcodeText = barcode.getText().toString();
            addProductToCartUseCase.execute(barcodeText);
            barcode.setText("");
            productAdapter.updateData(getAllCartItemsUseCase.execute());
        };
        return new ButtonModel(getString(R.string.enter_button), onPress);
    }

    private ButtonModel initChangeQtyButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {
            int newQty = Integer.parseInt(barcode.getText().toString());
            changeQtyOfProductUseCase.execute(selectedProduct.getId(), newQty);
            productAdapter.updateData(getAllCartItemsUseCase.execute());
            barcode.setText("");
        };
        return new ButtonModel(getString(R.string.change_qty_button), onPress);
    }

    private ButtonModel initRemoveButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {
            String barcodeValue = "";
            if (selectedProduct != null) {
                barcodeValue = selectedProduct.getBarcode();
            } else {
                barcodeValue = barcode.getText().toString();
            }
            removeProductFromCartUseCase.execute(barcodeValue);
            productAdapter.updateData(getAllCartItemsUseCase.execute());
        };
        return new ButtonModel(getString(R.string.remove_button), onPress);
    }

    private ButtonModel initCancelAllButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {
            clearCartUseCase.execute();
            productAdapter.updateData(getAllCartItemsUseCase.execute());
        };
        return new ButtonModel(getString(R.string.cancel_all_button), onPress);
    }

    private ButtonModel initSearchButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {

        };
        return new ButtonModel(getString(R.string.search_button), () -> {
        });
    }
}