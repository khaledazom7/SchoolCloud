package com.amjad.myapplicationschool.ui.activity.admin.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.ActivityEditTeacherProfileBinding;
import com.amjad.myapplicationschool.databinding.ActivityTeacherBinding;
import com.amjad.myapplicationschool.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EditTeacherProfile extends AppCompatActivity {
    private ActivityEditTeacherProfileBinding binding;
    private String teacherID;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<String> ClassRoomClass;
    private ArrayAdapter<CharSequence> adapterSpinnerClassRoomCurrentClass;
    private String documentID = "";

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
            spinnerClassRoom();
        }
    }

    private void spinnerClassRoom() {
        adapterSpinnerClassRoomCurrentClass = ArrayAdapter.createFromResource(this, R.array.spinnerCurrentClass, R.layout.spinner_item);
        adapterSpinnerClassRoomCurrentClass.setDropDownViewResource(R.layout.spinner_item_dropdown);
        binding.spinnerClassRoom.setAdapter(adapterSpinnerClassRoomCurrentClass);
        //binding.spinnerCurrentClass.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        binding.spinnerClassRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!ClassRoomClass.contains(parent.getItemAtPosition(position).toString())) {
                    ClassRoomClass.add(parent.getItemAtPosition(position).toString());
                    setTag();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTag() {
        binding.chipClasssRoom.removeAllViews();
        for (int index = 0; index < ClassRoomClass.size(); index++) {
            final String tagName = ClassRoomClass.get(index);
            final Chip chip = new Chip(this);
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            chip.setCloseIconResource(R.drawable.ic_baseline_close_24);
            chip.setCloseIconEnabled(true);
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClassRoomClass.remove(tagName);
                    binding.chipClasssRoom.removeView(chip);
                }
            });

            binding.chipClasssRoom.addView(chip);
        }
    }

    private void getTeacherJobInfo(String teacherID) {
        firebaseFirestore.collection("Teacher").whereEqualTo("teacherID", teacherID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty() || task.getResult() == null) {
                    Teacher teacher = new Teacher(teacherID, "", "", "", "", "", "", "", "1","",new ArrayList<>());
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