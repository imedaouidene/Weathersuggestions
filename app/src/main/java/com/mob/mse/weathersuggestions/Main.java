package com.mob.mse.weathersuggestions;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.mob.mse.weathersuggestions.fragments.home;
import com.mob.mse.weathersuggestions.fragments.suggestion;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

  /*  public static String[] countries1 = {"Brasilia", "Rio de Janeiro", "Havana", "Miami", "Kuala Lumpur", "Singapore", "Mumbai",
            "Lima", "Dubai", "Sydney", "Bangkok", "Hong Kong", "Taipei", "Cairo", "Beirut", "Rabat",
            "Abu Dhabi", "Havana", "Darwin", "Santiago"} ;
    public static String[] countries2 ={"Tunis", "San Francisco", "Las Vegas", "Istanbul", "Rome", "New Delhi", "Barcelona", "Paris", "London", "Madrid",
            "Berlin", "Frankfurt", "Prague", "Stockholm", "Moscow", "Tokyo", "Washington DC", "Seoul", "Montréal", "Toronto",
    };
*/


    public static String[] countries1 = {"Brasilia", "Havana", "Miami",  "Singapore", "Mumbai",
            "Lima", "Dubai", "Sydney", "Bangkok",  "Taipei", "Cairo", "Beirut", "Rabat"
         , "Havana", "Darwin", "Santiago","Tunis", "Istanbul", "Rome", "Barcelona", "Paris", "London", "Madrid",
            "Berlin", "Frankfurt", "Prague", "Stockholm", "Moscow", "Tokyo",  "Seoul", "Montreal", "Toronto",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            home fragment = new home() ;
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
/*            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.home_back));*/
            // Handle the camera action
        } else if (id == R.id.search) {

        } else if (id == R.id.suggestion) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            home fragment = new home() ;
            suggestion suggestion_frag = new suggestion() ;
            fragmentTransaction.add(R.id.fragment_container, suggestion_frag);
            fragmentTransaction.commit();
        }else if (id == R.id.favorite) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
