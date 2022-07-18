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
import com.example.schoolapp.models.admin.DataRekapModels;

import java.util.List;

public class AdapterRekap extends ArrayAdapter<DataRekapModels> {

    Context context;
    List<DataRekapModels> arrayRekapDetail;

    public AdapterRekap(@NonNull Context context, List<DataRekapModels> arrayRekapDetail) {
        super(context, R.layout.cust_rekap_list, arrayRekapDetail);

        this.context = context;
        this.arrayRekapDetail = arrayRekapDetail;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_rekap_list, null, true);

        TextView tvTanggal = view.findViewById(R.id.tab_tanggal);
        TextView tvTugas = view.findViewById(R.id.tab_tugas);
        TextView tvMapel = view.findViewById(R.id.tab_mapel);
        TextView tvNilai = view.findViewById(R.id.tab_nilai);

        tvTanggal.setText(arrayRekapDetail.get(position).getTanggal());
        tvTugas.setText(arrayRekapDetail.get(position).getIdTugas());
        tvMapel.setText(arrayRekapDetail.get(position).getMapel());
        tvNilai.setText(arrayRekapDetail.get(position).getNilai());

        return view;
    }
}
