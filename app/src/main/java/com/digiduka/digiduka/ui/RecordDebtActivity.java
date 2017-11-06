package com.digiduka.digiduka.ui;

import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.digiduka.digiduka.R;
import com.digiduka.digiduka.adapters.DebtsViewHolder;
import com.digiduka.digiduka.models.Debt;
import com.digiduka.digiduka.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordDebtActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView debtsRecyclerView;
    private Button debtSubmitButton;
    private AutoCompleteTextView debtValueEdit;
    private AutoCompleteTextView debtorNameEdit;
    private DatabaseReference mDebtsRef;
    private ArrayList<Debt> debts = new ArrayList<>();
    private FirebaseRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_debt);
        debtorNameEdit = findViewById(R.id.debtorNameEdit);
        debtValueEdit = findViewById(R.id.debtValueEdit);
        debtSubmitButton = findViewById(R.id.debtSubmitButton);
        debtsRecyclerView = findViewById(R.id.debtsRecyclerView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDebtsRef = FirebaseDatabase.getInstance().getReference(user.getUid()).child(Constants.DEBTS_DB_KEY);
        mDebtsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Debt debt = snapshot.getValue(Debt.class);
                    debts.add(debt);
                }
                setUpFirebaseDebtViewHolder();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        debtSubmitButton.setOnClickListener(this);




    }
    public void setUpFirebaseDebtViewHolder(){
        mAdapter = new FirebaseRecyclerAdapter<Debt, DebtsViewHolder>(Debt.class, R.layout.debts_list_item, DebtsViewHolder.class, mDebtsRef) {
            @Override
            protected void populateViewHolder(DebtsViewHolder viewHolder, Debt model, int position) {
                viewHolder.bindDebt(model);
            }
        };
        debtsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        debtsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == debtSubmitButton){
            String debtorName = debtorNameEdit.getText().toString().trim();
            int debtValue = 0;
            try{
                debtValue = Integer.parseInt(debtValueEdit.getText().toString().trim());
                if (isValidDebtDetails(debtorName, debtValue)){
                    Debt debt = new Debt(debtorName, debtValue);

                    DatabaseReference reference = FirebaseDatabase.getInstance()
                            .getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(Constants.DEBTS_DB_KEY);
                    DatabaseReference pushRef = reference.push();
                    String pushId = pushRef.getKey();
                    debt.setPushId(pushId);
                    pushRef.setValue(debt).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Debt record saved.",Toast.LENGTH_LONG).show();
                                mAdapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(getApplicationContext(),"Debt was not saved successfully.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }catch (NumberFormatException e){
                debtValueEdit.setError("Cannot submit empty amount");
                debtValueEdit.setFocusable(true);
            }

        }
    }
    public boolean isValidDebtDetails(String debtorName, int debtValue){
        if(debtorName.length()<1){
            debtorNameEdit.setError("Cannot submit empty name");
            debtorNameEdit.setFocusable(true);
            return false;
        }else if(Integer.toString(debtValue).length()<1){
            debtValueEdit.setError("Cannot submit empty amount");
            debtValueEdit.setFocusable(true);
            return false;
        }
        return true;
    }
}
