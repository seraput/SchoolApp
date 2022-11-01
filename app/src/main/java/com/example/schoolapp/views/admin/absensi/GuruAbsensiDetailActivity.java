package com.example.schoolapp.views.admin.absensi;

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
import com.example.schoolapp.adapter.admin.AdapterAbsensiDetail;
import com.example.schoolapp.adapter.admin.AdapterAbsensiGroup;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.admin.AbsensiDetailModels;
import com.example.schoolapp.models.admin.AbsensiGroupModels;
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.admin.datasiswa.GuruDataSiswaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruAbsensiDetailActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    private String datagroup = Server.URL_API + "absensi/absensi_detail.php";
    AdapterAbsensiDetail adapterAbsensiDetail;
    public static ArrayList<AbsensiDetailModels> absensiDetailModelsArrayList = new ArrayList<>();
    AbsensiDetailModels absensiDetailModels;
    String tanggal;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_absensi_detail);

        progressBar = findViewById(R.id.progress);
        listView = findViewById(R.id.list_sudah_koreksi);

        adapterAbsensiDetail = new AdapterAbsensiDetail(getApplicationContext(), absensiDetailModelsArrayList);
        listView.setAdapter(adapterAbsensiDetail);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tanggal = GuruAbsensiActivity.absensiGroupModelsArrayList.get(position).getTanggal();

        getData();

    }

    public void getData(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, datagroup,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        absensiDetailModelsArrayList.clear();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nis = object.getString("nis");
                                    String tgl = object.getString("tgl");
                                    String jam = object.getString("jam");
                                    String kelamin = object.getString("kelamin");
                                    String nama = object.getString("nama");

                                    if (jsonArray.length() > 0) {
                                        absensiDetailModels = new AbsensiDetailModels(nis, nama, tgl, jam, kelamin);
                                        absensiDetailModelsArrayList.add(absensiDetailModels);
                                        adapterAbsensiDetail.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
//                                        Toast.makeText(GuruKoreksiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruAbsensiDetailActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruAbsensiDetailActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruAbsensiDetailActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tanggal", tanggal);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruAbsensiDetailActivity.this);
        requestQueue.add(request);
    }

    public void back(View view) {
        startActivity(new Intent(GuruAbsensiDetailActivity.this, GuruAbsensiActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruAbsensiDetailActivity.this, GuruAbsensiActivity.class));
    }
}