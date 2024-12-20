package com.example.lab5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository repository;
    private LiveData<List<Product>> products;
    private AppDatabase db;

    public ProductViewModel(ProductRepository repository, String category) {
        this.repository = repository;
        this.products = repository.getProductsByCategory(category);
        //this.products = repository.getAllProducts();
    }

    public LiveData<List<Product>> getProducts(String category) {
        return repository.getProductsByCategory(category);
    }

    public LiveData<List<Product>> getAllProducts() {
        return repository.getAllProducts();
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final ProductRepository repository;
        private final String category;

        public Factory(ProductRepository repository, String category) {
            this.repository = repository;
            this.category = category;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ProductViewModel(repository, category);
        }
    }
}

