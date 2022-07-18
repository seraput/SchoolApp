package com.example.schoolapp.views.admin.informasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.views.admin.tugas.GuruTambahTugasActivity;
import com.example.schoolapp.views.admin.tugas.GuruTugasActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddInformasiGuru extends AppCompatActivity {

    MaterialEditText mtJudul, mtUcapan, mtIsi, mtPenting, mtPenutup, mtTanggal;
    Button btSimpan;
    SessionManager sessionManager;
    String myFormat = "yyyy-MM-dd";
    SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat);
    String create_date;
    private String store = Server.URL_API + "informasi/add_informasi.php";
    String guruID, guruNama;
    ImageView imgDate;
    Dialog dialog;
    Calendar myCalendar;
    String myFormat1 = "yyyy-MM-dd";
    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_add_informasi_activity);

        mtJudul = findViewById(R.id.judul);
        mtUcapan = findViewById(R.id.ucapan);
        mtIsi = findViewById(R.id.isi);
        mtPenutup = findViewById(R.id.penutup);
        mtPenting = findViewById(R.id.penting);
        mtTanggal = findViewById(R.id.tgl_expired);
        btSimpan = findViewById(R.id.info_simpan);
        imgDate = findViewById(R.id.img_select);

        sessionManager = new SessionManager(AddInformasiGuru.this);
//        SharedPreferences shared = SiswaTugasDetail.this.getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        guruID = user.get(SessionManager.NIS);
        guruNama = user.get(SessionManager.NAME);

        //Set Tanggal
        Calendar c1 = Calendar.getInstance();
        String str1 = sdfDate.format(c1.getTime());
        create_date = str1;

        //Select Tanggal
        myCalendar = Calendar.getInstance();

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddInformasiGuru.this, new DatePickerDialog.OnDateSetListener() {
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

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String judul = mtJudul.getText().toString();
                final String ucapan = mtUcapan.getText().toString();
                final String isi = mtIsi.getText().toString();
                final String penting = mtPenting.getText().toString();
                final String penutup = mtPenutup.getText().toString();
                final String created = guruNama;
                final String exp = mtTanggal.getText().toString();

                if (judul.isEmpty() || ucapan.isEmpty() || isi.isEmpty() || penting.isEmpty()
                        || penutup.isEmpty() || created.isEmpty() || exp.isEmpty())
                {
                    Toast.makeText(AddInformasiGuru.this, "Belum lengkap!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AddInformasiGuru.this, "Sudah Lengkap!", Toast.LENGTH_SHORT).show();
                    storeInfo();
                }
            }
        });


    }

    private void storeInfo() {
        final String judul = mtJudul.getText().toString();
        final String ucapan = mtUcapan.getText().toString();
        final String isi = mtIsi.getText().toString();
        final String penting = mtPenting.getText().toString();
        final String penutup = mtPenutup.getText().toString();
        final String created = guruNama;
        final String tgl = create_date;
        final String exp = mtTanggal.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(AddInformasiGuru.this);
        progressDialog.setMessage("Menyimpan . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, store,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(AddInformasiGuru.this, "Tersimpan!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddInformasiGuru.this, GuruInformasiActivity.class));
                        } else {
                            Toast.makeText(AddInformasiGuru.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddInformasiGuru.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("judul", judul);
                params.put("ucapan", ucapan);
                params.put("isi", isi);
                params.put("penutup", penutup);
                params.put("penting", penting);
                params.put("created", created);
                params.put("tanggal", tgl);
                params.put("expired", exp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddInformasiGuru.this);
        requestQueue.add(request);
    }

    private void showAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Yakin Ingin Membatalkan?");
        alertDialogBuilder
                .setMessage("Klik Ya untuk kembali")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        AddInformasiGuru.this.finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void back(View view) {
        showAlert();
    }
}