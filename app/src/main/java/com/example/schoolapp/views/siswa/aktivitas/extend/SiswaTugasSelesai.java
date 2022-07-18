package com.example.schoolapp.views.siswa.aktivitas.extend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.schoolapp.views.siswa.history.SiswaHistoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SiswaTugasSelesai extends AppCompatActivity {

    TextView tvNama, tvTgl, tvJam, benar, salah;
    SessionManager sessionManager;
    String id, nama;
    String times = "HH:mm:ss";
    SimpleDateFormat sdfTime = new SimpleDateFormat(times);
    String myFormat = "yyyy-MM-dd";
    SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat);
    private String InsertAPI = Server.URL_API + "tugas/insert_jawaban.php";
    CardView cdSubmit;
//    String usertoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_tugas_selesai);

        tvNama = findViewById(R.id.txt_nama);
        tvTgl = findViewById(R.id.txt_tgl);
        tvJam = findViewById(R.id.txt_jam);
        cdSubmit = findViewById(R.id.card_selesai);
        benar = findViewById(R.id.txt_benar);
        salah = findViewById(R.id.txt_salah);

        sessionManager = new SessionManager(SiswaTugasSelesai.this);
//        SharedPreferences shared = SiswaTugasDetail.this.getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        id = user.get(SessionManager.NIS);
        nama = user.get(SessionManager.NAME);
        tvNama.setText(nama);

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdfDate.format(c1.getTime());
        String str2 = sdfTime.format(c1.getTime());
        tvTgl.setText(str1);
        tvJam.setText(str2);
        int b = getIntent().getIntExtra("benar", 0);

        int s = getIntent().getIntExtra("salah", 0);

        String ab = String.valueOf(b);
        String as = String.valueOf(s);

        benar.setText(ab);
        salah.setText(as);
//
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (task.isSuccessful()) {
//                            usertoken = Objects.requireNonNull(task.getResult()).getToken();
//                            Log.d("toooo", "token "+ usertoken);
//                        }
//
//                    }
//                });

        cdSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });
    }


    private void InsertData() {
        final String tugasid = getIntent().getStringExtra("idtugas");
        final String mapel = getIntent().getStringExtra("mapel");
        final String modul = getIntent().getStringExtra("modul");
        final String siswaid = getIntent().getStringExtra("idsiswa");
        final String nama = tvNama.getText().toString();
        int b = getIntent().getIntExtra("benar",0);
        int s = getIntent().getIntExtra("salah",0);
        final String benar = String.valueOf(b);
        final String salah = String.valueOf(s);
        final String tanggal = tvTgl.getText().toString().trim();
        final String jam = tvJam.getText().toString().trim();
        final String nilai = "0";
        final String dikoreksi = "Belum";
        final String jenis = "Pilihan Ganda";

        final ProgressDialog progressDialog = new ProgressDialog(SiswaTugasSelesai.this);
        progressDialog.setMessage("Tunggu Sebentar Ya . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, InsertAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(SiswaTugasSelesai.this, "Selesai!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SiswaHistoryActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SiswaTugasSelesai.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SiswaTugasSelesai.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_tugas", tugasid);
                params.put("mapel", mapel);
                params.put("modul", modul);
                params.put("jenis", jenis);
                params.put("id_siswa", siswaid);
                params.put("nama", nama);
                params.put("benar", benar);
                params.put("salah", salah);
                params.put("tanggal", tanggal);
                params.put("jam", jam);
                params.put("nilai", nilai);
                params.put("dikoreksi", dikoreksi);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SiswaTugasSelesai.this);
        requestQueue.add(request);
    }
}