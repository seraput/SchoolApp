package com.example.schoolapp.views.admin.datasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.admin.informasi.GuruInformasiActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GuruDataSiswaDetail extends AppCompatActivity {

    MaterialEditText mtUsername, mtNama, mtNis, mtKelas, mtAgama, mtKelamin, mtTanggal, mtTelp;
    Button btSimpan, btUbah, btBatal;
    int position;
    private String putData = Server.URL_API + "siswa/edit_siswa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_data_siswa_detail_activity);

        btSimpan = findViewById(R.id.btSimpan);
        btUbah = findViewById(R.id.btEdit);

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disable();
                btBatal.setVisibility(View.GONE);
                btSimpan.setVisibility(View.GONE);
                btUbah.setVisibility(View.VISIBLE);
            }
        });

        btUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable();//enabled fun
                btBatal.setVisibility(View.VISIBLE);
                btSimpan.setVisibility(View.VISIBLE);
                btUbah.setVisibility(View.GONE);
            }
        });

        btBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruDataSiswaDetail.this, GuruDataSiswaActivity.class));
            }
        });

        mtUsername = findViewById(R.id.username);
        mtNama = findViewById(R.id.namalengkap);
        mtNis = findViewById(R.id.nis);
        mtKelas = findViewById(R.id.kelas);
        mtAgama = findViewById(R.id.agama);
        mtKelamin = findViewById(R.id.kelamin);
        mtTanggal = findViewById(R.id.tanggal);
        mtTelp = findViewById(R.id.notlp);

        disable();//disable fun

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        mtUsername.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getUsername());
        mtNama.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getNama());
        mtNis.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getId());
        mtKelas.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getKelas());
        mtTanggal.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getTanggal());
        mtKelamin.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getKelamin());
        mtAgama.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getAgama());
        mtTelp.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getTelp());
    }


    private void disable(){
        mtNama.setEnabled(false);
        mtUsername.setEnabled(false);
        mtKelas.setEnabled(false);
        mtTanggal.setEnabled(false);
        mtKelamin.setEnabled(false);
        mtTelp.setEnabled(false);
        mtNis.setEnabled(false);
        mtAgama.setEnabled(false);
    }

    private void enable(){
        mtNama.setEnabled(true);
        mtUsername.setEnabled(false);
        mtKelas.setEnabled(false);
        mtTanggal.setEnabled(true);
        mtKelamin.setEnabled(true);
        mtTelp.setEnabled(true);
        mtNis.setEnabled(false);
        mtAgama.setEnabled(true);
    }

    private void put(){
        final String nis = mtNis.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(GuruDataSiswaDetail.this);
        progressDialog.setMessage("Tunggu Sebentar...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, putData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(GuruDataSiswaDetail.this, "Berhasil..", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(GuruDataSiswaDetail.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(GuruDataSiswaDetail.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nis", nis);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruDataSiswaDetail.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruDataSiswaDetail.this, GuruDataSiswaActivity.class));
    }

    public void back1(View view) {
        startActivity(new Intent(GuruDataSiswaDetail.this, GuruDataSiswaActivity.class));
    }
}