package com.example.schoolapp.views.admin;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.models.admin.CountDashboard;
import com.example.schoolapp.models.admin.TugasGuruModels;
import com.example.schoolapp.models.siswa.SoalModels;
import com.example.schoolapp.views.admin.absensi.GuruAbsensiActivity;
import com.example.schoolapp.views.admin.datasiswa.GuruDataSiswaActivity;
import com.example.schoolapp.views.admin.informasi.AddInformasiGuru;
import com.example.schoolapp.views.admin.informasi.GuruInformasiActivity;
import com.example.schoolapp.views.admin.koreksi.GuruKoreksiActivity;
import com.example.schoolapp.views.admin.rekap.GuruRekapActivity;
import com.example.schoolapp.views.admin.tugas.GuruTambahTugasActivity;
import com.example.schoolapp.views.admin.tugas.GuruTugasActivity;
import com.example.schoolapp.views.auth.AuthLoginActivity;
import com.example.schoolapp.views.siswa.home.SiswaHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GuruHomeActivity extends AppCompatActivity {

    CardView cd1, cd2, cd3, cd4, cd5, cd6;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String getLevel;
    private long backPressedTime;
    private Toast backToast;
    Dialog dialog;
    CardView cdYes, cdNot;
    String getNama;
    private String getTugas = Server.URL_API + "count_siswa.php";
    ProgressBar progressBar;
    TextView username, tsiswa, tinfo, ttugas;
    public static ArrayList<CountDashboard> countModelsArrayList = new ArrayList<>();
    CountDashboard countDashboard;
    String csiswa = "";
    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_home_activity);

        username = findViewById(R.id.home_txt_name);

        sessionManager = new SessionManager(GuruHomeActivity.this);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getLevel = user.get(SessionManager.LEVEL);
        getNama = user.get(SessionManager.NAME);

        username.setText(getNama);

        cd1 = findViewById(R.id.card1);
        cd2 = findViewById(R.id.card2);
        cd3 = findViewById(R.id.card3);
        cd4 = findViewById(R.id.card4);
        cd5 = findViewById(R.id.card5);
        cd6 = findViewById(R.id.card6);
        tsiswa = findViewById(R.id.total_siswa);
        ttugas = findViewById(R.id.txttugas);
        tinfo = findViewById(R.id.txtinfo);


        dialog = new Dialog(GuruHomeActivity.this);
        dialog.setContentView(R.layout.cust_alert_logout);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        cdYes = dialog.findViewById(R.id.btn_yes);
        cdNot = dialog.findViewById(R.id.btn_not);

        cdYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefLoginState), "LoggedOut");
                editor.apply();
                startActivity(new Intent(GuruHomeActivity.this, AuthLoginActivity.class));
                finish();
            }
        });

        cdNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        String status = getLevel;
        if (status.equals("guru")) {
            cd1.setVisibility(View.GONE);
            cd2.setVisibility(View.GONE);
            cd3.setVisibility(View.VISIBLE);
            cd4.setVisibility(View.VISIBLE);
            cd5.setVisibility(View.VISIBLE);
            cd6.setVisibility(View.VISIBLE);
        } else if (status.equals("admin")) {
            cd1.setVisibility(View.VISIBLE);
            cd2.setVisibility(View.VISIBLE);
            cd3.setVisibility(View.VISIBLE);
            cd4.setVisibility(View.VISIBLE);
            cd5.setVisibility(View.VISIBLE);
            cd6.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Tidak Dapat Akses!", Toast.LENGTH_SHORT).show();
            logout();
        }

        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruInformasiActivity.class));
            }
        });

        cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruTugasActivity.class));
            }
        });

        cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruKoreksiActivity.class));
            }
        });

        cd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruAbsensiActivity.class));
            }
        });

        cd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruDataSiswaActivity.class));
            }
        });

        cd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruRekapActivity.class));
            }
        });

        list_data = new ArrayList<HashMap<String, String>>();
        getCount();
    }

    private void getCount(){
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
                                map.put("siswa", object.getString("siswa"));
                                map.put("tugas", object.getString("tugas"));
                                map.put("informasi", object.getString("informasi"));
                                list_data.add(map);
                                tsiswa.setText(list_data.get(0).get("siswa"));
                                tinfo.setText(list_data.get(0).get("informasi"));
                                ttugas.setText(list_data.get(0).get("tugas"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GuruHomeActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruHomeActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(GuruHomeActivity.this);
        requestQueue.add(request);
    }


    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState), "LoggedOut");
        editor.apply();
        startActivity(new Intent(GuruHomeActivity.this, AuthLoginActivity.class));
        finish();
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

    public void setting(View view) {
        dialog.show();
    }
}