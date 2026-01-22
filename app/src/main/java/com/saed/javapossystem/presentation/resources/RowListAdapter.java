package com.saed.javapossystem.presentation.resources;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.R;
import com.saed.javapossystem.domain.entities.BillRow;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.GetCartTotalPriceUseCase;
import com.saed.javapossystem.domain.usecase.GetCartTotalQtyUseCase;
import com.saed.javapossystem.domain.usecase.GetProductByIdUseCase;

import java.util.List;
import java.util.Locale;

public class RowListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private final List<BillRow> rowList;
   final private GetProductByIdUseCase getProductUseCase;


   final private double totalP;


    // Interface for selection




    public RowListAdapter(List<BillRow> rowList, GetProductByIdUseCase getProductUseCase, double totalP) {
        this.rowList = rowList;
        this.getProductUseCase = getProductUseCase;
        this.totalP = totalP;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        if (position == rowList.size() + 1) return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(inflater.inflate(R.layout.item_pos_header, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(inflater.inflate(R.layout.item_pos_footer, parent, false));
        } else {
            return new ItemViewHolder(inflater.inflate(R.layout.item_pos_row, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            int listIndex = position - 1; // Map adapter position to list index
            BillRow row = rowList.get(listIndex);
            ItemViewHolder h = (ItemViewHolder) holder;

            String name = getProductUseCase.execute(row.getProduct_id()).getName();
            h.name.setText(name);
            h.qty.setText(String.valueOf(row.getQuantity()));
            h.price.setText(String.format(Locale.getDefault(),"%.2f", row.getPrice()));
            h.total.setText(String.format(Locale.getDefault(),"%.2f", row.getQuantity()*row.getPrice()));


        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder f = (FooterViewHolder) holder;

            f.txtQty.setText("");
            f.txtPrice.setText("Total Price : " + String.format(Locale.getDefault(),"%.2f", totalP));
        }
    }

    @Override
    public int getItemCount() {
        return rowList.size() + 2;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name, qty, price, total;

        ItemViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtName);
            qty = v.findViewById(R.id.txtQty);
            price = v.findViewById(R.id.txtPrice);
            total = v.findViewById(R.id.txtTotal);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View v) {
            super(v);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice, txtQty;

        FooterViewHolder(View v) {
            super(v);
            txtPrice = v.findViewById(R.id.footerTotalPrice);
            txtQty = v.findViewById(R.id.footerTotalQty);
        }
    }
}