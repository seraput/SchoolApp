package com.example.schoolapp.adapter.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.schoolapp.R;
import com.example.schoolapp.models.admin.TugasGuruModels;

import java.util.List;

public class AdapterListTugasGuru extends ArrayAdapter<TugasGuruModels> {
    Context context;
    List<TugasGuruModels> arrayListTugasGuru;

    public AdapterListTugasGuru(@NonNull Context context, List<TugasGuruModels> arrayListTugasGuru) {
        super(context, R.layout.cust_list_tugas_guru, arrayListTugasGuru);

        this.context = context;
        this.arrayListTugasGuru = arrayListTugasGuru;
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_tugas_guru, null, true);
        TextView tvNama = view.findViewById(R.id.cust_txt_pel);
        TextView tvStatus = view.findViewById(R.id.cust_txt_status);
        TextView tvTanggal = view.findViewById(R.id.cust_tanggal);
        TextView tvExpired = view.findViewById(R.id.cust_tgl_exp);

        tvNama.setText(arrayListTugasGuru.get(position).getPelajaran());
        tvStatus.setText(arrayListTugasGuru.get(position).getStatus());
        tvTanggal.setText(arrayListTugasGuru.get(position).getTanggal());
        tvExpired.setText(arrayListTugasGuru.get(position).getExpired());

        if (tvStatus.getText().equals("Expired")){
            tvStatus.setTextColor(Color.RED);
            tvExpired.setTextColor(Color.RED);

        }
        else if (tvStatus.getText().equals("Active")){
            tvStatus.setTextColor(Color.GREEN);
        }

        return view;
    }
}
