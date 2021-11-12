package com.example.schoolapp.views.admin.tugas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.siswa.AdapterListTugas;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.siswa.TugasModels;
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.admin.informasi.GuruDetailInformasi;
import com.example.schoolapp.views.admin.informasi.GuruInformasiActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruTugasActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    private String getTugas = Server.URL_API + "get_tugas.php";
    private String HapusTugas = Server.URL_API + "nonaktiftugas.php";
    AdapterListTugas adapterListTugas;
    public static ArrayList<TugasModels> tugasModelsArrayList = new ArrayList<>();
    TugasModels tugasModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_tugas_activity);

        progressBar = findViewById(R.id.progress);
        listView = findViewById(R.id.list_tugas);

        adapterListTugas = new AdapterListTugas(getApplicationContext(), tugasModelsArrayList);
        listView.setAdapter(adapterListTugas);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                CharSequence[] dialogItem = {"Hapus"};
                builder.setTitle(tugasModelsArrayList.get(position).getPelajaran());

                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int i) {
                        switch (i) {
                            case 0:
                                final String id = tugasModelsArrayList.get(position).getId();
                                final ProgressDialog progressDialog = new ProgressDialog(GuruTugasActivity.this);
                                progressDialog.setMessage("Tunggu Sebentar...");
                                progressDialog.show();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, HapusTugas,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                progressDialog.dismiss();
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    String success = jsonObject.getString("success");

                                                    if (success.equals("1")){
                                                        getData();
                                                        Toast.makeText(GuruTugasActivity.this, "Berhasil..", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(GuruTugasActivity.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println(error.toString());
                                                progressDialog.dismiss();
                                                Toast.makeText(GuruTugasActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("id", id);
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(GuruTugasActivity.this);
                                requestQueue.add(stringRequest);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }


    public void getData(){
        final String txtstat = "Y";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, getTugas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tugasModelsArrayList.clear();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String pelajaran = object.getString("pelajaran");
                                    String modul = object.getString("modul");
                                    String tanggal = object.getString("tanggal");
                                    String expired = object.getString("expired");
                                    String guru_id = object.getString("guru_id");
                                    String guru_nama = object.getString("guru_nama");
                                    String jenis = object.getString("jenis");

                                    if (jsonArray.length() < 1) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruTugasActivity.this, "Belum Ada Tugas!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        tugasModels = new TugasModels(id, pelajaran, modul, tanggal, expired, guru_id, guru_nama, jenis);
                                        tugasModelsArrayList.add(tugasModels);
                                        adapterListTugas.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruTugasActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruTugasActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("status", txtstat);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruTugasActivity.this);
        requestQueue.add(request);
    }

    public void add_tugas(View view) {
        startActivity(new Intent(GuruTugasActivity.this, GuruTambahTugasActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruTugasActivity.this, GuruHomeActivity.class));
    }
}