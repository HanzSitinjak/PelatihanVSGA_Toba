package com.example.finalproject_hansagung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_hansagung.R;

public class DetailDiary extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewContent;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewContent = findViewById(R.id.textViewContent);

        databaseHelper = new DatabaseHelper(this);

        long diaryId = getIntent().getLongExtra("DIARY_ID", -1);

        String[] diaryDetail = databaseHelper.getDiaryDetail(diaryId);
        textViewTitle.setText(diaryDetail[0]);
        textViewContent.setText(diaryDetail[1]);

        Button btnEdit = findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEditDiary = new Intent(DetailDiary.this, EditDiary.class);
                toEditDiary.putExtra("DIARY_ID", diaryId);
                toEditDiary.putExtra("DIARY_TITLE", textViewTitle.getText().toString());
                toEditDiary.putExtra("DIARY_CONTENT", textViewContent.getText().toString());
                startActivity(toEditDiary);
            }
        });

        Button btnHapus = findViewById(R.id.btnHapus);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted = databaseHelper.deleteDiary(diaryId);
                if (deleted) {
                    Toast.makeText(DetailDiary.this, "Berhasil menghapus kenangan.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailDiary.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DetailDiary.this, "Gagal menghapus kenangan.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
