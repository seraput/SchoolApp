package com.example.schoolapp.views.admin.rekap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.admin.AdapterDataSiswa;
import com.example.schoolapp.adapter.admin.AdapterRekap;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.admin.DataRekapModels;
import com.example.schoolapp.models.admin.DataSiswaModels;
import com.example.schoolapp.models.siswa.TerkirimModels;
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.admin.datasiswa.GuruDataSiswaActivity;
import com.example.schoolapp.views.admin.koreksi.GuruKoreksiActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruRekapDetailActivity extends AppCompatActivity {

    int position;
    int position1;
    MaterialEditText meNis, meNama, meKelas;
    private String getTugas = Server.URL_API + "rekapnilai/getrekapnilai.php";
    private String getDetail = Server.URL_API + "rekapnilai/getlistrekap.php";
    ArrayList<HashMap<String, String>> list_data;
    TextView tvMapel, tvTugas, tvNilai, tvTest;
    ProgressBar progressBar;
    public static ArrayList<DataRekapModels> dataRekapModelsArrayList = new ArrayList<>();
    DataRekapModels dataRekapModels;
    LinearLayout clickDetail;
    ListView maList;
    AdapterRekap adapterRekap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_rekap_detail);

        meNis = findViewById(R.id.nisSiswa);
        meNama = findViewById(R.id.namaSiswa);
        meKelas = findViewById(R.id.kelasSiswa);
        tvMapel = findViewById(R.id.nilai_mapel);
        tvTugas = findViewById(R.id.nilai_tugas);
        tvNilai = findViewById(R.id.nilai_rata);
        clickDetail = findViewById(R.id.detail);
        tvTest = findViewById(R.id.test);
//        maList = findViewById(R.id.listDetail);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        meNis.setText(GuruRekapActivity.dataSiswaModelsArrayList.get(position).getId());
        meNama.setText(GuruRekapActivity.dataSiswaModelsArrayList.get(position).getNama());
        meKelas.setText(GuruRekapActivity.dataSiswaModelsArrayList.get(position).getKelas());

        list_data = new ArrayList<HashMap<String, String>>();
        getCount();

        clickDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < dataRekapModelsArrayList.size(); i++){
                    tvTest.setText(dataRekapModelsArrayList.get(position1).getMapel());
//                    tvTest.append(dataRekapModelsArrayList.get(i).toString());
                }
//                adapterRekap = new AdapterRekap(GuruRekapDetailActivity.this, dataRekapModelsArrayList);
//                maList.setAdapter(adapterRekap);
            }
        });
    }

    private void getCount(){
        final String id = meNis.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, getTugas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("read");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("nilai", object.getString("nilai"));
                                map.put("tugas", object.getString("tugas"));
                                map.put("mapel", object.getString("mapel"));
                                list_data.add(map);
                                tvNilai.setText(list_data.get(0).get("nilai"));
                                tvTugas.setText(list_data.get(0).get("tugas"));
                                tvMapel.setText(list_data.get(0).get("mapel"));

                                getRekap();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GuruRekapDetailActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruRekapDetailActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("siswa", id);
//                params.put("status", status);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(GuruRekapDetailActivity.this);
        requestQueue.add(request);
    }


    private void getRekap(){
        final String id = meNis.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, getDetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String tgl = object.getString("tanggal");
                                    String tugas = object.getString("idTugas");
                                    String modul = object.getString("modul");
                                    String mapel = object.getString("mapel");
                                    String nilai = object.getString("nilai");

                                    if (jsonArray.length() < 1) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruRekapDetailActivity.this, "Belum Ada Data!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dataRekapModels = new DataRekapModels(tgl, tugas, modul, mapel, nilai);
                                        dataRekapModelsArrayList.add(dataRekapModels);
//                                        adapterRekap.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruRekapDetailActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruRekapDetailActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("siswaid", id);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruRekapDetailActivity.this);
        requestQueue.add(request);
    }

    public void back(View view) {
    }
}