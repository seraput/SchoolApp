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
import com.example.schoolapp.models.admin.KoreksiGroupDetailModels;
import com.example.schoolapp.models.admin.KoreksiGroupModels;

import java.util.List;

public class AdapterKoreksiGroupDetail extends ArrayAdapter<KoreksiGroupDetailModels> {

    Context context;
    List<KoreksiGroupDetailModels> arrayListKoreksiGroupDetailModels;

    public AdapterKoreksiGroupDetail(@NonNull Context context, List<KoreksiGroupDetailModels> arrayListKoreksiGroupDetailModels) {
        super(context, R.layout.cust_list_dikoreksi_guru, arrayListKoreksiGroupDetailModels);

        this.context = context;
        this.arrayListKoreksiGroupDetailModels = arrayListKoreksiGroupDetailModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_dikoreksi_guru, null, true);

        TextView tvModul = view.findViewById(R.id.koreksi_guru_modul);
        TextView tvMapel = view.findViewById(R.id.koreksi_guru_mapel);
        TextView tvTanggal = view.findViewById(R.id.koreksi_guru_tanggal);
        TextView tvNilai = view.findViewById(R.id.koreksi_guru_nilai);
        TextView tvNama = view.findViewById(R.id.koreksi_guru_nama);

        tvTanggal.setText(arrayListKoreksiGroupDetailModels.get(position).getTanggal());
        tvMapel.setText(arrayListKoreksiGroupDetailModels.get(position).getMapel());
        tvNama.setText(arrayListKoreksiGroupDetailModels.get(position).getNama());
        tvModul.setText(arrayListKoreksiGroupDetailModels.get(position).getModul());
        tvNilai.setText(arrayListKoreksiGroupDetailModels.get(position).getNilai());
        return view;
    }
}
