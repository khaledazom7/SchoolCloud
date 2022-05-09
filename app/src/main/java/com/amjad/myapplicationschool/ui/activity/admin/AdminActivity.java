package com.amjad.myapplicationschool.ui.activity.admin;

import android.content.Intent;
import android.os.Bundle;

import com.amjad.myapplicationschool.adapter.FragmentPageAdapter;
import com.amjad.myapplicationschool.adapter.UsersAdapter;
import com.amjad.myapplicationschool.databinding.ActivityAdminBinding;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.LoginActivity;
import com.amjad.myapplicationschool.ui.activity.RegisterActivity;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.CurrentClassFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.FirstSchoolFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.PersonalInformationFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.ResponsibleStudentFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.StudentSecondaryOInfoFragment;
import com.amjad.myapplicationschool.ui.activity.admin.teacher.OpenTeacherProfile;
import com.amjad.myapplicationschool.ui.fragment.admin.SettingsFragment;
import com.amjad.myapplicationschool.ui.fragment.admin.StudentFragment;
import com.amjad.myapplicationschool.ui.fragment.admin.TeacherFragment;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FragmentPageAdapter fragmentPageAdapter;
    private RecyclerView recyclerView;
    private String[] titles = new String[]{"Teacher", "Student", "Settings"};
    private Fragment[] fragments = new Fragment[]{new TeacherFragment(), new StudentFragment(), new SettingsFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        addStudent();
        addTeacher();
        logout();
        preparFragmentPager();
    }

    private void preparFragmentPager() {
        fragmentPageAdapter = new FragmentPageAdapter(AdminActivity.this, titles, fragments);
        binding.viewPagerId.setAdapter(fragmentPageAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPagerId, ((tab, position) -> tab.setText(titles[position]))).attach();
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


}