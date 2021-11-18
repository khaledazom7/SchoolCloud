package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentStudentSecondaryOInfoBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentSecondaryOInfoFragment extends Fragment {

    private FragmentStudentSecondaryOInfoBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentSecondaryOInfoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        activity = (EditStudentActivity) getActivity();

        //Fill student info
        Student student = activity.getStudent();

    }
}