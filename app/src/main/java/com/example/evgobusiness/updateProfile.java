package com.example.evgobusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class updateProfile extends AppCompatActivity {

    EditText name,phoneNum,shopName;
    Button update;
    TextView mail;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        name = findViewById(R.id.nameInput);
        phoneNum = findViewById(R.id.phoneNumInput);
        shopName = findViewById(R.id.shopNameInput);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        mail = findViewById(R.id.mail);
        mail.setText(firebaseAuth.getCurrentUser().getEmail());



        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameR = name.getText().toString();
                String numberR = phoneNum.getText().toString();
                String shop_nameR = shopName.getText().toString();

               if(nameR.isEmpty()){
                   name.setError("Enter Name");
               } if(numberR.isEmpty()){
                   phoneNum.setError("Enter phone Number");
               } if(shop_nameR.isEmpty()){
                   shopName.setError("Enter shop Name");
               } if(phoneNum.length()<10){
                   phoneNum.setError("Enter valid phone Number");
               }else {

                    DocumentReference documentReference1 = firebaseFirestore.collection("Renders").document(firebaseAuth.getCurrentUser().getUid());
                    documentReference1.update("name",nameR);
                    documentReference1.update("shop_name",shop_nameR);
                    documentReference1.update("phone_number",numberR);
                    Toast.makeText(updateProfile.this, "!Updated", Toast.LENGTH_SHORT).show();

                }




            }
        });





    }
}