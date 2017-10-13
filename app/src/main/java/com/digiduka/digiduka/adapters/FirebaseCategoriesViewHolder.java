package com.digiduka.digiduka.adapters;

/**
 * Created by victor on 10/13/17.
 */

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;

/**
 * Created by victor on 10/11/17.
 */

public class FirebaseCategoriesViewHolder extends RecyclerView.ViewHolder{
    private TextView gridText;
    private ConstraintLayout gridViewHolder;

    public FirebaseCategoriesViewHolder(View itemView) {
        super(itemView);
        gridText = itemView.findViewById(R.id.gridText);
        gridViewHolder = itemView.findViewById(R.id.gridViewHolder);
    }

    public void bindCategory(Category category){
        gridText.setText(category.getCategoryTitle());
    }
}

