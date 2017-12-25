package com.mob.mse.weathersuggestions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import com.mob.mse.weathersuggestions.data.ConnectionDetector;

import static com.mob.mse.weathersuggestions.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
ConnectionDetector cd ;


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

        cd = new ConnectionDetector(getApplicationContext()) ;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

            // to handle the case where the user grants the permission. See the documentation

        }else {
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


        boolean  b = cd.isConnectingToInternet() ;
        if (b) {
            try {

                timerThread = new Thread(){
                    public void run(){
                        try{
                            sleep(2000);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }finally{
                            Intent intent = new Intent(MainActivity.this,Main.class);
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
            try {
                nointernet.show();
             }catch (Exception e){
                Log.w("ERROR","ERROR in snackbar   "+e.toString() );

            }
        }
    }
}
