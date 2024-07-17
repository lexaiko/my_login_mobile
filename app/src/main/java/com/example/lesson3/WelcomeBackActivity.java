package com.example.lesson3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.content.Context;
import android.widget.Toast;

import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lesson3.data.UserDao;
import com.example.lesson3.data.AppDbProvider;
import com.example.lesson3.data.User;

import java.util.Objects;

public class WelcomeBackActivity extends AppCompatActivity {

    // SharedPreferences yang digunakan untuk read/write
    private SharedPreferences sharedPrefs;

    private static final String USERNAME_KEY = "key_username";
    private static final String KEEP_LOGIN_KEY = "key_keep_login";

    // Komponen UI
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox chkRememberUsername;
    private CheckBox chkKeepLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back);

        this.sharedPrefs = this.getSharedPreferences("dtsapp_sharedprefs", Context.MODE_PRIVATE);

        this.initComponents();
        this.autoLogin();
        this.loadSavedUsername();
    }

    private void initComponents() {
        // Inisialisasi komponen
        this.edtUsername = this.findViewById(R.id.edt_username);
        this.edtPassword = this.findViewById(R.id.edt_password);
        this.chkRememberUsername = this.findViewById(R.id.chk_remember_username);
        this.chkKeepLogin = this.findViewById(R.id.chk_keep_login);
    }

    // Click Actions
    public void onTxvForgotPassword_Click(View view) {
        Intent i = new Intent(WelcomeBackActivity.this, ForgotPasswordActivity.class);
        startActivity(i);
    }

    public void onBtnLogin_Click(View view) {
        boolean valid = this.validateCredential();

        if (valid) {
            Intent i = new Intent(WelcomeBackActivity.this, HomeActivity.class);
            startActivity(i);

            this.saveUsername();
            this.makeAutoLogin();
        } else {
            Toast.makeText(this, "Invalid username/password", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBtnRegister_Click(View view) {
        Intent i = new Intent(WelcomeBackActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    // End of Click Actions

    private void saveUsername() {
        // Menyimpan username bila diperlukan
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        if (this.chkRememberUsername.isChecked())
            editor.putString(USERNAME_KEY, this.edtUsername.getText().toString());
        else
            editor.remove(USERNAME_KEY);
        editor.apply();
    }

    private void loadSavedUsername() {
        // Memeriksa apakah sebelumnya ada username yang tersimpan?
        // Jika ya, maka tampilkan username tersebut di EditText username.
        String savedUsername = this.sharedPrefs.getString(USERNAME_KEY, null);
        if (savedUsername != null) {
            this.edtUsername.setText(savedUsername);
            this.chkRememberUsername.setChecked(true);
        }
    }

    private void makeAutoLogin() {
        // Mengatur agar selanjutnya pada saat aplikasi dibuka menjadi otomatis login
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        if (this.chkKeepLogin.isChecked())
            editor.putBoolean(KEEP_LOGIN_KEY, true);
        else
            editor.remove(KEEP_LOGIN_KEY);
        editor.apply();
    }

    private void autoLogin() {
        // Cek apakah sebelumnya aplikasi diatur agar bypass login?
        boolean keepLogin = this.sharedPrefs.getBoolean(KEEP_LOGIN_KEY, false);
        if (keepLogin) {
            // Jika keepLogin true, buka activity berikutnya (contoh: HomeActivity)
            Intent intent = new Intent(WelcomeBackActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Optional: menutup activity saat ini agar tidak kembali ke WelcomeBackActivity jika tombol back ditekan
        }
    }

    private boolean validateCredential() {
        String currentUsername = this.edtUsername.getText().toString();
        String currentPassword = this.edtPassword.getText().toString();

        // Mendapatkan instance UserDao dari AppDbProvider
        UserDao userDao = AppDbProvider.getInstance(this).userDao();

        // Mencari pengguna berdasarkan username dan password
        User user = userDao.findByUsernameAndPassword(currentUsername, currentPassword);

        return user != null;
    }
}
