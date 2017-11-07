package com.digiduka.digiduka.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.digiduka.digiduka.adapters.CategoryListAdapter;
import com.digiduka.digiduka.adapters.StockItemsAdapter;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.models.Stock;
import com.digiduka.digiduka.databaseHandlers.TableControllerCategory;
import com.digiduka.digiduka.adapters.FirebaseCategoriesViewHolder;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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


public class AddStockItemFragment extends Fragment implements View.OnClickListener{

    private RecyclerView categoriesRecyclerView;
    private Button addCategoryButton;
    private CategoryListAdapter mAdapter;
    private Button cancelAddStockButton;
    private static FloatingActionButton button;
    private Button doneButton;
    public static Stock stock;
    private StockItemsAdapter mAdapter2;
    private static LinearLayout totalsSection;
    private static TextView priceTotal;
    private FirebaseUser currentUser;
    private View thisView;
    private Context mContext;

    /**
     * initializes the SQL Database TableController
     * **/
    TableControllerCategory tableControllerCategory = new TableControllerCategory(getContext());
    private static ArrayList<Category> categories = new ArrayList<>();




    public AddStockItemFragment() {
        // Required empty public constructor
    }
    public static AddStockItemFragment newInstance(FloatingActionButton comingButton){
        button = comingButton;//remove this
        return new AddStockItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories= Parcels.unwrap(getArguments().getParcelable("categories"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_stock_item, container, false);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(this);


        mAdapter2 = new StockItemsAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoriesRecyclerView.setLayoutManager(layoutManager);
        categoriesRecyclerView.setHasFixedSize(false);
        categoriesRecyclerView.setAdapter(mAdapter2);

        cancelAddStockButton = view.findViewById(R.id.cancelAddStockButton);
        cancelAddStockButton.setOnClickListener(this);
        doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(this);

        totalsSection = view.findViewById(R.id.totalsSection);
        priceTotal = view.findViewById(R.id.priceTotal);

        totalsSection.setVisibility(View.GONE);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mContext = view.getContext();


        thisView = view;
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == addCategoryButton) {
            //pick products here by opening a list of all categories
            Bundle bundle = new Bundle();
            FragmentManager fm = getFragmentManager();
            DisplayCategoriesFragment fragment = DisplayCategoriesFragment.newInstance(categories, mAdapter2, Constants.STOCK_SIDE);
            AddCategoryFragment addStockItemFragment = new AddCategoryFragment();
            fragment.show(fm, "dialog");
        }else if(view == cancelAddStockButton){
            //how do we close the fragment??
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            getFragmentManager().popBackStack();
            stock = null;
            thisView.setVisibility(View.GONE);

        }else if(view ==doneButton){
            if (stock==null){
                Toast.makeText(getContext(), "You haven't added any new items", Toast.LENGTH_LONG).show();
            }else{
                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference(currentUser.getUid())
                        .child(Constants.STOCKS_DB_KEY);



                DatabaseReference pushRef = reference.push();
                String pushId = pushRef.getKey();
                stock.setStockId(pushId);
                pushRef.setValue(stock);

                final ArrayList<String> productIds = new ArrayList<>();
                for (Product product: stock.getProducts()){
                    productIds.add(product.getPushId());
                }

                DatabaseReference productsRef = FirebaseDatabase.getInstance()
                        .getReference(currentUser.getUid())
                        .child(Constants.PRODUCTS_DB_KEY);
                productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Product thisProduct = snapshot.getValue(Product.class);
                            if (stock.containsProduct(thisProduct)){
                                Product transactionProduct = stock.getSimilarProduct(thisProduct);
                                int amount = thisProduct.getAmount()+transactionProduct.getAmount();
                                thisProduct.setAmount(amount);
                                DatabaseReference thisProductRef = snapshot.getRef();
                                thisProductRef.setValue(thisProduct);
                            }
                        }
                        stock = null;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getContext(), "You have added new items", Toast.LENGTH_LONG).show();
                thisView.setVisibility(View.GONE);
            }
        }
    }

    public static void refreshUi(){
        if (stock!=null && stock.getProducts().size()>0){
            totalsSection.setVisibility(View.VISIBLE);
            priceTotal.setText("KSH. "+Integer.toString(stock.getTotalCost()));
        }else{
            totalsSection.setVisibility(View.GONE);
        }
    }
}
