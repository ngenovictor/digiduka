package com.digiduka.digiduka.ui;


import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.telecom.Call;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.digiduka.digiduka.R;


import org.parceler.Parcels;

import java.util.Calendar;


public class GenerateReportsActivity extends AppCompatActivity {
    private DatabaseReference refrence;
    private FirebaseAuth mAuth;
    private ArrayList<Product> products=new ArrayList<>();
    private ArrayList<String> productname=new ArrayList<>();
    private RecyclerView transactionsRecycler;
    private ProductListAdapter mAdapter;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat dateFormatin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private TextView day;

    int mDay,mMonth,mYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_reports);
        transactionsRecycler=findViewById(R.id.transactionsRecycler);
//        profitTotal=findViewById(R.id.daysTransactionsTotal);
//        date=findViewById(R.id.profitDate);
        mAuth = FirebaseAuth.getInstance();
        getTransactions();
        day=findViewById(R.id.daysprofit);
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Calendar calendar=Calendar.getInstance();
                 calendar.setFirstDayOfWeek(Calendar.MONDAY);

                mDay=calendar.get(Calendar.DAY_OF_MONTH);
                mMonth=calendar.get(Calendar.MONTH);
                mYear=calendar.get(Calendar.YEAR);


                DatePickerDialog datePicker=new DatePickerDialog(GenerateReportsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                            day.setText(i1);
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
                },mDay,mMonth,mYear);
                datePicker.show();

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProfitReportFragment fragment = new ProfitReportFragment();
                ft.add(R.id.todayProfitZZ, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });





    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
//        profitTotal.setText("Total profit   Ksh"+String.valueOf(total));

    }

}
