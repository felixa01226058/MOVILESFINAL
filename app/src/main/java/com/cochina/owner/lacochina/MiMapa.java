package com.cochina.owner.lacochina;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.lang.*;

public class MiMapa extends FragmentActivity implements LocationListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,  ActivityCompat.OnRequestPermissionsResultCallback {

    private MapFragment mapFragment;
    private GoogleMap mMap;
    private Context context;
    private LocationManager locationManager;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    LocationRequest mLocationRequest;
    ArrayList<Restaurant> arrayRestaurant;
    ArrayList<Restaurant> markerRestaurant;
    FirebaseDatabase db;
    DatabaseReference ref;

    public ArrayList<Restaurant> getRestaurantes(ArrayList<Restaurant> restaurantes){

        Log.d("TOY JUERA CABRON ",Integer.toString(restaurantes.size()));
        Log.d("TOY JUERA CABRON ",restaurantes.get(0).getRestaurantName());
        return restaurantes;
    }
    public double getDistance(double markerLat,double markerLon,double myLat,double myLon){
        double distancia = Math.sqrt(Math.pow((markerLat-myLat),2)+Math.pow(markerLon-myLon,2));
        return distancia;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_mapa);
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment =((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        context = this;
        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                mCurrLocationMarker.setPosition(point);
            }
        });
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Log.d("TOY JUERA CABRON ",markerRestaurant.get(0).getRestaurantName());

        db= FirebaseDatabase.getInstance();
        ref=db.getReference("Restaurants");

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
                Log.d("ENTRE A METODO", Integer.toString(arrayRestaurant.size()));
                //markerRestaurant= getRestaurantes(arrayRestaurant);

                for(int i = 0;i<arrayRestaurant.size();i++){
                    Log.d("RESTAURANTES ",arrayRestaurant.get(i).getRestaurantName()+" LATITUDE: "
                            +arrayRestaurant.get(i).getLatitude()+" LONGITUDE: "+arrayRestaurant.get(i).getLongitude());

                    Double latitude = Double.valueOf(arrayRestaurant.get(i).latitude);
                    Double longitude = Double.valueOf(arrayRestaurant.get(i).longitude);



                    //Log.d("esta es la distancia",String.valueOf(getDistance(latitude,longitude,mLastLocation.getLatitude(),mLastLocation.getLongitude())));
                    /*float [] result=new float[1];
                    Location.distanceBetween(mLastLocation.getLatitude(),mLastLocation.getLongitude(),latitude,longitude,result);
                    Log.d("Hola",Float.toString(result[0]));
*/


                    LatLng newRestaurant = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(newRestaurant).title(
                            arrayRestaurant.get(i).getRestaurantName()).snippet(
                                    arrayRestaurant.get(i).getRestaurantType()+
                                            ", Rating: "+arrayRestaurant.get(i).getRestaurantReputation()+
                                            ", Tel: "+arrayRestaurant.get(i).getRestaurantTelephone()
                    ));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(listener);
        //Log.d("TOY JUERA CABRON ",Integer.toString(markerRestaurant.size()));

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


       /* if ( Build.VERSION.SDK_INT >= 23){
            if(
                    ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION )
                            ==
                            PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && canAccessLocation()) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, (int)0, 0.0f, this);
                }
            }
            else{
            }
        }
        else{

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }*/
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000).setFastestInterval(5000);
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                canAccessLocation()) {
            try{
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "No location permission", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MiMapa.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        displayLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("GMS Location", "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        mLastLocation =   LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            //Toast.makeText(this, latitude + ", " + longitude, Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(this, "LOCATION", Toast.LENGTH_SHORT).show();
        }
    }

    public void newPlace(View v){
        Intent intent = new Intent(this,AddRestaurantActivity.class);
        intent.putExtra("lat", mCurrLocationMarker.getPosition().latitude);
        intent.putExtra("lon", mCurrLocationMarker.getPosition().longitude);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 14), 500, null);

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Pick Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 14), 500, null);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                        mLocationRequest = new LocationRequest();
                        mLocationRequest.setInterval(1000);
                        mLocationRequest.setFastestInterval(1000);
                        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) && hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private void  notifyCannotAccessLocation(){
        Toast.makeText(MiMapa.this, "No se pude acceder a su ubicación, conceda permiso a la aplicación", Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;
        private String name, vicinity;

        public MyInfoWindowAdapter(String name, String vicinity) {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.custom_info_contents, null);
            this.name = name;
            this.vicinity = vicinity;
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView) myContentsView
                    .findViewById(R.id.title));
            tvTitle.setText(name);
            TextView tvSnippet = ((TextView) myContentsView
                    .findViewById(R.id.snippet));
            tvSnippet.setText(vicinity);

            Toast.makeText(getApplicationContext(),
                    "Picaste un marker", Toast.LENGTH_LONG)
                    .show();

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}



