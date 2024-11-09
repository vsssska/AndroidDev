package com.example.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class KotlinFragment extends Fragment {

    private RecyclerView recyclerView;
    private Product[] products = new Product[] {
            new Product("Курс подготовки Kotlin junior gaydev", "Курс для начинающих смешариков", 850.0, R.drawable.ic_coffemaker)
    };
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kotlin, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        productAdapter = new ProductAdapter(products, requireContext());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        recyclerView.setAdapter(productAdapter);

        return view;
    }
}