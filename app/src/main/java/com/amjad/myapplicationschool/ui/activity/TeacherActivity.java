package com.amjad.myapplicationschool.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.amjad.myapplicationschool.databinding.ActivityTeacherBinding;
import com.amjad.myapplicationschool.databinding.FragmentEditTeacherBinding;
import com.amjad.myapplicationschool.utils.PreferenceUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TeacherActivity extends AppCompatActivity {

    private ActivityTeacherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveEmail(null,getApplicationContext());
                PreferenceUtils.saveType("",getApplicationContext());
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }


    public static class EditTeacherFragment extends Fragment {
        FragmentEditTeacherBinding binding;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = FragmentEditTeacherBinding.inflate(inflater, container, false);
            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        }
    }
}