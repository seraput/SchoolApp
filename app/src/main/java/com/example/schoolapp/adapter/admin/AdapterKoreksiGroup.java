package com.example.schoolapp.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.schoolapp.R;
import com.example.schoolapp.models.admin.KoreksiGroupModels;
import com.example.schoolapp.models.siswa.KoreksiModels;

import java.util.List;

public class AdapterKoreksiGroup extends ArrayAdapter<KoreksiGroupModels> {


    Context context;
    List<KoreksiGroupModels> arrayListKoreksiGroupModels;

    public AdapterKoreksiGroup(@NonNull Context context, List<KoreksiGroupModels> arrayListKoreksiGroupModels) {
        super(context, R.layout.cust_list_dikoreksi_group, arrayListKoreksiGroupModels);

        this.context = context;
        this.arrayListKoreksiGroupModels = arrayListKoreksiGroupModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_dikoreksi_group, null, true);

        TextView tvModul = view.findViewById(R.id.koreksi_group_modul);
        TextView tvMapel = view.findViewById(R.id.koreksi_group_mapel);
        TextView tvTanggal = view.findViewById(R.id.koreksi_group_tanggal);
        TextView tvjumlah = view.findViewById(R.id.koreksi_group_jumlah);
        TextView tvId = view.findViewById(R.id.koreksi_group_id);

        tvId.setText(arrayListKoreksiGroupModels.get(position).getId_tugas());
        tvMapel.setText(arrayListKoreksiGroupModels.get(position).getMapel());
        tvModul.setText(arrayListKoreksiGroupModels.get(position).getModul());
        tvTanggal.setText(arrayListKoreksiGroupModels.get(position).getTanggal());
        tvjumlah.setText(arrayListKoreksiGroupModels.get(position).getTotal());
        return view;
    }
}
