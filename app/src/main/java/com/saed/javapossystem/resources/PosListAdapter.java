package com.saed.javapossystem.resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.R;
import com.saed.javapossystem.entities.Product;

import java.util.List;

public class PosListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<Product> productList;

    public PosListAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        if (position == productList.size() + 1) return TYPE_FOOTER;
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
            Product product = productList.get(position - 1); // -1 because of header
            ItemViewHolder h = (ItemViewHolder) holder;
            h.name.setText(product.getName());
            h.qty.setText(String.valueOf(product.getQty()));
            h.price.setText(String.format("%.2f", product.getPrice()));
            h.total.setText(String.format("%.2f", product.getQty() * product.getPrice()));
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder f = (FooterViewHolder) holder;
            // Calculate totals here
            double totalP = 0; int totalQ = 0;
            for(Product p : productList) {
                totalP += (p.getPrice() * p.getQty());
                totalQ += p.getQty();
            }
            f.txtQty.setText("Total QTY : " + totalQ);
            f.txtPrice.setText("Total Price : " + String.format("%.2f", totalP));
        }
    }

    @Override
    public int getItemCount() {
        return productList.size() + 2; // Data + Header + Footer
    }

    // ViewHolders
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
    static class HeaderViewHolder extends RecyclerView.ViewHolder { HeaderViewHolder(View v) { super(v); } }
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice, txtQty;
        FooterViewHolder(View v) {
            super(v);
            txtPrice = v.findViewById(R.id.footerTotalPrice);
            txtQty = v.findViewById(R.id.footerTotalQty);
        }
    }
}