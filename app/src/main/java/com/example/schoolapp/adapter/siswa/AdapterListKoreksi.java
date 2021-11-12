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
import com.example.schoolapp.models.siswa.KoreksiModels;

import java.util.List;

public class AdapterListKoreksi extends ArrayAdapter<KoreksiModels> {

    Context context;
    List<KoreksiModels> arrayListKoreksiModels;

    public AdapterListKoreksi(@NonNull Context context, List<KoreksiModels> arrayListKoreksiModels) {
        super(context, R.layout.cust_list_dikoreksi, arrayListKoreksiModels);

        this.context = context;
        this.arrayListKoreksiModels = arrayListKoreksiModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_dikoreksi, null, true);

        TextView tvModul = view.findViewById(R.id.koreksi_modul);
        TextView tvMapel = view.findViewById(R.id.koreksi_mapel);
        TextView tvJenis = view.findViewById(R.id.koreksi_jenis);
        TextView tvTanggal = view.findViewById(R.id.koreksi_tanggal);
        TextView tvNilai = view.findViewById(R.id.koreksi_nilai);

        tvModul.setText(arrayListKoreksiModels.get(position).getModul());
        tvMapel.setText(arrayListKoreksiModels.get(position).getMapel());
        tvJenis.setText(arrayListKoreksiModels.get(position).getJenis());
        tvTanggal.setText(arrayListKoreksiModels.get(position).getTanggal());
        tvNilai.setText(arrayListKoreksiModels.get(position).getNilai());
        return view;
    }
}
