package com.amjad.myapplicationschool.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.CategoryAdapter;
import com.amjad.myapplicationschool.databinding.ActivityTeacherBinding;
import com.amjad.myapplicationschool.databinding.FragmentEditTeacherBinding;
import com.amjad.myapplicationschool.model.Category;
import com.amjad.myapplicationschool.model.Teacher;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categoryArrayList;
    private ActivityTeacherBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private String userID;
    private Uri userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();

        logout();
        getCtegories();
        getTeacherInfoAccount(userID);
        getTeacherJobInfo(userID);
    }

    private void getCtegories() {
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryArrayList.add(new Category(R.drawable.student, "الفصول الدراسية", "ابتدائي اعدادي ثانوي"));
        categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryArrayList);
        binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        binding.recyclerViewCategory.setAdapter(categoryAdapter);
        binding.recyclerViewCategory.setHasFixedSize(true);
    }

    private void logout() {
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveEmail(null, getApplicationContext());
                PreferenceUtils.saveType("", getApplicationContext());
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    private void getTeacherInfoAccount(String teacherID) {
        firebaseFirestore.collection("Users").document(teacherID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                binding.textViewTeacherName.setText(user.getName());
                binding.textViewMajor.setText(user.getEmail());
                //binding.imageViewTeacher.setText(user.getEmail());
            }
        });
    }

    //TODO:: fix email in major
    private void getTeacherJobInfo(String teacherID) {
        firebaseFirestore.collection("Teacher").whereEqualTo("teacherID", teacherID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty() || task.getResult() != null) {
                    if (task.getResult().getDocuments().size() > 0) {
                        Teacher teacher = task.getResult().getDocuments().get(0).toObject(Teacher.class);
                        binding.textViewMajor.setText(teacher.getMajor());
                    }
                } else {
                    binding.textViewMajor.setText("أنت غير موظف بعد");
                }

            }
        });
    }
}