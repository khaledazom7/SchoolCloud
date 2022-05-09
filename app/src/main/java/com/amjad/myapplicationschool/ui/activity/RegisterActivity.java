package com.amjad.myapplicationschool.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.ActivityRegisterBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.model.Teacher;
import com.amjad.myapplicationschool.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private String name, email, password, date;
    private String accountType = "student";
    private String userImage = "https://firebasestorage.googleapis.com/v0/b/school-cloud-870b3.appspot.com/o/account%2Faccount.png?alt=media&token=d8a708c5-1b1e-4f9f-b738-db6b1cf3352a";
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
                name = binding.editTextTextName.getText().toString().trim();
                email = binding.editTextTextEmailAddress.getText().toString().trim();
                password = binding.editTextTextPassword.getText().toString().trim();
                checkInputInfo();
                if (count == 3) {
                    registerUserByEmailAndPassword(email, password);
                    /*startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();*/
                }
            }
        });
    }

    private void registerUserByEmailAndPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userID = task.getResult().getUser().getUid();
                    addNewUserOnDbFirebase();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                }
                //progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewUserOnDbFirebase() {
        firebaseUser = firebaseAuth.getCurrentUser();
        User user = new User(name, email, userImage, "", "", accountType, "", "", date, true);
        // save on cloudFireStore
        DocumentReference documentReference = firebaseFirestore.collection("Users")
                .document(firebaseUser.getUid());
        documentReference.set(user);
        verifyEmail();
        createUserByType(accountType);
    }

    private String userID = "";
    private ArrayList<String> arrayListReturendClass = new ArrayList<>();
    private ArrayList<String> arrayListSkills = new ArrayList<>();
    Teacher teacher = null;
    Student student = null;

    private void createUserByType(String accountType) {
        switch (accountType) {
            case "student":
                student = new Student(userID, "", "", "", ""
                        , "", "", "", "", ""
                        , arrayListReturendClass, "", "", "", "", "",""
                        , "", "", "", "", ""
                        , "", "", arrayListSkills, "", "1");
                firebaseFirestore.collection("Student").whereEqualTo("studentID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                firebaseFirestore.collection("Student").add(student);
                            }
                        }
                    }
                });
                break;
            case "teacher":
                teacher = new Teacher(userID, "", "", "", "", "", "", "", "1", "",new ArrayList<>());
                firebaseFirestore.collection("Teacher").whereEqualTo("teacherID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                firebaseFirestore.collection("Teacher").add(teacher);
                            }
                        }
                    }
                });
                break;
        }
    }

    private void verifyEmail() {
        assert firebaseUser != null;
        firebaseUser.sendEmailVerification();
        Toast.makeText(getApplicationContext(), "Registered Successfully!\nCheck your email to verify your account!", Toast.LENGTH_LONG).show();
    }

    int count = 3;

    private void checkInputInfo() {
        count = 3;
        if (name.isEmpty()) {
            binding.editTextTextName.setError("Your name is Required*");
            binding.editTextTextName.hasFocus();
            count--;
            return;
        }
        if (email.isEmpty()) {
            binding.editTextTextEmailAddress.setError("Your email is Required*");
            binding.editTextTextEmailAddress.hasFocus();
            count--;
            return;
        }

        if (password.isEmpty()) {
            binding.editTextTextPassword.setError("The Password is Required*");
            binding.editTextTextPassword.hasFocus();
            count--;
            return;
        }
        if (accountType.isEmpty()) {
            Toast.makeText(getApplicationContext(), "You must select the account type", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void radioOnClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdmin:
                accountType = "admin";
                // Pirates are the best
                break;
            case R.id.buttonTeacher:
                accountType = "teacher";
                break;
            case R.id.buttonStudent:
                accountType = "student";
                // Ninjas rule
                break;
        }
        Toast.makeText(getApplicationContext(), accountType, Toast.LENGTH_SHORT).show();
    }
}