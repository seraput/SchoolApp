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
import com.example.schoolapp.models.siswa.InformationModels;

import java.util.List;

public class AdapterListInformasi extends ArrayAdapter<InformationModels> {

    Context context;
    List<InformationModels> arrayListInformasi;

    public AdapterListInformasi(@NonNull Context context, List<InformationModels> arrayListInformasi) {
        super(context, R.layout.cust_list_informasi, arrayListInformasi);

        this.context = context;
        this.arrayListInformasi = arrayListInformasi;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_informasi, null, true);

        TextView tvTitle = view.findViewById(R.id.cust_txt_title);
        TextView tvTanggal = view.findViewById(R.id.cust_txt_tanggal);
        TextView tvPenting = view.findViewById(R.id.cust_txt_penting);
        ImageView imgAlert = view.findViewById(R.id.cust_alert);

        tvTitle.setText(arrayListInformasi.get(position).getJudul());
        tvTanggal.setText(arrayListInformasi.get(position).getTanggal());
        tvPenting.setText(arrayListInformasi.get(position).getPenting());
        final String txt = tvPenting.getText().toString();
        if (txt.equals("N")){
            imgAlert.setVisibility(View.GONE);
        }

        return view;
    }
}
