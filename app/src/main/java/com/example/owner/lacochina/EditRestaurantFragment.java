package com.example.owner.lacochina;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.games.event.Event;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class EditRestaurantFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
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
            longitude,
            latitude;

    private Restaurant restaurant;

    private FirebaseDatabase database;
    private DatabaseReference ref;

    private Button boton, boton2;

    private final static int LOCATION_REQUEST = 0;
    private GoogleApiClient mGoogleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private GoogleMap mMap;

    private double lat;
    private double log;


    public EditRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_restaurant, container, false);

        nameEdit = (EditText) v.findViewById(R.id.resNameEdit);
        addressEdit = (EditText) v.findViewById(R.id.resAddrEdit);
        typeEdit = (EditText) v.findViewById(R.id.resTypeEdit);
        telephoneEdit = (EditText) v.findViewById(R.id.resTelephoneEdit);
        reputationEdit = (EditText) v.findViewById(R.id.resRepEdit);
        //latitude = (EditText) v.findViewById(R.id.latitude);
        //longitude = (EditText)v.findViewById(R.id.longitude) ;

        nameEdit.setText(restaurant.getRestaurantName());
        addressEdit.setText(restaurant.getRestaurantAddress());
        typeEdit.setText(restaurant.getRestaurantType());
        telephoneEdit.setText(restaurant.getRestaurantTelephone());
        reputationEdit.setText(String.valueOf(restaurant.getRestaurantReputation()));
        //latitude.setText(String.valueOf(restaurant.getLatitude()));
        //longitude.setText(String.valueOf(restaurant.getLongitude()));


        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Restaurants");

        boton = (Button) v.findViewById(R.id.button4);
        boton2 = (Button) v.findViewById(R.id.button5);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant res = new Restaurant(nameEdit.getText().toString(), addressEdit.getText().toString(), typeEdit.getText().toString(), telephoneEdit.getText().toString(),
                        Double.parseDouble(reputationEdit.getText().toString()), log, lat);
                ref.child(restaurant.getRestaurantName()).setValue(res);
                Toast.makeText(getActivity(), "Restaurant Editado", Toast.LENGTH_LONG).show();

            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListRestFragment fragment = ListRestFragment.newInstance("", "");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.content_menu__principal, fragment, "Visualizacion");
                t.commit();

            }
        });

        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000 * 5);
        locationRequest.setFastestInterval(1000 * 3);

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
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.mapEdit);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // mylocation layer
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng newLatLon) {
                mMap.addMarker(new MarkerOptions().position(newLatLon).title("Fiesta"));
                lat = newLatLon.latitude;
                log = newLatLon.longitude;
            }
        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        Log.d("LOCATION CHANGED", String.valueOf(lastLocation.getLatitude()) + "," +
                String.valueOf(lastLocation.getLongitude()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == LOCATION_REQUEST) {

            if (permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(lastLocation != null){

            Log.d("LOCATION", String.valueOf(lastLocation.getLatitude()) + "," +
                    String.valueOf(lastLocation.getLongitude()));
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }






}
