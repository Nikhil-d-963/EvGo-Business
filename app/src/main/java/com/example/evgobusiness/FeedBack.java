package com.example.evgobusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class FeedBack extends AppCompatActivity {

    int number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        TextView mail;
        EditText feedBackInput;
        Button feedBackSend;
        ImageView profile;


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();



        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentz = new Intent(getApplicationContext(),Account.class);
                startActivity(intentz);
            }
        });





        feedBackInput = findViewById(R.id.feedbackInput);
        feedBackSend = findViewById(R.id.feedbackSend);
        mail = findViewById(R.id.mail);




        String userID = firebaseAuth.getCurrentUser().getUid();


        String mEmailId = firebaseAuth.getCurrentUser().getEmail();


        mail.setText(mEmailId);

        feedBackSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedBackMsg = feedBackInput.getText().toString();
                DocumentReference documentReference = firebaseFirestore.collection("FeedBacks").document(userID+number);
                Map<String, Object> user = new HashMap<>();
                user.put("feedBack"+number, feedBackMsg);
                user.put("emailID",mEmailId);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Thanks for your feedBack", Toast.LENGTH_SHORT).show();
                        feedBackInput.setText("");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error! " + e, Toast.LENGTH_SHORT).show();
                    }
                });
                number = number+1;
            }
        });


    }
}