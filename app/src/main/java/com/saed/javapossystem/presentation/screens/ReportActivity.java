package com.saed.javapossystem.presentation.screens;

import static androidx.core.util.TypedValueCompat.dpToPx;
import static androidx.core.util.TypedValueCompat.pxToDp;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.PosApplication;
import com.saed.javapossystem.R;
import com.saed.javapossystem.di.AppContainer;
import com.saed.javapossystem.domain.entities.Bill;
import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.GetAllBillsUseCase;
import com.saed.javapossystem.domain.usecase.GetBillRowsUseCase;
import com.saed.javapossystem.domain.usecase.GetProductByIdUseCase;
import com.saed.javapossystem.presentation.resources.BillModel;
import com.saed.javapossystem.presentation.resources.ReportListAdapter;

import java.util.List;

public class ReportActivity extends AppCompatActivity {


    RecyclerView billList;

    GetAllBillsUseCase getAllBillsUseCase;
    List<Bill> bills;
    List<BillRow> billRows;
    AppContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        billList = findViewById(R.id.recyclerView);
        container = ((PosApplication) getApplication()).appContainer;
        getAllBillsUseCase= new GetAllBillsUseCase(container.billRepository);


        bills = getAllBillsUseCase.execute();
        billList.setLayoutManager(new LinearLayoutManager(this));

        ReportListAdapter productAdapter = new ReportListAdapter(BillModel.billModelsFromBills(bills),new GetBillRowsUseCase(container.billRowRepository), new GetProductByIdUseCase(container.productRepository),(isExpanded) -> {

        },ReportActivity.this.getResources().getDisplayMetrics());
        billList.setAdapter(productAdapter);


    }
}
