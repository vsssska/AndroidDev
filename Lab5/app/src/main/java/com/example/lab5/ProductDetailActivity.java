package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView productImage = findViewById(R.id.productImageDetail);
        TextView productName = findViewById(R.id.productNameDetail);
        TextView productDescription = findViewById(R.id.productDescriptionDetail);
        TextView productPrice = findViewById(R.id.productPriceDetail);

        // Получаем данные из Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("product_name");
        String description = intent.getStringExtra("product_description");
        double price = intent.getDoubleExtra("product_price", 0.0);
        int imageResId = intent.getIntExtra("product_image", -1);

        // Устанавливаем данные в соответствующие представления
        productName.setText(name);
        productDescription.setText(description);
        productPrice.setText(String.format("₽%.2f", price));
        productImage.setImageResource(imageResId);
    }
}
