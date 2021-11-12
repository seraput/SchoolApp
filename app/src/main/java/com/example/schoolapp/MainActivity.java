package com.example.schoolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schoolapp.testing.FcmNotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btSubmit, btTarget;
    MaterialEditText mtTitle, mtBody, mtToken;
    String usertoken;
    String duptok = "d69z0_UZD8A:APA91bH4wJuVY6pUedGee4ytZwak5w-pMEr4GzaSxftuwEEhA-1YVVft_35pRvMZYixfDSg0OO70I2IpgB06is7GD_NtePVPjvqjumZVHbv36TGeUOVEXfw3FLzE8_EQzsLI9goZBXCW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtTitle = findViewById(R.id.et_title);
        mtBody = findViewById(R.id.et_body);
        btSubmit = findViewById(R.id.bt_submit);
        btTarget = findViewById(R.id.bt_target);
        mtToken = findViewById(R.id.et_token);

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
                            mtToken.setText(duptok);
                        }

                    }
                });


        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mtTitle.getText().toString().isEmpty() && !mtBody.getText().toString().isEmpty()){
                    FcmNotificationSender notificationSender = new FcmNotificationSender("/topics/all",
                            mtTitle.getText().toString(), mtBody.getText().toString(), getApplicationContext(), MainActivity.this);
                    notificationSender.SendNotifications();
                }
                else {
                    Toast.makeText(MainActivity.this, "Field Empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mtTitle.getText().toString().isEmpty() && !mtBody.getText().toString().isEmpty()){
                    FcmNotificationSender notificationSender = new FcmNotificationSender(mtToken.getText().toString(),
                            mtTitle.getText().toString(), mtBody.getText().toString(), getApplicationContext(), MainActivity.this);
                    notificationSender.SendNotifications();
                }
                else {
                    Toast.makeText(MainActivity.this, "Field Empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}