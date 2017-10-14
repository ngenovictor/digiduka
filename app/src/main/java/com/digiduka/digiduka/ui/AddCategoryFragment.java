package com.digiduka.digiduka.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.databaseHandlers.TableControllerCategory;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends DialogFragment implements View.OnClickListener {
    private Button submitCategory;
    private AutoCompleteTextView categoryTitle;
    private AutoCompleteTextView categoryDescription;
    private FirebaseAuth mAuth;
    private ImageView cameraBtn;
    private ImageView imageLabel;
    private static final int REQUEST_IMAGE_CAPTURE = 111;

    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        submitCategory = view.findViewById(R.id.submitCategory);
        categoryTitle = view.findViewById(R.id.categoryTitle);
        categoryDescription = view.findViewById(R.id.categoryDescription);
        mAuth = FirebaseAuth.getInstance();
        submitCategory.setOnClickListener(this);
        cameraBtn = view.findViewById(R.id.cameraIcon);
        imageLabel = view.findViewById(R.id.imageLabel);
        imageLabel.setVisibility(View.INVISIBLE);
        cameraBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == submitCategory){
            String title = categoryTitle.getText().toString().trim();
            String description = categoryDescription.getText().toString().trim();
            Category newCategory = new Category(title, description);
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference(Constants.CATEGORY_DB_KEY).child(mAuth.getCurrentUser().getUid());
            DatabaseReference puhRef = reference.push();
            String categoryId = puhRef.getKey();
            newCategory.setCategoryId(categoryId);
            puhRef.setValue(newCategory);

            /**
             * this line enables offline mode for firebase
             * **/
             reference.keepSynced(true);
            /**
             * this line enables offline mode for firebase
             * **/


            /**
             * send to sql methods here
             * **/
             TableControllerCategory tableControllerCategory = new TableControllerCategory(getContext());

            boolean createSuccessful = tableControllerCategory.create(newCategory);

            if (createSuccessful) {
                Toast.makeText(getContext(), "Category data saved successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Failed to save category data", Toast.LENGTH_LONG).show();
            }
            /**
             * send to sql methods here
             * **/

            /**
             * Retrieve object count in Database
             * **/
            //countRecords();
            dismiss();
        }

        if (view == cameraBtn) {
            onLaunchCamera();
        }
    }

    public void onLaunchCamera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //set image visible
            imageLabel.setVisibility(View.VISIBLE);
            imageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        /**
         * someone fix this reference to store to a specific category
         * **/
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("images");
        /**
         * someone fix this reference to store to a specific category
         * **/
        ref.setValue(imageEncoded);
    }

}
