package com.digiduka.digiduka.ui;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Category;
import com.digiduka.digiduka.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

/**

 */
public class AddProductFragment extends DialogFragment implements View.OnClickListener {
    private Button newProductButton;
    private EditText nameOfProductEditText;
    private EditText descriptionOfProductEditText;
    private TextInputLayout priceVariationsEditWrapper1;
    private Button addVariationsButton;
    private int variations = 1;
    private Context mContext;
    private AutoCompleteTextView variationSize1;
    private AutoCompleteTextView variationPrice1;
    private Category mCategory;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        newProductButton = view.findViewById(R.id.newProductButton);
        nameOfProductEditText = view.findViewById(R.id.nameOfProductEditText);
        descriptionOfProductEditText = view.findViewById(R.id.descriptionOfProductEditText);
        priceVariationsEditWrapper1 = view.findViewById(R.id.priceVariationsEditWrapper1);
        addVariationsButton = view.findViewById(R.id.addVariationsButton);

        variationSize1 = view.findViewById(R.id.variationSize1);
        variationPrice1 = view.findViewById(R.id.variationPrice1);

        Bundle bundle = getArguments();

        mCategory = Parcels.unwrap(bundle.getParcelable("category"));

        Log.d("checkssssssss", mCategory.getCategoryTitle());

        addVariationsButton.setOnClickListener(this);
        newProductButton.setOnClickListener(this);

        mContext = view.getContext();

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == newProductButton){
            String name = nameOfProductEditText.getText().toString().trim();
            String description = descriptionOfProductEditText.getText().toString().trim();
            Product product = new Product(name, description,mCategory.getCategoryId());
            String size = variationSize1.getText().toString().trim();
            Integer price = Integer.parseInt(variationPrice1.getText().toString().trim());
            product.addVariations(size, price);

            if (variations>1){
                for(int i=1; i<=variations; i++){
                    String variationSizeId = "variationSize"+i;
                    String variationPriceId = "variationPrice"+i;
                    AutoCompleteTextView sizeEditText = view.findViewById(getResources().getIdentifier(variationSizeId, "id", getActivity().getPackageName()));
                    String sizeExtra = sizeEditText.getText().toString().trim();
                    Integer priceExtra = Integer.parseInt(((AutoCompleteTextView) view.findViewById(view.getResources().getIdentifier(variationPriceId, "id", getActivity().getPackageName()))).getText().toString().trim());
                    product.addVariations(sizeExtra, priceExtra);
                }
            }

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
            reference.setValue(product);
            dismiss();
        }else if(view == addVariationsButton){
            TextInputLayout textInputLayout = new TextInputLayout(mContext);

        }
    }
}
