package com.saed.javapossystem.presentation.screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.TorchState;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
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

import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PosActivity extends AppCompatActivity {
    RecyclerView productRecyclerList, buttonRecyclerGrid;
    PosListAdapter productAdapter;
    PosButtonAdapter buttonAdapter;
    EditText barcode;
    AppContainer container;
    private Product selectedProduct = null;
    ImageButton btnClear;
    private Camera cameraVal;
    boolean isFlashOn = false;
    private boolean isCameraExpanded = false;
    private long lastScanTime = 0;
    private String lastScannedBarcode = "";


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
        initUseCases();
        initListView();
        initBarcode();
        initProductList();
        ImageButton button = findViewById(R.id.btnFlash);
        button.setOnClickListener(v -> {
            try {
                toggleFlash();
            } catch (ExecutionException | InterruptedException ignored) {
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            // Request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 101);
        }
        PreviewView previewView = findViewById(R.id.previewView);
        previewView.setOnClickListener(v -> toggleCameraSize());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (productAdapter != null) productAdapter.updateData(getAllCartItemsUseCase.execute());
    }

    private void initBarcode() {
        barcode = findViewById(R.id.barcodeInput);
        btnClear = findViewById(R.id.btnClear);
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
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            Toast.makeText(this, "Camera permission required for scanning", Toast.LENGTH_SHORT).show();
        }
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
            payBillUseCase.execute(this);
            productAdapter.updateData(getAllCartItemsUseCase.execute());
        };
        return new ButtonModel(getString(R.string.pay_button), onPress);
    }

    private ButtonModel initEnterButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {
            String barcodeText = barcode.getText().toString();
            addProductToCartUseCase.execute(barcodeText, this);
            barcode.setText("");
            productAdapter.updateData(getAllCartItemsUseCase.execute());
        };
        return new ButtonModel(getString(R.string.enter_button), onPress);
    }

    private ButtonModel initChangeQtyButton() {
        ButtonModel.OnButtonClickListener onPress = () -> {
            int newQty = Integer.parseInt(barcode.getText().toString());
            changeQtyOfProductUseCase.execute(selectedProduct.getId(), newQty, this);
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
            removeProductFromCartUseCase.execute(barcodeValue, this);
            barcode.setText("");
            selectedProduct = null;
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
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        };
        return new ButtonModel(getString(R.string.search_button), onPress);
    }

    private void startCamera() {
        System.out.println("camera started");
        PreviewView previewView = findViewById(R.id.previewView); // Get reference

        ListenableFuture<ProcessCameraProvider> camera =
                ProcessCameraProvider.getInstance(this);

        camera.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = camera.get();

                // PREVIEW: This is what shows the camera on screen
                Preview preview = new Preview.Builder().build();

                // This is the line that was failing:
                preview.setSurfaceProvider(previewView.getSurfaceProvider());


                // SELECTOR: Use the back camera
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                // ANALYSIS: This is the "Background" scanner logic
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();


                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), image -> {
                    scanBarcodes(image);
                });


                // Unbind everything and bind to Lifecycle
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
                cameraVal = cameraProvider.bindToLifecycle(
                        this,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageAnalysis
                );

            } catch (ExecutionException | InterruptedException e) {
                Log.e("POS_CAMERA", "Use case binding failed", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void scanBarcodes(ImageProxy imageProxy) {
        Image mediaImage = imageProxy.getImage();

        if (mediaImage != null) {

            InputImage image = InputImage.fromMediaImage(mediaImage,
                    imageProxy.getImageInfo().getRotationDegrees());

            BarcodeScanner scanner = BarcodeScanning.getClient();

            scanner.process(image)
                    .addOnSuccessListener(barcodes -> {

                        for (Barcode barcode : barcodes) {
                            String rawValue = barcode.getRawValue();

                            runOnUiThread(() -> {

                                if (rawValue != null && !rawValue.isEmpty()) {
                                    try {
                                        if (System.currentTimeMillis() - lastScanTime < 1000 && lastScannedBarcode.equals(rawValue)) {
                                            return;
                                        } else {
                                            addProductToCartUseCase.execute(rawValue, this);// Your POS logic

                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(this, getString(R.string.product_not_found), Toast.LENGTH_SHORT).show();
                                    }
                                }


                            });
                        }
                    })
                    .addOnCompleteListener(task -> {
                        if (!task.getResult().isEmpty()) {
                            System.out.println("image captured" + task.getResult());
                            lastScanTime = System.currentTimeMillis();
                            lastScannedBarcode = task.getResult().toString();
                            productAdapter.updateData(getAllCartItemsUseCase.execute());
                        }
                        imageProxy.close();

                    });
        }
    }

    private void toggleFlash() throws ExecutionException, InterruptedException {
        ImageButton btnFlash = findViewById(R.id.btnFlash);

        if (cameraVal != null) {
            // Check if the device even has a flash unit
            if (cameraVal.getCameraInfo().hasFlashUnit()) {
                cameraVal.getCameraInfo().getTorchState().observe(this, state -> {
                    if (state == TorchState.OFF) {
                        isFlashOn = false;
                        // update icon to gray
                    } else {
                        isFlashOn = true;
                        // update icon to yellow
                    }
                });
                isFlashOn = !isFlashOn;
                cameraVal.getCameraControl().enableTorch(isFlashOn);

                // Optional: Update icon color to show it's active
                btnFlash.setColorFilter(isFlashOn ? Color.YELLOW : Color.parseColor("#D1D1D1"));
            } else {
                Toast.makeText(this, "Flash not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 1. Helper method to convert DP to Pixels (needed for LayoutParams)
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    // 2. The Toggle Logic
    private void toggleCameraSize() {
        PreviewView previewView = findViewById(R.id.previewView);
        ViewGroup container = findViewById(R.id.cameraContainer);

        // Modern Animation: This makes the grow/shrink smooth
        TransitionManager.beginDelayedTransition(container);

        ViewGroup.LayoutParams params = previewView.getLayoutParams();

        if (!isCameraExpanded) {
            // GO BIG
            params.width = dpToPx(140);
            params.height = dpToPx(60); // Large height for scanning
            isCameraExpanded = true;
        } else {
            // GO SMALL
            params.width = dpToPx(10);
            params.height = dpToPx(10);
            isCameraExpanded = false;
        }
        previewView.setLayoutParams(params);
    }

}