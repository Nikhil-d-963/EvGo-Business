package com.example.evgobusiness;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class transactions extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TransactionDetailsService> transactionArrayList;
    transactionAdopter transactionAdopter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    String renderId;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);



//        profile = findViewById(R.id.profile);
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentz = new Intent(getApplicationContext(),Account.class);
//                startActivity(intentz);
//            }
//        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching transaction details....");
        progressDialog.setCancelable(true);
        progressDialog.show();

        firebaseAuth = FirebaseAuth.getInstance();
        renderId = firebaseAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        transactionArrayList = new ArrayList<TransactionDetailsService>();
        transactionAdopter = new transactionAdopter(transactions.this, transactionArrayList);
        recyclerView.setAdapter(transactionAdopter);


        EventChangeListner();

    }

    private void EventChangeListner() {
        firebaseFirestore.collection("Transactions-" + renderId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) ;
                            progressDialog.dismiss();
                            Log.e("fireBase error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                transactionArrayList.add(dc.getDocument().toObject(TransactionDetailsService.class));
                            }

                            transactionAdopter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) ;
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}