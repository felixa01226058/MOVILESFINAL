package com.example.owner.lacochina;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddRestaurantFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText restName,
                        restAddress,
                        restType,
                        resTelephone,
                        resReputation,
                        resDelivery,
                        longitude,
                        latitude;

    private Button addButton,regresarButton;

    private FirebaseDatabase db;
    private DatabaseReference ref;


    public AddRestaurantFragment() {
        // Required empty public constructor
    }

    public static AddRestaurantFragment newInstance(String param1, String param2) {
        AddRestaurantFragment fragment = new AddRestaurantFragment();
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
        View v= inflater.inflate(R.layout.fragment_add_restaurant, container, false);

        restName= (EditText) v.findViewById(R.id.resNameEditText);
        restAddress=(EditText)v.findViewById(R.id.resAddressEditText);
        restType=(EditText)v.findViewById(R.id.resTypeEditText);
        resTelephone=(EditText)v.findViewById(R.id.resTelephoneEditText);
        resReputation=(EditText)v.findViewById(R.id.resReputationEditText);
        latitude = (EditText) v.findViewById(R.id.latitude);
        longitude = (EditText)v.findViewById(R.id.longitude) ;

        db= FirebaseDatabase.getInstance();
        ref=db.getReference("Restaurants");


        addButton = (Button) v.findViewById(R.id.addFragmentButton);
        regresarButton = (Button) v.findViewById(R.id.button6);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Restaurant restaurant= new Restaurant(restName.getText().toString(),restAddress.getText().toString()
                        ,restType.getText().toString(),resTelephone.getText().toString()
                        ,resReputation.getText().toString(),
                        Double.parseDouble(longitude.getText().toString()),Double.parseDouble(latitude.getText().toString()));
                ref.child(restName.getText().toString()).setValue(restaurant);
                Toast.makeText(getActivity(),"Restaurant Añadido",Toast.LENGTH_LONG).show();
            }
        });
        regresarButton.setOnClickListener(new View.OnClickListener() {
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
