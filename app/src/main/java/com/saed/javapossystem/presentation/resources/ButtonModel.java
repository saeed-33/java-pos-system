package com.saed.javapossystem.presentation.resources;

public class ButtonModel {
    public interface OnButtonClickListener {
        void onButtonClick();
    }

    private OnButtonClickListener onPress;
    private String text;
    private int color;


    public ButtonModel(String text, OnButtonClickListener onPress) {
        this.text = text;
        this.onPress=onPress;
    }

    public String getText() {
        return text;
    }
    public OnButtonClickListener getOnPress() {
        return onPress;
    }

}