package com.example.schoolapp.views.admin.koreksi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.schoolapp.MainActivity;
import com.example.schoolapp.R;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.admin.TokenModels;
import com.example.schoolapp.models.siswa.BiodataModels;
import com.example.schoolapp.testing.FcmNotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuruKoreksiDetailActivity extends AppCompatActivity {

    int position;
    TextView tvIDtugas, tvJenis, tvMapel, tvModul, tvIDsiswa, tvNamaSiswa, tvTgl, tvBenar, tvSalah;
    MaterialEditText meNilai, meToken, meTitle, meBody;
    private String koreksi = Server.URL_API + "koreksi/koreksi_nilai.php";
    private String getToken = Server.URL_API + "koreksi/get_token.php";
    String usertoken;
    String duptok = "";
    public static ArrayList<TokenModels> tokenModelsArrayList = new ArrayList<>();
    private TokenModels tokenModels;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_koreksi_detail_activity);

        meNilai = findViewById(R.id.nilai);
        tvBenar = findViewById(R.id.jawab_benar);
        tvSalah = findViewById(R.id.jawab_salah);
        tvTgl = findViewById(R.id.tanggal);
        tvIDsiswa = findViewById(R.id.siswaID);
        tvIDtugas = findViewById(R.id.tugasID);
        tvJenis = findViewById(R.id.jenis);
        tvMapel = findViewById(R.id.mapel);
        tvModul = findViewById(R.id.modul);
        tvNamaSiswa = findViewById(R.id.namasiswa);
        meToken = findViewById(R.id.tokenuser);
        meTitle = findViewById(R.id.tokentitle);
        meBody = findViewById(R.id.tokenbody);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tvIDtugas.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getId_tugas());
        tvMapel.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getMapel());
        tvModul.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getModul());
        tvJenis.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getJenis());
        tvTgl.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getJam());
        tvNamaSiswa.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getNama());
        tvIDsiswa.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getId_siswa());
        tvBenar.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getBenar());
        tvSalah.setText(GuruKoreksiActivity.terkirimModelsArrayList.get(position).getSalah());

        getToken();

        meTitle.setText("Pemberitahuan!");
        meBody.setText("Tugas "+tvMapel.getText().toString()+" Kamu Sudah Dinilai!");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            usertoken = Objects.requireNonNull(task.getResult()).getToken();
                            Log.d("toooo", "token "+ usertoken);
                        }

                    }
                });
    }

    public void back(View view) {
        startActivity(new Intent(GuruKoreksiDetailActivity.this, GuruKoreksiActivity.class));
    }

    public void SimpanNilai(View view) {
        final String nilai = meNilai.getText().toString();
        if (nilai.isEmpty()){
            Toast.makeText(this, "Nilai Masih Kosong!", Toast.LENGTH_SHORT).show();
        }
        else{
            NilaiTugas();
            FcmNotificationSender notificationSender = new FcmNotificationSender(meToken.getText().toString(),
                    meTitle.getText().toString(), meBody.getText().toString(), getApplicationContext(), GuruKoreksiDetailActivity.this);
            notificationSender.SendNotifications();
        }
    }


    public void NilaiTugas() {
        final String tugasID = tvIDtugas.getText().toString();
        final String siswaID = tvIDsiswa.getText().toString();
        final String nilai = meNilai.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(GuruKoreksiDetailActivity.this);
        progressDialog.setMessage("Proses Simpan ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, koreksi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(GuruKoreksiDetailActivity.this, "Berhasil..", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(GuruKoreksiDetailActivity.this, GuruKoreksiActivity.class));
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(GuruKoreksiDetailActivity.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(GuruKoreksiDetailActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_tugas", tugasID);
                params.put("id_siswa", siswaID);
                params.put("nilai", nilai);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruKoreksiDetailActivity.this);
        requestQueue.add(stringRequest);

    }


    public void getToken(){
        final String txtnis = tvIDsiswa.getText().toString();
        StringRequest request = new StringRequest(Request.Method.POST, getToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tokenModelsArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nis = object.getString("nis");
                                    String token = object.getString("token");

                                    if (jsonArray.length() < 1) {
                                        Toast.makeText(GuruKoreksiDetailActivity.this, "Terjadi Kesalahan!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        tokenModels = new TokenModels(nis, token);
                                        tokenModelsArrayList.add(tokenModels);
                                        meToken.setText(token);
//                                        Toast.makeText(GuruKoreksiDetailActivity.this, ""+meToken.getText(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(GuruKoreksiDetailActivity.this, "Server Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruKoreksiDetailActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(GuruKoreksiDetailActivity.this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruKoreksiDetailActivity.this, GuruKoreksiActivity.class));
    }
}