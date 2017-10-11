package com.digiduka.digiduka.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digiduka.digiduka.R;

import java.util.Random;

/**
 * Created by victor on 10/11/17.
 */

public class CategoriesGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private TextView gridText;
    private ConstraintLayout gridViewHolder;
    public CategoriesGridViewAdapter(Context context){
        mContext = context;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View thisView = LayoutInflater.from(mContext).inflate(R.layout.category_grid_item, viewGroup, false);
        gridText = thisView.findViewById(R.id.gridText);
        gridViewHolder = thisView.findViewById(R.id.gridViewHolder);
        gridText.setText("this");
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        gridViewHolder.setBackgroundColor(color);
//        view.setBackgroundColor(color);
//        TextView textView;
//        textView = new TextView(mContext);
//        textView.setText(Integer.toString(i));
        return thisView;
    }
}
