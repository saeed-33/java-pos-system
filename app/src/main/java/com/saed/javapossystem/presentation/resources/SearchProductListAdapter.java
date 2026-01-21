package com.saed.javapossystem.presentation.resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.saed.javapossystem.R;
import com.saed.javapossystem.domain.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchProductListAdapter extends RecyclerView.Adapter<SearchProductListAdapter.ViewHolder> implements Filterable {
    private List<Product> originalList;
    private List<Product> filteredList;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }


    public SearchProductListAdapter(List<Product> list) {
        this.originalList = list;
        this.filteredList = new ArrayList<>(list);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                List<Product> results = new ArrayList<>();
                if (query.isEmpty()) {
                    results.addAll(originalList);
                } else {
                    for (Product p : originalList) {
                        if (p.getName().toLowerCase().contains(query) || p.getBarcode().contains(query)) {
                            results.add(p);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = results;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Product p = filteredList.get(pos);
        holder.name.setText(p.getName());
        holder.barcode.setText(p.getBarcode());
        holder.price.setText(String.format("%.2f", p.getPrice()));
        holder.qty.setText(String.valueOf(p.getQty()));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(p);
        });
    }

    @Override public int getItemCount() { return filteredList.size(); }
    @Override public ViewHolder onCreateViewHolder(ViewGroup p, int t) {
        return new ViewHolder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_search_product, p, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, barcode, price, qty;
        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtName);
            barcode = v.findViewById(R.id.txtBarcode);
            price = v.findViewById(R.id.txtPrice);
            qty = v.findViewById(R.id.txtQty);
        }
    }
}