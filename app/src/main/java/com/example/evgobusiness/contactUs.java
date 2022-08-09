package com.example.evgobusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class contactUs extends AppCompatActivity {

    CardView appinfo,visiteWebsite,tc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        appinfo = findViewById(R.id.appinfo);
        appinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),appInfo.class);
                startActivity(intent);
            }
        });

        visiteWebsite = findViewById(R.id.visiteWebsite);
        visiteWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.evgo.tech/?m=0";
                Intent vw = new Intent(Intent.ACTION_VIEW);
                vw.setData(Uri.parse(url));
                startActivity(vw);
            }
        });

        tc = findViewById(R.id.tc);
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.evgo.tech/p/terms-and-conditions.html";
                Intent vw = new Intent(Intent.ACTION_VIEW);
                vw.setData(Uri.parse(url));
                startActivity(vw);
            }
        });

    }
}