package com.example.schoolapp.views.admin.datasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.schoolapp.models.admin.AutoNumber;
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

public class TambahSiswaActivity extends AppCompatActivity {

    ImageView iBack, iSave, iDate;
    MaterialEditText mtUsername, mtNama, mtNis, mtKelas, mtAgama, mtKelamin, mtTanggal, mtTelp, mtLevel;
    Spinner spinKelamin, spinAgama, spinKelas, spinLevel;
    private String insertBio = Server.URL_API + "siswa/post_siswa.php";
    private String insertUser = Server.URL_API + "siswa/post_user.php";
    private String getNumber = Server.URL_API + "siswa/autonumber.php";
    AutoNumber autoNumber;
    public static ArrayList<AutoNumber> autoNumberArrayList = new ArrayList<>();
    String number = "";
    int hitung = 0;

    String myFormat1 = "yyyy-MM-dd";
    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1);
    Calendar myCalendar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_siswa);

        iDate = findViewById(R.id.img_select);
        iSave = findViewById(R.id.saved);
        iBack = findViewById(R.id.back);
        mtUsername = findViewById(R.id.username);
        mtNama = findViewById(R.id.namalengkap);
        mtNis = findViewById(R.id.nis);
        mtKelas = findViewById(R.id.kelas);
        mtAgama = findViewById(R.id.agama);
        mtKelamin = findViewById(R.id.kelamin);
        mtTanggal = findViewById(R.id.tanggal);
        mtTelp = findViewById(R.id.notlp);
        mtLevel = findViewById(R.id.level);

        spinKelamin = findViewById(R.id.spinkelamin);
        spinAgama = findViewById(R.id.spinagama);
        spinKelas = findViewById(R.id.spinkelas);
        spinLevel = findViewById(R.id.spinlevel);

        AutoNumber();

        myCalendar = Calendar.getInstance();

        iDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TambahSiswaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        mtTanggal.setText(sdf1.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        spinKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String txtKelamin = adapterView.getItemAtPosition(i).toString();
                mtKelamin.setText(txtKelamin);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinAgama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String txtAgama = adapterView.getItemAtPosition(i).toString();
                mtAgama.setText(txtAgama);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String txtKelas = adapterView.getItemAtPosition(i).toString();
                mtKelas.setText(txtKelas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String txtLevel = adapterView.getItemAtPosition(i).toString();
                mtLevel.setText(txtLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        iSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mtUsername.getText().toString();
                String nama = mtNama.getText().toString();
                String nis = mtNis.getText().toString();
                String kelas = mtKelas.getText().toString();
                String tanggal = mtTanggal.getText().toString();
                String agama = mtAgama.getText().toString();
                String kelamin = mtKelamin.getText().toString();
                String telp = mtTelp.getText().toString();
                String level = mtLevel.getText().toString();

                if (username.isEmpty() || nama.isEmpty() || nis.isEmpty() || kelas.equals("Pilih") || level.equals("Pilih") ||
                        tanggal.equals("-") || agama.equals("Pilih") || kelamin.equals("Pilih") || telp.equals("-"))
                {
                    Toast.makeText(TambahSiswaActivity.this, "Item harus terisi lengkap!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    InsertBio();
                }
            }
        });

        iBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }


    private void ConvertID(){
        int position = 0;
//        txtid.setText(autoNumberArrayList.get(position).getId());
        number = autoNumberArrayList.get(position).getId();

//        Toast.makeText(this, "ID: "+ number.toString(), Toast.LENGTH_SHORT).show();
        mtNis.setText(number);
//        getData();

    }

    public void AutoNumber(){
        final String txtstat = "Y";
        final ProgressDialog progressDialog = new ProgressDialog(TambahSiswaActivity.this);
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
                            Toast.makeText(TambahSiswaActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahSiswaActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(TambahSiswaActivity.this);
        requestQueue.add(request);
    }

    private void InsertBio() {
        String nis = mtNis.getText().toString();
        String kelas = mtKelas.getText().toString();
        String tanggal = mtTanggal.getText().toString();
        String agama = mtAgama.getText().toString();
        String kelamin = mtKelamin.getText().toString();
        String telp = mtTelp.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(TambahSiswaActivity.this);
        progressDialog.setMessage("Menyimpan Data . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, insertBio,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            InsertUser();
                            progressDialog.dismiss();
                            Toast.makeText(TambahSiswaActivity.this, "Selesai!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TambahSiswaActivity.this, GuruDataSiswaActivity.class));
                        } else {
                            Toast.makeText(TambahSiswaActivity.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahSiswaActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("kelas", kelas);
                params.put("agama", agama);
                params.put("kelamin", kelamin);
                params.put("tanggal", tanggal);
                params.put("telp", telp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TambahSiswaActivity.this);
        requestQueue.add(request);
    }

    private void InsertUser() {
        String username = mtUsername.getText().toString();
        String nama = mtNama.getText().toString();
        String nis = mtNis.getText().toString();
        String level = mtLevel.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, insertUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            AutoNumber();
                            Toast.makeText(TambahSiswaActivity.this, "Tersimpan!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TambahSiswaActivity.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahSiswaActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("nama", nama);
                params.put("username", username);
                params.put("level", level);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TambahSiswaActivity.this);
        requestQueue.add(request);
    }
}