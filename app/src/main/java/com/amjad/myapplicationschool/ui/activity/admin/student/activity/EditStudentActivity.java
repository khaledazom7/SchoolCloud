package com.amjad.myapplicationschool.ui.activity.admin.student.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class EditStudentActivity extends AppCompatActivity {
    private String userImage = "https://firebasestorage.googleapis.com/v0/b/school-cloud-870b3.appspot.com/o/account%2Faccount.png?alt=media&token=d8a708c5-1b1e-4f9f-b738-db6b1cf3352a";
    private ActivityEditStudentBinding binding;
    private FragmentPageAdapter fragmentPageAdapter;
    private String studentID;
    private String[] titles = new String[]{"Student Information", "Responsible Student", "Current Class", "First School", "More Information"};
    private Fragment[] fragments = new Fragment[]{new PersonalInformationFragment(), new ResponsibleStudentFragment(), new CurrentClassFragment(), new FirstSchoolFragment()
            , new StudentSecondaryOInfoFragment()};
    private FirebaseFirestore firebaseFirestore;
    private User user;
    private Student student;

    @SuppressLint("ShowToast")
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

       /* User user = new User("moomen", "mmsss875@gmail.com", userImage, "26/11", "mail", "admin", "059912791", "asdfasa", "Sep 4, 2021", true);
       String adminId = PreferenceUtils.getId(this);
        firebaseFirestore.collection("Users").document(adminId).set(user);*/
        getStudentAccountInfo();

    }

    private void preparFragmentsPager() {
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
        firebaseFirestore.collection("Student").whereEqualTo("studentID", studentID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    student = task.getResult().getDocuments().get(0).toObject(Student.class);
                    preparFragmentsPager();
                }
            }
        });
    }

    /*Update student*/
    private void updateStudentAccountInfo() {
        firebaseFirestore.collection("Users").document(studentID).set(user);
        //User user = new User("moomen", "mmsss875@gmail.com", userImage, "26/11", "mail", "admin", "059912791", "asdfasa", "Sep 4, 2021", true);
    }

    private void updateStudentInfo() {
        firebaseFirestore.collection("Student").whereEqualTo("studentID", studentID).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
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

    public void setUser(User user) {
        this.user = user;
        firebaseFirestore.collection("Users").document(studentID).set(user);
    }

    public void setStudent(Student student) {
        this.student = student;

    }
}