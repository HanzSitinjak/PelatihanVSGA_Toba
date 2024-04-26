package com.example.finalproject_hansagung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {
    private TextView textViewUsername;
    private TextView textViewEmail;
    private ImageButton btnAddNote;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        textViewUsername = findViewById(R.id.textUsername);
        textViewEmail = findViewById(R.id.textEmail);
        btnAddNote = findViewById(R.id.btnAddNote);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddNote = new Intent(ProfilePage.this, TambahNotes.class);
                startActivity(toAddNote);
            }
        });

        Button btnSignOut = findViewById(R.id.buttonSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        long userID = getIntent().getLongExtra("ID_USER", -1);

        databaseHelper = new DatabaseHelper(this);

        String[] userProfile = databaseHelper.getUserDetail(userID);

        if (userProfile != null && userProfile.length == 3) {
            textViewUsername.setText("Username: " + userProfile[0]);
            textViewEmail.setText("Email: " + userProfile[1]);
        } else {
            textViewUsername.setText("Username Tidak ditemukan.");
            textViewEmail.setText("Email Tidak ditemukan.");
        }
    }
}
