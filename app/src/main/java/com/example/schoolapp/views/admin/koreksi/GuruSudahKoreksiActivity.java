package com.example.schoolapp.views.admin.koreksi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.schoolapp.adapter.admin.AdapterListTerkirimGuru;
import com.example.schoolapp.adapter.siswa.AdapterListKoreksi;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.admin.KoreksiGroupModels;
import com.example.schoolapp.models.siswa.TerkirimModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruSudahKoreksiActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ListView listView;
    private String getTerkirim= Server.URL_API + "get_jawaban_dikoreksi_group.php";
    AdapterKoreksiGroup adapterKoreksiGroup;
    public static ArrayList<KoreksiGroupModels> koreksiGroupModelsArrayListk = new ArrayList<>();
    KoreksiGroupModels koreksiGroupModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_sudah_koreksi_activity);

        progressBar = findViewById(R.id.progress);
        listView = findViewById(R.id.list_sudah_koreksi);


        adapterKoreksiGroup = new AdapterKoreksiGroup(getApplicationContext(), koreksiGroupModelsArrayListk);
        listView.setAdapter(adapterKoreksiGroup);

        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), GuruSudahKoreksiDetail.class).putExtra("position", position));
            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(GuruSudahKoreksiActivity.this, GuruKoreksiActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruSudahKoreksiActivity.this, GuruKoreksiActivity.class));
    }

    public void getData(){
        final String txtstat = "Y";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, getTerkirim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        koreksiGroupModelsArrayListk.clear();
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
                                    String tanggal = object.getString("tanggal");
                                    String jumlah = object.getString("total");

                                    if (jsonArray.length() > 0) {
                                        koreksiGroupModels = new KoreksiGroupModels(id_tugas, mapel, modul, tanggal, jumlah);
                                        koreksiGroupModelsArrayListk.add(koreksiGroupModels);
                                        adapterKoreksiGroup.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
//                                        Toast.makeText(GuruKoreksiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruSudahKoreksiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruSudahKoreksiActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruSudahKoreksiActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dikoreksi", txtstat);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruSudahKoreksiActivity.this);
        requestQueue.add(request);
    }
}