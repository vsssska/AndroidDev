package com.example.lab5;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertAll(List<Product> products);

    @Query("SELECT * FROM products WHERE TRIM(LOWER(category)) = TRIM(LOWER(:category))")
    LiveData<List<Product>> getProductsByCategory(String category);

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();
}

