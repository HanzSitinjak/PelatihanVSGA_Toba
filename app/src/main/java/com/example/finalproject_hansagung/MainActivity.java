package com.example.finalproject_hansagung;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_hansagung.DatabaseHelper;
import com.example.finalproject_hansagung.ListAdapter;
import com.example.finalproject_hansagung.databinding.ActivityDetailDiaryBinding;
import com.example.finalproject_hansagung.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ListView listViewDiary;
    private DatabaseHelper databaseHelper;
    private ListAdapter adapter;
    ActivityMainBinding binding;
    ActivityDetailDiaryBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        listViewDiary = findViewById(R.id.listViewDiary);
        Cursor cursor = databaseHelper.getAllNotes();
        adapter = new ListAdapter(this, cursor, 0);
        listViewDiary.setAdapter(adapter);

        binding.btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddNotes = new Intent(MainActivity.this, TambahNotes.class);
                startActivity(toAddNotes);
            }
        });
        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfile = new Intent(MainActivity.this, ProfilePage.class);
                startActivity(toProfile);
            }
        });

        listViewDiary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Mendapatkan cursor dari adapter
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                // Mendapatkan ID dari cursor
                long diaryId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_NOTES));

                // Buat Intent untuk membuka halaman detail dan sertakan ID diary
                Intent intent = new Intent(MainActivity.this, DetailDiary.class);
                intent.putExtra("DIARY_ID", diaryId);
                startActivity(intent);
            }
        });
    }

    // Pastikan untuk menutup Cursor dan DatabaseHelper saat Activity di-destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.changeCursor(null); // Menutup Cursor
        }
        if (databaseHelper != null) {
            databaseHelper.close(); // Menutup DatabaseHelper
        }
    }
}
