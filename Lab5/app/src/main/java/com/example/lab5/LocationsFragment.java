package com.example.lab5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class LocationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        // Устанавливаем FlexboxLayoutManager
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerView.setLayoutManager(flexboxLayoutManager);

        // Настраиваем адаптер
        locationAdapter = new LocationAdapter(getLocations());
        recyclerView.setAdapter(locationAdapter);


        return view;
    }

    // Метод для получения списка локаций
    private List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Location 1", "Address 1"));
        locations.add(new Location("Location 2", "Address 2"));
        locations.add(new Location("Location 3", "Address 3"));
        locations.add(new Location("Location 4", "Address 4"));
        return locations;
    }
}