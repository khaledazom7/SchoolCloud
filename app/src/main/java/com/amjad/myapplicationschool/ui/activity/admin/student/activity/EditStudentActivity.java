package com.amjad.myapplicationschool.ui.activity.admin.student.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.FragmentPageAdapter;
import com.amjad.myapplicationschool.databinding.ActivityEditStudentBinding;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.CurrentClassFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.FirstSchoolFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.PersonalInformationFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.ResponsibleStudentFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.StudentSecondaryOInfoFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EditStudentActivity extends AppCompatActivity {

    private ActivityEditStudentBinding binding;
    private FragmentPageAdapter fragmentPageAdapter;
    private String studentID;
    private String[] titles = new String[]{"First School", "Current Class", "Student Information", "Responsible Student", "More Information"};
    private Fragment[] fragments = new Fragment[]{new FirstSchoolFragment(), new CurrentClassFragment()
            , new PersonalInformationFragment(), new ResponsibleStudentFragment()
            , new StudentSecondaryOInfoFragment()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("STUDENT_ID")) {
            studentID = intent.getStringExtra("STUDENT_ID");
        }
        fragmentPageAdapter = new FragmentPageAdapter(EditStudentActivity.this, titles, fragments);
        binding.viewPagerId.setAdapter(fragmentPageAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPagerId, ((tab, position) -> tab.setText(titles[position]))).attach();


    }


}