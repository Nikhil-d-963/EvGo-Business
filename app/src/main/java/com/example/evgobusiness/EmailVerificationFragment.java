package com.example.evgobusiness;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

import java.util.concurrent.Executor;


public class EmailVerificationFragment extends Fragment {

    View view;
    Button emailVerify;
    TextView mail,name;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    public EmailVerificationFragment() {
        // Required empty public constructor
    }


    String userID = firebaseAuth.getCurrentUser().getUid();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_email_verification, container, false);




        if(firebaseUser.isEmailVerified()){
            Intent intent = new Intent(getActivity(),DashBoardActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }


        name = view.findViewById(R.id.name);
        mail = view.findViewById(R.id.mailz);



        DocumentReference documentReference = firebaseFirestore.collection("Renders").document(userID);
        documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                name.setText(documentSnapshot.getString("name"));
            }
        });


        emailVerify = view.findViewById(R.id.Verify_Email);


        String mMail = firebaseAuth.getCurrentUser().getEmail();

        mail.setText(mMail);



        emailVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Email sent", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(getActivity(),Authentication_Activity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }
}