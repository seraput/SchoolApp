package com.example.schoolapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    //user
    private static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String LOGIN_ADMIN = "IS_LOGED_IN";
    public static final String NIS = "NIS";
    public static final String EMAIL = "EMAIL";
    public static final String NAME = "NAME";
    public static final String LEVEL = "LEVEL";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession (String nis, String nama, String email, String level){
        editor.putBoolean(LOGIN, true);
        editor.putBoolean(LOGIN_ADMIN, true);
        editor.putString(NIS, nis);
        editor.putString(EMAIL, email);
        editor.putString(NAME, nama);
        editor.putString(LEVEL, level);
        editor.apply();
    }

//    public boolean isLogin(){
//        return sharedPreferences.getBoolean(LOGIN, false);
//    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NIS, sharedPreferences.getString(NIS, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(LEVEL, sharedPreferences.getString(LEVEL, null));

        return user;
    }
}
