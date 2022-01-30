package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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

import java.util.Calendar;

public class PersonalInformationFragment extends Fragment {

    private FragmentPersonalInformationBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;
    private User studentAccount;
    private Student student;

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
        studentAccount = activity.getUser();
        student = activity.getStudent();
        fillStudentInfo();
        updateUser();
        calenderViewDate();
    }

    private void fillStudentInfo() {
        Picasso.get().load(studentAccount.getUserImage()).into(binding.imageStudent);
        binding.editTextStudentName.setText(studentAccount.getName());
        binding.editTextStudentDateOfBirth.setText(studentAccount.getDateOfBirth());
        binding.editTextIdentification.setText(student.getIdentification());
        binding.editTextCountryOrigin.setText(student.getCountryOrigin());
        binding.editTextCountryBirth.setText(student.getCountryBirth());
    }

    private void updateUser() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentAccount.setName(binding.editTextStudentName.getText().toString());
                studentAccount.setDateOfBirth(binding.editTextStudentDateOfBirth.getText().toString());
                Log.d("getDateOfBirth",studentAccount.getDateOfBirth());
                activity.setUser(studentAccount);

                student.setIdentification(binding.editTextIdentification.getText().toString());
                student.setCountryOrigin(binding.editTextCountryOrigin.getText().toString());
                student.setCountryBirth(binding.editTextCountryBirth.getText().toString());
                activity.setStudent(student);
            }
        });
    }

    private void calenderViewDate() {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH) + 1;
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        //binding.editTextStudentDateOfBirth.setText(mYear + "/" + mMonth + "/" + mDay);
        binding.editTextStudentDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String selectedDate = selectedyear + "/" + (selectedmonth + 1) + "/" + selectedday;
                        binding.editTextStudentDateOfBirth.setText(selectedDate);
                    }
                }, mYear, mMonth - 1, mDay);
                mDatePicker.show();
            }
        });
        /*binding.editTextStudentDateOfBirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    if (event.getRawX() >= (binding.editTextStudentDateOfBirth.getRight() - binding.editTextStudentDateOfBirth.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                String selectedDate = selectedyear + "/" + (selectedmonth + 1) + "/" + selectedday;

                            }
                        }, mYear, mMonth - 1, mDay);
                        mDatePicker.show();
                    }


                }
                return false;
            }
        });*/
    }


}