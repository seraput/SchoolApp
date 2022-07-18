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
import com.example.schoolapp.models.siswa.TerkirimModels;
import java.util.List;

public class AdapterListTerkirimGuru extends ArrayAdapter<TerkirimModels> {

    Context context;
    List<TerkirimModels> arrayListTerkirimModels;

    public AdapterListTerkirimGuru(@NonNull Context context, List<TerkirimModels> arrayListTerkirimModels) {
        super(context, R.layout.cust_list_terkirim_guru, arrayListTerkirimModels);

        this.context = context;
        this.arrayListTerkirimModels = arrayListTerkirimModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_terkirim_guru, null, true);

        TextView tvModul = view.findViewById(R.id.guru_terkirim_mapel);
        TextView tvMapel = view.findViewById(R.id.guru_terkirim_nama);
        TextView tvJenis = view.findViewById(R.id.guru_terkirim_jenis);
        TextView tvTanggal = view.findViewById(R.id.guru_terkirim_tanggal);

        tvModul.setText(arrayListTerkirimModels.get(position).getMapel());
        tvMapel.setText(arrayListTerkirimModels.get(position).getNama());
        tvJenis.setText(arrayListTerkirimModels.get(position).getJenis());
        tvTanggal.setText(arrayListTerkirimModels.get(position).getTanggal());

        return view;
    }

}
