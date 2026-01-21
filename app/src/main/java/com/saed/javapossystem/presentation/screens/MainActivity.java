package com.saed.javapossystem.presentation.screens;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.saed.javapossystem.R;
import com.saed.javapossystem.presentation.widgets.NavButtonWidget;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    private NavButtonWidget posButton;
    private NavButtonWidget productButton;
    private NavButtonWidget reportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        posButton = findViewById(R.id.pos_nav_button);
        productButton = findViewById(R.id.product_nav_button);
        reportButton = findViewById(R.id.report_nav_button);

       posButton.setNavigateTo(PosActivity.class);

    }
}