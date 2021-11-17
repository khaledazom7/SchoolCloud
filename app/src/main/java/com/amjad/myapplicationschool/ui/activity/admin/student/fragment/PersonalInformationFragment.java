package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentFirstSchoolBinding;
import com.amjad.myapplicationschool.databinding.FragmentPersonalInformationBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.LoginActivity;
import com.amjad.myapplicationschool.ui.activity.admin.AdminActivity;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.amjad.myapplicationschool.ui.activity.student.StudentActivity;
import com.amjad.myapplicationschool.ui.activity.teacher.TeacherActivity;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class PersonalInformationFragment extends Fragment {

    private FragmentPersonalInformationBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonalInformationBinding.inflate(inflater, container, false);
        //studentID = activity.getStudentID();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        activity = (EditStudentActivity) getActivity();

        //Fill student info
        User studentAccount = activity.getUser();
        Student student = activity.getStudent();

        Picasso.get().load(studentAccount.getUserImage()).into(binding.imageStudent);
        binding.editTextStudentName.setText(studentAccount.getName());
        binding.editTextStudentDateOfBirth.setText(student.getDateOfBirth());

    }


}