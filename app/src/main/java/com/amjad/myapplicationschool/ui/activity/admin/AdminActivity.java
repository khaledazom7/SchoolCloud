package com.amjad.myapplicationschool.ui.activity.admin;

import android.content.Intent;
import android.os.Bundle;

import com.amjad.myapplicationschool.adapter.UsersAdapter;
import com.amjad.myapplicationschool.databinding.ActivityAdminBinding;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.LoginActivity;
import com.amjad.myapplicationschool.ui.activity.RegisterActivity;
import com.amjad.myapplicationschool.ui.activity.admin.teacher.OpenTeacherProfile;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = binding.recyclerViewTeachers;
        addStudent();

        addTeacher();
        getAllTeachers();
        logout();
    }

    private void addStudent() {
        binding.buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    //-------TEACHER---------
    private void addTeacher() {
        binding.buttonAddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
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

    private void getAllTeachers() {
        Query query = FirebaseFirestore.getInstance().collection("Users")
                .whereEqualTo("userType", "teacher");
        /*query.whereEqualTo("visibility", true)
                .whereEqualTo("newsStatus", true)
                .orderBy("date", Query.Direction.DESCENDING);*/
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();
        fillTeacherRecycleAdapter(options);
    }

    private void fillTeacherRecycleAdapter(FirestoreRecyclerOptions<User> options) {
        UsersAdapter usersAdapter = new UsersAdapter(options);
        usersAdapter.onItemSetOnClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String teacherId = documentSnapshot.getId();
                Intent intent = new Intent(getApplicationContext(), OpenTeacherProfile.class);
                intent.putExtra("TEACHER_ID", teacherId);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(usersAdapter);
        usersAdapter.startListening();
    }
}