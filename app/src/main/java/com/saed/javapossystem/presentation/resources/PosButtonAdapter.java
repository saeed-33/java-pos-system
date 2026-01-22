package com.saed.javapossystem.presentation.resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.saed.javapossystem.R;

import java.util.List;

public class PosButtonAdapter extends RecyclerView.Adapter<PosButtonAdapter.ButtonViewHolder> {

    private List<ButtonModel> buttons;


    public PosButtonAdapter(List<ButtonModel> buttons) {
        this.buttons = buttons;
    }

    @Override
    public ButtonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pos_button, parent, false);
        return new ButtonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ButtonViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        ButtonModel model = buttons.get(position);
        holder.button.setText(model.getText());
        holder.button.setOnClickListener(v -> {
            try {
                model.getOnPress().onButtonClick();
            } catch (Exception e) {
                Toast.makeText(v.getContext(), context.getString(R.string.error,e.getMessage()) , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }

    static class ButtonViewHolder extends RecyclerView.ViewHolder {
        MaterialButton button;

        ButtonViewHolder(View v) {
            super(v);
            button = (MaterialButton) v;
        }
    }
}