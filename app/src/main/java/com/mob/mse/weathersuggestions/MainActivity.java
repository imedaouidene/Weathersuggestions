package com.mob.mse.weathersuggestions;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import static com.mob.mse.weathersuggestions.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

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



        checkconnection() ;


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

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean  b = cm.getActiveNetworkInfo() != null;
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


                timerThread.run();
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
