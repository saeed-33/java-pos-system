package com.saed.javapossystem.presentation.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saed.javapossystem.PosApplication;
import com.saed.javapossystem.R;
import com.saed.javapossystem.di.AppContainer;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.CreateProductUseCase;
import com.saed.javapossystem.domain.usecase.GetProductByIdUseCase;
import com.saed.javapossystem.domain.usecase.UpdateProductUseCase;

import java.util.concurrent.atomic.AtomicReference;

public class ProductActivity extends AppCompatActivity {
    GetProductByIdUseCase getProductByIdUseCase ;
    CreateProductUseCase createProductUseCase;
    UpdateProductUseCase updateProductUseCase;
    EditText name,quantity,price,barcode;
    Button addProduct;
    TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int productId = intent.getIntExtra("ProductId",-1);
        setContentView(R.layout.activity_product);
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        barcode = findViewById(R.id.barcode);
        addProduct = findViewById(R.id.addProduct);
        AppContainer container = ((PosApplication) getApplication()).appContainer;

        getProductByIdUseCase = new GetProductByIdUseCase(container.productRepository);
        createProductUseCase = new CreateProductUseCase(container.productRepository);
        updateProductUseCase = new UpdateProductUseCase(container.productRepository);
        AtomicReference<Product> product = new AtomicReference<>();
        if(productId ==-1){
            product.set(new Product(-1, "", "", -1, -1.0));
            addProduct.setText(R.string.add_product);
        }
        else {
            product.set(getProductByIdUseCase.execute(productId));
            id.setText(String.valueOf(product.get().getId()));
            name.setText(product.get().getName());
            quantity.setText(String.valueOf(product.get().getQty()));
            price.setText(String.valueOf(product.get().getPrice()));
            barcode.setText(product.get().getBarcode());
            addProduct.setText(R.string.edit_product);
        }

        addProduct.setOnClickListener((v)->{
            String quantityStr = quantity.getText().toString().trim();
            String nameStr = name.getText().toString().trim();
            String priceStr = price.getText().toString().trim();
            String barcodeStr = barcode.getText().toString().trim();
            int quantityInt =0;
            double priceDouble =0;
            if(quantityStr.isEmpty() || nameStr.isEmpty() || priceStr.isEmpty() || barcodeStr.isEmpty()){
                Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                quantityInt = Integer.parseInt(quantityStr);
                priceDouble = Double.parseDouble(priceStr);
                if(quantityInt <0 || priceDouble <0){
                    throw new NumberFormatException();
                }
            }catch (NumberFormatException e){
                Toast.makeText(this, "please enter valid numbers in quantity and price", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                product.set(new Product(productId, barcodeStr, nameStr, quantityInt, priceDouble));
                if(productId ==-1){
                    createProductUseCase.execute(product.get());
                    Toast.makeText(this, "product added successfully", Toast.LENGTH_SHORT).show();
                }else{
                    updateProductUseCase.execute(product.get());
                    Toast.makeText(this, "product edited successfully", Toast.LENGTH_SHORT).show();

                }


            }catch (Exception e){
                System.out.println(e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
            if(productId==-1){
                name.setText("");
                quantity.setText("");
                price.setText("");
                barcode.setText("");
                id.setText("");
            }
        });

    }
}
