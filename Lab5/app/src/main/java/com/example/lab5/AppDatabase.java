package com.example.lab5;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "product_db")
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(() -> {
                ProductDao dao = INSTANCE.productDao();
                dao.insertAll(Arrays.asList(
                        new Product("Test1", "Test Product 1", "drawable/android_logo", "java"),
                        new Product("Test2", "Test Product 2", "drawable/android_logo", "java"),
                        new Product("Test3", "Test Product 3", "drawable/android_logo", "kotlin"),
                        new Product("Test4", "Test Product 4", "drawable/android_logo", "kotlin")));
            }).start();
        }
    };
}
