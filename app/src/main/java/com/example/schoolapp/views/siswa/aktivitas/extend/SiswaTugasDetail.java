package com.example.schoolapp.views.siswa.aktivitas.extend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.schoolapp.models.siswa.SoalModels;
import com.example.schoolapp.views.siswa.aktivitas.SiswaTugasActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiswaTugasDetail extends AppCompatActivity {

    int position;
    String times = "HH:mm:ss";
    SimpleDateFormat sdfTime = new SimpleDateFormat(times);
    CardView jawabA, jawabB, jawabC, jawabD;
    TextView tvA, tvB, tvC, tvD, tvSoal, tvIdTugas, tvMapel, tvModul, tvIdSiswa, tvJam;
    List<SoalModels> questionItems = new ArrayList<>();
    private String GETDATA = Server.URL_API + "get_soal.php";
    private int questionNumber = 0;
    private int correct=0, wrong=0;
    SessionManager sessionManager;
    String getNis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_tugas_detail);

        sessionManager = new SessionManager(SiswaTugasDetail.this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getNis = user.get(SessionManager.NIS);

        jawabA = findViewById(R.id.cdA);
        jawabB = findViewById(R.id.cdB);
        jawabC = findViewById(R.id.cdC);
        jawabD = findViewById(R.id.cdD);
        tvA = findViewById(R.id.jawabA);
        tvB = findViewById(R.id.jawabB);
        tvC = findViewById(R.id.jawabC);
        tvD = findViewById(R.id.jawabD);
        tvSoal = findViewById(R.id.txt_soal);
        tvIdTugas = findViewById(R.id.tugas_id);
        tvMapel = findViewById(R.id.tugas_mapel);
        tvModul = findViewById(R.id.tugas_modul);
        tvIdSiswa = findViewById(R.id.siswa_id);
        tvJam = findViewById(R.id.tugas_jam);

        tvIdSiswa.setText(getNis);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tvIdTugas.setText(SiswaTugasActivity.tugasModelsArrayList.get(position).getId());
        tvMapel.setText(SiswaTugasActivity.tugasModelsArrayList.get(position).getPelajaran());
        tvModul.setText(SiswaTugasActivity.tugasModelsArrayList.get(position).getModul());

        Calendar c1 = Calendar.getInstance();
        String str1 = sdfTime.format(c1.getTime());
        tvJam.setText(str1);

        jawabA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionItems.get(questionNumber).getJawab1().equals(questionItems.get(questionNumber).getJawaban())){
                    correct++;
                }
                else{
                    wrong++;
                }
                if (questionNumber < questionItems.size()-1){
                    questionNumber++;
                    setPertanyaan();
                }
                else{
                    final String tugasID = tvIdTugas.getText().toString();
                    final String siswaID = tvIdSiswa.getText().toString();
                    final String mapel = tvMapel.getText().toString();
                    final String modul = tvModul.getText().toString();
                    Intent intent = new Intent(SiswaTugasDetail.this, SiswaTugasSelesai.class);
                    intent.putExtra("benar", correct);
                    intent.putExtra("salah", wrong);
                    intent.putExtra("idtugas", tugasID);
                    intent.putExtra("mapel", mapel);
                    intent.putExtra("modul", modul);
                    intent.putExtra("idsiswa", siswaID);
                    startActivity(intent);
                    finish();
                }
            }
        });

        jawabB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionItems.get(questionNumber).getJawab2().equals(questionItems.get(questionNumber).getJawaban())){
                    correct++;
                }
                else{
                    wrong++;
                }
                if (questionNumber < questionItems.size()-1){
                    questionNumber++;
                    setPertanyaan();
                }
                else{
                    final String tugasID = tvIdTugas.getText().toString();
                    final String siswaID = tvIdSiswa.getText().toString();
                    final String mapel = tvMapel.getText().toString();
                    final String modul = tvModul.getText().toString();
                    Intent intent = new Intent(SiswaTugasDetail.this, SiswaTugasSelesai.class);
                    intent.putExtra("benar", correct);
                    intent.putExtra("salah", wrong);
                    intent.putExtra("idtugas", tugasID);
                    intent.putExtra("mapel", mapel);
                    intent.putExtra("modul", modul);
                    intent.putExtra("idsiswa", siswaID);
                    startActivity(intent);
                    finish();
                }
            }
        });

        jawabC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionItems.get(questionNumber).getJawab3().equals(questionItems.get(questionNumber).getJawaban())){
                    correct++;
                }
                else{
                    wrong++;
                }
                if (questionNumber < questionItems.size()-1){
                    questionNumber++;
                    setPertanyaan();
                }
                else{
                    final String tugasID = tvIdTugas.getText().toString();
                    final String siswaID = tvIdSiswa.getText().toString();
                    final String mapel = tvMapel.getText().toString();
                    final String modul = tvModul.getText().toString();
                    Intent intent = new Intent(SiswaTugasDetail.this, SiswaTugasSelesai.class);
                    intent.putExtra("benar", correct);
                    intent.putExtra("salah", wrong);
                    intent.putExtra("idtugas", tugasID);
                    intent.putExtra("mapel", mapel);
                    intent.putExtra("modul", modul);
                    intent.putExtra("idsiswa", siswaID);
                    startActivity(intent);
                    finish();
                }
            }
        });

        jawabD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionItems.get(questionNumber).getJawab4().equals(questionItems.get(questionNumber).getJawaban())){
                    correct++;
                }
                else{
                    wrong++;
                }
                if (questionNumber < questionItems.size()-1){
                    questionNumber++;
                    setPertanyaan();
                }
                else{
                    final String tugasID = tvIdTugas.getText().toString();
                    final String siswaID = tvIdSiswa.getText().toString();
                    final String mapel = tvMapel.getText().toString();
                    final String modul = tvModul.getText().toString();
                    Intent intent = new Intent(SiswaTugasDetail.this, SiswaTugasSelesai.class);
                    intent.putExtra("benar", correct);
                    intent.putExtra("salah", wrong);
                    intent.putExtra("idtugas", tugasID);
                    intent.putExtra("mapel", mapel);
                    intent.putExtra("modul", modul);
                    intent.putExtra("idsiswa", siswaID);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        receiveData();
        super.onStart();
    }

    public void receiveData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . .");
        final String tugasID = tvIdTugas.getText().toString();
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, GETDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        questionItemArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String tugasid = object.getString("tugas_id");
                                    String no = object.getString("no");
                                    String pertanyaan = object.getString("pertanyaan");
                                    String jawab1 = object.getString("pilih1");
                                    String jawab2 = object.getString("pilih2");
                                    String jawab3 = object.getString("pilih3");
                                    String jawab4 = object.getString("pilih4");
                                    String jawaban = object.getString("jawaban");

                                    questionItems.add(new SoalModels(tugasid, no, pertanyaan, jawab1, jawab2, jawab3, jawab4, jawaban));
                                    setPertanyaan();
//                                    Toast.makeText(SiswaTugasDetail.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SiswaTugasDetail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tugas_id", tugasID);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setPertanyaan(){
        int number = questionNumber;
        tvSoal.setText(questionItems.get(number).getPertanyaan());
        tvA.setText(questionItems.get(number).getJawab1());
        tvB.setText(questionItems.get(number).getJawab2());
        tvC.setText(questionItems.get(number).getJawab3());
        tvD.setText(questionItems.get(number).getJawab4());
//            Collections.shuffle(questionItems);
    }
}