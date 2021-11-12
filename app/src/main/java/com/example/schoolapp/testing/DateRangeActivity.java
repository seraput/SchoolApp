package com.example.schoolapp.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.admin.AdapterListSoal;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.models.admin.AutoNumber;
import com.example.schoolapp.models.siswa.SoalModels;
import com.example.schoolapp.views.admin.tugas.GuruTambahTugasActivity;
import com.example.schoolapp.views.admin.tugas.GuruTugasActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DateRangeActivity extends AppCompatActivity {


    MaterialEditText meID, meModul, meMapel, meTanggal, mePertanyaan, meJawaban, mePilihA, mePilihB, mePilihC, mePilihD;
    ImageView imgTop, imgBot, imgTop1, imgBot1;
    ListView listSoal;
    Button btnSimpan;
    TextView txt_tgl;
    SessionManager sessionManager;
    String myFormat = "yyyy-MM-dd";
    SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat);
    private String InsertSoal = Server.URL_API + "insert_pertanyaan.php";
    private String InsertTugas = Server.URL_API + "insert_tugas.php";
    private String getNumber = Server.URL_API + "autonumber.php";
    String guruID, guruNama;
    private String getSoal = Server.URL_API + "get_soal.php";
    private String HapusInfo = Server.URL_API + "delete_soal.php";
    AdapterListSoal adapterListSoal;
    public static ArrayList<SoalModels> soalModelsArrayList = new ArrayList<>();
    SoalModels soalModels;
    AutoNumber autoNumber;
    public static ArrayList<AutoNumber> autoNumberArrayList = new ArrayList<>();
    Dialog dialog;
    TextView tvID;
    CardView cvYes, cvNot;
    String number = "";
    String myFormat1 = "yyyy-MM-dd";
    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1);
    Calendar myCalendar;
    ImageView imgSelect, imgSaved;
    LinearLayout linTgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_range);


        imgBot = findViewById(R.id.arrBot);
        imgTop = findViewById(R.id.arrTop);
        meID = findViewById(R.id.tugas_id);
        meMapel = findViewById(R.id.tugas_mapel);
        meModul = findViewById(R.id.tugas_modul);
        meTanggal = findViewById(R.id.tugas_expired);
        imgBot1 = findViewById(R.id.arrBot1);
        imgTop1 = findViewById(R.id.arrTop1);
        meJawaban = findViewById(R.id.tugas_jawaban);
        mePertanyaan = findViewById(R.id.tugas_pertanyaan);
        mePilihA = findViewById(R.id.tugas_pilihA);
        mePilihB = findViewById(R.id.tugas_pilihB);
        mePilihC = findViewById(R.id.tugas_pilihC);
        mePilihD = findViewById(R.id.tugas_pilihD);
        listSoal = findViewById(R.id.list_soal);
        btnSimpan = findViewById(R.id.tugas_simpan);
        txt_tgl = findViewById(R.id.txtTgl);
        imgSelect = findViewById(R.id.img_select);
        linTgl = findViewById(R.id.lintgl);
        imgSaved = findViewById(R.id.imgSimpan);


        dialog = new Dialog(DateRangeActivity.this);
        dialog.setContentView(R.layout.cust_alert_delete);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        cvYes = dialog.findViewById(R.id.btn_yes);
        cvNot= dialog.findViewById(R.id.btn_not);
        tvID = dialog.findViewById(R.id.dialog_tugas_id);

        sessionManager = new SessionManager(DateRangeActivity.this);
