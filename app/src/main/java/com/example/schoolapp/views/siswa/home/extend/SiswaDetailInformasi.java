package com.example.schoolapp.views.siswa.home.extend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolapp.R;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.views.admin.informasi.GuruInformasiActivity;
import com.example.schoolapp.views.siswa.home.SiswaHomeActivity;

import java.util.HashMap;


public class SiswaDetailInformasi extends AppCompatActivity {

    TextView tvCreated, tvDate, tvJudul, tvUcapan, tvIsi, tvPenting, tvPenutup;
    int position;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    private String getLevel;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_detail_informasi);

        sessionManager = new SessionManager(SiswaDetailInformasi.this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getLevel = user.get(SessionManager.LEVEL);

        tvCreated   = findViewById(R.id.det_info_created);
        tvDate      = findViewById(R.id.det_info_tgl);
        tvJudul     = findViewById(R.id.det_info_judul);
        tvUcapan    = findViewById(R.id.det_info_ucapan);
        tvIsi       = findViewById(R.id.det_info_isi);
        tvPenting   = findViewById(R.id.det_info_peting);
        tvPenutup   = findViewById(R.id.det_info_penutup);


        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tvCreated.setText(SiswaHomeActivity.informationModelsArrayList.get(position).getCreated());
        tvDate.setText(SiswaHomeActivity.informationModelsArrayList.get(position).getTanggal());
        tvJudul.setText(SiswaHomeActivity.informationModelsArrayList.get(position).getJudul());
        tvUcapan.setText(SiswaHomeActivity.informationModelsArrayList.get(position).getUcapan());
        tvIsi.setText(SiswaHomeActivity.informationModelsArrayList.get(position).getIsi());
        final String txt = SiswaHomeActivity.informationModelsArrayList.get(position).getPenting();
        if (txt.equals("N")){
            tvPenting.setVisibility(View.GONE);
        }
        else{
            tvPenting.setVisibility(View.VISIBLE);
            tvPenting.setText(SiswaHomeActivity.informationModelsArrayList.get(position).getPenting());
        }
        tvPenutup.setText(SiswaHomeActivity.informationModelsArrayList.get(position).getPenutup());
    }

    public void det_info_back(View view) {
        final String level = getLevel;
        if (level.equals("siswa")){
            startActivity(new Intent(getApplicationContext(), SiswaHomeActivity.class));
        }
        else if (level.equals("admin")){
            startActivity(new Intent(getApplicationContext(), GuruInformasiActivity.class));
        }
        else{
            Toast.makeText(this, "Tidak Diketahui!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        final String level = getLevel;
        if (level.equals("siswa")){
            startActivity(new Intent(getApplicationContext(), SiswaHomeActivity.class));
        }
        else if (level.equals("admin")){
            startActivity(new Intent(getApplicationContext(), GuruInformasiActivity.class));
        }
        else{
            Toast.makeText(this, "Tidak Diketahui!", Toast.LENGTH_SHORT).show();
        }
    }
}