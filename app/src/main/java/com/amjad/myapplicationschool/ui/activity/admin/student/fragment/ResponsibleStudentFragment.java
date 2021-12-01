package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentResponsibleStudentBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ResponsibleStudentFragment extends Fragment {

    private FragmentResponsibleStudentBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;
    private  Student student;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResponsibleStudentBinding.inflate(inflater,container,false);
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
        binding.editTextResponsibleName.setText(student.getRespStName());
        binding.editTextResponsibleCuntry.setText(student.getRespStCountry());
        binding.editTextVillage.setText(student.getRespStVillage());
        binding.editTextCountryCurrentPlace.setText(student.getRespStCurrentPlace());
        binding.editTextPhone.setText(student.getRespStMainPhone());
        binding.editTextSecondaryPhone.setText(student.getRespStSecondaryPhone());
        binding.editTextCardNumber.setText(student.getRespStRationCardNumber());
    }

    private void updateUser() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setRespStName(binding.editTextResponsibleName.getText().toString());
                student.setRespStCountry(binding.editTextResponsibleCuntry.getText().toString());
                student.setRespStVillage(binding.editTextVillage.getText().toString());
                student.setRespStCurrentPlace(binding.editTextCountryCurrentPlace.getText().toString());
                student.setRespStMainPhone(binding.editTextPhone.getText().toString());
                student.setRespStSecondaryPhone(binding.editTextSecondaryPhone.getText().toString());
                student.setRespStRationCardNumber(binding.editTextCardNumber.getText().toString());
                activity.setStudent(student);
            }
        });
    }
}