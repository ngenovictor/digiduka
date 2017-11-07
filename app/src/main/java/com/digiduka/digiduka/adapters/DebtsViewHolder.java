package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.models.Debt;
import com.digiduka.digiduka.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by victor on 11/6/17.
 */

public class DebtsViewHolder extends RecyclerView.ViewHolder {
    private TextView debtorNameDisplay;
    private TextView debtorAmountDisplay;
    private TextView debtorDateDisplay;
    private Button clearDebtButton;
    private Context mContext;

    public DebtsViewHolder(View itemView) {
        super(itemView);
        debtorNameDisplay = itemView.findViewById(R.id.debtorNameDisplay);
        debtorAmountDisplay = itemView.findViewById(R.id.debtorAmountDisplay);
        debtorDateDisplay = itemView.findViewById(R.id.debtorDateDisplay);
        clearDebtButton = itemView.findViewById(R.id.clearDebtButton);
        mContext = itemView.getContext();
    }
    public void bindDebt(final Debt debt){
        debtorNameDisplay.setText(debt.getDebtorName());
        debtorAmountDisplay.setText(Integer.toString(debt.getDebtValue()));
        clearDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.DEBTS_DB_KEY).child(debt.getPushId());
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mContext,"Debt of "+debt.getDebtorName()+" cleared.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(mContext,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
