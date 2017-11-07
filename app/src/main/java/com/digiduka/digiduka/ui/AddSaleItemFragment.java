package com.digiduka.digiduka.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.SaleProductsAdapter;
import com.digiduka.digiduka.adapters.StockItemsAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.models.Transaction;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSaleItemFragment extends Fragment implements View.OnClickListener{
    private View thisView;
    private Button cancelSaleButton;
    private Button pickProducts;
    private Button doneButton;
    public static Transaction transaction;
    private RecyclerView saleItems;
    private SaleProductsAdapter mAdapter;
    private static ArrayList<Category> categories = new ArrayList<>();
    private static LinearLayout totalsSection;
    private static TextView priceTotal;



    public AddSaleItemFragment() {
        // Required empty public constructor
    }
    public static AddSaleItemFragment newInstance(){
        return new AddSaleItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories= Parcels.unwrap(getArguments().getParcelable("categories"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_add_sale_item, container, false);
        cancelSaleButton = thisView.findViewById(R.id.cancelSaleButton);
        pickProducts = thisView.findViewById(R.id.pickProducts);
        doneButton = thisView.findViewById(R.id.doneButton);
        saleItems = thisView.findViewById(R.id.saleItems);
        totalsSection = thisView.findViewById(R.id.totalsSection);
        priceTotal = thisView.findViewById(R.id.priceTotal);

        totalsSection.setVisibility(View.GONE);

        mAdapter = new SaleProductsAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        saleItems.setLayoutManager(layoutManager);
        saleItems.setHasFixedSize(false);
        saleItems.setAdapter(mAdapter);
        pickProducts.setOnClickListener(this);
        doneButton.setOnClickListener(this);
        cancelSaleButton.setOnClickListener(this);

        return thisView;
    }

    @Override
    public void onClick(View view) {
        if (view==pickProducts){
            //put action t pick products
            Bundle bundle = new Bundle();
            FragmentManager fm = getFragmentManager();
            DisplayCategoriesFragment fragment = DisplayCategoriesFragment.newInstance(categories, mAdapter, Constants.SALES_SIDE);
            AddCategoryFragment addStockItemFragment = new AddCategoryFragment();
            fragment.show(fm, "dialog");

        }else if (view==doneButton){
            //put actions to done button
            if (transaction==null){
                Toast.makeText(getContext(), "You haven't added any new items", Toast.LENGTH_LONG).show();
            }else{
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference(currentUser.getUid())
                        .child(Constants.TRANSACTIONS_DB_KEY);

                DatabaseReference pushRef = reference.push();
                String pushId = pushRef.getKey();
                transaction.setTransactionId(pushId);
                pushRef.setValue(transaction);



                DatabaseReference productsRef = FirebaseDatabase.getInstance()
                        .getReference(currentUser.getUid())
                        .child(Constants.PRODUCTS_DB_KEY);
                productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Product thisProduct = snapshot.getValue(Product.class);
                            if (transaction.containsProduct(thisProduct)){
                                Product transactionProduct = transaction.getSimilarProduct(thisProduct);
                                int amount = thisProduct.getAmount()-transactionProduct.getAmount();
                                thisProduct.setAmount(amount);
                                DatabaseReference thisProductRef = snapshot.getRef();
                                thisProductRef.setValue(thisProduct);
                            }
                        }
                        transaction = null;
                        Toast.makeText(getContext(), "You have recorded sale of items", Toast.LENGTH_LONG).show();
                        thisView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }else if (view==cancelSaleButton){
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            transaction = null;
            getFragmentManager().popBackStack();
            thisView.setVisibility(View.GONE);
        }
    }
    public static void refreshUi(){
        if (transaction!=null && transaction.getProducts().size()>0){
            totalsSection.setVisibility(View.VISIBLE);
            priceTotal.setText("KSH. "+Integer.toString(transaction.getTotalCost()));
        }else{
            totalsSection.setVisibility(View.GONE);
        }
    }
}
