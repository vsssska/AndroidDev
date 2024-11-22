package com.example.lab5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JavaFragment extends Fragment {

    private RecyclerView recyclerView;
    private Product[] products = new Product[] {
            new Product("Коффе машина", "Машина для приготовления коффе", 9350.0, R.drawable.ic_coffemaker),
            new Product("Junior gaydev", "Junior подготовка к разработке игр", 1350.0, R.drawable.ic_javajunior),
            new Product("Middle gaydev", "Middle подготовка к разработке игр", 1930.0, R.drawable.ic_javamiddle),
            new Product("TeamLid gaydev", "TeamLid подготовка к разработке игр", 1930.0, R.drawable.ic_javalid),
            new Product("Junior webprog", "Junior подготовка к разработке сайтов", 1350.0, R.drawable.ic_javajunior),
            new Product("Middle webprog", "Middle подготовка к разработке сайтов", 1930.0, R.drawable.ic_javamiddle),
            new Product("TeamLid webprog", "TeamLid подготовка к разработке сайтов", 1930.0, R.drawable.ic_javalid),
            new Product("Scam java guides", "Подготовка к скаму новичков, путем продажи нерабочих курсов", 5000, R.drawable.ic_javascam)
    };
    private ProductAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_java, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        productAdapter = new ProductAdapter(products, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(productAdapter);

        return view;
    }
}