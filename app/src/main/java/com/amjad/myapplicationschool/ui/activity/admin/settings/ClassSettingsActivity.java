package com.amjad.myapplicationschool.ui.activity.admin.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.ClassSettingsAdapter;
import com.amjad.myapplicationschool.adapter.UsersAdapter;
import com.amjad.myapplicationschool.databinding.ActivityClassSettingsBinding;
import com.amjad.myapplicationschool.model.ClassModel;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

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
        getAllClassSection();
        createClassSection();
        getAllClassName();
        createClassName();
    }

    private void getAllClassNumber() {
        Query query = FirebaseFirestore.getInstance().collection("ClassRoom").whereEqualTo("type", 0);
        FirestoreRecyclerOptions<ClassModel> options = new FirestoreRecyclerOptions.Builder<ClassModel>()
                .setQuery(query, ClassModel.class)
                .build();
        fillCLassNumberRecycleAdapter(options);
    }

    private void fillCLassNumberRecycleAdapter(FirestoreRecyclerOptions<ClassModel> options) {
        ClassSettingsAdapter classAdapter = new ClassSettingsAdapter(options);
        classAdapter.setType(0);
        classAdapter.onItemSetOnClickListener(new ClassSettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String CLassId = documentSnapshot.getId();
                Intent intent = new Intent(getApplicationContext(), ClassSettingsActivity.class);
                intent.putExtra("CLass_ID", CLassId);
                startActivity(intent);
            }
        });
        binding.recyclerViewClassNumber.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewClassNumber.setAdapter(classAdapter);
        classAdapter.startListening();
    }

    private void createClassNumber() {
        binding.buttonClassNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(ClassSettingsActivity.this);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.create_class_num_item, null);
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
                        if (numberArabicValue.isEmpty() && numberEnglishValue.isEmpty()) {
                            numberArabic.setError("The first class number is required!");
                            numberArabic.requestFocus();
                            numberEnglish.setError("The first class number is required!");
                            numberEnglish.requestFocus();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        ClassModel classModel = new ClassModel(numberArabicValue, numberEnglishValue, "", "", "", "", 0);
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
        FirebaseFirestore.getInstance().collection("ClassRoom").add(classModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    //Class Section
    private void getAllClassSection() {
        Query query = FirebaseFirestore.getInstance().collection("ClassRoom").whereEqualTo("type", 1);
        FirestoreRecyclerOptions<ClassModel> options = new FirestoreRecyclerOptions.Builder<ClassModel>()
                .setQuery(query, ClassModel.class)
                .build();
        fillCLassSectionRecycleAdapter(options);
    }

    private void fillCLassSectionRecycleAdapter(FirestoreRecyclerOptions<ClassModel> options) {
        ClassSettingsAdapter classAdapter = new ClassSettingsAdapter(options);
        classAdapter.setType(1);
        classAdapter.onItemSetOnClickListener(new ClassSettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String CLassId = documentSnapshot.getId();
                Intent intent = new Intent(getApplicationContext(), ClassSettingsActivity.class);
                intent.putExtra("CLass_ID", CLassId);
                startActivity(intent);
            }
        });
        binding.recyclerViewClassSection.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewClassSection.setAdapter(classAdapter);
        classAdapter.startListening();
    }

    private void createClassSection() {
        binding.buttonClassSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(ClassSettingsActivity.this);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.create_class_section_item, null);
                progressBar = view.findViewById(R.id.progressBar_id);
                progressBar.setVisibility(View.GONE);
                Button create = (Button) view.findViewById(R.id.button_create_opening_game_id);
                ImageView back = (ImageView) view.findViewById(R.id.image_back_id);
                EditText sectionArabic = view.findViewById(R.id.editTextTextSectionArabic);
                EditText sectionEnglish = view.findViewById(R.id.editTextTextSectionEnglish);
                String userId = PreferenceUtils.getId(getApplicationContext());
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sectionArabicValue = sectionArabic.getText().toString();
                        String sectionEnglishValue = sectionEnglish.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        if (sectionArabicValue.isEmpty() && sectionEnglishValue.isEmpty()) {
                            sectionArabic.setError("The first class section is required!");
                            sectionArabic.requestFocus();
                            sectionEnglish.setError("The first class section is required!");
                            sectionEnglish.requestFocus();
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                        ClassModel classModel = new ClassModel("", "", sectionArabicValue, sectionEnglishValue, "", "", 1);
                        storClassSection(classModel);
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

    private void storClassSection(ClassModel classModel) {
        FirebaseFirestore.getInstance().collection("ClassRoom").add(classModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    //Class Name
    private void getAllClassName() {
        Query query = FirebaseFirestore.getInstance().collection("ClassRoom").whereEqualTo("type", 2);
        FirestoreRecyclerOptions<ClassModel> options = new FirestoreRecyclerOptions.Builder<ClassModel>()
                .setQuery(query, ClassModel.class)
                .build();
        fillCLassNameRecycleAdapter(options);
    }

    private void fillCLassNameRecycleAdapter(FirestoreRecyclerOptions<ClassModel> options) {
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
        binding.recyclerViewClassName.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewClassName.setAdapter(classAdapter);
        classAdapter.startListening();
    }
    int numSize = 0;
    private void createClassName() {
        binding.createClassLayout.setVisibility(View.GONE);
        binding.buttonClassName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.createClassLayout.setVisibility(View.VISIBLE);
                binding.classNameInclude.progressBarId.setVisibility(View.GONE);
                ArrayList<String> classModelsSection = new ArrayList<>();
                spinnerNumber();
                //spinnerSection(classModelsSection, binding.classNameInclude.spinnerSection);
                binding.classNameInclude.buttonCreateOpeningGameId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.classNameInclude.progressBarId.setVisibility(View.VISIBLE);
                        ClassModel classModel = new ClassModel("", "", "", "", spinnerNumberValue, spinnerSectionValue, 2);
                        storClassName(classModel);
                    }
                });
                binding.classNameInclude.imageBackId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
    }

    private void storClassName(ClassModel classModel) {
        FirebaseFirestore.getInstance().collection("ClassRoom").add(classModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                binding.classNameInclude.progressBarId.setVisibility(View.GONE);
                binding.createClassLayout.setVisibility(View.GONE);
            }
        });
    }

    private ArrayAdapter<String> adapterSpinnerNumber;
    private ArrayAdapter<String> adapterSpinnerSection;
    String spinnerNumberValue = "";
    String spinnerSectionValue = "";

    private void spinnerNumber() {
       // String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};
        FirebaseFirestore.getInstance()
                .collection("ClassRoom")
                .whereEqualTo("type", 0)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                numSize = task.getResult().getDocuments().size();
                Log.d("asdsad",numSize+"");
            }
        });
        String[] classModelsNumber = new String[2];
        FirebaseFirestore.getInstance()
                .collection("ClassRoom")
                .whereEqualTo("type", 0)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (int i = 0; i < 2; i++) {
                   // classModelsNumber = new String[task.getResult().getDocuments().size()];
                    classModelsNumber[i] = task.getResult().getDocuments().get(i).toObject(ClassModel.class).getNumber();
                    //classModelsNumber.add(task.getResult().getDocuments().get(i).toObject(ClassModel.class).getNumber());
                    Log.d("asdsad", classModelsNumber[i]+"");
                }

            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinnerNumber);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, classModelsNumber);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);


       /* adapterSpinnerNumber = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item,0, arrayList);
        adapterSpinnerNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerNumber);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerNumberValue = parent.getItemAtPosition(position).toString();
                Log.d("spinnerNumberValue", spinnerNumberValue);
                Toast.makeText(getApplicationContext(), spinnerNumberValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void spinnerSection( Spinner spinner) {
        ArrayList<String> arrayList = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("ClassRoom")
                .whereEqualTo("type", 1)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                    arrayList.add(task.getResult().getDocuments().get(i).toObject(ClassModel.class).getNumber());
                }
            }
        });
        adapterSpinnerSection = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayList);
        //adapterSpinnerCurrentClass.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner.setAdapter(adapterSpinnerSection);
        //binding.spinnerCurrentClass.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSectionValue = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), spinnerNumberValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}