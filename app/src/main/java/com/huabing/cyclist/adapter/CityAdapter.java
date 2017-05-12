package com.huabing.cyclist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huabing.cyclist.R;

import java.util.List;

/**
 * Created by 30781 on 2017/5/6.
 */

public class CityAdapter extends ArrayAdapter<String> {
    private int resourceId;
    public CityAdapter(Context context, int textViewResourceId, List<String> objects)
    {
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String city=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tvCity=(TextView)view.findViewById(R.id.tv_city);
        tvCity.setText(city);
        return view;
    }
}
