package com.amjad.myapplicationschool.ui.activity.admin.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.ClassSettingsAdapter;
import com.amjad.myapplicationschool.adapter.UsersAdapter;
import com.amjad.myapplicationschool.databinding.ActivityClassSettingsBinding;
import com.amjad.myapplicationschool.model.ClassModel;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ClassSettingsActivity extends AppCompatActivity {

    private ActivityClassSettingsBinding binding;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetDialog bottomSheetDialogVisitGame;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClassSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getAllClassNumber();
        createClassNumber();
    }

    private void getAllClassNumber() {
        Query query = FirebaseFirestore.getInstance().collection("ClassNumbers");
        /*query.whereEqualTo("visibility", true)
                .whereEqualTo("newsStatus", true)
                .orderBy("date", Query.Direction.DESCENDING);*/
        FirestoreRecyclerOptions<ClassModel> options = new FirestoreRecyclerOptions.Builder<ClassModel>()
                .setQuery(query, ClassModel.class)
                .build();
        fillCLassRecycleAdapter(options);
    }

    private void fillCLassRecycleAdapter(FirestoreRecyclerOptions<ClassModel> options) {
        ClassSettingsAdapter classAdapter = new ClassSettingsAdapter(options);
        classAdapter.onItemSetOnClickListener(new ClassSettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String CLassId = documentSnapshot.getId();
                Intent intent = new Intent(getApplicationContext(), ClassSettingsActivity.class);
                intent.putExtra("CLass_ID", CLassId);
                startActivity(intent);
            }
        });
        binding.recyclerViewClassNumber.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewClassNumber.setAdapter(classAdapter);
        classAdapter.startListening();
    }

    private void createClassNumber() {
        binding.buttonClassNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(getApplicationContext());
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.create_class_number, null);
                progressBar = view.findViewById(R.id.progressBar_id);
                progressBar.setVisibility(View.GONE);
                Button create = (Button) view.findViewById(R.id.button_create_opening_game_id);
                ImageView back = (ImageView) view.findViewById(R.id.image_back_id);
                EditText numberArabic = view.findViewById(R.id.editTextTextNumberArabic);
                EditText numberEnglish = view.findViewById(R.id.editTextTextNumberEnglish);
                String userId = PreferenceUtils.getId(getApplicationContext());
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numberArabicValue = numberArabic.getText().toString();
                        String numberEnglishValue = numberEnglish.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        if (numberArabicValue.isEmpty() || numberEnglishValue.isEmpty()) {
                            numberArabic.setError("The first class number is required!");
                            numberArabic.requestFocus();
                            numberEnglish.setError("The first class number is required!");
                            numberEnglish.requestFocus();
                            return;
                        }
                        ClassModel classModel = new ClassModel(numberArabicValue,numberEnglishValue,"","");
                        storClassNumber(classModel);
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
            }
        });
    }

    private void storClassNumber(ClassModel classModel) {
        FirebaseFirestore.getInstance().collection("ClassNumbers").add(classModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                bottomSheetDialog.dismiss();
            }
        });
    }
}