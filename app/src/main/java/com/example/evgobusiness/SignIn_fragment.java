package com.example.evgobusiness;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.protobuf.Api;


public class SignIn_fragment extends Fragment {


    //Views declaring
    EditText loginEmail,loginPassword;
    Button loginBtn;
    ProgressBar progressRing;
    TextView registerText,forgotPassword;
    ProgressDialog progressDialog;


    //firebase declaration
    FirebaseAuth firebaseAuth;
    View view;
    public SignIn_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.sign_in_fragment, container, false);

        loginEmail = view.findViewById(R.id.loginEmailInput);
        loginPassword = view.findViewById(R.id.passwordInput);
        loginBtn = view.findViewById(R.id.loginBtn);
        progressRing = view.findViewById(R.id.prograssRing);
        registerText = view.findViewById(R.id.registerText);
        forgotPassword = view.findViewById(R.id.forgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();





        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Logging In");
                progressDialog.show();
                String mEmailId = loginEmail.getText().toString().trim();
                String mPasswordInput = loginPassword.getText().toString().trim();


                //email input validating
                final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                if(TextUtils.isEmpty(mEmailId)){
                    loginEmail.setError("Enter email");
                    progressDialog.dismiss();
                    return;
                }if (!mEmailId.matches(EMAIL_PATTERN)) {
                    loginEmail.setError("Invalid Email Address");
                    progressDialog.dismiss();

                }if(TextUtils.isEmpty(mPasswordInput)){
                    loginPassword.setError("Enter password");
                    progressDialog.dismiss();
                    return;
                }

                //Authenticate the user
                firebaseAuth.signInWithEmailAndPassword(mEmailId,mPasswordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //DashBoard
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.holder, new EmailVerificationFragment(), "NewFragmentTag");
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



        ///////////////////
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.holder, new Registration_fragment(), "NewFragmentTag");
                ft.commit();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SignIn_fragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.holder, new ForgotPasswordFragment(), "NewFragmentTag");
                ft.addToBackStack(String.valueOf(fragment));
                ft.commit();
            }
        });
        return  view;



    }






}