package com.amjad.myapplicationschool.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amjad.myapplicationschool.databinding.ActivityLoginBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.admin.AdminActivity;
import com.amjad.myapplicationschool.ui.activity.student.StudentActivity;
import com.amjad.myapplicationschool.ui.activity.teacher.TeacherActivity;
import com.amjad.myapplicationschool.utils.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        loginUser();
    }

    private void loginUser() {
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = binding.editTextTextEmailAddress.getText().toString().trim();
                password = binding.editTextTextPassword.getText().toString().trim();
                checkInputInfo();
                signInToAccountByEmailAndPassword();
            }
        });
    }

    private void signInToAccountByEmailAndPassword() {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                checkUserType();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkUserType() {
        firebaseUser = firebaseAuth.getCurrentUser();
        String userID = firebaseUser.getUid();
        firebaseFirestore.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "You not confirm your account", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Successfully Sign in", Toast.LENGTH_SHORT).show();
                    String typeUser = user.getUserType();
                    String email = user.getEmail();
                    PreferenceUtils.saveEmail(email, getApplicationContext());
                    PreferenceUtils.saveId(userID, getApplicationContext());
                    PreferenceUtils.saveType(typeUser, getApplicationContext());
                    if (typeUser.equals("teacher")) {
                        startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                    } else if (typeUser.equals("admin")) {
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    } else if (typeUser.equals("student")) {
                        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                    }
                    finish();
                }

            }
        });
    }

    private void checkInputInfo() {
        if (email.isEmpty()) {
            binding.editTextTextEmailAddress.setError("Your email is Required*");
            binding.editTextTextEmailAddress.hasFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.editTextTextPassword.setError("The Password is Required*");
            binding.editTextTextPassword.hasFocus();
            return;
        }
    }

}