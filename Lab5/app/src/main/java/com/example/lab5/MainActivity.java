package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.content.res.Configuration;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Настройка Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Настройка ViewPager
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                toolbar.setTitle(bottomNavigationView.getMenu().getItem(position).getTitle());
            }
        });

        // Настройка BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            boolean setNewMenu = false;
            if(itemId == R.id.nav_home){
                viewPager.setCurrentItem(0);
                return true;
            }
            else if(itemId == R.id.nav_java){
                viewPager.setCurrentItem(1);
                return true;
            }
            else if(itemId == R.id.nav_kotlin){
                viewPager.setCurrentItem(2);
                return true;
            }else if(itemId == R.id.nav_locations){
                viewPager.setCurrentItem(3);
                return true;
            }
            return false;
        });

        // Обработка нажатия "Начать квиз"
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_start_quiz) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.action_create_order) {
                Intent intent = new Intent(this, CreateOrderActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void setupViewPager(ViewPager2 viewPager) {
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0: return new HomeFragment();
                    case 1: return new JavaFragment();
                    case 2: return new KotlinFragment();
                    case 3: return new LocationsFragment();
                    default: return new HomeFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 4;
            }
        };
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
