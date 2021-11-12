package com.example.schoolapp.views.admin.informasi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.siswa.AdapterListInformasi;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.models.siswa.InformationModels;
import com.example.schoolapp.views.admin.GuruHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GuruInformasiActivity extends AppCompatActivity {

    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    private long backPressedTime;
    private Toast backToast;
    private String getData = Server.URL_API + "get_informasi.php";
    private String HapusInfo = Server.URL_API + "nonaktifinfo.php";
    AdapterListInformasi adapterListInformasi;
    public static ArrayList<InformationModels> informationModelsArrayList = new ArrayList<>();
    InformationModels informationModels;
    private ListView listInformasi;
    ProgressBar progressBar;
    ImageView imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_informasi_activity);

        listInformasi = findViewById(R.id.list_informasi);
        progressBar = findViewById(R.id.progress);
        imgAdd = findViewById(R.id.img_add);

        adapterListInformasi = new AdapterListInformasi(GuruInformasiActivity.this, informationModelsArrayList);
        listInformasi.setAdapter(adapterListInformasi);
        receiveData();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });


        listInformasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                CharSequence[] dialogItem = {"Lihat", "Hapus"};
                builder.setTitle(informationModelsArrayList.get(position).getJudul());

                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int i) {
                        switch (i) {
                            case 0:
                                startActivity(new Intent(GuruInformasiActivity.this, GuruDetailInformasi.class)
                                    .putExtra("position", position));
                                break;
                            case 1:
                                final String id = informationModelsArrayList.get(position).getId();
                                final ProgressDialog progressDialog = new ProgressDialog(GuruInformasiActivity.this);
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
                                                        receiveData();
                                                        Toast.makeText(GuruInformasiActivity.this, "Berhasil..", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(GuruInformasiActivity.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println(error.toString());
                                                progressDialog.dismiss();
                                                Toast.makeText(GuruInformasiActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
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
                                RequestQueue requestQueue = Volley.newRequestQueue(GuruInformasiActivity.this);
                                requestQueue.add(stringRequest);
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
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

                                    if (jsonArray.length() < 1) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruInformasiActivity.this, "Belum Ada Data!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        informationModels = new InformationModels(id, judul, ucapan, isi, penutup, penting, created, tanggal);
                                        informationModelsArrayList.add(informationModels);
                                        adapterListInformasi.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruInformasiActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruInformasiActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(GuruInformasiActivity.this);
        requestQueue.add(request);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruInformasiActivity.this, GuruHomeActivity.class));
    }
}