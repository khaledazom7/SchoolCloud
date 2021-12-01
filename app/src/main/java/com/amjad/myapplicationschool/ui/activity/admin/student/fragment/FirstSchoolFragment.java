package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentFirstSchoolBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirstSchoolFragment extends Fragment {
    private FragmentFirstSchoolBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;
    private Student student;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstSchoolBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        activity = (EditStudentActivity) getActivity();

        //Fill student info
         student = activity.getStudent();
        fillStudentInfo();
        updateUser();
    }

    private void fillStudentInfo() {
        binding.editTextDateInterSchool.setText(student.getDateInterSchool());
        binding.editTextAgeInterSchool.setText(student.getAgeInterSchool());
        binding.editTextAgeInOctober.setText(student.getAgeInOctober());
    }

    private void updateUser() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setDateCurrentClass(binding.editTextDateInterSchool.getText().toString());
                student.setTypeCurrentClass(binding.editTextAgeInterSchool.getText().toString());
                student.setMajorCurrentClass(binding.editTextAgeInOctober.getText().toString());
                activity.setStudent(student);
            }
        });
    }
}