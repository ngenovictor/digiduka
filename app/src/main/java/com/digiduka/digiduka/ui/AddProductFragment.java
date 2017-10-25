package com.digiduka.digiduka.ui;


import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.parceler.Parcels;
import java.util.ArrayList;

/**

 */
public class AddProductFragment extends DialogFragment implements View.OnClickListener {
    private Button newProductButton;
    private AutoCompleteTextView nameOfProductEditText;
    private AutoCompleteTextView descriptionOfProductEditText;
    private AutoCompleteTextView productSizeEdit;
    private AutoCompleteTextView productBuyingPriceEdit;
    private AutoCompleteTextView productSellingPriceEdit;
    private Context mContext;
    private Category mCategory;
    private View mView;
    private TextView newProductPageTitle;
    private Button closeNewProductButton;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_product, container, false);
        newProductButton = mView.findViewById(R.id.newProductButton);
        nameOfProductEditText = mView.findViewById(R.id.nameOfProductEditText);
        descriptionOfProductEditText = mView.findViewById(R.id.descriptionOfProductEditText);
        productSizeEdit = mView.findViewById(R.id.productSizeEdit);
        productBuyingPriceEdit = mView.findViewById(R.id.productBuyingPriceEdit);
        productSellingPriceEdit = mView.findViewById(R.id.productSellingPriceEdit);
        closeNewProductButton = mView.findViewById(R.id.closeNewProductButton);
        closeNewProductButton.setOnClickListener(this);


        Bundle bundle = getArguments();

        mCategory = Parcels.unwrap(bundle.getParcelable("category"));
        newProductButton.setOnClickListener(this);

        mContext = mView.getContext();

        newProductPageTitle = mView.findViewById(R.id.newProductPageTitle);

        newProductPageTitle.setText("Add new product under category: "+mCategory.getCategoryTitle());

        return mView;
    }

    @Override
    public void onClick(View view) {
        if(view == newProductButton) {
            String name = nameOfProductEditText.getText().toString().trim();
            String description = descriptionOfProductEditText.getText().toString().trim();
            String size = productSizeEdit.getText().toString().trim();
            boolean isValid = true;
            if (name.length()<1){
                nameOfProductEditText.setError("cannot set empty product name");
                nameOfProductEditText.setFocusable(true);
                isValid = false;
            }if (size.length()<1){
                productSizeEdit.setError("cannot set empty product size");
                productSizeEdit.setFocusable(true);
                isValid = false;
            }if (productBuyingPriceEdit.getText().toString().trim().length()<1){
                productBuyingPriceEdit.setError("cannot set empty buying price");
                productBuyingPriceEdit.setFocusable(true);
                isValid = false;
            }if (productSellingPriceEdit.getText().toString().trim().length()<1){
                productSellingPriceEdit.setError("cannot set empty selling price");
                productSellingPriceEdit.setFocusable(true);
                isValid = false;
            }

            if (isValid){
                Integer buyingPrice = Integer.parseInt(productBuyingPriceEdit.getText().toString().trim());
                Integer sellingPrice = Integer.parseInt(productSellingPriceEdit.getText().toString().trim());
                Product product = new Product(name, description, mCategory.getCategoryId(), size, buyingPrice, sellingPrice);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(user.getUid()).child(Constants.PRODUCTS_DB_KEY);

                DatabaseReference puhRef = reference.push();
                String pushId = puhRef.getKey();
                product.setPushId(pushId);
                puhRef.setValue(product);


                dismiss();
            }

        }else if(view==closeNewProductButton){
            dismiss();
        }
    }
}
