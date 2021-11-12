package com.example.schoolapp.views.admin.koreksi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.admin.AdapterKoreksiGroup;
import com.example.schoolapp.adapter.admin.AdapterKoreksiGroupDetail;
import com.example.schoolapp.adapter.admin.AdapterListTerkirimGuru;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.admin.KoreksiGroupDetailModels;
import com.example.schoolapp.models.admin.KoreksiGroupModels;
import com.example.schoolapp.models.siswa.TerkirimModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruSudahKoreksiDetail extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    private String getTerkirim= Server.URL_API + "get_jawaban_dikoreksi_guru.php";
    AdapterKoreksiGroupDetail adapterKoreksiGroupDetail;
    public static ArrayList<KoreksiGroupDetailModels> koreksiGroupDetailModelsArrayList = new ArrayList<>();
    KoreksiGroupDetailModels koreksiGroupDetailModels;
    int position;
    String GetID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_sudah_koreksi_detail_activity);

        progressBar = findViewById(R.id.progress);
        listView = findViewById(R.id.list_sudah_koreksi_detail);

        adapterKoreksiGroupDetail = new AdapterKoreksiGroupDetail(getApplicationContext(), koreksiGroupDetailModelsArrayList);
        listView.setAdapter(adapterKoreksiGroupDetail);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        GetID = GuruSudahKoreksiActivity.koreksiGroupModelsArrayListk.get(position).getId_tugas();

//        tvIDtugas.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getId_tugas());
        getData();

    }

    public void back(View view) {
        startActivity(new Intent(GuruSudahKoreksiDetail.this, GuruSudahKoreksiActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruSudahKoreksiDetail.this, GuruSudahKoreksiActivity.class));
    }


    public void getData(){
        final String tugasID = GetID;
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, getTerkirim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        koreksiGroupDetailModelsArrayList.clear();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id_tugas = object.getString("id_tugas");
                                    String mapel = object.getString("mapel");
                                    String modul = object.getString("modul");
                                    String jenis = object.getString("jenis");
                                    String id_siswa = object.getString("id_siswa");
                                    String nama = object.getString("nama");
                                    String benar = object.getString("benar");
                                    String salah = object.getString("salah");
                                    String tanggal = object.getString("tanggal");
                                    String jam = object.getString("jam");
                                    String nilai = object.getString("nilai");
                                    String dikoreksi = object.getString("dikoreksi");

                                    if (jsonArray.length() > 0) {
                                        koreksiGroupDetailModels = new KoreksiGroupDetailModels(id_tugas, mapel, modul, jenis, id_siswa, nama, benar, salah, tanggal, jam, nilai, dikoreksi);
                                        koreksiGroupDetailModelsArrayList.add(koreksiGroupDetailModels);
                                        adapterKoreksiGroupDetail.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
//                                        Toast.makeText(GuruKoreksiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruSudahKoreksiDetail.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruSudahKoreksiDetail.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruSudahKoreksiDetail.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_tugas", tugasID);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruSudahKoreksiDetail.this);
        requestQueue.add(request);
    }
}