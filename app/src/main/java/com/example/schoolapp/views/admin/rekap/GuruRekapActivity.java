package com.example.schoolapp.views.admin.rekap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.admin.AdapterDataSiswa;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.models.admin.DataSiswaModels;
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.admin.datasiswa.GuruDataSiswaActivity;
import com.example.schoolapp.views.admin.datasiswa.GuruDataSiswaDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuruRekapActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    private String getData = Server.URL_API + "siswa/get_siswa.php";
    AdapterDataSiswa adapterDataSiswa;
    public static ArrayList<DataSiswaModels> dataSiswaModelsArrayList = new ArrayList<>();
    DataSiswaModels dataSiswaModels;
    private ListView listSiswa;
    ProgressBar progressBar;
    ImageView imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_rekap_activity);


        listSiswa = findViewById(R.id.list_siswa);
        progressBar = findViewById(R.id.progress);
//        imgAdd = findViewById(R.id.img_add);

        adapterDataSiswa = new AdapterDataSiswa(GuruRekapActivity.this, dataSiswaModelsArrayList);
        listSiswa.setAdapter(adapterDataSiswa);
        receiveData();

        listSiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), GuruRekapDetailActivity.class).putExtra("position", position));
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
                        dataSiswaModelsArrayList.clear();
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String nama = object.getString("nama");
                                    String username = object.getString("username");
                                    String level = object.getString("level");
                                    String kelas = object.getString("kelas");
                                    String agama = object.getString("agama");
                                    String kelamin = object.getString("kelamin");
                                    String tanggal = object.getString("tanggal");
                                    String alamat = object.getString("alamat");
                                    String telp = object.getString("telp");
                                    String foto = object.getString("foto");

                                    if (jsonArray.length() < 1) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(GuruRekapActivity.this, "Belum Ada Data!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dataSiswaModels = new DataSiswaModels(id, nama, username, level, kelas, agama, kelamin, tanggal, alamat, telp, foto);
                                        dataSiswaModelsArrayList.add(dataSiswaModels);
                                        adapterDataSiswa.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(GuruRekapActivity.this, "Server Lagi Bermasalah Nih!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuruRekapActivity.this, "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(GuruRekapActivity.this);
        requestQueue.add(request);
    }

    public void back(View view) {
        startActivity(new Intent(GuruRekapActivity.this, GuruHomeActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuruRekapActivity.this, GuruHomeActivity.class));
    }
}