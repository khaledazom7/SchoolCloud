package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;
import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentCurrentClassBinding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class CurrentClassFragment extends Fragment {

    private FragmentCurrentClassBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentClassBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChipView cvTag = (ChipView) binding.chipReturnedClass;
        ArrayList<Object> data = new ArrayList<>();
        data.add("First Item");
        data.add("Second Item");
        data.add("Third Item");
        data.add("Fourth Item");
        data.add("Fifth Item");
        data.add("Sixth Item");
        data.add("Seventh Item");
        SimpleChipAdapter adapter = new SimpleChipAdapter(data);
        cvTag.setAdapter(adapter);
    }
}