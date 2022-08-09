package com.example.evgobusiness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class issues extends AppCompatActivity {
    EditText issue;
    Button report;
    TextView mail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String uuid;
    String currentUserID;
    String dateF;
    String currentUserPhoneNumber,currentUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issues);

        issue = findViewById(R.id.issuesInput);
        report =findViewById(R.id.report);
        mail = findViewById(R.id.mail);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference1 = firebaseFirestore.collection("Renders").document(firebaseAuth.getCurrentUser().getUid());
        documentReference1.addSnapshotListener(issues.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                currentUserPhoneNumber = documentSnapshot.getString("phone_number");
                currentUserName = documentSnapshot.getString("name");

            }
        });



        mail.setText(firebaseAuth.getCurrentUser().getEmail());

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userIssuesInput = issue.getText().toString();

                Date currentTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    currentTime = Calendar.getInstance().getTime();
                }

                dateF = String.valueOf(currentTime);
                uuid = UUID.randomUUID().toString();
                currentUserID = firebaseAuth.getCurrentUser().getUid();




                DocumentReference documentReference = firebaseFirestore.collection("EVB-Issues-"+currentUserID).document(uuid);
                Map<String, Object> user = new HashMap<>();
                user.put("issue", userIssuesInput);
                user.put("date_time", dateF);
                user.put("userId",currentUserID);
                user.put("userReportID",uuid);
                user.put("userEmail",firebaseAuth.getCurrentUser().getEmail());
                user.put("phoneNumber",currentUserPhoneNumber);
                user.put("name",currentUserName);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(issues.this, "Submitted Thanks "+currentUserName, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),DashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(issues.this, "!Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




    }
}