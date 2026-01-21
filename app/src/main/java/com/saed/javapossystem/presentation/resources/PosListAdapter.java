package com.saed.javapossystem.presentation.resources;

import android.graphics.Color; // Added
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.PosApplication;
import com.saed.javapossystem.R;
import com.saed.javapossystem.di.AppContainer;
import com.saed.javapossystem.domain.entities.Product;
import com.saed.javapossystem.domain.usecase.GetCartTotalPriceUseCase;
import com.saed.javapossystem.domain.usecase.GetCartTotalQtyUseCase;

import java.util.List;

public class PosListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<Product> productList;
   final private GetCartTotalQtyUseCase getCartTotalQtyUseCase;
   final private GetCartTotalPriceUseCase getCartTotalPriceUseCase;

    // Selection state
    private int selectedPosition = RecyclerView.NO_POSITION;
    private OnProductSelectedListener listener;

    // Interface for selection
    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

    public void setOnProductSelectedListener(OnProductSelectedListener listener) {
        this.listener = listener;
    }

    public PosListAdapter(List<Product> productList, GetCartTotalQtyUseCase getCartTotalQtyUseCase, GetCartTotalPriceUseCase getCartTotalPriceUseCase) {
        this.productList = productList;
        this.getCartTotalQtyUseCase = getCartTotalQtyUseCase;
        this.getCartTotalPriceUseCase = getCartTotalPriceUseCase;
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
            int listIndex = position - 1; // Map adapter position to list index
            Product product = productList.get(listIndex);
            ItemViewHolder h = (ItemViewHolder) holder;

            h.name.setText(product.getName());
            h.qty.setText(String.valueOf(product.getSelldQuantity()));
            h.price.setText(String.format("%.2f", product.getPrice()));
            h.total.setText(String.format("%.2f", product.getTotalPrice()));

            // --- SELECTION LOGIC ---
            if (listIndex == selectedPosition) {
                // Modern light highlight (Light Blue)
                h.itemView.setBackgroundColor(Color.parseColor("#E3F2FD"));
            } else {
                // Standard background (White)
                h.itemView.setBackgroundColor(Color.WHITE);
            }

            h.itemView.setOnClickListener(v -> {
                int previousSelected = selectedPosition;
                selectedPosition = listIndex;

                // Refresh old and new selected rows to update background color
                notifyItemChanged(previousSelected + 1); // +1 because of header
                notifyItemChanged(selectedPosition + 1);

                if (listener != null) {
                    listener.onProductSelected(product);
                }
            });
            // --- END SELECTION LOGIC ---

        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder f = (FooterViewHolder) holder;

            double totalP =getCartTotalPriceUseCase.execute();
            int totalQ = getCartTotalQtyUseCase.execute();

            f.txtQty.setText("Total QTY : " + totalQ);
            f.txtPrice.setText("Total Price : " + String.format("%.2f", totalP));
        }
    }

    @Override
    public int getItemCount() {
        return productList.size() + 2;
    }

    public void updateData(List<Product> newProducts) {
        this.productList = newProducts;
        clearSelection(); // Clear selection when data is refreshed
        notifyDataSetChanged();
    }

    // Helper to clear highlight after deletion
    public void clearSelection() {
        selectedPosition = RecyclerView.NO_POSITION;
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