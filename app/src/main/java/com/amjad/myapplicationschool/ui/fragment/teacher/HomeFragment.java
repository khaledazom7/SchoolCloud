package com.amjad.myapplicationschool.ui.fragment.teacher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.CategoryAdapter;
import com.amjad.myapplicationschool.databinding.FragmentHomeBinding;
import com.amjad.myapplicationschool.model.Category;
import com.amjad.myapplicationschool.model.Teacher;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.LoginActivity;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categoryArrayList;
    private FragmentHomeBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private String userID;
    private Uri userImage;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        logout();
        getCtegories();
        getTeacherInfoAccount(userID);
        getTeacherJobInfo(userID);
        return view;
    }

    private void getCtegories() {
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category(0, R.drawable.classroom, "???????????? ????????????????", "?????????????? ???????????? ??????????"));
        categoryArrayList.add(new Category(1, R.drawable.recoedstudent, "?????? ????????????", "?????????? ???????? "));
        categoryArrayList.add(new Category(2, R.drawable.brecenceandabscence, "???????????? ??????????????", "???????????? ??????????"));
        categoryArrayList.add(new Category(3, R.drawable.studentactivity, "???????? ????????????", "???????????? ?????????????? ????????????????"));
        categoryArrayList.add(new Category(4, R.drawable.exam, "????????????????????", "?????????? ????????????????????"));
        categoryArrayList.add(new Category(5, R.drawable.report, "??????????", "????????????  ???????????????? ????????"));
        categoryArrayList.add(new Category(6, R.drawable.monyfolder, "?????????? ????????????", "???????? ????????"));
        categoryArrayList.add(new Category(7, R.drawable.logout, "?????????? ????????", ""));
        categoryAdapter = new CategoryAdapter(getContext(), categoryArrayList);
        categoryAdapter.onItemSetOnClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int categoryID) {
                switch (categoryID) {
                    case 0:
                        fragment = new ClassRoomFragment();
                        break;
                    case 1:
                        fragment = new StudentRecordFragment();
                        break;
                    case 2:
                        fragment = new PresenceFragment();
                        break;
                    case 3:
                        fragment = new StudentActivityFragment();
                        break;
                    case 4:
                        fragment = new ExamFragment();
                        break;
                    case 5:
                        fragment = new ReportsFragment();
                        break;
                    case 6:
                        fragment = new MonyFileFragment();
                        break;
                    case 7:
                        fragment = new ReportsFragment();
                        break;
                }
                setFragment(fragment);
            }
        });
        binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewCategory.setAdapter(categoryAdapter);
        binding.recyclerViewCategory.setHasFixedSize(true);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment,null) .commit();
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

    private void getTeacherJobInfo(String teacherID) {
        firebaseFirestore.collection("Teacher").whereEqualTo("teacherID", teacherID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.getResult().isEmpty() || task.getResult() != null) {
                    if (task.getResult().getDocuments().size() > 0) {
                        Teacher teacher = task.getResult().getDocuments().get(0).toObject(Teacher.class);
                        binding.textViewMajor.setText(teacher.getMajor());
                    } else {
                        binding.textViewMajor.setText("?????? ?????? ???????? ??????");
                    }
                } else {
                    binding.textViewMajor.setText("?????? ?????? ???????? ??????");
                }

            }
        });
    }

    private void logout() {
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveEmail(null, getContext());
                PreferenceUtils.saveType("", getContext());
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }
}