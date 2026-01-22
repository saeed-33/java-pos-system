package com.saed.javapossystem.presentation.resources;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.R;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.GetCartTotalPriceUseCase;
import com.saed.javapossystem.domain.usecase.GetCartTotalQtyUseCase;
import com.saed.javapossystem.presentation.screens.ProductActivity;
import com.saed.javapossystem.presentation.screens.ProductListActivity;

import java.util.List;
import java.util.Locale;

public class ProductsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Product> productList;
    // Interface for selection
    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

     Command<Integer> command;

    public ProductsListAdapter(List<Product> productList, Command<Integer> command) {
        this.productList = productList;
        this.command = command;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(inflater.inflate(R.layout.item_product_header, parent, false));
        } else {
            return new ItemViewHolder(inflater.inflate(R.layout.item_product_row, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            int listIndex = position - 1;// Map adapter position to list index
            if(productList.isEmpty()){
                return;
            }
            Product product = productList.get(listIndex);
            ItemViewHolder h = (ItemViewHolder) holder;

            h.name.setText(product.getName());
            h.qty.setText(String.valueOf(product.getQty()));
            h.price.setText(String.format(Locale.getDefault(),"%.2f", product.getPrice()));
            h.barcode.setText(product.getBarcode());

            h.itemView.setBackgroundColor(Color.WHITE);


            h.itemView.setOnClickListener(v -> {
                command.execute(product.getId());
            });

        }
    }

    @Override
    public int getItemCount() {
        return productList.size() + 1;
    }


    // ViewHolders
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name, qty, price, barcode;

        ItemViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtName);
            qty = v.findViewById(R.id.txtQty);
            price = v.findViewById(R.id.txtPrice);
            barcode = v.findViewById(R.id.txtBarcode);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View v) {
            super(v);
        }
    }

}