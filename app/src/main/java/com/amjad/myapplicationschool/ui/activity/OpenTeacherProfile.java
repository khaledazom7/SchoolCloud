package com.amjad.myapplicationschool.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.ActivityOpenTeacherProfileBinding;
import com.amjad.myapplicationschool.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OpenTeacherProfile extends AppCompatActivity {
    private ActivityOpenTeacherProfileBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private String teacherID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpenTeacherProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonEditTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditTeacherProfile.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEACHER_ID")) {
            teacherID = intent.getStringExtra("TEACHER_ID");
            getTeacherInfo(teacherID);
        }
    }

    private void getTeacherInfo(String teacherID) {
        firebaseFirestore.collection("Users").document(teacherID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                binding.textViewEmail.setText(user.getEmail());
                binding.textViewDateOfBirth.setText(user.getDateOfBirth());
                binding.textViewGender.setText(user.getGender());
                binding.textViewJop.setText(user.getEmail());
                binding.textViewPhone.setText(user.getPhone());
                binding.textViewAddress.setText(user.getAddress());
                binding.textViewDateOfCreate.setText(user.getDateCreate());
                binding.textViewStatus.setText(user.isStatus() + "");
            }
        });
    }
    //TODO:: REPLACE FRAGMET IN ACTIVITY


}