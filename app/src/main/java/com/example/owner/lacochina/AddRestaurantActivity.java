package com.example.owner.lacochina;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRestaurantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

    Intent intent;

    double placeLat;
    double placeLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        restName= (EditText) findViewById(R.id.resNameEditText);
        restAddress=(EditText)findViewById(R.id.resAddressEditText);
        restType=(EditText)findViewById(R.id.resTypeEditText);
        resTelephone=(EditText)findViewById(R.id.resTelephoneEditText);
        resReputation=(EditText)findViewById(R.id.resReputationEditText);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText)findViewById(R.id.longitude) ;

        intent = getIntent();

        if(intent.hasExtra("lat") && intent.hasExtra("lon")){
            placeLat = intent.getDoubleExtra("lat", 0);
            placeLon = intent.getDoubleExtra("lon", 0);
        }

        latitude.setText(placeLat+"");
        longitude.setText(placeLon+"");
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu__principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            /*
            Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }*/
            Intent intent = new Intent(this,MiMapa.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
