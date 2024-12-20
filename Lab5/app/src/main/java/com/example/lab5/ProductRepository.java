package com.example.lab5;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private final ProductDao productDao;

    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    public LiveData<List<Product>> getProductsByCategory(String category) {
        return productDao.getProductsByCategory(category);
        //return productDao.getAllProducts();
    }

    public LiveData<List<Product>> getAllProducts() {
        return productDao.getAllProducts();
    }
}
