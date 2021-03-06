package com.amjad.myapplicationschool.ui.activity.admin.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.SpinnerAdapter;
import com.amjad.myapplicationschool.adapter.UsersAdapter;
import com.amjad.myapplicationschool.databinding.ActivityEditTeacherProfileBinding;
import com.amjad.myapplicationschool.databinding.ActivityTeacherBinding;
import com.amjad.myapplicationschool.model.ClassModel;
import com.amjad.myapplicationschool.model.ClassName;
import com.amjad.myapplicationschool.model.Teacher;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EditTeacherProfile extends AppCompatActivity {
    private ActivityEditTeacherProfileBinding binding;
    private String teacherID;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<ClassName> ClassRoomClass;
    private ArrayAdapter<CharSequence> adapterSpinnerClassRoomCurrentClass;
    private String documentID = "";
    private String spinnerNumberValue = "";
    private String spinnerSectionValue = "";
    private String spinnerClassName = "";
    private ArrayList<ClassModel> arraySpinnerNumber;
    private int size = 0;
    private ArrayList<ClassName> classNameArrayList;
    private ArrayList<String> classIds;
    private SpinnerAdapter spinnerAdapter;
    private ArrayList<String> classNameTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTeacherProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        ClassRoomClass = new ArrayList<>();
        classNameArrayList = new ArrayList<>();
        if (intent != null && intent.hasExtra("TEACHER_ID")) {
            teacherID = intent.getStringExtra("TEACHER_ID");
            getTeacherJobInfo(teacherID);
            updateTeacherJobInfo();
            // spinnerClassRoom();
            getAllClassName();
        }
    }

    //Class Name
    private void getAllClassName() {
        FirebaseFirestore.getInstance()
                .collection("ClassRoom")
                .whereEqualTo("type", 2)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                arraySpinnerNumber = new ArrayList<>();
                if (size < task.getResult().getDocuments().size() - 1)
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        size = i;
                        spinnerClassName = "";
                        String classId = task.getResult().getDocuments().get(i).getId();
                        ClassModel classModel = task.getResult().getDocuments().get(i).toObject(ClassModel.class);
                        FirebaseFirestore.getInstance().collection("ClassRoom")
                                .document(classModel.getNumberId())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ClassModel classModelNumber = documentSnapshot.toObject(ClassModel.class);
                                FirebaseFirestore.getInstance().collection("ClassRoom")
                                        .document(classModel.getSectionId())
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        //TODO:: GEt CLASS Name
                                        ClassModel classModelSection = documentSnapshot.toObject(ClassModel.class);
                                        spinnerClassName = classModelNumber.getNumber() + classModelSection.getSection();
                                        ClassName className = new ClassName(classId, spinnerClassName);
                                        classNameArrayList.add(className);
                                        if (classNameTea.contains(classId)) {
                                            if (!ClassRoomClass.contains(className)) {
                                                spinnerAdapter.notifyDataSetChanged();
                                                ClassRoomClass.add(className);
                                                setTag();
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
            }
        });
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), classNameArrayList);
        binding.spinnerClassRoom.setAdapter(spinnerAdapter);
        binding.spinnerClassRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClassName className = (ClassName) parent.getItemAtPosition(position);
                if (!ClassRoomClass.contains(className)) {
                    binding.spinnerClassRoom.setSelection(position, true);
                    String name = className.getTitle();
                    ClassRoomClass.add(className);
                    setTag();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*spinnerAdapter.onItemSetOnClickListener(new SpinnerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ClassName className) {
                binding.spinnerClassRoom.setSelection(position, true);
                String name = className.getTitle();
                Log.d("clickedItem", name);
                ClassRoomClass.add(className);
                setTag();
            }
        });*/
    }

    private void setTag() {
        Log.d("aasdsadasdasdasda", ClassRoomClass.size() + "");
        binding.chipClasssRoom.removeAllViews();
        for (int index = 0; index < ClassRoomClass.size(); index++) {
            ClassName className = ClassRoomClass.get(index);
            final String tagName = className.getTitle();
            final String tagId = className.getId();
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
                    ClassRoomClass.remove(className);
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
                    //TODO:: Fetch CLass Name Firebase
                    /*for (int i = 0; i < teacher.getClassRoom().size(); i++) {
                        String classId = teacher.getClassRoom().get(i);
                        FirebaseFirestore.getInstance().collection("ClassRoom")
                                .document(teacher.getClassRoom().get(i))
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                //TODO:: GEt CLASS Name
                                ClassModel classModel = documentSnapshot.toObject(ClassModel.class);
                                FirebaseFirestore.getInstance().collection("ClassRoom")
                                        .document(classModel.getNumberId())
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        ClassModel classModelNumber = documentSnapshot.toObject(ClassModel.class);
                                        // spinnerClassName = classModelNumber.getNumber();
                                        FirebaseFirestore.getInstance().collection("ClassRoom")
                                                .document(classModel.getSectionId())
                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                ClassModel classModelSection = documentSnapshot.toObject(ClassModel.class);
                                                spinnerClassName = classModelNumber.getNumber() + classModelSection.getSection();
                                                ClassName className = new ClassName(classId, spinnerClassName);
                                                ClassRoomClass.add(className);
                                                setTag();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                    setTag();*/
                    classNameTea = new ArrayList<>(teacher.getClassRoom());
                }
            }
        });
    }

    private void createTeacherInfoJob(Teacher teacher) {
        firebaseFirestore.collection("Teacher").add(teacher);
    }


    private void updateTeacherJobInfo() {
        binding.buttonEditTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("asdadasdasd", "asdasd");
                classIds = new ArrayList<>();
                for (int i = 0; i < ClassRoomClass.size(); i++) {
                    classIds.add(ClassRoomClass.get(i).getId());
                    Log.d("classIdsas", ClassRoomClass.get(i).getId());
                }
                firebaseFirestore.collection("Teacher")
                        .document(documentID)
                        .update("degree", binding.textViewDegree.getText().toString(),
                                "major", binding.textViewMajor.getText().toString(),
                                "experiences", binding.textViewExperiences.getText().toString(),
                                "skills", binding.textViewSkills.getText().toString(),
                                "identification", binding.textViewIdentification.getText().toString(),
                                "eduCourses", binding.textViewEducationCourses.getText().toString(),
                                "medicalRecord", binding.textViewMedicalRecord.getText().toString(),
                                "accountStatement", binding.textViewAccountStatement.getText().toString(),
                                "classRoom", classIds
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