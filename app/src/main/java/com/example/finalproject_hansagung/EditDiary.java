package com.example.finalproject_hansagung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditDiary extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private DatabaseHelper databaseHelper;
    private long diaryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        databaseHelper = new DatabaseHelper(this);

        String title = getIntent().getStringExtra("DIARY_TITLE");
        String content = getIntent().getStringExtra("DIARY_CONTENT");

        if (title != null && content != null) {
            editTextTitle.setText(title);
            editTextContent.setText(content);
        }

        diaryId = getIntent().getLongExtra("DIARY_ID", -1);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String content = editTextContent.getText().toString().trim();

                String[] diaryDetail = databaseHelper.getDiaryDetail(diaryId);
                String oldTitle = diaryDetail[0];
                String oldContent = diaryDetail[1];

                if (!title.isEmpty() && !content.isEmpty()) {
                    if (!title.equals(oldTitle) || !content.equals(oldContent)) {
                        boolean updated = databaseHelper.updateDiary(diaryId, title, content);
                        if (updated) {
                            Toast.makeText(EditDiary.this, "Kenangan Berhasil diubah!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditDiary.this, "Gagal mengubah kenangan.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditDiary.this, "Tidak ada perubahan pada kenangan.", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(EditDiary.this, MainActivity.class);
                    intent.putExtra("DIARY_ID", diaryId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditDiary.this, "Judul dan isi kenangan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditDiary.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
