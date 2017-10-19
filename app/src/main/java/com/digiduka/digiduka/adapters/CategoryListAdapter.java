package com.digiduka.digiduka.adapters;

import android.app.Activity;


import android.content.Context;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.ui.AddProductFragment;
import com.digiduka.digiduka.ui.MainActivity;
import com.digiduka.digiduka.ui.ProductsFragment;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by mosoti on 10/13/17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private Context mContext;
    private ArrayList<Category> mCategorys = new ArrayList<>();
    private String source;
    private StockItemsAdapter mAdapter;


    public CategoryListAdapter(Context context, ArrayList<Category> categories, String msource, StockItemsAdapter adapter) {
        mContext = context;
        mCategorys = categories;
        source = msource;
        mAdapter = adapter;

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

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView gridText;
        private ConstraintLayout categoryProductsHolder;
        private ImageView dropDownImage;
        private ConstraintLayout gridViewHolder;
        private Button addProductButton;
        private Context mContext1;
        private RecyclerView categoryProductsRecyclerView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mContext1 = itemView.getContext();
            gridText = itemView.findViewById(R.id.gridText);
            categoryProductsHolder = itemView.findViewById(R.id.categoryProductsHolder);
            dropDownImage = itemView.findViewById(R.id.dropDownImage);
            gridViewHolder = itemView.findViewById(R.id.gridViewHolder);
            addProductButton = itemView.findViewById(R.id.addProductButton);


            categoryProductsRecyclerView = itemView.findViewById(R.id.categoryProductsRecyclerView);


            categoryProductsHolder.setVisibility(View.GONE);
            gridViewHolder.setOnClickListener(this);
            dropDownImage.setOnClickListener(this);
        }

        public void bindCategory(final Category category) {
            gridText.setText(category.getCategoryTitle());
            gridText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (source.equals("sell")) {
                        MainActivity activity = (MainActivity) (mContext);
                        android.app.FragmentManager fm = activity.getFragmentManager();
                        ProductsFragment fragment = new ProductsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("category", Parcels.wrap(category));
                        fragment.setArguments(bundle);
                        fragment.show(fm, "product");
                    }

                }
            });


            //the products under each category:
            //set the adapter
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(currentUser.getUid()).child(Constants.CATEGORY_DB_KEY).child(category.getCategoryId()).child(Constants.PRODUCTS_DB_KEY);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        products.add(snapshot.getValue(Product.class));
                    }
                    CategoriesProductsListAdapter adapter = new CategoriesProductsListAdapter(category, mContext, products, mAdapter);
                    categoryProductsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    categoryProductsRecyclerView.setHasFixedSize(false);
                    categoryProductsRecyclerView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            addProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddProductFragment fragment = new AddProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("category", Parcels.wrap(category));
                    fragment.setArguments(bundle);

                    //FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();

                    android.support.v4.app.FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();

                    fragment.show(fm, "dialog");
                }
            });

        }

        @Override
        public void onClick(View view) {
            if (source.equals("stock")) {
                if (view == dropDownImage || view == gridViewHolder) {
                    if (categoryProductsHolder.getVisibility() == View.VISIBLE) {
                        categoryProductsHolder.setVisibility(View.GONE);
                    } else {
                        categoryProductsHolder.setVisibility(View.VISIBLE);
                    }
                }
            }
        }


    }
}
