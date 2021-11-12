package com.example.schoolapp.views.admin.informasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.schoolapp.R;

public class GuruDetailInformasi extends AppCompatActivity {

    TextView tvCreated, tvDate, tvJudul, tvUcapan, tvIsi, tvPenting, tvPenutup;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_detail_informasi_activity);

        tvCreated   = findViewById(R.id.det_info_created);
        tvDate      = findViewById(R.id.det_info_tgl);
        tvJudul     = findViewById(R.id.det_info_judul);
        tvUcapan    = findViewById(R.id.det_info_ucapan);
        tvIsi       = findViewById(R.id.det_info_isi);
        tvPenting   = findViewById(R.id.det_info_peting);
        tvPenutup   = findViewById(R.id.det_info_penutup);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tvCreated.setText(GuruInformasiActivity.informationModelsArrayList.get(position).getCreated());
        tvDate.setText(GuruInformasiActivity.informationModelsArrayList.get(position).getTanggal());
        tvJudul.setText(GuruInformasiActivity.informationModelsArrayList.get(position).getJudul());
        tvUcapan.setText(GuruInformasiActivity.informationModelsArrayList.get(position).getUcapan());
        tvIsi.setText(GuruInformasiActivity.informationModelsArrayList.get(position).getIsi());
        final String txt = GuruInformasiActivity.informationModelsArrayList.get(position).getPenting();
        if (txt.equals("N")){
            tvPenting.setVisibility(View.GONE);
        }
        else{
            tvPenting.setVisibility(View.VISIBLE);
            tvPenting.setText(GuruInformasiActivity.informationModelsArrayList.get(position).getPenting());
        }
        tvPenutup.setText(GuruInformasiActivity.informationModelsArrayList.get(position).getPenutup());
    }

    public void det_info_back(View view) {
        startActivity(new Intent(GuruDetailInformasi.this, GuruInformasiActivity.class));
    }
}