package com.example.lab5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab5.databinding.FragmentProductBinding;

public class ProductFragment extends Fragment {
    private static final String CATEGORY_KEY = "category";
    private FragmentProductBinding binding;
    private ProductAdapter adapter;

    public static ProductFragment newInstance(String category) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String category = getArguments().getString(CATEGORY_KEY);

        if (category == null) {
            Log.e("ProductFragment", "Category is null");
            return;
        }
        Log.d("ProductFragment", "Category: " + category);

        // Настройка RecyclerView
        adapter = new ProductAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        // ViewModel
        AppDatabase db = AppDatabase.getInstance(requireContext());
        ProductRepository repository = new ProductRepository(db.productDao());
        ProductViewModel.Factory factory = new ProductViewModel.Factory(repository, category);
        ProductViewModel viewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);

        viewModel.getProducts(category).observe(getViewLifecycleOwner(), products -> {
            Log.d("ProductFragment", "Loaded products: " + products.size());
            for (Product product : products) {
                Log.d("ProductFragment", "Product: " + product.getName());
            }
            adapter.submitList(products);
        });
    }
}
