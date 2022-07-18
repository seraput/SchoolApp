package com.example.schoolapp.views.admin.datasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.schoolapp.R;

public class TambahSiswaActivity extends AppCompatActivity {

    ImageView iBack, iSave, iDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_siswa);

        iDate = findViewById(R.id.date);
        iSave = findViewById(R.id.saved);
        iBack = findViewById(R.id.back);

        iDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        iSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        iBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}