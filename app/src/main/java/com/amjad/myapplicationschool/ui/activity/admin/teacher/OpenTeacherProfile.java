package com.amjad.myapplicationschool.ui.activity.admin.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amjad.myapplicationschool.databinding.ActivityOpenTeacherProfileBinding;
import com.amjad.myapplicationschool.model.Teacher;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.admin.teacher.EditTeacherProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEACHER_ID")) {
            teacherID = intent.getStringExtra("TEACHER_ID");
            getTeacherInfoAccount(teacherID);
            getTeacherJobInfo(teacherID);
        }
        binding.buttonEditTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTeacherProfile.class);
                intent.putExtra("TEACHER_ID", teacherID);
                startActivity(intent);
            }
        });
    }

    private void getTeacherInfoAccount(String teacherID) {
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

    private String documentID = "";

    private void getTeacherJobInfo(String teacherID) {
        firebaseFirestore.collection("Teacher").whereEqualTo("teacherID", teacherID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty() || task.getResult() == null) {
                    Teacher teacher = new Teacher(teacherID, "", "", "", "", "", "", "", "1", "", new ArrayList<>());
                    createTeacherInfoJob(teacher);
                } else {
                    documentID = task.getResult().getDocuments().get(0).getId();
                    Teacher teacher = task.getResult().getDocuments().get(0).toObject(Teacher.class);
                    binding.textViewMajor.setText(teacher.getMajor());
                    binding.textViewDegree.setText(teacher.getDegree());
                    binding.textViewExperiences.setText(teacher.getExperiences());
                    binding.textViewSkills.setText(teacher.getSkills());
                    binding.textViewIdentification.setText(teacher.getIdentification());
                    binding.textViewEducationCourses.setText(teacher.getEduCourses());
                    binding.textViewMedicalRecord.setText(teacher.getMedicalRecord());
                    binding.textViewAccountStatement.setText(teacher.getAccountStatement());
                }
            }
        });
    }

    private void createTeacherInfoJob(Teacher teacher) {
        firebaseFirestore.collection("Teacher").add(teacher);
    }

}