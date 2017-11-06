package com.digiduka.digiduka.ui;
import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.digiduka.digiduka.R;
import org.parceler.Parcels;
import java.util.Calendar;
public class GenerateReportsActivity extends AppCompatActivity {
    private TextView day;
    int mDay,mMonth,mYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_reports);
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
                        i1+=1;

                        day.setText(i2 + "/"
                                + i1 + "/" + i);

                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ProfitReportFragment fragment = new ProfitReportFragment();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("date", Parcels.wrap(i2+ "/" + i1 + "/" + i) );
                        fragment.setArguments(bundle);
                        ft.add(R.id.todayProfitZZ, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                },mYear,mMonth,mDay);
                datePicker.show();
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
    }

}