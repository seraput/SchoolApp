package com.example.schoolapp.views.siswa.aktivitas;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.models.siswa.TugasModels;
import com.example.schoolapp.views.siswa.aktivitas.extend.SiswaTugasDetail;
import com.example.schoolapp.views.siswa.history.SiswaHistoryActivity;
import com.example.schoolapp.views.siswa.home.SiswaHomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SiswaTugasActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    private String getTugas = Server.URL_API + "koreksi/get_tugas.php";
    private String cekTugas = Server.URL_API + "tugas/cek_tugas.php";
    AdapterListTugas adapterListTugas;
    public static ArrayList<TugasModels> tugasModelsArrayList = new ArrayList<>();
    TugasModels tugasModels;
    ListView listTugas;
    SessionManager sessionManager;
    ProgressBar progressBar;
    String nis;
    Dialog dialog;
    Button tutup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_tugas_activity);

        sessionManager = new SessionManager(SiswaTugasActivity.this);
//        SharedPreferences shared = SiswaTugasDetail.this.getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        nis = user.get(SessionManager.NIS);

        listTugas = findViewById(R.id.list_tugas);
        progressBar = findViewById(R.id.progress);
        dialog = new Dialog(SiswaTugasActivity.this);
        dialog.setContentView(R.layout.cust_alert_duplicated);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        tutup = dialog.findViewById(R.id.tutup);

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        adapterListTugas = new AdapterListTugas(getApplicationContext(), tugasModelsArrayList);
        listTugas.setAdapter(adapterListTugas);

        listTugas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String getnis = nis;
                final String getpos = tugasModelsArrayList.get(position).getId();
                final ProgressDialog progressDialog = new ProgressDialog(SiswaTugasActivity.this);
                progressDialog.setMessage("Tunggu Sebentar . . .");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, cekTugas,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("silahkan")) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), SiswaTugasDetail.class).putExtra("position", position));
//                                    Toast.makeText(SiswaTugasActivity.this, "Masih Boleh", Toast.LENGTH_SHORT).show();
                                } else if(response.equalsIgnoreCase("duplicated")){
                                    Toast.makeText(SiswaTugasActivity.this, "Sudah Ada", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    dialog.show();
                                }
                                else {
                                    Toast.makeText(SiswaTugasActivity.this, "Gagal, Terjadi Masalah!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SiswaTugasActivity.this, "Error Connection" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id_tugas", getpos);
                        params.put("id_siswa", getnis);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(SiswaTugasActivity.this);
                requestQueue.add(request);

//                startActivity(new Intent(getApplicationContext(), SiswaTugasDetail.class)
//                        .putExtra("position", position));
            }
        });

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tugas);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                SiswaHomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.tugas:
//                        startActivity(new Intent(getApplicationContext(),
//                                SiswaTugasActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),
                                SiswaHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
//                        final ProgressDialog progressDialog = new ProgressDialog(SiswaHomeActivity.this);
//                        progressDialog.setMessage("Tunggu Sebentar Ya . . .");
//                        progressDialog.show();
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
////                                Data6();
//                            }
//                        }, 3000);
                }
                return false;
            }
        });
        //End ButtomNav
    }

    @Override
    protected void onStart() {
        getData();
        super.onStart();
    }

    public void getData(){
        final String txtstat = "Active";
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
                                        Toast.makeText(SiswaTugasActivity.this, "Belum Ada Tugas!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SiswaTugasActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SiswaTugasActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SiswaTugasActivity.this);
        requestQueue.add(request);
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else {
            backToast = Toast.makeText(this, "Tekan Lagi Untuk Keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}