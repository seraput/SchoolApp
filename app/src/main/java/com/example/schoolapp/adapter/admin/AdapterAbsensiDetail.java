package com.example.schoolapp.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.schoolapp.R;
import com.example.schoolapp.models.admin.AbsensiDetailModels;

import java.util.List;

public class AdapterAbsensiDetail extends ArrayAdapter<AbsensiDetailModels> {

    Context context;
    List<AbsensiDetailModels> arrayAbsensiDetail;

    public AdapterAbsensiDetail(@NonNull Context context, List<AbsensiDetailModels> arrayAbsensiDetail) {
        super(context, R.layout.cust_list_absensi_detail, arrayAbsensiDetail);

        this.context = context;
        this.arrayAbsensiDetail = arrayAbsensiDetail;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_absensi_detail, null, true);

        TextView tvTanggal = view.findViewById(R.id.absen_tanggal);
        TextView tvnama = view.findViewById(R.id.absen_nama);
        TextView tvjam = view.findViewById(R.id.absen_jam);
        ImageView img = view.findViewById(R.id.absen_img);

        tvTanggal.setText(arrayAbsensiDetail.get(position).getTanggal());
        tvnama.setText(arrayAbsensiDetail.get(position).getNama());
        tvjam.setText(arrayAbsensiDetail.get(position).getJam());

        String kelamin = arrayAbsensiDetail.get(position).getKelamin();
        if (kelamin.equals("Wanita")){
            img.setBackgroundResource(R.drawable.siswa_cw);
        }
        else
        {
            img.setBackgroundResource(R.drawable.siswa_laki);
        }

        return view;
    }
}
