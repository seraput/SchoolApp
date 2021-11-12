package com.example.schoolapp.views.auth;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.schoolapp.views.admin.GuruHomeActivity;
import com.example.schoolapp.views.siswa.home.SiswaHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AuthLoginActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    Button cdLogin, btTutup, btAktifkan;
    MaterialEditText mtEmail, mtPass;
    CheckBox loginstate;
    LinearLayout lupaPass;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    private String LoginAPI = Server.URL_API + "login.php";
    private String InsertAPI = Server.URL_API + "insert_token.php";
    private String UpdateData = Server.URL_API + "aktivasi.php";
    Dialog dialog, dialog2;
    String usertoken;
    EditText etNisn, etToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login_activity);

        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        cdLogin = findViewById(R.id.login_masuk);
        mtEmail = findViewById(R.id.login_email);
        mtPass = findViewById(R.id.login_pass);
        lupaPass = findViewById(R.id.login_lupa);
        loginstate = findViewById(R.id.login_state);

        checkPermission();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.cust_alert_connection);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.cust_alert_aktivasi);
        dialog2.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.setCancelable(false);

        btAktifkan = dialog2.findViewById(R.id.aktifkan);
        etNisn = dialog2.findViewById(R.id.dialog_nisn);
        etToken = dialog2.findViewById(R.id.dialog_token);
        btTutup = dialog.findViewById(R.id.tutup);

        String loginstatus = sharedPreferences.getString(getResources().getString(R.string.prefLoginState),"");
        if (loginstatus.equals("LoggedIn")){
            startActivity(new Intent(AuthLoginActivity.this, SiswaHomeActivity.class));
        }
        else if (loginstatus.equals("LoggedOn")){
            startActivity(new Intent(AuthLoginActivity.this, GuruHomeActivity.class));
        }

        cdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mtEmail.getText().toString();
                String pass = mtPass.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(AuthLoginActivity.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginProses(email, pass);
                }
            }
        });

        lupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        btTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                haveNetwork();
            }
        });

        btAktifkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nisn = etNisn.getText().toString();
                final String token = etToken.getText().toString();
                if (nisn.isEmpty() || token.isEmpty()){
                    Toast.makeText(AuthLoginActivity.this, "Ada Sedikit Masalah, Nanti Coba Lagi!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
                else{
                    InsertToken();
                }
            }
        });
    }

    private void checkPermission(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }
        };
        TedPermission.with(AuthLoginActivity.this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
                .check();
    }

    private boolean haveNetwork(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }


    private void LoginProses(final String email, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(AuthLoginActivity.this);
        final Handler handler = new Handler();
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Tunggu Sebentar Ya . . .");
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginAPI,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                                    if (success.equals("1")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String nis = object.getString("nis").trim();
                                            String nama = object.getString("nama").trim();
                                            String email = object.getString("email").trim();
                                            String level = object.getString("level").trim();
                                            String status = object.getString("status").trim();

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            if (loginstate.isChecked() && level.equals("siswa")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedIn");
                                            }else if (loginstate.isChecked() && level.equals("admin")){
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOn");
                                            }
                                            else {
                                                editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
                                            }

//                                            final String getid = nis;
                                            if (status.equals("N")){
                                                progressDialog.dismiss();
                                                dialog2.show();

                                                // for sending notification to all
                                                FirebaseMessaging.getInstance().subscribeToTopic("all");
                                                //         fcm settings for perticular user
                                                FirebaseInstanceId.getInstance().getInstanceId()
                                                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                                if (task.isSuccessful()) {
                                                                    usertoken = Objects.requireNonNull(task.getResult()).getToken();
                                                                    Log.d("toooo", "token "+ usertoken);
                                                                    etToken.setText(usertoken);
                                                                    etNisn.setText(nis);
                                                                }
                                                                else{
                                                                    Toast.makeText(AuthLoginActivity.this, "Filed Generate Token!", Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });
                                            }
                                            else{

                                                if (level.equals("siswa")) {
                                                    sessionManager.createSession(nis, nama, email, level);
                                                    editor.apply();
                                                    progressDialog.dismiss();
                                                    final Intent inte = new Intent(AuthLoginActivity.this, SiswaHomeActivity.class);
                                                    startActivity(inte);
                                                    finish();
                                                }else if (level.equals("admin")){
                                                    sessionManager.createSession(nis, nama, email, level);
                                                    editor.apply();
                                                    progressDialog.dismiss();
                                                    final Intent in = new Intent(AuthLoginActivity.this, GuruHomeActivity.class);
                                                    startActivity(in);
                                                    finish();
                                                }
                                                else{
                                                    progressDialog.dismiss();
                                                    editor.putString(getResources().getString(R.string.prefLoginState),"LoggedOut");
                                                }

                                            }

                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                    Toast.makeText(AuthLoginActivity.this, "Email atau Password Kamu Salah!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(AuthLoginActivity.this, "Error, Koneksi Bermasalah!" +error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(AuthLoginActivity.this);
                requestQueue.add(stringRequest);
            }
        }, 2000);

    }


    public void UpdateUser() {
        final String nis = etNisn.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(AuthLoginActivity.this);
        progressDialog.setMessage("Aktivasi Proses ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdateData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(AuthLoginActivity.this, "Berhasil, Silahkan Login Kembali!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AuthLoginActivity.this, AuthLoginActivity.class));
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            Toast.makeText(AuthLoginActivity.this, "Internet Terputus ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(AuthLoginActivity.this, "Error Server", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(AuthLoginActivity.this);
        requestQueue.add(stringRequest);

    }


    private void InsertToken() {
        final String nis = etNisn.getText().toString();
        final String token = etToken.getText().toString();
        final String status = "Y";
        final ProgressDialog progressDialog = new ProgressDialog(AuthLoginActivity.this);
        progressDialog.setMessage("Menyesuaikan Data ...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, InsertAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("success")) {
                            progressDialog.dismiss();
                            UpdateUser();
//                            Toast.makeText(AuthLoginActivity.this, "Data Oke!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AuthLoginActivity.this, "Gagal, Internet Bermasalah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AuthLoginActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("token", token);
                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AuthLoginActivity.this);
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

    public void aktivasi(View view) {
        startActivity(new Intent(AuthLoginActivity.this, AuthAktivasiActivity.class));
    }
}