package com.digiduka.digiduka.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class GenerateReportsActivity extends AppCompatActivity {
    private DatabaseReference refrence;
    private FirebaseAuth mAuth;
    private ArrayList<Product> products=new ArrayList<>();
    private ArrayList<String> productname=new ArrayList<>();
    private RecyclerView transactionsRecycler;
    private ProductListAdapter mAdapter;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat dateFormatin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_reports);
        transactionsRecycler=findViewById(R.id.transactionsRecycler);
//        profitTotal=findViewById(R.id.daysTransactionsTotal);
//        date=findViewById(R.id.profitDate);
        mAuth = FirebaseAuth.getInstance();
        getTransactions();




    }
    public void getTransactions(){
        refrence= FirebaseDatabase.getInstance().getReference(mAuth.getCurrentUser().getUid()).child(Constants.TRANSACTIONS_DB_KEY);
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    String datein=data.getValue(Transaction.class).getDateCreated();
                    Date date = null;
                    try {
                         date=dateFormatin.parse(datein);
                    } catch (ParseException e) { 
                       
                        e.printStackTrace();
                    }
                   
                    if(dateFormat.format(new Date()).equals(dateFormat.format(date))) {
                        for (Product product : data.getValue(Transaction.class).getProducts()) {

                            if (productname.contains(product.getNameOfProduct())) {
                                for (Product product1 : products) {
                                    if (product1.getPushId().equals(product.getPushId())) {
                                        product1.setProfitAmount(product, product1);
                                    }
                                }
                            } else {
                                products.add(product);
                                productname.add(product.getNameOfProduct());
                            }

                            Log.v("product", product.toString());
                        }
                    }
                }
//                date.setText("Profit for  " +dateFormat.format(new Date()));
                Log.v("size",String.valueOf( products.size()));
                mAdapter = new ProductListAdapter(getApplicationContext(), products);
                transactionsRecycler.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                transactionsRecycler.setLayoutManager(layoutManager);
                transactionsRecycler.setHasFixedSize(false);

                gettotalProfit(products);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void gettotalProfit(ArrayList<Product> products){
        int total=0;
        for (Product product:products){
           total+= ((product.getSellingPrice()-product.getBuyingPrice())*product.getAmount()) ;
        }
//        profitTotal.setText("Total profit   Ksh"+String.valueOf(total));

    }
}
