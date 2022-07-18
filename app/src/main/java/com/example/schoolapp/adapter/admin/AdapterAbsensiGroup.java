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
import com.example.schoolapp.models.admin.AbsensiGroupModels;

import java.util.List;

public class AdapterAbsensiGroup extends ArrayAdapter<AbsensiGroupModels> {


    Context context;
    List<AbsensiGroupModels> arrayListAbsensiGroupModels;

    public AdapterAbsensiGroup(@NonNull Context context, List<AbsensiGroupModels> arrayListAbsensiGroupModels) {
        super(context, R.layout.cust_list_absensi_group, arrayListAbsensiGroupModels);

        this.context = context;
        this.arrayListAbsensiGroupModels = arrayListAbsensiGroupModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_absensi_group, null, true);

        TextView tvTanggal = view.findViewById(R.id.absensi_group_tanggal);
        TextView tvJumlah = view.findViewById(R.id.absensi_group_jumlah);

        tvTanggal.setText(arrayListAbsensiGroupModels.get(position).getTanggal());
        tvJumlah.setText(arrayListAbsensiGroupModels.get(position).getTotal());

        return view;
    }
}
