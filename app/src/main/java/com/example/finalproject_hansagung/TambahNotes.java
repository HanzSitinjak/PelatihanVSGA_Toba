package com.example.finalproject_hansagung;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.finalproject_hansagung.databinding.ActivityTambahNotesBinding;

public class TambahNotes extends AppCompatActivity {

    ActivityTambahNotesBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = binding.editTextTitle.getText().toString();
                String konten = binding.editTextContent.getText().toString();

                if (judul.isEmpty() || konten.isEmpty()) {
                    Toast.makeText(TambahNotes.this, "Masukkan data yang valid", Toast.LENGTH_SHORT).show();
                } else {
                    boolean insertData = databaseHelper.addNote(judul, konten);
                    if (insertData) {
                        Toast.makeText(TambahNotes.this, "Catatan berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TambahNotes.this, MainActivity.class));
                    } else {
                        Toast.makeText(TambahNotes.this, "Gagal menyimpan catatan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahNotes.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
