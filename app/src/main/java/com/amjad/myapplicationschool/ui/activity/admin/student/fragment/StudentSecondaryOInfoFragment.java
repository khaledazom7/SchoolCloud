package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentStudentSecondaryOInfoBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StudentSecondaryOInfoFragment extends Fragment {

    private FragmentStudentSecondaryOInfoBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;
    private Student student;

    private ArrayAdapter<CharSequence> adapterSpinnerSkills;
    private ArrayList<String> skills;
    ArrayList<Object> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentSecondaryOInfoBinding.inflate(inflater, container, false);
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
        spinnerSkills();
        addSkill();
    }

    private void addSkill() {
        binding.buttonAddSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skill = binding.editTextSkills.getText().toString();
                if (!skill.isEmpty()) {
                    skills.add(skill);
                    binding.editTextSkills.setText("");
                    setTag();
                }

            }
        });
    }

    private void fillStudentInfo() {
        binding.editTextMedicalRecord.setText(student.getMedicalRecord());
        skills = student.getSkills();
        setTag();
    }

    private void updateUser() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setMedicalRecord(binding.editTextMedicalRecord.getText().toString());
                student.setSkills(skills);
                activity.setStudent(student);
            }
        });
    }

    private void spinnerSkills() {
        adapterSpinnerSkills = ArrayAdapter.createFromResource(getContext(), R.array.spinnerSkills, R.layout.spinner_item);
        adapterSpinnerSkills.setDropDownViewResource(R.layout.spinner_item_dropdown);
        binding.spinnerSkills.setAdapter(adapterSpinnerSkills);
        //binding.spinnerCurrentClass.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        binding.spinnerSkills.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!skills.contains(parent.getItemAtPosition(position).toString())) {
                    skills.add(parent.getItemAtPosition(position).toString());
                    setTag();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTag() {
        binding.chipSkills.removeAllViews();
        for (int index = 0; index < skills.size(); index++) {
            final String tagName = skills.get(index);
            final Chip chip = new Chip(getContext());
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
                    skills.remove(tagName);
                    binding.chipSkills.removeView(chip);
                }
            });

            binding.chipSkills.addView(chip);
        }
    }
}