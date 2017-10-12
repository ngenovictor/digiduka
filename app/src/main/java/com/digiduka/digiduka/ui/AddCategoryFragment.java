package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.databaseHandlers.TableControllerCategory;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends DialogFragment implements View.OnClickListener {
    private Button submitCategory;
    private AutoCompleteTextView categoryTitle;
    private AutoCompleteTextView categoryDescription;
    /**
     * initializes the SQL Database TableController
     * **/
    TableControllerCategory tableControllerCategory = new TableControllerCategory(getContext());

    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        submitCategory = view.findViewById(R.id.submitCategory);
        categoryTitle = view.findViewById(R.id.categoryTitle);
        categoryDescription = view.findViewById(R.id.categoryDescription);
        submitCategory.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == submitCategory){
            String title = categoryTitle.getText().toString().trim();
            String description = categoryDescription.getText().toString().trim();
            Category newCategory = new Category(title, description);
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference(Constants.CATEGORY_DB_KEY);
            DatabaseReference puhRef = reference.push();
            String categoryId = puhRef.getKey();
            newCategory.setCategoryId(categoryId);
            puhRef.setValue(newCategory);


            /**
             * send to sql methods here
             * **/

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
    }

}
