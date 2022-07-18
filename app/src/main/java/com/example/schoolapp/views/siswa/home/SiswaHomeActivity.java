package com.example.schoolapp.views.siswa.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.siswa.AdapterListInformasi;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.models.siswa.BiodataModels;
import com.example.schoolapp.models.siswa.InformationModels;
import com.example.schoolapp.views.admin.informasi.AddInformasiGuru;
import com.example.schoolapp.views.admin.informasi.GuruInformasiActivity;
import com.example.schoolapp.views.auth.AuthLoginActivity;
import com.example.schoolapp.views.siswa.aktivitas.SiswaTugasActivity;
import com.example.schoolapp.views.siswa.aktivitas.extend.SiswaTugasSelesai;
import com.example.schoolapp.views.siswa.history.SiswaHistoryActivity;
import com.example.schoolapp.views.siswa.home.extend.SiswaDetailInformasi;
import com.example.schoolapp.views.siswa.home.extend.SiswaProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class SiswaHomeActivity extends AppCompatActivity {

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    private long backPressedTime;
    private Toast backToast;
    String time = "HH";
    String times = "HH:mm";
    SimpleDateFormat sdfTime = new SimpleDateFormat(time);
    SimpleDateFormat sdfTimes = new SimpleDateFormat(times);
    String myFormat = "yyyy-MM-dd";
    SimpleDateFormat sdfDate = new SimpleDateFormat(myFormat);
    String tanggal = "";
    private TextView tvGreet, tvName, tvjam;
    String timeGone, getNis, getNama, Absensi;
    Dialog dialog;
    private ImageView imgprofile, imgDialog,closeD;
    private String getData = Server.URL_API + "informasi/get_informasi.php";
    private String getBio = Server.URL_API + "auth/get_biodata.php";
    private String absen = Server.URL_API + "auth/absen_siswa.php";
    private String store = Server.URL_API + "insert_absensi.php";
    AdapterListInformasi adapterListInformasi;
    public static ArrayList<InformationModels> informationModelsArrayList = new ArrayList<>();
    InformationModels informationModels;
    public static ArrayList<BiodataModels> biodataModelsArrayList = new ArrayList<>();
    private BiodataModels biodataModels;
    private ListView listInformasi;
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;
    private CardView cdAbsen, cdAbsen2, editD, logoutD;
    private TextView tvDnis, tvDnama, tvDtgl, tvDagama, tvDkelamin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_home_activity);

        tvGreet = findViewById(R.id.tvGreet);
        tvjam = findViewById(R.id.txt_jam);
        imgprofile = findViewById(R.id.imgProfile);
        listInformasi = findViewById(R.id.list_information);
        cdAbsen = findViewById(R.id.absen_siswa);
        cdAbsen2 = findViewById(R.id.absen_siswa2);
        tvName = findViewById(R.id.home_txt_name);
        progressBar = findViewById(R.id.progress);

