package com.amjad.myapplicationschool.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.ActivityEditTeacherProfileBinding;
import com.amjad.myapplicationschool.databinding.ActivityTeacherBinding;
import com.amjad.myapplicationschool.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EditTeacherProfile extends AppCompatActivity {
    private ActivityEditTeacherProfileBinding binding;
    private String teacherID;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTeacherProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEACHER_ID")) {
            teacherID = intent.getStringExtra("TEACHER_ID");
            getTeacherJobInfo(teacherID);
            updateTeacherJobInfo();
        }
    }
    private String documentID = "";
    private void getTeacherJobInfo(String teacherID) {
        firebaseFirestore.collection("Teacher").whereEqualTo("teacherID", teacherID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty() || task.getResult() == null) {
                    Teacher teacher = new Teacher(teacherID, "", "", "", "", "", "", "", "1");
                    createTeacherInfoJob(teacher);
                }else {
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


    private void updateTeacherJobInfo(){
        binding.buttonEditTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO:: Save new data
                firebaseFirestore.collection("Teacher")
                        .document(documentID)
                        .update("degree",  binding.textViewDegree.getText().toString(),
                                "major",  binding.textViewMajor.getText().toString(),
                                "experiences",  binding.textViewExperiences.getText().toString(),
                                "skills",  binding.textViewSkills.getText().toString(),
                                "identification",  binding.textViewIdentification.getText().toString(),
                                "eduCourses",  binding.textViewEducationCourses.getText().toString(),
                                "medicalRecord",  binding.textViewMedicalRecord.getText().toString(),
                                "accountStatement",  binding.textViewAccountStatement.getText().toString()
                                )
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

            }
        });
    }

}