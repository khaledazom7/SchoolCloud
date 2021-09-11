package com.amjad.myapplicationschool.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.CategoryAdapter;
import com.amjad.myapplicationschool.model.Category;
import com.amjad.myapplicationschool.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categoryArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerView_category);
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student,"الفصول الدراسية","ابتدائي اعدادي ثانوي"));
        categoryAdapter = new CategoryAdapter(getContext(),categoryArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setHasFixedSize(true);
        return root;
    }
}