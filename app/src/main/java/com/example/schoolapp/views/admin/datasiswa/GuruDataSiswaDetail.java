package com.example.schoolapp.views.admin.datasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.admin.informasi.GuruInformasiActivity;
import com.example.schoolapp.views.admin.tugas.GuruTambahTugasActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GuruDataSiswaDetail extends AppCompatActivity {

    MaterialEditText mtUsername, mtNama, mtNis, mtKelas, mtAgama, mtKelamin, mtTanggal, mtTelp;
//    Button btSimpan, btUbah;
    int position;
    Spinner spinKelamin, spinAgama;
    private String putData = Server.URL_API + "siswa/edit_siswa.php";

    String myFormat1 = "yyyy-MM-dd";
    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1);
    Calendar myCalendar;
    ImageView imgDate, imgSaved, imgEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_data_siswa_detail_activity);

        imgSaved = findViewById(R.id.saved);
        imgEdit = findViewById(R.id.updated);

        mtUsername = findViewById(R.id.username);
        mtNama = findViewById(R.id.namalengkap);
        mtNis = findViewById(R.id.nis);
        mtKelas = findViewById(R.id.kelas);
        mtAgama = findViewById(R.id.agama);
        mtKelamin = findViewById(R.id.kelamin);
        mtTanggal = findViewById(R.id.tanggal);
        mtTelp = findViewById(R.id.notlp);

        spinKelamin = findViewById(R.id.spin_kelamin);
        spinAgama = findViewById(R.id.spin_agama);

        imgDate = findViewById(R.id.img_select);
        myCalendar = Calendar.getInstance();

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GuruDataSiswaDetail.this, new DatePickerDialog.OnDateSetListener() {
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

        imgSaved.setOnClickListener(new View.OnClickListener() {
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

                if (username.isEmpty() || nama.isEmpty() || nis.isEmpty() || kelas.isEmpty() ||
                        tanggal.equals("-") || agama.equals("Pilih") || kelamin.equals("Pilih") || telp.equals("-"))
                {
                    Toast.makeText(GuruDataSiswaDetail.this, "Item harus terisi lengkap!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    put();
                }
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable();//enabled fun
                imgSaved.setVisibility(View.VISIBLE);
                imgEdit.setVisibility(View.GONE);
            }
        });



        disable();//disable fun

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        mtUsername.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getUsername());
        mtNama.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getNama());
        mtNis.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getId());
        mtKelas.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getKelas());
        mtTanggal.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getTanggal());
        mtKelamin.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getKelamin());
        mtAgama.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getAgama());
        mtTelp.setText(GuruDataSiswaActivity.dataSiswaModelsArrayList.get(position).getTelp());
    }

    private void disable(){
        mtNama.setEnabled(false);
        mtUsername.setEnabled(false);
        mtKelas.setEnabled(false);
        mtTanggal.setEnabled(false);
        mtKelamin.setEnabled(false);
        mtTelp.setEnabled(false);
        mtNis.setEnabled(false);
        mtAgama.setEnabled(false);
        spinAgama.setVisibility(View.GONE);
        spinKelamin.setVisibility(View.GONE);
        imgDate.setVisibility(View.GONE);
    }

    private void enable(){
        mtNama.setEnabled(true);
        mtUsername.setEnabled(false);
        mtKelas.setEnabled(false);
        mtTelp.setEnabled(true);
        mtNis.setEnabled(false);
        spinAgama.setVisibility(View.VISIBLE);
        spinKelamin.setVisibility(View.VISIBLE);
        imgDate.setVisibility(View.VISIBLE);
    }

    private void put(){
        String nis = mtNis.getText().toString();
        String tanggal = mtTanggal.getText().toString();
        String agama = mtAgama.getText().toString();
        String kelamin = mtKelamin.getText().toString();
        String telp = mtTelp.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(GuruDataSiswaDetail.this);
        progressDialog.setMessage("Tunggu Sebentar...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, putData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(GuruDataSiswaDetail.this, "Berhasil..", Toast.LENGTH_SHORT).show();
                                disable();
                                imgSaved.setVisibility(View.GONE);
                                imgEdit.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(GuruDataSiswaDetail.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(GuruDataSiswaDetail.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nis", nis);
                params.put("agama", agama);
                params.put("kelamin", kelamin);
                params.put("tanggal", tanggal);
                params.put("telp", telp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GuruDataSiswaDetail.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruDataSiswaDetail.this, GuruDataSiswaActivity.class));
    }

    public void back1(View view) {
        startActivity(new Intent(GuruDataSiswaDetail.this, GuruDataSiswaActivity.class));
    }
}