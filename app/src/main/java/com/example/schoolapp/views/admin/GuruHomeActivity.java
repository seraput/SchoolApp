package com.example.schoolapp.views.admin;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.schoolapp.R;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.views.admin.absensi.GuruAbsensiActivity;
import com.example.schoolapp.views.admin.datasiswa.GuruDataSiswaActivity;
import com.example.schoolapp.views.admin.informasi.GuruInformasiActivity;
import com.example.schoolapp.views.admin.koreksi.GuruKoreksiActivity;
import com.example.schoolapp.views.admin.rekap.GuruRekapActivity;
import com.example.schoolapp.views.admin.tugas.GuruTugasActivity;
import com.example.schoolapp.views.auth.AuthLoginActivity;
import com.example.schoolapp.views.siswa.home.SiswaHomeActivity;

import java.util.HashMap;


public class GuruHomeActivity extends AppCompatActivity {

    CardView cd1, cd2, cd3, cd4, cd5, cd6;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String getLevel;
    private long backPressedTime;
    private Toast backToast;
    Dialog dialog;
    CardView cdYes, cdNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guru_home_activity);

        sessionManager = new SessionManager(GuruHomeActivity.this);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getLevel = user.get(SessionManager.LEVEL);

        cd1 = findViewById(R.id.card1);
        cd2 = findViewById(R.id.card2);
        cd3 = findViewById(R.id.card3);
        cd4 = findViewById(R.id.card4);
        cd5 = findViewById(R.id.card5);
        cd6 = findViewById(R.id.card6);


        dialog = new Dialog(GuruHomeActivity.this);
        dialog.setContentView(R.layout.cust_alert_logout);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        cdYes = dialog.findViewById(R.id.btn_yes);
        cdNot = dialog.findViewById(R.id.btn_not);

        cdYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getResources().getString(R.string.prefLoginState), "LoggedOut");
                    editor.apply();
                    startActivity(new Intent(GuruHomeActivity.this, AuthLoginActivity.class));
                    finish();
            }
        });

        cdNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        String status = getLevel;
        if (status.equals("guru")){
            cd1.setVisibility(View.GONE);
            cd2.setVisibility(View.GONE);
            cd3.setVisibility(View.VISIBLE);
            cd4.setVisibility(View.VISIBLE);
            cd5.setVisibility(View.VISIBLE);
            cd6.setVisibility(View.VISIBLE);
        }
        else if (status.equals("admin")){
            cd1.setVisibility(View.VISIBLE);
            cd2.setVisibility(View.VISIBLE);
            cd3.setVisibility(View.VISIBLE);
            cd4.setVisibility(View.VISIBLE);
            cd5.setVisibility(View.VISIBLE);
            cd6.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(this, "Tidak Dapat Akses!", Toast.LENGTH_SHORT).show();
            logout();
        }

        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruInformasiActivity.class));
            }
        });

        cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruTugasActivity.class));
            }
        });

        cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruKoreksiActivity.class));
            }
        });

        cd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruAbsensiActivity.class));
            }
        });

        cd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruDataSiswaActivity.class));
            }
        });

        cd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuruHomeActivity.this, GuruRekapActivity.class));
            }
        });
    }


    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getResources().getString(R.string.prefLoginState), "LoggedOut");
        editor.apply();
        startActivity(new Intent(GuruHomeActivity.this, AuthLoginActivity.class));
        finish();
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

    public void setting(View view) {
        dialog.show();
    }
}