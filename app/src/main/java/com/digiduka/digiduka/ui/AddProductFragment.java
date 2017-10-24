package com.digiduka.digiduka.ui;


import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.digiduka.digiduka.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.parceler.Parcels;

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

        Bundle bundle = getArguments();

        mCategory = Parcels.unwrap(bundle.getParcelable("category"));
        newProductButton.setOnClickListener(this);

        mContext = mView.getContext();

        return mView;
    }

    @Override
    public void onClick(View view) {
        if(view == newProductButton) {
            String name = nameOfProductEditText.getText().toString().trim();
            String description = descriptionOfProductEditText.getText().toString().trim();
            String size = productSizeEdit.getText().toString().trim();
            Integer buyingPrice = Integer.parseInt(productBuyingPriceEdit.getText().toString().trim());
            Integer sellingPrice = Integer.parseInt(productSellingPriceEdit.getText().toString().trim());

            Product product = new Product(name, description, mCategory.getCategoryId(), size, buyingPrice, sellingPrice);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(user.getUid()).child(Constants.CATEGORY_DB_KEY).child(mCategory.getCategoryId()).child(Constants.PRODUCTS_DB_KEY);

            DatabaseReference puhRef = reference.push();
            String pushId = puhRef.getKey();
            product.setPushId(pushId);
            puhRef.setValue(product);


            dismiss();
        }
    }
}
