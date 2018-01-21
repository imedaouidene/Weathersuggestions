package com.mob.mse.weathersuggestions;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import static com.mob.mse.weathersuggestions.R.layout.activity_main;

public class Splash extends AppCompatActivity {



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    checkconnection();
                } else {
                    Toast.makeText(getApplicationContext(),"Permission to access your GPS not granted!", Toast.LENGTH_SHORT);
                    finish();
                }
                return;
            }

        }


    }
    LocationManager locationManager ;
    boolean GpsStatus ;


    public void CheckGpsStatus(){

        locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }



    public void showSettingsAlert1() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS not active");

        // Setting Dialog Message
        alertDialog.setMessage("Please consider activating your GPS");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        checkconnection() ;



                    }
                });



        // Showing Alert Message
        alertDialog.create().show();
    }




    public void showSettingsAlert() {
        android.support.v7.app.AlertDialog.Builder settingsAlert = new android.support.v7.app.AlertDialog.Builder(Splash.this);
        settingsAlert.setTitle("GPS not active ");
        settingsAlert.setMessage("ACTIVATE GPS ?");

        settingsAlert.setPositiveButton("Go to Settings",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goToSettings = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(goToSettings);

                    }
                });

        settingsAlert.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        settingsAlert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        view= findViewById(android.R.id.content) ;
        getSupportActionBar().hide(); // hiding Support Action Bar
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.White));

        //Thread to pass automatically to next layout


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

            // to handle the case where the user grants the permission. See the documentation

        }else{
            checkconnection();
        }

    }

    Snackbar nointernet;
    Thread timerThread ;
    View view ;
    void checkconnection() {


        nointernet  = Snackbar.make(view,"No internet connection! ",Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkconnection() ;
                    }
                });


        boolean  b = isConnectingToInternet() ;

        if (b) {


            CheckGpsStatus();
           // Toast.makeText(getApplicationContext(),Boolean.toString(GpsStatus),Toast.LENGTH_LONG).show();
            if (GpsStatus) {





            try {

                timerThread = new Thread(){
                    public void run(){
                        try{
                            sleep(500);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }finally{
                            Intent intent = new Intent(Splash.this,Main.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };


                timerThread.start();
            } catch (Exception e){
                Log.w("ERROR","ERROR in snack bar   "+e.toString() );

            }


            }else{
                //Toast.makeText(getApplicationContext(),"I'm here ",Toast.LENGTH_LONG).show();
                showSettingsAlert();
            }
        }else{
            try {
                nointernet.show();
             }catch (Exception e){
                Log.w("ERROR","ERROR in snackbar   "+e.toString() );

            }
        }
    }


    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    @Override
    protected void onResume() {


        super.onResume();


        checkconnection();
    }
}
