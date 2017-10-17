package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by victor on 10/17/17.
 */

public class CategoriesProductsListAdapter extends RecyclerView.Adapter<CategoriesProductsListAdapter.CategoriesProductsListViewHolder> {
    private FirebaseUser currentUser;
    private Category category;
    private Context mContext;
    private ArrayList<Product> products = new ArrayList<>();
    public CategoriesProductsListAdapter(Category category, Context context){
        this.category = category;
        mContext = context;
    }

    @Override
    public CategoriesProductsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_products_list, parent, false);
        CategoriesProductsListViewHolder holder = new CategoriesProductsListViewHolder(view);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(currentUser.getUid()).child(Constants.CATEGORY_DB_KEY).child(category.getCategoryId()).child(Constants.PRODUCTS_DB_KEY);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Log.d("products", snapshot.getValue(Product.class).getNameOfProduct());
                    products.add(snapshot.getValue(Product.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(CategoriesProductsListViewHolder holder, int position) {
        holder.bindProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CategoriesProductsListViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        public CategoriesProductsListViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
        }
        public void bindProduct(Product product){
            productName.setText(product.getNameOfProduct());
        }
    }
}
