package com.example.lesson3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lesson3.data.UserDao;
import com.example.lesson3.data.AppDbProvider;
import com.example.lesson3.data.User;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity
{
    private User currentUser;
    // Komponen
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtEmail;
    private EditText edtPhoneNumber;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.loadData();
        this.initComponents();
    }

    private void loadData()
    {
        // Mendatapatkan DAO dari DTSAppDatabase
        UserDao daoUser = AppDbProvider.getInstance(this).userDao();

        // Melakukan SELECT terhadap 1 user yang paling awal, dan mengembalikan hasilnya ke property currentUser
        this.currentUser = daoUser.selectOne();

    }

    private void initComponents()
    {
        this.edtUsername = this.findViewById(R.id.edt_username);
        this.edtPassword = this.findViewById(R.id.edt_password);
        this.edtEmail = this.findViewById(R.id.edt_email);
        this.edtPhoneNumber = this.findViewById(R.id.edt_phone_number);
        this.btnSave = this.findViewById(R.id.btn_save);



        // Jika tidak ada data registrasi sebelumnya, tidak perlu melakukan apa-apa, dan matikan Button Save agar user tidak menyimpan data kosong.
        if(this.currentUser == null)
        {
            this.btnSave.setEnabled(false);
            return;
        }

        // Menyalin data dari property currentUser ke semua komponen yang sesuai
        this.edtUsername.setText(this.currentUser.username);
        this.edtPassword.setText(this.currentUser.password);
        this.edtEmail.setText(this.currentUser.email);
        this.edtPhoneNumber.setText(this.currentUser.phoneNumber);
    }

    public void onBtnSave_Click(View view)
    {
        //memperbaharui/update data di property currentUser
        this.syncData();

        //mendapatkan class DAO dari DTS App Database

        UserDao daoUser = AppDbProvider.getInstance(this).userDao();

        // menggunakan DAO untuk menyimpan data user ke currentUser
        daoUser.update(this.currentUser);
        Toast.makeText(this, "Your data has been updated!", Toast.LENGTH_SHORT).show();
    }

    public void onTxvDeleteAccount_Click(View view)
    {
        // Mendapatkan DAO dari DTSAppDatabase
        UserDao daoUser = AppDbProvider.getInstance(this).userDao();

        // Menghapus pengguna dari database
        if (this.currentUser != null) {
            daoUser.delete(this.currentUser);
            Toast.makeText(this, "Your data has been deleted.", Toast.LENGTH_SHORT).show();

            // Clear the currentUser and disable the save button
            this.currentUser = null;
            this.clearComponents();
            this.btnSave.setEnabled(false);
        } else {
            Toast.makeText(this, "No user to delete.", Toast.LENGTH_SHORT).show();
        }
    }


    // Menangkap data dari semua EditText dan menyalinnya ke Entity currentUser
    private void syncData()
    {
        this.currentUser.password = this.edtPassword.getText().toString();
        this.currentUser.email = this.edtEmail.getText().toString();
        this.currentUser.phoneNumber = this.edtPhoneNumber.getText().toString();
    }
    private void clearComponents()
    {
        this.edtUsername.setText("");
        this.edtPassword.setText("");
        this.edtEmail.setText("");
        this.edtPhoneNumber.setText("");
    }
}