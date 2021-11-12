package com.example.schoolapp.adapter.siswa;

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
import com.example.schoolapp.models.siswa.TerkirimModels;

import java.util.List;

public class AdapterListTerkirim extends ArrayAdapter<TerkirimModels> {
    Context context;
    List<TerkirimModels> arrayListTerkirimModels;

    public AdapterListTerkirim(@NonNull Context context, List<TerkirimModels> arrayListTerkirimModels) {
        super(context, R.layout.cust_list_terkirim, arrayListTerkirimModels);

        this.context = context;
        this.arrayListTerkirimModels = arrayListTerkirimModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_terkirim, null, true);

        TextView tvModul = view.findViewById(R.id.terkirim_modul);
        TextView tvMapel = view.findViewById(R.id.terkirim_mapel);
        TextView tvJenis = view.findViewById(R.id.terkirim_jenis);
        TextView tvTanggal = view.findViewById(R.id.terkirim_tanggal);

        tvModul.setText(arrayListTerkirimModels.get(position).getModul());
        tvMapel.setText(arrayListTerkirimModels.get(position).getMapel());
        tvJenis.setText(arrayListTerkirimModels.get(position).getJenis());
        tvTanggal.setText(arrayListTerkirimModels.get(position).getTanggal());

        return view;
    }
}
