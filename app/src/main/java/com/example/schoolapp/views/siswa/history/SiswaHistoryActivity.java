package com.example.schoolapp.views.siswa.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.schoolapp.R;
import com.example.schoolapp.views.siswa.aktivitas.SiswaTugasActivity;
import com.example.schoolapp.views.siswa.history.extend.SiswaHistoryDikoreksiFragment;
import com.example.schoolapp.views.siswa.history.extend.SiswaHistoryTerkirimFragment;
import com.example.schoolapp.views.siswa.home.SiswaHomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class SiswaHistoryActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_history_activity);

        animatedBottomBar = findViewById(R.id.top_nav);

        if (savedInstanceState==null){
            animatedBottomBar.selectTabById(R.id.terkirim, true);
            fragmentManager = getSupportFragmentManager();
            SiswaHistoryTerkirimFragment siswaHistoryTerkirimFragment = new SiswaHistoryTerkirimFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, siswaHistoryTerkirimFragment)
                    .commit();
        }

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()){
                    case R.id.terkirim:
                        fragment = new SiswaHistoryTerkirimFragment();
                        break;
                    case R.id.dikoreksi:
                        fragment = new SiswaHistoryDikoreksiFragment();
                        break;
                }
                if (fragment!=null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit();
                }
                else{
                    Toast.makeText(SiswaHistoryActivity.this, "Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });

        //ButtomNav
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                SiswaHomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.tugas:
                        startActivity(new Intent(getApplicationContext(),
                                SiswaTugasActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.history:
//                        startActivity(new Intent(getApplicationContext(),
//                                SiswaHistoryActivity.class));
//                        overridePendingTransition(0,0);
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