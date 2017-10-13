package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by mosoti on 10/13/17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private Context mContext;
    private ArrayList<Category> mCategorys=new ArrayList<>();

    public CategoryListAdapter(Context context,ArrayList<Category> categories) {
        mContext = context;
        mCategorys = categories;
    }
    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.bindCategory(mCategorys.get(position));
    }

    @Override
    public int getItemCount() {
        // Log.v("items",mItems.get(0).getName());
        return mCategorys.size();
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView mNameView;

        private Context mContext;

        public CategoryViewHolder(View itemView){
            super(itemView);
            //ButterKnife.bind(this,itemView);
            mNameView =(TextView) itemView.findViewById(R.id.gridText);

            mContext=itemView.getContext();


        }

        public void bindCategory(Category category){

            mNameView.setText(category.getCategoryTitle());

        }


    }
}
