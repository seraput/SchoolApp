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
import com.example.schoolapp.models.siswa.SoalModels;

import java.util.List;

public class AdapterListSoal extends ArrayAdapter<SoalModels> {

    Context context;
    List<SoalModels> arrayListSoalModels;

    public AdapterListSoal(@NonNull Context context, List<SoalModels> arrayListSoalModels) {
        super(context, R.layout.cust_list_soal, arrayListSoalModels);

        this.context = context;
        this.arrayListSoalModels = arrayListSoalModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_soal, null, true);

        TextView tvId = view.findViewById(R.id.list_tugasid);
        TextView tvPertanyaan = view.findViewById(R.id.list_pertanyaan);
        TextView tvJawaban = view.findViewById(R.id.list_jawab);

        tvId.setText(arrayListSoalModels.get(position).getTugas_id());
        tvPertanyaan.setText(arrayListSoalModels.get(position).getPertanyaan());
        tvJawaban.setText(arrayListSoalModels.get(position).getJawaban());

        return view;
    }

}
