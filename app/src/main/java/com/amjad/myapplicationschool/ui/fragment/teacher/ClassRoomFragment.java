package com.amjad.myapplicationschool.ui.fragment.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentClassRoomBinding;

public class ClassRoomFragment extends Fragment {

    private FragmentClassRoomBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClassRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonFirstClassBlock.setVisibility(View.GONE);
        binding.buttonMiddleClassBlock.setVisibility(View.GONE);
        binding.buttonSeconderyClassBLock.setVisibility(View.GONE);
        clickClassesButton();
    }

    private void clickClassesButton() {
        binding.buttonFirstClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.buttonFirstClassBlock.getVisibility() == View.VISIBLE)
                    binding.buttonFirstClassBlock.setVisibility(View.GONE);
                else
                    binding.buttonFirstClassBlock.setVisibility(View.VISIBLE);

            }
        });
        binding.buttonMiddleClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.buttonMiddleClassBlock.getVisibility() == View.VISIBLE)
                    binding.buttonMiddleClassBlock.setVisibility(View.GONE);
                else
                    binding.buttonMiddleClassBlock.setVisibility(View.VISIBLE);

            }
        });
        binding.buttonSeconderyClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.buttonSeconderyClassBLock.getVisibility() == View.VISIBLE)
                    binding.buttonSeconderyClassBLock.setVisibility(View.GONE);
                else
                    binding.buttonSeconderyClassBLock.setVisibility(View.VISIBLE);

            }
        });
    }
}