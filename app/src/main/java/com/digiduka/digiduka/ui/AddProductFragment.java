package com.digiduka.digiduka.ui;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    private EditText nameOfProductEditText;
    private EditText descriptionOfProductEditText;
    private TextInputLayout sizeVariationsEditWrapper1;
    private TextInputLayout priceVariationsEditWrapper1;
    private Button addVariationsButton;
    private int variations = 1;
    private Context mContext;
    private AutoCompleteTextView variationSize1;
    private AutoCompleteTextView variationPrice1;
    private Category mCategory;
    private View mView;
    private LinearLayout layout;
    private ArrayList<Integer> sizeViewIds = new ArrayList<>();
    private ArrayList<Integer> priceViewIds = new ArrayList<>();

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_product, container, false);
        newProductButton = mView.findViewById(R.id.newProductButton);
        nameOfProductEditText = mView.findViewById(R.id.nameOfProductEditText);
        descriptionOfProductEditText = mView.findViewById(R.id.descriptionOfProductEditText);
        priceVariationsEditWrapper1 = mView.findViewById(R.id.priceVariationsEditWrapper1);
        sizeVariationsEditWrapper1 = mView.findViewById(R.id.sizeVariationsEditWrapper1);
        addVariationsButton = mView.findViewById(R.id.addVariationsButton1);

        variationSize1 = mView.findViewById(R.id.variationSize1);
        variationSize1.setTag("variationSize1");
        variationPrice1 = mView.findViewById(R.id.variationPrice1);
        variationPrice1.setTag("variationPrice1");
        sizeViewIds.add(variationSize1.getId());
        priceViewIds.add(variationPrice1.getId());

        Bundle bundle = getArguments();

        mCategory = Parcels.unwrap(bundle.getParcelable("category"));

        Log.d("checkssssssss", mCategory.getCategoryTitle());

        addVariationsButton.setOnClickListener(this);
        newProductButton.setOnClickListener(this);

        mContext = mView.getContext();

        return mView;
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

            Log.d("hgvbjh", "variations"+Integer.toString(variations));

            if (variations>1){
                for(int i=2; i<=variations; i++){
                    String variationSizeId = "variationSize"+i;
                    Log.d("ds", variationSizeId);
                    String variationPriceId = "variationPrice"+i;
                    AutoCompleteTextView sizeEditText = mView.findViewWithTag(variationSizeId);
                    String sizeExtra = sizeEditText.getText().toString().trim();
                    AutoCompleteTextView priceEditText = mView.findViewWithTag(variationPriceId);
                    Integer priceExtra = Integer.parseInt(priceEditText.getText().toString().trim());
                    product.addVariations(sizeExtra, priceExtra);
                }
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(user.getUid()).child(Constants.CATEGORY_DB_KEY).child(mCategory.getCategoryId()).child(Constants.PRODUCTS_DB_KEY);

            DatabaseReference puhRef = reference.push();
            String pushId = puhRef.getKey();
            product.setPushId(pushId);
            puhRef.setValue(product);
            dismiss();
        }else if(view == addVariationsButton){
            variations+=1;
            String variationSizeId = "variationSize"+variations;
            String variationPriceId = "variationPrice"+variations;
            LinearLayout parentLayout = mView.findViewById(R.id.newVariationsHolder);
            LinearLayout childLayout = mView.findViewById(R.id.newVariationsRow1);

            //the new layout to hold the new items
            LinearLayout newLayout = new LinearLayout(mContext);
            newLayout.setOrientation(childLayout.getOrientation());
            newLayout.setLayoutParams(childLayout.getLayoutParams());
            parentLayout.addView(newLayout);

            //addbutton to accompany the forms
            Button addButton = new Button(mContext);
            addButton.setBackground(addVariationsButton.getBackground());
            addButton.setHeight(addVariationsButton.getHeight());
            addButton.setWidth(addVariationsButton.getWidth());
            newLayout.addView(addButton);

            // input size
            TextInputLayout sizeTextInputLayout = new TextInputLayout(mContext);
            sizeTextInputLayout.setLayoutParams(sizeVariationsEditWrapper1.getLayoutParams());
            newLayout.addView(sizeTextInputLayout);

            AutoCompleteTextView sizeEdit = new AutoCompleteTextView(mContext);
            sizeEdit.setTag(variationSizeId);
            sizeEdit.setHint(variationSize1.getHint());
            sizeEdit.setGravity(View.TEXT_ALIGNMENT_CENTER);
            sizeTextInputLayout.addView(sizeEdit);

            //input price
            TextInputLayout priceTextInputLayout = new TextInputLayout(mContext);
            priceTextInputLayout.setLayoutParams(sizeVariationsEditWrapper1.getLayoutParams());
            newLayout.addView(priceTextInputLayout);

            AutoCompleteTextView priceEdit = new AutoCompleteTextView(mContext);
            priceEdit.setTag(variationPriceId);
            priceEdit.setHint(variationPrice1.getHint());
            priceEdit.setGravity(View.TEXT_ALIGNMENT_CENTER);
            priceTextInputLayout.addView(priceEdit);


//            addProductFragmentParent.addView(textInputLayout,0);
//
//            set.clone(addProductFragmentParent);
//
//            addProductFragmentParent.addView(textInputLayout);





        }
    }
}
