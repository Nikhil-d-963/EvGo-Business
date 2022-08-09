package com.example.evgobusiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView name,address,shopName,phoneNumber,mail;
        Button editInfo,signOut;

        name = findViewById(R.id.renderName);
        shopName = findViewById(R.id.renderShopName);
        phoneNumber = findViewById(R.id.renderPhoneNumber);
        address = findViewById(R.id.renderAddress);
        mail = findViewById(R.id.mail);

        editInfo = findViewById(R.id.editInfo);
        signOut = findViewById(R.id.signOut);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        mail.setText(firebaseAuth.getCurrentUser().getEmail());
        String userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Renders").document(userID);
        documentReference.addSnapshotListener(Account.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                name.setText(documentSnapshot.getString("name"));
                shopName.setText(documentSnapshot.getString("shop_name"));
                phoneNumber.setText(documentSnapshot.getString("phone_number"));
                address.setText(documentSnapshot.getString("address"));
            }
        });

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Eintent = new Intent(Account.this,emailHolder2.class);
                startActivity(Eintent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent Sintent = new Intent(Account.this,Authentication_Activity.class);
                startActivity(Sintent);
                finish();


            }
        });

    }
}