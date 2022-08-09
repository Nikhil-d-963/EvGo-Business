package com.example.evgobusiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DashBoardActivity extends AppCompatActivity {


    TextView custemer_name;
    CardView wallete,transactions,feedback,contactUs,profileUpdate,account;
    String userID;
    ImageView profile;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        if (firebaseUser.isEmailVerified()){
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        }else{
            Intent Sintent = new Intent(DashBoardActivity.this,emailHolder1.class);
            startActivity(Sintent);
            finish();
        }





        custemer_name = findViewById(R.id.name);
        wallete = findViewById(R.id.cardWallete);
        transactions = findViewById(R.id.cardTransaction);
        feedback = findViewById(R.id.cardFeedBack);
        contactUs = findViewById(R.id.cardContactUs);
        profileUpdate = findViewById(R.id.cardProfile);
        account = findViewById(R.id.cardAccount);
        profile = findViewById(R.id.profile);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentz = new Intent(getApplicationContext(),Account.class);
                startActivity(intentz);
            }
        });

//
//
        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Renders").document(userID);
        documentReference.addSnapshotListener(DashBoardActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                custemer_name.setText(documentSnapshot.getString("name"));
            }
        });



        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Pintent = new Intent(DashBoardActivity.this,updateProfile.class);
                startActivity(Pintent);
            }
        });

        wallete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Wintent = new Intent(DashBoardActivity.this,wallete.class);
                startActivity(Wintent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Aintent = new Intent(DashBoardActivity.this,issues.class);
                startActivity(Aintent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Fintent = new Intent(DashBoardActivity.this,FeedBack.class);
                startActivity(Fintent);
            }
        });

        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Tintent = new Intent(DashBoardActivity.this,transactions.class);
                startActivity(Tintent);
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Fintent = new Intent(DashBoardActivity.this,contactUs.class);
                startActivity(Fintent);
            }
        });











    }
}