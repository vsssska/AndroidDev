package com.example.lab5;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Product[] products;
    private Context context;
//    идентификаторы типов представлений
    private static final int VIEW_TYPE_PRODUCT = 0;
    private static final int VIEW_TYPE_AD = 1;

    public ProductAdapter(Product[] products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 5 == 0 && position != 0) {
            return VIEW_TYPE_AD;
        }
        return VIEW_TYPE_PRODUCT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_AD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad, parent, false);
            return new AdViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_AD) {
            AdViewHolder adHolder = (AdViewHolder) holder;
            adHolder.bindAd("Реклама: Идем на Свою игру!");
        } else {
            ProductViewHolder productHolder = (ProductViewHolder) holder;
            int adjustedPosition = position - position / 5; // Учитываем смещение из-за рекламы
            productHolder.bind(products[adjustedPosition]);

            // Настройка нажатия на товар с полной карточкой
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product_name", products[adjustedPosition].getName());
                intent.putExtra("product_description", products[adjustedPosition].getDescription());
                intent.putExtra("product_price", products[adjustedPosition].getPrice());
                intent.putExtra("product_image", products[adjustedPosition].getImageResId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        // Учитываем количество элементов с рекламой
        return products.length + products.length / 5;
    }

    // ViewHolder для продукта
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView priceTextView;
        private final ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            priceTextView = itemView.findViewById(R.id.productPrice);
            imageView = itemView.findViewById(R.id.productImage);
        }

        public void bind(Product product) {
            nameTextView.setText(product.getName());
            priceTextView.setText(String.format("$%.2f", product.getPrice()));
            imageView.setImageResource(product.getImageResId());
        }
    }

    // ViewHolder для рекламы
    public static class AdViewHolder extends RecyclerView.ViewHolder {
        private final ImageView adImage;
        private final TextView adText;

        public AdViewHolder(View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.ad_image);
            adText = itemView.findViewById(R.id.ad_text);
        }

        public void bindAd(String adContent) {
            adText.setText(adContent);
            adImage.setImageResource(R.drawable.ic_ad);
        }
    }


}
