package com.digiduka.digiduka.ui;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.User;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment {

    private EditText username;
    private EditText userphone;
    private EditText useremail;
    private EditText usershop;
    private ImageView userImage;
    private  Spinner gender;
    private Button usersubmit;
    private String usergender;
    String userimage;
    private FirebaseAuth mAuth;
    private static final int REQUEST_IMAGE_CAPTURE1 = 123;

    public UserInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_info, container, false);
        username=view.findViewById(R.id.username);
        userphone=view.findViewById(R.id.userphone);
        useremail=view.findViewById(R.id.useremail);
        usershop=view.findViewById(R.id.usershop);
        userImage=view.findViewById(R.id.userimage);
        gender=view.findViewById(R.id.gender);
        usersubmit=view.findViewById(R.id.usersubmit);
        mAuth = FirebaseAuth.getInstance();
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE1);
                }

            }
        });

        usersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString().trim();
                String phone=userphone.getText().toString().trim();
                String email=useremail.getText().toString().trim();
                String shop=usershop.getText().toString().trim();

                //String usergender=;



                User user= new User(name,shop,email,phone,userimage,usergender);
                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference().child(mAuth.getCurrentUser().getUid()).child(Constants.USER_INFO_KEY);
                /**
                 * someone fix this reference to store to a specific category
                 * **/
                ref.setValue(user);
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);


            }
        });


        Spinner spinner = (Spinner) view.findViewById(R.id.gender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usergender=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        userimage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE1 && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //set image visible
            userImage.setVisibility(View.VISIBLE);
            userImage.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

}
