package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;
import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.adapter.SpinnerAdapter;
import com.amjad.myapplicationschool.databinding.FragmentCurrentClassBinding;
import com.amjad.myapplicationschool.model.ClassModel;
import com.amjad.myapplicationschool.model.ClassName;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CurrentClassFragment extends Fragment {

    private FragmentCurrentClassBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;
    private Student student;
    private ArrayAdapter<CharSequence> adapterSpinnerCurrentClass;
    private ArrayAdapter<CharSequence> adapterSpinnerSectionCurrentClass;
    private ArrayAdapter<CharSequence> adapterSpinnerReturnedCurrentClass;
    private String currentClass = "";
    private String sectionCurrentClass = "";
    private ArrayList<String> returnedClass;
    ArrayList<Object> data;
    private SimpleChipAdapter adapterChip;
    ArrayList<String> data2;
    private String spinnerClassName = "";
    private int size = 0;
    private ArrayList<ClassName> classNameArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentClassBinding.inflate(inflater, container, false);
        classNameArrayList = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        activity = (EditStudentActivity) getActivity();
        student = activity.getStudent();
        updateUser();
        //spinnerCurrentClass();
        getAllClassName();
        spinnerSectionCurrentClass();
        spinnerReturnedClass();
        fillStudentInfo();
    }


    private void fillStudentInfo() {
        binding.editTextStudentDateCurrentClass.setText(student.getDateCurrentClass());
        binding.editTextStudentTypeCurrentClass.setText(student.getTypeCurrentClass());
        binding.editTextMajorCurrentClass.setText(student.getMajorCurrentClass());
        binding.editTextMajorCurrentClass.setText(student.getMajorCurrentClass());
        //binding.spinnerCurrentClass.setSelection(adapterSpinnerCurrentClass.getPosition(student.getCurrentClass()));
        binding.spinnerSectionCurrentClass.setSelection(adapterSpinnerSectionCurrentClass.getPosition(student.getSectionCurrentClass()));
        returnedClass = student.getReturnedClass();
        setTag();
    }

    private void updateUser() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setDateCurrentClass(binding.editTextStudentDateCurrentClass.getText().toString());
                student.setTypeCurrentClass(binding.editTextStudentTypeCurrentClass.getText().toString());
                student.setMajorCurrentClass(binding.editTextMajorCurrentClass.getText().toString());
                student.setCurrentClass(currentClass);
                student.setSectionCurrentClass(sectionCurrentClass);
                student.setReturnedClass(returnedClass);
                activity.setStudent(student);
            }
        });
    }


    //Class Name
    private void getAllClassName() {
        FirebaseFirestore.getInstance()
                .collection("ClassRoom")
                .whereEqualTo("type", 2)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (size < task.getResult().getDocuments().size() - 1)
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        size = i;
                        spinnerClassName = "";
                        String classId = task.getResult().getDocuments().get(i).getId();
                        ClassModel classModel = task.getResult().getDocuments().get(i).toObject(ClassModel.class);
                        FirebaseFirestore.getInstance().collection("ClassRoom")
                                .document(classModel.getNumberId())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                ClassModel classModelNumber = documentSnapshot.toObject(ClassModel.class);
                                FirebaseFirestore.getInstance().collection("ClassRoom")
                                        .document(classModel.getSectionId())
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        //TODO:: GEt CLASS Name
                                        ClassModel classModelSection = documentSnapshot.toObject(ClassModel.class);
                                        spinnerClassName = classModelNumber.getNumber() + classModelSection.getSection();
                                        ClassName className = new ClassName(classId, spinnerClassName);
                                        classNameArrayList.add(className);
                                    }
                                });
                            }
                        });
                    }
            }
        });
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), classNameArrayList);
        binding.spinnerCurrentClass.setAdapter(spinnerAdapter);
        binding.spinnerCurrentClass.setSelection(0, true);
        spinnerAdapter.onItemSetOnClickListener(new SpinnerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ClassName className) {
                binding.spinnerCurrentClass.setSelection(position, true);
                String name = className.getTitle();
                currentClass = className.getId();
            }
        });
    }

    private void spinnerCurrentClass() {
        adapterSpinnerCurrentClass = ArrayAdapter.createFromResource(getContext(), R.array.spinnerCurrentClass, R.layout.spinner_item);
        adapterSpinnerCurrentClass.setDropDownViewResource(R.layout.spinner_item_dropdown);
        binding.spinnerCurrentClass.setAdapter(adapterSpinnerCurrentClass);
        //binding.spinnerCurrentClass.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        binding.spinnerCurrentClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentClass = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinnerSectionCurrentClass() {
        adapterSpinnerSectionCurrentClass = ArrayAdapter.createFromResource(getContext(), R.array.sectionCurrentClass, R.layout.spinner_item);
        adapterSpinnerSectionCurrentClass.setDropDownViewResource(R.layout.spinner_item_dropdown);
        binding.spinnerSectionCurrentClass.setAdapter(adapterSpinnerSectionCurrentClass);
        //binding.spinnerCurrentClass.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        binding.spinnerSectionCurrentClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectionCurrentClass = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinnerReturnedClass() {
        adapterSpinnerReturnedCurrentClass = ArrayAdapter.createFromResource(getContext(), R.array.spinnerCurrentClass, R.layout.spinner_item);
        adapterSpinnerReturnedCurrentClass.setDropDownViewResource(R.layout.spinner_item_dropdown);
        binding.spinnerReturnedClass.setAdapter(adapterSpinnerReturnedCurrentClass);
        //binding.spinnerCurrentClass.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        binding.spinnerReturnedClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!returnedClass.contains(parent.getItemAtPosition(position).toString())) {
                    returnedClass.add(parent.getItemAtPosition(position).toString());
                    setTag();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTag() {
        binding.chipReturnedClasss.removeAllViews();
        for (int index = 0; index < returnedClass.size(); index++) {
            final String tagName = returnedClass.get(index);
            final Chip chip = new Chip(getContext());
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            chip.setCloseIconResource(R.drawable.ic_baseline_close_24);
            chip.setCloseIconEnabled(true);
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnedClass.remove(tagName);
                    binding.chipReturnedClasss.removeView(chip);
                }
            });

            binding.chipReturnedClasss.addView(chip);
        }
    }


    private void chipReturnedClass() {
        data = new ArrayList<>();
        data.add("الصف الأول");
        data.add("الصف الثاني");
        data.add("الصف الثالث");
        adapterChip = new SimpleChipAdapter(data);
        //binding.chipReturnedClass.setAdapter(adapterChip);
    }
}