package com.digiduka.digiduka.adapters;

/**
 * Created by victor on 10/13/17.
 */

import android.app.Activity;


import android.app.FragmentManager;
import android.content.Context;

import android.support.constraint.ConstraintLayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.ui.AddProductFragment;

/**
 * Created by victor on 10/11/17.
 */

public class FirebaseCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView gridText;
    private ConstraintLayout categoryProductsHolder;
    private ImageView dropDownImage;
    private ConstraintLayout gridViewHolder;
    private Button addProductButton;
    private Context mContext;

    public FirebaseCategoriesViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        gridText = itemView.findViewById(R.id.gridText);
        categoryProductsHolder = itemView.findViewById(R.id.categoryProductsHolder);
        dropDownImage = itemView.findViewById(R.id.dropDownImage);
        gridViewHolder = itemView.findViewById(R.id.gridViewHolder);
        addProductButton = itemView.findViewById(R.id.addProductButton);

        categoryProductsHolder.setVisibility(View.GONE);
        gridViewHolder.setOnClickListener(this);
        dropDownImage.setOnClickListener(this);
        addProductButton.setOnClickListener(this);

    }

    public void bindCategory(Category category){
        gridText.setText(category.getCategoryTitle());
    }

    @Override
    public void onClick(View view) {
        if (view == dropDownImage || view == gridViewHolder){
            if (categoryProductsHolder.getVisibility()==View.VISIBLE){
                categoryProductsHolder.setVisibility(View.GONE);
            }else{
                categoryProductsHolder.setVisibility(View.VISIBLE);
            }
        }else if(view == addProductButton){
            AddProductFragment fragment = new AddProductFragment();
            FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
            android.app.FragmentManager fm = ((Activity) mContext).getFragmentManager();
            fragment.show(fm, "dialog");
        }
    }
}

