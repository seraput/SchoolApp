package com.example.schoolapp.adapter.siswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.schoolapp.R;
import com.example.schoolapp.models.siswa.TugasModels;

import java.util.List;

public class AdapterListTugas extends ArrayAdapter<TugasModels> {
    Context context;
    List<TugasModels> arrayListTugas;

    public AdapterListTugas(@NonNull Context context, List<TugasModels> arrayListTugas) {
        super(context, R.layout.cust_list_tugas, arrayListTugas);

        this.context = context;
        this.arrayListTugas = arrayListTugas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_tugas, null, true);
        TextView tvNama = view.findViewById(R.id.cust_txt_guru);
        TextView tvMapel = view.findViewById(R.id.cust_txt_mapel);
        TextView tvModul = view.findViewById(R.id.cust_txt_modul);
        TextView tvTanggal = view.findViewById(R.id.cust_tanggal);

        tvNama.setText(arrayListTugas.get(position).getModul());
        tvMapel.setText(arrayListTugas.get(position).getPelajaran());
        tvModul.setText(arrayListTugas.get(position).getJenis());
        tvTanggal.setText(arrayListTugas.get(position).getExpired());

        return view;
    }
}