//
        adapterListInformasi = new AdapterListInformasi(SiswaHomeActivity.this, informationModelsArrayList);
        listInformasi.setAdapter(adapterListInformasi);

        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String tgl = biodataModels.getTanggal().toString();
                String agama = biodataModels.getAgama().toString();
                String kelamin = biodataModels.getKelamin().toString();

                tvDtgl.setText(tgl);
                tvDagama.setText(agama);
                tvDkelamin.setText(kelamin);
            }
        });

        cdAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeAbsen();
            }
        });

        cdAbsen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SiswaHomeActivity.this, "Kamu Sudah Absen..", Toast.LENGTH_SHORT).show();
            }
        });

        Calendar c1 = Calendar.getInstance();
        String str1 = sdfTime.format(c1.getTime());
        String str2 = sdfTimes.format(c1.getTime());
        String str3 = sdfDate.format(c1.getTime());
        tanggal = str3;
        tvjam.setText(str2);
        timeGone = str1;
        greeting();

        listInformasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(SiswaHomeActivity.this, SiswaDetailInformasi.class)
                        .putExtra("position", position));
            }
        });

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(),
//                                AdminMainActivity.class));
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.tugas:
                        if (Absensi.equals("N")){
                            Toast.makeText(SiswaHomeActivity.this, "Kamu Harus Absen!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(),
                                    SiswaTugasActivity.class));
                            overridePendingTransition(0,0);
                        }
                        return true;

                    case R.id.history:
                        if (Absensi.equals("N")){
                            Toast.makeText(SiswaHomeActivity.this, "Kamu Harus Absen!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(),
                                    SiswaHistoryActivity.class));
                            overridePendingTransition(0,0);
                        }
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

        Log.i(TAG, "OnCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "OnStart");

        sessionManager = new SessionManager(SiswaHomeActivity.this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getNis = user.get(SessionManager.NIS);
        getNama = user.get(SessionManager.NAME);
        tvName.setText(getNama);
//
        receiveData();
        getBiodata();
//        AbsenSiswa();


        dialog = new Dialog(SiswaHomeActivity.this);
        dialog.setContentView(R.layout.cust_alert);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        imgDialog = dialog.findViewById(R.id.img_dialog);
        editD = dialog.findViewById(R.id.edit_dialog);
        closeD = dialog.findViewById(R.id.close);
        logoutD = dialog.findViewById(R.id.logout_dialog);
        tvDnis = dialog.findViewById(R.id.dialog_profile_nis);
        tvDnama = dialog.findViewById(R.id.dialog_profile_nama);
        tvDtgl = dialog.findViewById(R.id.dialog_profile_tgl);
        tvDagama = dialog.findViewById(R.id.dialog_profile_agama);
        tvDkelamin = dialog.findViewById(R.id.dialog_profile_kelamin);



        closeD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(SiswaHomeActivity.this, SiswaProfileActivity.class));
            }
        });

        logoutD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                logout();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume");
        adapterListInformasi = new AdapterListInformasi(SiswaHomeActivity.this, informationModelsArrayList);
        listInformasi.setAdapter(adapterListInformasi);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "OnRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"OnStop");
    }

    private void greeting(){
        final String sjam = timeGone;
        //cek jam
        int jam = Integer.parseInt(sjam);
        if(jam >= 10 && jam < 14){
            tvGreet.setText("Selamat Siang");
        } else if(jam >= 15 && jam < 19){
            tvGreet.setText("Selamat Sore");
        } else if(jam >= 19 && jam < 24){
            tvGreet.setText("Selamat Malam");
        } else {
            tvGreet.setText("Selamat Pagi");
        }
    }



    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState), "LoggedOut");
        editor.apply();
        startActivity(new Intent(SiswaHomeActivity.this, AuthLoginActivity.class));
        finish();
    }

    public void receiveData(){
        final String txtstat = "Y";
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, getData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        informationModelsArrayList.clear();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String judul = object.getString("judul");
                                    String ucapan = object.getString("ucapan");
                                    String isi = object.getString("isi");
                                    String penutup = object.getString("penutup");
                                    String penting = object.getString("penting");
                                    String created = object.getString("created");
                                    String tanggal = object.getString("tanggal");
                                    String status = object.getString("status");

                                    if (jsonArray.length() < 1) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SiswaHomeActivity.this, "Belum Ada Data!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        informationModels = new InformationModels(id, judul, ucapan, isi, penutup, penting, created, tanggal,status);
                                        informationModelsArrayList.add(informationModels);
                                        adapterListInformasi.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(SiswaHomeActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SiswaHomeActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SiswaHomeActivity.this);
        requestQueue.add(request);
    }


    public void getBiodata(){
        final String txtnis = getNis;
        StringRequest request = new StringRequest(Request.Method.POST, getBio,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        biodataModelsArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nis = object.getString("nis");
                                    String nama = object.getString("nama");
                                    String username = object.getString("username");
                                    String kelas = object.getString("kelas");
                                    String agama = object.getString("agama");
                                    String kelamin = object.getString("kelamin");
                                    String tanggal = object.getString("tanggal");
                                    String alamat = object.getString("alamat");
                                    String telp = object.getString("telp");
                                    String foto = object.getString("foto");
                                    String absen = object.getString("absen");

                                    if (jsonArray.length() < 1) {
                                        Toast.makeText(SiswaHomeActivity.this, "Terjadi Kesalahan!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        biodataModels = new BiodataModels(nis, nama, username, kelas, agama, kelamin, tanggal, alamat, telp, foto, absen);
                                        biodataModelsArrayList.add(biodataModels);
                                        Absensi = absen;
//                                        Toast.makeText(SiswaHomeActivity.this, "Absen = "+Absensi, Toast.LENGTH_SHORT).show();
                                        final String url = foto;
                                        Glide.with(getApplicationContext()).load(url).apply(RequestOptions.circleCropTransform()).into(imgprofile);
                                        Glide.with(getApplicationContext()).load(url).apply(RequestOptions.circleCropTransform()).into(imgDialog);
                                        if (absen.equals("Y")){
                                            cdAbsen.setVisibility(View.GONE);
                                            cdAbsen2.setVisibility(View.VISIBLE);
                                        }
                                        else{
                                            cdAbsen.setVisibility(View.VISIBLE);
                                            cdAbsen2.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(SiswaHomeActivity.this, "Server Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SiswaHomeActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nis", txtnis);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SiswaHomeActivity.this);
        requestQueue.add(request);
    }

    public void AbsenSiswa() {
        final String nis = getNis;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, absen,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(SiswaHomeActivity.this, "Success...", Toast.LENGTH_SHORT).show();
                                getBiodata();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(SiswaHomeActivity.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(SiswaHomeActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nis", nis);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SiswaHomeActivity.this);
        requestQueue.add(stringRequest);

    }


    private void storeAbsen() {
        final String nis = getNis;
        final String tgl = tanggal;
        final String jam = tvjam.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(SiswaHomeActivity.this);
        progressDialog.setMessage("Menyimpan . . .");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, store,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            progressDialog.dismiss();
//                            Toast.makeText(SiswaHomeActivity.this, "Wait...", Toast.LENGTH_SHORT).show();
                            AbsenSiswa();
//                            getBiodata();
                        } else {
                            Toast.makeText(SiswaHomeActivity.this, "Gagal, Coba Kembali!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SiswaHomeActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("tanggal", tgl);
                params.put("jam", jam);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SiswaHomeActivity.this);
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