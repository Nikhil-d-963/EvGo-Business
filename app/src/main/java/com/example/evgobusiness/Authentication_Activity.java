package com.example.evgobusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class Authentication_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this Thread is to mention the duration of splash screen
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this); //Installed the splash screen api that as in dependency
        // and the configurations in res/values/styles.xml;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        //Fragment initialization  fragment->registration
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.holder,new Registration_fragment());
        ft.commit();

    }
}