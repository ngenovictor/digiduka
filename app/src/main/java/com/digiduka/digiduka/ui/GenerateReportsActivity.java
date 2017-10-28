package com.digiduka.digiduka.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.ProductListAdapter;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.models.Transaction;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GenerateReportsActivity extends AppCompatActivity {
    private DatabaseReference refrence;
    private FirebaseAuth mAuth;
    private ArrayList<Product> products=new ArrayList<>();
    private ArrayList<String> productname=new ArrayList<>();
    private RecyclerView transactionsRecycler;
    private ProductListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_reports);
        transactionsRecycler=findViewById(R.id.transactionsRecycler);
        mAuth = FirebaseAuth.getInstance();
        getTransactions();



    }
    public void getTransactions(){
        refrence= FirebaseDatabase.getInstance().getReference(mAuth.getCurrentUser().getUid()).child(Constants.TRANSACTIONS_DB_KEY);
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    for (Product product:data.getValue(Transaction.class).getProducts()){
                        if (productname.contains(product.getNameOfProduct())){
                            for (Product product1:products){
                                if (product1.getPushId().equals(product.getPushId())){
                                    product1.setProfitAmount(product,product1);
                                }
                            }
                        }else{
                            products.add(product);
                            productname.add(product.getNameOfProduct());
                        }

                        Log.v("product",product.toString());
                    }
                }
                Log.v("size",String.valueOf( products.size()));
                mAdapter = new ProductListAdapter(getApplicationContext(), products);
                transactionsRecycler.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                transactionsRecycler.setLayoutManager(layoutManager);
                transactionsRecycler.setHasFixedSize(false);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
