package com.example.owner.lacochina;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class ListRestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase db;
    DatabaseReference ref;

    ListView list;

    public ListRestFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListRestFragment newInstance(String param1, String param2) {
        ListRestFragment fragment = new ListRestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v=inflater.inflate(R.layout.fragment_list_rest, container, false);
        db= FirebaseDatabase.getInstance();
        ref=db.getReference("Restaurants");

        list=(ListView) v.findViewById(R.id.listView);

        ValueEventListener listener = new ValueEventListener() {

            ArrayList<Restaurant> arrayRestaurant;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayRestaurant = new ArrayList<Restaurant>();
                Restaurant restaurant= new Restaurant();
                for(DataSnapshot userSnap: dataSnapshot.getChildren()){
                    restaurant = userSnap.getValue(Restaurant.class);
                    arrayRestaurant.add(restaurant);
                }

                Restaurant temp;
                Log.d("SIZE", Integer.toString(arrayRestaurant.size()));

                for(int i=0; i < arrayRestaurant.size(); i++) {

                    for (int j = 1; j < (arrayRestaurant.size() - i); j++) {
                        Log.d("ENTRE","ENTRE");
                        Log.d("J-1", Double.toString(arrayRestaurant.get(j - 1).getRestaurantReputation()));
                        Log.d("J", Double.toString(arrayRestaurant.get(j).getRestaurantReputation()));
                        if (arrayRestaurant.get(j - 1).getRestaurantReputation() < arrayRestaurant.get(j).getRestaurantReputation()) {

                            //swap the elements!
                            temp = arrayRestaurant.get(j - 1);
                            arrayRestaurant.set(j - 1, arrayRestaurant.get(j));
                            arrayRestaurant.set(j, temp);
                        }
                    }
                }




                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RestaurantVisualization frag= RestaurantVisualization.newInstance("","");
                        frag.setRestaurant(arrayRestaurant.get(position));
                        FragmentManager mf= getFragmentManager();
                        FragmentTransaction ft= mf.beginTransaction();
                        ft.replace(R.id.content_menu__principal,frag,"RestaurantVisualization");
                        ft.commit();
                    }
                });




                MyAdapter adapter = new MyAdapter(arrayRestaurant,getActivity());
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(listener);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
