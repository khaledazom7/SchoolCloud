package com.amjad.myapplicationschool.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.ui.activity.admin.AdminActivity;
import com.amjad.myapplicationschool.ui.activity.student.StudentActivity;
import com.amjad.myapplicationschool.ui.activity.teacher.TeacherActivity;
import com.amjad.myapplicationschool.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String email = PreferenceUtils.getEmail(getApplicationContext());
                String typeUser = PreferenceUtils.getType(getApplicationContext());
                if (email == null ){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else {
                    if (typeUser.equals("teacher")) {
                        startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                    } else if (typeUser.equals("admin")) {
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    }
                    else if (typeUser.equals("student")) {
                        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                    }
                }
                finish();
            }
        }, 2000);
    }
}