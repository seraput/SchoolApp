package com.example.schoolapp.views.admin.koreksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.admin.AdapterListTerkirimGuru;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.siswa.TerkirimModels;
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.siswa.aktivitas.extend.SiswaTugasDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruKoreksiActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    ImageView imgCek;
    private String getTerkirim= Server.URL_API + "koreksi/get_jawaban_dikoreksi_guru.php";
    AdapterListTerkirimGuru adapterListTerkirimGuru;
    public static ArrayList<TerkirimModels> terkirimModelsArrayList = new ArrayList<>();
    TerkirimModels terkirimModels;
    TextView tvMessage;
    String txt = "Belum ada Data";
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_koreksi_activity);

        listView = findViewById(R.id.list_koreksi);
        imgCek = findViewById(R.id.sudah_dikoreksi);
        progressBar = findViewById(R.id.progress);
        tvMessage = findViewById(R.id.txtMessage);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        adapterListTerkirimGuru = new AdapterListTerkirimGuru(getApplicationContext(), terkirimModelsArrayList);
        listView.setAdapter(adapterListTerkirimGuru);

        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), GuruKoreksiDetailActivity.class).putExtra("position", position));
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.bluebar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Berhenti berputar/refreshing
                        swipeRefreshLayout.setRefreshing(false);
                        getData();

                    }
                },1000);
            }
        });
    }


    public void getData(){
        final String txtstat = "Y";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, getTerkirim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        terkirimModelsArrayList.clear();
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

                                    if (jsonArray.length() < 1){
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        terkirimModels = new TerkirimModels(id_tugas, mapel, modul, jenis, id_siswa, nama, benar, salah, tanggal, jam, nilai, dikoreksi);
                                        terkirimModelsArrayList.add(terkirimModels);
                                        adapterListTerkirimGuru.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                        tvMessage.setVisibility(View.GONE);
                                    }

//                                    if (jsonArray.length() != 0) {
//
////                                        Toast.makeText(GuruKoreksiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
//                                    } else {
//
////                                        Toast.makeText(GuruKoreksiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
//                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruKoreksiActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruKoreksiActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(GuruKoreksiActivity.this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruKoreksiActivity.this, GuruHomeActivity.class));
    }

    public void dikoreksi(View view) {
        startActivity(new Intent(GuruKoreksiActivity.this, GuruSudahKoreksiActivity.class));
    }
}