package com.saed.javapossystem.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.saed.javapossystem.R;

public class PosButtonWidget extends MaterialButton {

    public PosButtonWidget(Context context) {
        this(context, null);
    }

    public PosButtonWidget(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.navButtonWidgetStyle);
    }

    public PosButtonWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);

        // Disable Material background system
        setCornerRadius(0);
        setStrokeWidth(1); // This is 1 pixel
        // Remove internal spacing that hides strokes
        setInsetTop(0);
        setInsetBottom(0);

    }

    private void init(AttributeSet attrs) {
        setAllCaps(false);
        setClickable(true);
        setFocusable(true);
    }
    public void setTitle(String title) {
        setText(title);
    }

}
