package com.example.owner.lacochina;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class RestaurantVisualization extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView name,
                    address,
                    type,
                    telephone,
                    reputation,
                    delivery;

    private Button editButton,editButton2;

    private Restaurant restaurant;

    private FirebaseDatabase database;
    private DatabaseReference ref;


    public RestaurantVisualization() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantVisualization.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantVisualization newInstance(String param1, String param2) {
        RestaurantVisualization fragment = new RestaurantVisualization();
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

        View v=inflater.inflate(R.layout.fragment_restaurant_visualization, container, false);
        name=(TextView) v.findViewById(R.id.nameRestTextField);
        address=(TextView) v.findViewById(R.id.addressTextField);
        type=(TextView)v.findViewById(R.id.typeTextField);
        telephone= (TextView)v.findViewById(R.id.telTextField);
        reputation=(TextView)v.findViewById(R.id.repTextField);
        delivery=(TextView) v.findViewById(R.id.delTextField);

        name.setText(restaurant.getRestaurantName());
        address.setText(restaurant.getRestaurantAddress());
        type.setText(restaurant.getRestaurantType());
        telephone.setText(restaurant.getRestaurantTelephone());
        reputation.setText(restaurant.getRestaurantReputation());

        editButton = (Button) v.findViewById(R.id.button7);
        editButton2 = (Button) v.findViewById(R.id.button8);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditRestaurantFragment editar = EditRestaurantFragment.newInstance("","");
                editar.setRestaurant(restaurant);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction t = manager.beginTransaction();
                t.replace(R.id.content_menu__principal, editar, "Editar");
                t.commit();
            }
        });

        editButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListRestFragment fragment = ListRestFragment.newInstance("","");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.content_menu__principal, fragment, "Visualizacion");
                t.commit();
            }
        });


        return v;
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant=restaurant;
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
