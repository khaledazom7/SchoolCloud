package com.amjad.myapplicationschool.ui.activity.admin.student.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.FragmentPageAdapter;
import com.amjad.myapplicationschool.databinding.ActivityEditStudentBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.CurrentClassFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.FirstSchoolFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.PersonalInformationFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.ResponsibleStudentFragment;
import com.amjad.myapplicationschool.ui.activity.admin.student.fragment.StudentSecondaryOInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class EditStudentActivity extends AppCompatActivity {

    private ActivityEditStudentBinding binding;
    private FragmentPageAdapter fragmentPageAdapter;
    private String studentID;
    private String[] titles = new String[]{"Student Information", "Responsible Student", "Current Class", "First School", "More Information"};
    private Fragment[] fragments = new Fragment[]{new PersonalInformationFragment(), new ResponsibleStudentFragment(), new CurrentClassFragment(), new FirstSchoolFragment()
            , new StudentSecondaryOInfoFragment()};
    private FirebaseFirestore firebaseFirestore;
    private User user;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("STUDENT_ID")) {
            studentID = intent.getStringExtra("STUDENT_ID");
        }
        getStudentAccountInfo();

    }

    private void preparFragmentsPager(){
        fragmentPageAdapter = new FragmentPageAdapter(EditStudentActivity.this, titles, fragments);
        binding.viewPagerId.setAdapter(fragmentPageAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPagerId, ((tab, position) -> tab.setText(titles[position]))).attach();
    }

    private void getStudentAccountInfo() {
        firebaseFirestore.collection("Users").document(studentID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    user = task.getResult().toObject(User.class);
                    getStudentInfo();
                }
            }
        });
    }

    private void getStudentInfo() {
        firebaseFirestore.collection("Student").whereEqualTo("studentID",studentID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    student = task.getResult().getDocuments().get(0).toObject(Student.class);
                    preparFragmentsPager();
                }
            }
        });
    }

    public User getUser() {
        return user;
    }

    public Student getStudent() {
        return student;
    }

    /*public String getStudentID() {
        return studentID;
    }*/
}