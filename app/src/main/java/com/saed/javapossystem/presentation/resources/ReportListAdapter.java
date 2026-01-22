package com.saed.javapossystem.presentation.resources;

import static androidx.core.util.TypedValueCompat.dpToPx;
import static androidx.core.util.TypedValueCompat.pxToDp;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.R;
import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.GetBillRowsUseCase;
import com.saed.javapossystem.domain.usecase.GetProductByIdUseCase;
import com.saed.javapossystem.presentation.screens.ReportActivity;

import java.util.List;
import java.util.Locale;

public class ReportListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final List<BillModel> billModelList;

    private final GetBillRowsUseCase getBillRowsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    // Interface for selection
    public interface OnProductSelectedListener {
        void onProductSelected(Product billModel);
    }

     Command<Boolean> command;
    final DisplayMetrics displayMetrics;

    public ReportListAdapter(List<BillModel> billModelList, GetBillRowsUseCase getBillRowsUseCase, GetProductByIdUseCase getProductByIdUseCase, Command<Boolean> command, DisplayMetrics displayMetrics) {
        this.billModelList = billModelList;
        this.getBillRowsUseCase = getBillRowsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.command = command;
        this.displayMetrics = displayMetrics;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new ItemViewHolder(inflater.inflate(R.layout.item_bill_row, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            // Map adapter position to list index
            if(billModelList.isEmpty()){
                return;
            }
            BillModel billModel = billModelList.get(position);
            ItemViewHolder h = (ItemViewHolder) holder;

            h.date.setText(billModel.getDate());
            System.out.println(billModel.getDate());
            h.totalPrice.setText(String.format(Locale.getDefault(),"%.2f", billModel.getTotalPrice()));

            h.itemView.setBackgroundColor(Color.WHITE);

            List<BillRow> billRows = getBillRowsUseCase.execute(billModel.getId());

            h.recyclerView.setAdapter(new RowListAdapter(billRows, getProductByIdUseCase,billModel.getTotalPrice()));

            h.itemView.setOnClickListener(v -> {
                billModel.toggleExpanded();
                ViewGroup.LayoutParams params = h.layout.getLayoutParams();

// Change the height parameter to WRAP_CONTENT
                params.height = billModel.isExpanded? ViewGroup.LayoutParams.WRAP_CONTENT :(int)dpToPx(50,displayMetrics);

// Apply the modified parameters back to the view
                h.layout.setLayoutParams(params);
            });

        }
    }

    @Override
    public int getItemCount() {
        return billModelList.size() ;
    }


    // ViewHolders
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView date , totalPrice;
        RecyclerView recyclerView;
LinearLayout layout;

        ItemViewHolder(View v) {
            super(v);
            totalPrice = v.findViewById(R.id.txtTotalPrice);
            date = v.findViewById(R.id.txtDate);
            recyclerView = v.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

            layout = v.findViewById(R.id.layout);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View v) {
            super(v);
        }
    }

}