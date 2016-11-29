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



public class EditRestaurantFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText nameEdit,
                    addressEdit,
                    typeEdit,
                    telephoneEdit,
                    reputationEdit,
                    deliveryEdit;

    private Restaurant restaurant;

    private FirebaseDatabase database;
    private DatabaseReference ref;

    private Button boton,boton2;


    public EditRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditRestaurantFragment newInstance(String param1, String param2) {
        EditRestaurantFragment fragment = new EditRestaurantFragment();
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
        View v=inflater.inflate(R.layout.fragment_edit_restaurant, container, false);

        nameEdit=(EditText) v.findViewById(R.id.resNameEdit);
        addressEdit=(EditText)v.findViewById(R.id.resAddrEdit);
        typeEdit=(EditText)v.findViewById(R.id.resTypeEdit);
        telephoneEdit=(EditText)v.findViewById(R.id.resTelephoneEdit);
        reputationEdit=(EditText)v.findViewById(R.id.resRepEdit);
        deliveryEdit=(EditText)v.findViewById(R.id.resDeliveryEdit);

        nameEdit.setText(restaurant.getRestaurantName());
        addressEdit.setText(restaurant.getRestaurantAddress());
        typeEdit.setText(restaurant.getRestaurantType());
        telephoneEdit.setText(restaurant.getRestaurantTelephone());
        reputationEdit.setText(restaurant.getRestaurantReputation());
        deliveryEdit.setText(restaurant.getDelivery());

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Restaurants");

        boton = (Button) v.findViewById(R.id.button4);
        boton2 = (Button) v.findViewById(R.id.button5);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant res = new Restaurant(nameEdit.getText().toString(),addressEdit.getText().toString(),typeEdit.getText().toString(),telephoneEdit.getText().toString(),
                        reputationEdit.getText().toString(),deliveryEdit.getText().toString());
                ref.child(restaurant.getRestaurantName()).setValue(res);
                Toast.makeText(getActivity(),"Restaurant Editado",Toast.LENGTH_LONG).show();

            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
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

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
