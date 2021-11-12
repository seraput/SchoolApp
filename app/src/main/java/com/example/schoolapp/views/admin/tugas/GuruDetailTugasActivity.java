package com.example.schoolapp.views.admin.tugas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.schoolapp.R;
import com.example.schoolapp.adapter.admin.AdapterListSoal;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.models.admin.AutoNumber;
import com.example.schoolapp.models.siswa.SoalModels;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GuruDetailTugasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_detail_tugas_activity);
    }
}