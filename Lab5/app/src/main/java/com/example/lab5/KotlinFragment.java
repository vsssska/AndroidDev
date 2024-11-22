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
            new Product("Курс подготовки Junior gaydev", "Курс для начинающих смешариков", 850.0, R.drawable.ic_kotlinjunior),
            new Product("Курс подготовки Middle gaydev", "Курс для средних смешариков", 1300, R.drawable.ic_kotlinmid),
            new Product("Курс подготовки TeamLid gaydev", "Курс для уже НОРМ таких смешариков", 2000, R.drawable.ic_kotlinlid),
            new Product("Курс подготовки Junior webProg", "Курс для начинающих смешариков", 950.0, R.drawable.ic_kotlinjunior),
            new Product("Курс подготовки Middle webProg", "Курс для средних смешариков", 1400.50, R.drawable.ic_kotlinmid),
            new Product("Курс подготовки TeamLid webProg", "Курс для уже НОРМ таких смешариков", 1900, R.drawable.ic_kotlinlid),
            new Product("Курс подготовки Junior Minecraft modding", "Курс для начинающих смешариков", 350.0, R.drawable.ic_kotlinjunior),
            new Product("Курс подготовки Middle Minecraft modding", "Курс для средних смешариков", 500, R.drawable.ic_kotlinmid),
            new Product("Курс подготовки TeamLid Minecraft modding", "Курс для уже НОРМ таких смешариков", 800, R.drawable.ic_kotlinlid)

    };
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kotlin, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        productAdapter = new ProductAdapter(products, requireContext());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(productAdapter);

        return view;
    }
}