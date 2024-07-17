package com.example.lesson3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    // Key-key untuk data yang disimpan di SharedPrefernces
    private static final String KEEP_LOGIN_KEY = "key_keep_login";

    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inisialisasi SharedPreferences
        this.sharedPrefs = this.getSharedPreferences("dtsapp_sharedprefs", Context.MODE_PRIVATE);
    }

    public void onBtnShowProfile_Click(View view)
    {
        Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
        this.startActivity(i);
    }

    public void onBtnLogout_Click(View view)
    {
        // Mengatur agar selanjutnya pada saat aplikasi dibuka menjadi otomatis login
        SharedPreferences.Editor editor = this.sharedPrefs.edit();

        editor.remove(KEEP_LOGIN_KEY);
        editor.apply();

        this.finish();
    }
}