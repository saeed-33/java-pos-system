package com.saed.javapossystem.presentation.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.saed.javapossystem.R;

public class NavButtonWidget extends MaterialButton {

    private Class<?> navigateTo;

    public NavButtonWidget(Context context) {
        this(context, null);
    }

    public NavButtonWidget(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.navButtonWidgetStyle);
    }

    public NavButtonWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setAllCaps(false);
        setClickable(true);
        setFocusable(true);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NavButtonWidget);
            int iconRes = a.getResourceId(R.styleable.NavButtonWidget_iconRes, -1);
            if (iconRes != -1) setIconResource(iconRes);

            String title = a.getString(R.styleable.NavButtonWidget_titleText);
            if (title != null) setText(title);

            a.recycle();
        }
    }


    /** Set the icon drawable */
    public void setIconResource(int resId) {
        setIcon(ContextCompat.getDrawable(getContext(), resId));
    }

    /** Set the button text */
    public void setTitle(String title) {
        setText(title);
    }

    /** Set the screen to navigate to on click */
    public void setNavigateTo(Class<?> screen) {
        this.navigateTo = screen;
        setOnClickListener(v -> {
            if (navigateTo != null) {
                Context ctx = getContext();
                Intent intent = new Intent(ctx, navigateTo);
                ctx.startActivity(intent);
            }
        });
    }
}