//        SharedPreferences shared = SiswaTugasDetail.this.getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        guruID = user.get(SessionManager.NIS);
        guruNama = user.get(SessionManager.NAME);

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdfDate.format(c1.getTime());
        txt_tgl.setText(str1);

        //Select Tanggal
        myCalendar = Calendar.getInstance();

        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DateRangeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        meTanggal.setText(sdf1.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        AutoNumber();

//        final String idt = txtid.getText().toString();
//        Toast.makeText(this, "id: "+idt, Toast.LENGTH_SHORT).show();

        adapterListSoal = new AdapterListSoal(getApplicationContext(), soalModelsArrayList);
        listSoal.setAdapter(adapterListSoal);

        imgSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = meID.getText().toString();
                String modul = meModul.getText().toString();
                String mapel = meMapel.getText().toString();
                String tanggal = meTanggal.getText().toString();

                if (id.isEmpty() || modul.isEmpty() || mapel.isEmpty() || tanggal.isEmpty()){
                    Toast.makeText(DateRangeActivity.this, "Data Tugas Belum Lengkap!", Toast.LENGTH_LONG).show();
                }
                else{
                    InsertTugas();
                }
            }
        });

        listSoal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.show();
                tvID.setText(soalModelsArrayList.get(position).getNo());
            }
        });

        cvNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = tvID.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(DateRangeActivity.this);
                progressDialog.setMessage("Tunggu Sebentar...");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, HapusInfo,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    if (success.equals("1")){
                                        getData();
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(DateRangeActivity.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.toString());
                                progressDialog.dismiss();
                                Toast.makeText(DateRangeActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
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
                RequestQueue requestQueue = Volley.newRequestQueue(DateRangeActivity.this);
                requestQueue.add(stringRequest);
            }
        });
    }


    private void ConvertID(){
        int position = 0;
//        txtid.setText(autoNumberArrayList.get(position).getId());
        number = autoNumberArrayList.get(position).getId();
//        Toast.makeText(this, "ID: "+ number.toString(), Toast.LENGTH_SHORT).show();
        meID.setText(number);
        getData();
//        String con = id;
//        int number = Integer.parseInt(con);
//        int tambah = number + 1;
//        String tes = String.valueOf(tambah);

    }


    public void AutoNumber(){
        final String txtstat = "Y";
        final ProgressDialog progressDialog = new ProgressDialog(DateRangeActivity.this);
        progressDialog.setMessage("Tunggu Sebentar . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, getNumber,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        autoNumberArrayList.clear();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");

                                    String con = id;
                                    int number = Integer.parseInt(con);
                                    int tambah = number + 1;
                                    String tes = String.valueOf(tambah);
                                    autoNumber = new AutoNumber(tes);
                                    autoNumberArrayList.add(autoNumber);
                                    ConvertID();

//                                    txtid.setText(id);
                                    progressDialog.dismiss();

                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(DateRangeActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DateRangeActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("status", txtstat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DateRangeActivity.this);
        requestQueue.add(request);
    }


    private void InsertPertanyaan() {
        final String tugasid = meID.getText().toString();
        final String pertanyaan = mePertanyaan.getText().toString();
        final String pilih1 = mePilihA.getText().toString();
        final String pilih2 = mePilihB.getText().toString();
        final String pilih3 = mePilihC.getText().toString();
        final String pilih4 = mePilihD.getText().toString();
        final String jawaban = meJawaban.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(DateRangeActivity.this);
        progressDialog.setMessage("Menyimpan . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, InsertSoal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            clearFieldSoal();
                            getData();
                            progressDialog.dismiss();
                            Toast.makeText(DateRangeActivity.this, "Tersimpan!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DateRangeActivity.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DateRangeActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tugas_id", tugasid);
                params.put("pertanyaan", pertanyaan);
                params.put("pilih1", pilih1);
                params.put("pilih2", pilih2);
                params.put("pilih3", pilih3);
                params.put("pilih4", pilih4);
                params.put("jawaban", jawaban);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DateRangeActivity.this);
        requestQueue.add(request);
    }

    private void InsertTugas() {
        final String tugasid = meID.getText().toString();
        final String modul = meModul.getText().toString();
        final String pelajaran = meMapel.getText().toString();
        final String tgladd = txt_tgl.getText().toString();
        final String tanggal = meTanggal.getText().toString();
        final String guruid = guruID;
        final String gurunama = guruNama;

        final ProgressDialog progressDialog = new ProgressDialog(DateRangeActivity.this);
        progressDialog.setMessage("Menyimpan . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, InsertTugas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            AutoNumber();
                            progressDialog.dismiss();
                            Toast.makeText(DateRangeActivity.this, "Tersimpan!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DateRangeActivity.this, GuruTugasActivity.class));
                        } else {
                            Toast.makeText(DateRangeActivity.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DateRangeActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", tugasid);
                params.put("mapel", pelajaran);
                params.put("modul", modul);
                params.put("tglAdd", tgladd);
                params.put("tanggal", tanggal);
                params.put("guruid", guruid);
                params.put("gurunama", gurunama);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DateRangeActivity.this);
        requestQueue.add(request);
    }



    public void getData(){
        final String tugas = number;
        StringRequest request = new StringRequest(Request.Method.POST, getSoal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        soalModelsArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String no = object.getString("no");
                                    String tugasid = object.getString("tugas_id");
                                    String pertanyaan = object.getString("pertanyaan");
                                    String jawab1 = object.getString("pilih1");
                                    String jawab2 = object.getString("pilih2");
                                    String jawab3 = object.getString("pilih3");
                                    String jawab4 = object.getString("pilih4");
                                    String jawaban = object.getString("jawaban");

                                    if (jsonArray.length() < 1) {
                                        Toast.makeText(DateRangeActivity.this, "Belum Ada Tugas!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        soalModels = new SoalModels(tugasid, no, pertanyaan, jawab1, jawab2, jawab3, jawab4, jawaban);
                                        soalModelsArrayList.add(soalModels);
                                        adapterListSoal.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(DateRangeActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DateRangeActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tugas_id", tugas);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DateRangeActivity.this);
        requestQueue.add(request);
    }

    private void clearFieldSoal(){
        mePertanyaan.setText("");
        mePilihA.setText("");
        mePilihB.setText("");
        mePilihC.setText("");
        mePilihD.setText("");
        meJawaban.setText("");
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), GuruTugasActivity.class));
    }

    public void arrTop(View view) {
        imgTop.setVisibility(View.GONE);
        imgBot.setVisibility(View.VISIBLE);
        meID.setVisibility(View.GONE);
        linTgl.setVisibility(View.GONE);
        meModul.setVisibility(View.GONE);
        meMapel.setVisibility(View.GONE);
    }

    public void arrBot(View view) {
        imgTop.setVisibility(View.VISIBLE);
        imgBot.setVisibility(View.GONE);
        meID.setVisibility(View.VISIBLE);
        linTgl.setVisibility(View.VISIBLE);
        meModul.setVisibility(View.VISIBLE);
        meMapel.setVisibility(View.VISIBLE);
    }

    public void arrTop1(View view) {
        imgTop1.setVisibility(View.GONE);
        imgBot1.setVisibility(View.VISIBLE);
        mePertanyaan.setVisibility(View.GONE);
        meJawaban.setVisibility(View.GONE);
        mePilihA.setVisibility(View.GONE);
        mePilihB.setVisibility(View.GONE);
        mePilihC.setVisibility(View.GONE);
        mePilihD.setVisibility(View.GONE);
        btnSimpan.setVisibility(View.GONE);
    }

    public void arrBot1(View view) {
        imgTop1.setVisibility(View.VISIBLE);
        imgBot1.setVisibility(View.GONE);
        mePertanyaan.setVisibility(View.VISIBLE);
        meJawaban.setVisibility(View.VISIBLE);
        mePilihA.setVisibility(View.VISIBLE);
        mePilihB.setVisibility(View.VISIBLE);
        mePilihC.setVisibility(View.VISIBLE);
        mePilihD.setVisibility(View.VISIBLE);
        btnSimpan.setVisibility(View.VISIBLE);
    }

    public void SimpanSoal(View view) {
        String id = meID.getText().toString();
        String pertanyaan = mePertanyaan.getText().toString();
        String pilih1 = mePilihA.getText().toString();
        String pilih2 = mePilihB.getText().toString();
        String pilih3 = mePilihC.getText().toString();
        String pilih4 = mePilihD.getText().toString();
        String jawaban = meJawaban.getText().toString();

        if (id.isEmpty() || pertanyaan.isEmpty() || pilih1.isEmpty() || pilih2.isEmpty() || pilih3.isEmpty() || pilih4.isEmpty() || jawaban.isEmpty()){
            Toast.makeText(this, "Harap Lengkapi Data!", Toast.LENGTH_SHORT).show();
        }
        else{
            InsertPertanyaan();
        }
    }
}