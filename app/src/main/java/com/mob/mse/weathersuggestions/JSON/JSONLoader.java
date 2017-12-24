package com.mob.mse.weathersuggestions.JSON;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Imed on 24-Dec-17.
 */
public class JSONLoader {

    public interface AsyncResponse {

        void processFinish(JSONObject output1);
    }
    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            String myurl = params[0] ;
            try {
                jsonWeather = getJSON(myurl);
            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }


            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {

                if(json != null){
                    Log.w("i'm here",json.toString());
                    //Log.d("list",list.toString()) ;

                    delegate.processFinish(json);

                }
            } catch (Exception e) {
                Log.e("erreur", "Cannot process JSON results", e);
            }



        }
    }






    public static JSONObject getJSON(String myurl){
        try {
            URL url = new URL(String.format(myurl));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            //	connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(2048);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            Log.d("json","ahlaaaa");

            Log.d("json",json.toString());


            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful

            return data;
        }catch(Exception e){
            Log.e("error",e.toString());
            return null;
        }

    }
}


