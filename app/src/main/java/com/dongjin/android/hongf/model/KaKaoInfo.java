package com.dongjin.android.hongf.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

/**
 * Created by kimdongjin on 2017. 2. 17..
 */

public class KaKaoInfo {
    static private Context context;
    static private KaKaoInfo kaKaoInfo;
    private SharedPreferences pref;

    public static KaKaoInfo getInstance() {
        if (kaKaoInfo == null) {
            kaKaoInfo = new KaKaoInfo();
        }
        return kaKaoInfo;
    }

    public void getCacheDir(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("kakoInfo", Context.MODE_PRIVATE);
    }
    public void write_id_kakao(String obj){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("kakaoId", obj);
        editor.commit();
    }

    public String read_id_kakao() {
        String text = pref.getString("kakaoId", "");
        return text;
    }

    public void write_name_kakao(String obj){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("kakaoName", obj);
        editor.commit();
    }

    public String read_name_kakao() {
        String text = pref.getString("kakaoName", "");
        return text;
    }
    public void write_picture_kakao(String obj){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("kakaoPicture", obj);
        editor.commit();
    }

    public String read_picture_kakao() {
        String text = pref.getString("kakaoPictur", "");
        return text;
    }

    public void delete() throws IOException {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("Token");
        editor.commit();
    }


    public void writeId(String obj) throws IOException {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Id", obj);
        editor.commit();
    }


}
