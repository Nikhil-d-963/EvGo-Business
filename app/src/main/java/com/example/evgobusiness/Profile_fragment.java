package com.example.evgobusiness;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Profile_fragment extends Fragment {

    //declaration
    View view;
    EditText name,phoneNum,address,shopName,upiId;

    Button locationImgUpdateBtn,letsgooBtn;
    ImageView locationPhoto;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;


    int wallet = 1;

    FusedLocationProviderClient fusedLocationProviderClient;
    GeoPoint geoPoint;




    public Profile_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        name = view.findViewById(R.id.nameInput);
        phoneNum = view.findViewById(R.id.phoneNumInput);
        address = view.findViewById(R.id.addressInput);
        shopName = view.findViewById(R.id.shopNameInput);
        upiId = view.findViewById(R.id.upiID);


//        locationImgUpdateBtn = view.findViewById(R.id.locationImageBtn);
        letsgooBtn = view.findViewById(R.id.letsgoo);

//        locationPhoto = view.findViewById(R.id.locationImage);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        TextView mail = view.findViewById(R.id.mail);
        mail.setText(firebaseAuth.getCurrentUser().getEmail());


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

                //if location got catch then take the shop photo
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getLocation();

        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);

        }





        letsgooBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String mName = name.getText().toString().trim();
                String mShopName = shopName.getText().toString().trim();
                String mUpi = upiId.getText().toString().trim();
                String mPhoneNum = phoneNum.getText().toString().trim();
                String mAddress = address.getText().toString().trim();
                String mWallet = String.valueOf(wallet);



                //validating the user input
                if(TextUtils.isEmpty(mName)){
                    name.setError("Enter name");
                    return;
                }if(TextUtils.isEmpty(mPhoneNum)){
                    phoneNum.setError("Enter Phone number");
                    return;
                }if(mPhoneNum.length()<10){
                    phoneNum.setError("Invalid phone number");
                    return;
                }if(mUpi.isEmpty()){
                    upiId.setError("Enter margent upi ID");
                    return;
                }

                if(TextUtils.isEmpty(mShopName)){
                    shopName.setError("Enter shop name");
                    return;
                }if(TextUtils.isEmpty(mAddress)){
                    address.setError("Enter address");
                    return;
                }if(mAddress.length()<10){
                    address.setError("please enter valid address");
                    return;
                }


                if(firebaseAuth.getCurrentUser()!=null){
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    //declaring the object of documentation reference
                    DocumentReference documentReference = firebaseFirestore.collection("Renders").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", mName);
                    user.put("shop_name", mShopName);
                    user.put("phone_number", mPhoneNum);
                    user.put("address", mAddress);
                    user.put("wallet",mWallet);
//                    user.put("imageUrl",mvR);//image
                    user.put("geo",geoPoint);//geo
                    user.put("m_upi",mUpi);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.holder, new EmailVerificationFragment(), "NewFragmentTag");
                            ft.commit();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error! " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        return view;
    }



    ////method of the location getting
    @SuppressLint("MissingPermission")
    public void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location!=null){

                    try {
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        double Latitude = addresses.get(0).getLatitude();
                        double Longitude = addresses.get(0).getLongitude();

                         geoPoint = new GeoPoint(Latitude,Longitude);



                    }catch (IOException e){
                        e.printStackTrace();
                    }


                }
            }
        });
    }

}