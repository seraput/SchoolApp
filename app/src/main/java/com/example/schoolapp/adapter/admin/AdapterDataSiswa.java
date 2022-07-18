package com.example.schoolapp.adapter.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.schoolapp.R;
import com.example.schoolapp.models.admin.DataSiswaModels;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDataSiswa extends ArrayAdapter<DataSiswaModels> {

    Context context;
    List<DataSiswaModels> arrayListDataSiswaModels;

    public AdapterDataSiswa(@NonNull Context context, List<DataSiswaModels> arrayListDataSiswaModels) {
        super(context, R.layout.cust_list_data_siswa, arrayListDataSiswaModels);

        this.context = context;
        this.arrayListDataSiswaModels = arrayListDataSiswaModels;
    }


    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_list_data_siswa, null, true);

        TextView tvNama = view.findViewById(R.id.nama);
        TextView tvKelas = view.findViewById(R.id.kelas);
        RoundedImageView tvFoto = view.findViewById(R.id.foto);

        tvNama.setText(arrayListDataSiswaModels.get(position).getNama());
        tvKelas.setText(arrayListDataSiswaModels.get(position).getKelas());

        String kelamin = arrayListDataSiswaModels.get(position).getKelamin();
        if(kelamin == "Wanita"){
            tvFoto.setBackgroundResource(R.drawable.siswa_cw);
        }
        else if (kelamin == "Pria"){
            tvFoto.setBackgroundResource(R.drawable.siswa_laki);
        }
        else{
            Picasso.with(context)
                    .load(arrayListDataSiswaModels.get(position).getFoto())
                    .into(tvFoto);
        }

        return view;
    }

}
