package com.example.finalproject_hansagung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_hansagung.databinding.ActivityLoginPageBinding;

public class LoginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginPage.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (databaseHelper.checkEmailPassword(email, password)) {
                        long loggedInUserID = databaseHelper.getLoggedInUserID(email, password);
                        Toast.makeText(LoginPage.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                        // Mendapatkan nama dan email pengguna berdasarkan ID yang berhasil login
                        String[] userProfile = databaseHelper.getUserDetail(loggedInUserID);
                        String username = userProfile[0];
                        String userEmail = userProfile[1];

                        // Membuat intent untuk beralih ke ProfilePage dan mengirimkan data nama dan email
                        Intent intent = new Intent(LoginPage.this, ProfilePage.class);
                        intent.putExtra("ID_USER", loggedInUserID);
                        intent.putExtra("USERNAME", username);
                        intent.putExtra("EMAIL", userEmail);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginPage.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}
