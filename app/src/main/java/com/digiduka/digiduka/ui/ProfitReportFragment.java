package com.digiduka.digiduka.ui;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.parceler.Parcels;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * A simple {@link Fragment} subclass.
 */
public class ProfitReportFragment extends Fragment {
    private DatabaseReference refrence;
    private FirebaseAuth mAuth;
    private ArrayList<Product> products=new ArrayList<>();
    private ArrayList<String> productname=new ArrayList<>();
    private RecyclerView transactionsRecycler;
    private ProductListAdapter mAdapter;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateFormatin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private TextView profitTotal;
    private TextView date;
    private String newDate;
    private String newDate1;


    public ProfitReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profit_report, container, false);
        transactionsRecycler=view.findViewById(R.id.transactionsRecycler);
        profitTotal=view.findViewById(R.id.daysTransactionsTotal);
        date=view.findViewById(R.id.profitDate);
        mAuth = FirebaseAuth.getInstance();
        newDate= Parcels.unwrap(getArguments().getParcelable(Constants.START_DAY));
        newDate1= Parcels.unwrap(getArguments().getParcelable(Constants.END_DAY));
        getProfitBtwTwoDates();
        return view;
    }
    public void getProfitBtwTwoDates(){
        refrence= FirebaseDatabase.getInstance().getReference(mAuth.getCurrentUser().getUid()).child(Constants.TRANSACTIONS_DB_KEY);
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    String datein=data.getValue(Transaction.class).getDateCreated();
                    Date date = null;
                    Date datenew=null;
                    Date datenew1=null;
                    try {
                        date=dateFormatin.parse(datein);
                        datenew=dateFormat.parse(newDate);
                        datenew1=dateFormat.parse(newDate1);
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    Log.v("date",dateFormat.format(datenew)+"/"+dateFormat.format(date));

                    if(date.after(datenew)&&date.before(datenew1)) {
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

                date.setText("Profit for  " +dateFormat.format(new Date()));
                Log.v("size",String.valueOf( products.size()));
                mAdapter = new ProductListAdapter(getActivity(), products);
                transactionsRecycler.setAdapter(mAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
        profitTotal.setText("Total profit   Ksh"+String.valueOf(total));

    }

}
