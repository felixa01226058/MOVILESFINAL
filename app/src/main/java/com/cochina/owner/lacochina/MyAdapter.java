package com.cochina.owner.lacochina;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Asus gl552 on 23/10/2016.
 */

public class MyAdapter extends BaseAdapter {

    ArrayList<Restaurant> arrayRest;
    Activity activity;

    public MyAdapter(ArrayList<Restaurant> arrayRest, Activity activity) {
        this.arrayRest = arrayRest;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrayRest.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayRest.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView== null){
            convertView=activity.getLayoutInflater().inflate(R.layout.row,null);
        }

        TextView restName=(TextView) convertView.findViewById(R.id.nameTextField);
        TextView restType=(TextView) convertView.findViewById(R.id.typeText);
        TextView restReputation= (TextView) convertView.findViewById(R.id.reputationText);

        restName.setText(arrayRest.get(position).getRestaurantName());
        restType.setText(arrayRest.get(position).getRestaurantType());
        restReputation.setText(String.valueOf(arrayRest.get(position).getRestaurantReputation()));

        return convertView;
    }

}
