package com.example.finalproject_hansagung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_hansagung.databinding.ActivityRegisterPageBinding;


public class RegisterPage extends AppCompatActivity {

    ActivityRegisterPageBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.editTextUsername.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                String confirmPassword = binding.editTextConfirmPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterPage.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (!checkUserEmail) {
                            boolean insert = databaseHelper.insertData(username ,email, password);

                            if (insert) {
                                Toast.makeText(RegisterPage.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterPage.this, LoginPage.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterPage.this, "Register Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterPage.this, "Pengguna Sudah Ada", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterPage.this, "Password salah!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this, LoginPage.class));
                finish();
            }
        });
    }
}
