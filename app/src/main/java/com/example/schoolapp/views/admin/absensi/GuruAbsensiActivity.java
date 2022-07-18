package com.example.schoolapp.views.admin.absensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.admin.AdapterAbsensiGroup;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.admin.AbsensiGroupModels;
import com.example.schoolapp.views.admin.koreksi.GuruSudahKoreksiDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruAbsensiActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    private String datagroup = Server.URL_API + "absensi/absensi_group.php";
    AdapterAbsensiGroup adapterAbsensiGroup;
    public static ArrayList<AbsensiGroupModels> absensiGroupModelsArrayList = new ArrayList<>();
    AbsensiGroupModels absensiGroupModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_absensi_activity);


        progressBar = findViewById(R.id.progress);
        listView = findViewById(R.id.list_sudah_koreksi);


        adapterAbsensiGroup = new AdapterAbsensiGroup(getApplicationContext(), absensiGroupModelsArrayList);
        listView.setAdapter(adapterAbsensiGroup);

        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), GuruAbsensiDetailActivity.class).putExtra("position", position));
            }
        });
    }

    public void getData(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, datagroup,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        absensiGroupModelsArrayList.clear();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nis = object.getString("nis");
                                    String tanggal = object.getString("tanggal");
                                    String jam = object.getString("jam");
                                    String total = object.getString("total");

                                    if (jsonArray.length() > 0) {
                                        absensiGroupModels = new AbsensiGroupModels(nis, tanggal, jam, total);
                                        absensiGroupModelsArrayList.add(absensiGroupModels);
                                        adapterAbsensiGroup.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
//                                        Toast.makeText(GuruKoreksiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruAbsensiActivity.this, "Belum Ada Data...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruAbsensiActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruAbsensiActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruAbsensiActivity.this);
        requestQueue.add(request);
    }

    public void back(View view) {
    }
}