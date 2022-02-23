package com.amjad.myapplicationschool.ui.activity.admin.student.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.amjad.myapplicationschool.R;
import com.amjad.myapplicationschool.databinding.FragmentPersonalInformationBinding;
import com.amjad.myapplicationschool.model.Student;
import com.amjad.myapplicationschool.model.User;
import com.amjad.myapplicationschool.ui.activity.admin.student.activity.EditStudentActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class PersonalInformationFragment extends Fragment {

    private FragmentPersonalInformationBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private EditStudentActivity activity;
    private User studentAccount;
    private Student student;

    private String imageName;
    private Bitmap compressor;
    private String studentImage;
    private String imageCertification;
    private String imagePreviousClassCertificate;
    private Uri imageUri = null;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private static final int MAX_LENGTH = 100;
    private int image_type = 0; //0:student personal image, 1:Cirtifecation image, 2:Identfication
    private String image_path = "account/students/";
    private int image_h = 2;
    private int image_w = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonalInformationBinding.inflate(inflater, container, false);
        //studentID = activity.getStudentID();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        activity = (EditStudentActivity) getActivity();

        //Fill student info
        studentAccount = activity.getUser();
        student = activity.getStudent();
        fillStudentInfo();
        updateUser();
        calenderViewDate();
        selectImage();
    }

    private void fillStudentInfo() {
        studentImage = studentAccount.getUserImage();
        imageCertification = student.getBirthCertificate();
        imagePreviousClassCertificate = student.getPreviousClassCertificate();
        Picasso.get().load(studentImage).into(binding.imageStudent);
        binding.editTextStudentName.setText(studentAccount.getName());
        binding.editTextStudentDateOfBirth.setText(studentAccount.getDateOfBirth());
        binding.editTextIdentification.setText(student.getIdentification());
        binding.editTextCountryOrigin.setText(student.getCountryOrigin());
        binding.editTextCountryBirth.setText(student.getCountryBirth());
        Picasso.get().load(imageCertification).into(binding.imageViewCirtefication);
        Picasso.get().load(imagePreviousClassCertificate).into(binding.imageViewPreviousClassCertificate);
    }

    private void updateUser() {
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentAccount.setUserImage(studentImage);
                studentAccount.setName(binding.editTextStudentName.getText().toString());
                studentAccount.setDateOfBirth(binding.editTextStudentDateOfBirth.getText().toString());
                Log.d("getDateOfBirth", studentAccount.getDateOfBirth());
                activity.setUser(studentAccount);

                student.setIdentification(binding.editTextIdentification.getText().toString());
                student.setCountryOrigin(binding.editTextCountryOrigin.getText().toString());
                student.setCountryBirth(binding.editTextCountryBirth.getText().toString());
                student.setBirthCertificate(imageCertification);
                student.setPreviousClassCertificate(imagePreviousClassCertificate);
                activity.setStudent(student);
            }
        });
    }

    private void calenderViewDate() {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH) + 1;
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        //binding.editTextStudentDateOfBirth.setText(mYear + "/" + mMonth + "/" + mDay);
        binding.editTextStudentDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String selectedDate = selectedyear + "/" + (selectedmonth + 1) + "/" + selectedday;
                        binding.editTextStudentDateOfBirth.setText(selectedDate);
                    }
                }, mYear, mMonth - 1, mDay);
                mDatePicker.show();
            }
        });
    }

    private void selectImage() {
        binding.imageStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_type = 0;
                image_path = "account/students/";
                image_h = 2;
                image_w = 2;
                cropImage();
            }
        });
        binding.constraintCirtifecation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_type = 1;
                image_path = "students/certification";
                image_h = 4;
                image_w = 2;
                cropImage();
            }
        });
        binding.constraintPreviousClassCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_type = 2;
                image_path = "students/PreviousClassCertificate";
                image_h = 4;
                image_w = 2;
                cropImage();
            }
        });

    }

    private void cropImage() {
        imageUri = null;
        imageName = "";
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {
            //TODO: Must add activity on Manifest file add this line code
            //            AndroidManifest.xml
            //            <activity
            //            android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
            //            android:theme="@style/Base.Theme.AppCompat" />
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    //.setMinCropResultSize(512,512)
                    .setAspectRatio(image_w, image_h)
                    .start(getContext(), PersonalInformationFragment.this);//getContext(), PersonalInformationFragment.this
        }
    }

    //Crop image

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                imageUri = result.getUri();
               /* byte[] decodedString = Base64.decode(imageUri+"", Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.imageStudent.setImageBitmap(decodedByte);*/
                //imageViewItem.setImageURI(imageUri);
                postImageOnFireBase();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //asdaddcddccffdfd
    private void postImageOnFireBase() {
        if (imageUri != null) {
            compressAndNameImage();
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            compressor.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayInputStream);
            byte[] thumpData = byteArrayInputStream.toByteArray();
            StorageReference filePath = storageReference.child(image_path).child(imageName);
            UploadTask uploadTask = filePath.putBytes(thumpData);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Toast.makeText(getContext(), filePath.getDownloadUrl() + "", Toast.LENGTH_LONG).show();
                            // studentImage = filePath.getDownloadUrl() + "";
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                switch (image_type) {
                                    case 0:
                                        studentImage = task.getResult().toString();
                                        // Toast.makeText(getContext(), studentImage + "", Toast.LENGTH_LONG).show();
                                        Picasso.get().load(studentImage).into(binding.imageStudent);
                                        break;
                                    case 1:
                                        imageCertification = task.getResult().toString();
                                        // Toast.makeText(getContext(), studentImage + "", Toast.LENGTH_LONG).show();
                                        Picasso.get().load(imageCertification).into(binding.imageViewCirtefication);
                                        break;
                                    case 2:
                                        imagePreviousClassCertificate = task.getResult().toString();
                                        // Toast.makeText(getContext(), studentImage + "", Toast.LENGTH_LONG).show();
                                        Picasso.get().load(imagePreviousClassCertificate).into(binding.imageViewPreviousClassCertificate);
                                        break;
                                }
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            });
        } else {
            //Please upload image
        }
    }

    //Name image
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void compressAndNameImage() {
        imageName = random() + ".jpg";
        File imageFile = new File(imageUri.getPath());
        try {
            compressor = new Compressor(getContext())//Ctrl + F  search,
                    .setMaxHeight(240)
                    .setMaxWidth(360)
                    .setQuality(5)
                    .compressToBitmap(imageFile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}