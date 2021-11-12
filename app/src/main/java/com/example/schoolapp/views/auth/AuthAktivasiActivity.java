package com.example.schoolapp.views.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.schoolapp.models.siswa.SoalModels;
import com.example.schoolapp.models.siswa.TugasModels;
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.siswa.aktivitas.SiswaTugasActivity;
import com.example.schoolapp.views.siswa.aktivitas.extend.SiswaTugasDetail;
import com.example.schoolapp.views.siswa.aktivitas.extend.SiswaTugasSelesai;
import com.example.schoolapp.views.siswa.history.SiswaHistoryActivity;
import com.example.schoolapp.views.siswa.home.SiswaHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthAktivasiActivity extends AppCompatActivity {

    MaterialEditText mtNisn, mtNama, mtEmail, mtPass, mtPass2;
    Button btCek, btSimpan;
    LinearLayout btBantuan;
    String usertoken;
    EditText genToken;
    Dialog dialog;
    private String GETDATA = Server.URL_API + "get_user.php";
    private String InsertAPI = Server.URL_API + "insert_token.php";
    private String UpdateData = Server.URL_API + "aktivasi.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_aktivasi_activity);

        mtNisn = findViewById(R.id.txt_nisn);
        mtNama = findViewById(R.id.txt_nama);
        mtEmail = findViewById(R.id.txt_email);
        mtPass = findViewById(R.id.txt_pass);
        genToken = findViewById(R.id.gentoken);
        btCek = findViewById(R.id.bt_cek);
        btSimpan = findViewById(R.id.bt_simpan);
        btBantuan = findViewById(R.id.bt_bantuan);

        generateToken();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.cust_alert_connection);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nisn = mtNisn.getText().toString();
                if (!nisn.isEmpty()){
//                    Toast.makeText(AuthAktivasiActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                    AuthProses();
                }
                else{
                    Toast.makeText(AuthAktivasiActivity.this, "Nisn Harus Di Isi!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nis = mtNisn.getText().toString();
                final String nama = mtNama.getText().toString();
                final String email = mtEmail.getText().toString();
                final String pass = mtPass.getText().toString();

                if (nis.isEmpty() || nama.isEmpty() || email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(AuthAktivasiActivity.this, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                }
                else{
                    InsertToken();
                }
            }
        });

        btBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void generateToken(){
        // for sending notification to all
        FirebaseMessaging.getInstance().subscribeToTopic("all");
//         fcm settings for perticular user
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            usertoken = Objects.requireNonNull(task.getResult()).getToken();
                            Log.d("toooo", "token "+ usertoken);
                            genToken.setText(usertoken);
//                            Toast.makeText(AuthAktivasiActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(AuthAktivasiActivity.this, "Filed Generate Token!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void AuthProses(){
        final String txtnis = mtNisn.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(AuthAktivasiActivity.this);
        progressDialog.setMessage("Sedang Memuat Data . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, GETDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nis = object.getString("nis");
                                    String nama = object.getString("nama");

                                    if (jsonArray.length() == 0){
                                        dialog.show();
                                        progressDialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(AuthAktivasiActivity.this, "Isi Email", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        mtNama.setText(nama);
                                        mtNisn.setEnabled(false);
                                        mtEmail.setFocusable(true);
                                        mtEmail.setEnabled(true);
                                        mtPass.setEnabled(true);
                                    }

                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(AuthAktivasiActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AuthAktivasiActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nis", txtnis);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AuthAktivasiActivity.this);
        requestQueue.add(request);
    }


    public void UpdateUser() {
        final String nis = mtNisn.getText().toString();
        final String email = mtEmail.getText().toString();
        final String pass = mtPass.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(AuthAktivasiActivity.this);
        progressDialog.setMessage("Aktivasi Proses ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdateData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(AuthAktivasiActivity.this, "Berhasil Aktivasi", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AuthAktivasiActivity.this, AuthLoginActivity.class));
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(AuthAktivasiActivity.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(AuthAktivasiActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nis", nis);
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AuthAktivasiActivity.this);
        requestQueue.add(stringRequest);

    }


    private void InsertToken() {
        final String nis = mtNisn.getText().toString();
        final String token = genToken.getText().toString();
        final String status = "Y";
        final ProgressDialog progressDialog = new ProgressDialog(AuthAktivasiActivity.this);
        progressDialog.setMessage("Menyesuaikan Data ...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, InsertAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            progressDialog.dismiss();
                            UpdateUser();
                            Toast.makeText(AuthAktivasiActivity.this, "Data Oke!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AuthAktivasiActivity.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AuthAktivasiActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("token", token);
                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AuthAktivasiActivity.this);
        requestQueue.add(request);
    }

}