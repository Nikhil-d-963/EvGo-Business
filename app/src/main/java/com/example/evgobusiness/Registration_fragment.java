package com.example.evgobusiness;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;


public class Registration_fragment extends Fragment {



    //Views declaring
    View view;
    EditText emailInput,passwordInput,confirmPasswordInput;
    Button registerBtn;
    ProgressBar progressRing;
    TextView signInText;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ProgressDialog progressDialog;





    public Registration_fragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        //if location got catch then take the shop photo
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
        //Check the user already logged in or not
        if(firebaseAuth.getCurrentUser()!=null){
            Intent intent = new Intent(getActivity(),DashBoardActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }





        //input initialization
        emailInput = view.findViewById(R.id.regEmailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordInput);

        //button initialization
        registerBtn = view.findViewById(R.id.registerBtn);
        progressRing = view.findViewById(R.id.prograssRing);
        signInText = view.findViewById(R.id.signInText);

        //button status declaring
        registerBtn.setVisibility(View.VISIBLE);
        progressRing.setVisibility(View.GONE);


        //register btn onclick code
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Registering");
                progressDialog.show();
                String sEmailId = emailInput.getText().toString().trim();
                String sPasswordInput = passwordInput.getText().toString().trim();
                String sConfirmPasswordInput = confirmPasswordInput.getText().toString().trim();


                //email input validating
                final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                //validating the null input and others
                if(TextUtils.isEmpty(sEmailId)) {
                    emailInput.setError("Enter email");
                    progressDialog.dismiss();
                    return;
                }if (!sEmailId.matches(EMAIL_PATTERN)) {
                        emailInput.setError("Invalid Email Address");
                    progressDialog.dismiss();
                }if(passwordInput.toString().trim().isEmpty()){
                    passwordInput.setError("Enter password");
                    progressDialog.dismiss();
                    return;
                }if(confirmPasswordInput.toString().trim().isEmpty()){
                    confirmPasswordInput.setError("Enter password");
                    progressDialog.dismiss();
                    return;
                }if(sPasswordInput.length()<6){
                    passwordInput.setError("Password is short");
                    progressDialog.dismiss();
                    return;
                }if(!sPasswordInput.equals(sConfirmPasswordInput)) {
                    confirmPasswordInput.setError("Password not match");
                    passwordInput.setError("Password not match");
                    progressDialog.dismiss();
                    return;
                }


                //btn status declaring
                registerBtn.setVisibility(View.VISIBLE);
                progressRing.setVisibility(View.INVISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(sEmailId, sPasswordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            registerBtn.setVisibility(View.INVISIBLE);
                            progressRing.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "successfully registered", Toast.LENGTH_SHORT).show();

                            //registration fragment to profile fragment
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.holder, new Profile_fragment(), "NewFragmentTag");
                            progressDialog.dismiss();
                            ft.commit();

                        }else {
                            Toast.makeText(getActivity(), "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
            }
        });

        //registration fragment to sign in fragment
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.holder, new SignIn_fragment(), "NewFragmentTag");
                ft.commit();
            }
        });





        return view;
    }
}