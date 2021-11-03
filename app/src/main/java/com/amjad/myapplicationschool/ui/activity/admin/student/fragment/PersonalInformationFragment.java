package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentFirstSchoolBinding;
import com.amjad.myapplicationschool.databinding.FragmentPersonalInformationBinding;

public class PersonalInformationFragment extends Fragment {

    private FragmentPersonalInformationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonalInformationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }



}