package com.mob.mse.weathersuggestions.JSON;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mob.mse.weathersuggestions.model.ForecastResponse;
import com.mob.mse.weathersuggestions.model.WeatherResponse;

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

        void processFinish(WeatherResponse output1);
    }
    public static class placeIdTask extends AsyncTask<String, Void, WeatherResponse> {

        private String weatherString = null ;
        private  String forecastString = null ;
        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }
        WeatherResponse w ;
        @Override
        protected WeatherResponse  doInBackground(String... params) {
            WeatherResponse weather		= new WeatherResponse();
            ForecastResponse forecast 	= new ForecastResponse();
            JSONObject weatherResponse = null;
            JSONObject forcastResponse = null ;
            String weatherurl=  params[0] ;
            String forecasturl =  params[1] ;
            try {
                Gson gson = new Gson();

                weatherResponse = getJSON(weatherurl);
                forcastResponse = getJSON(forecasturl);
                weatherString = weatherResponse.toString();
                forecastString = forcastResponse.toString() ;

                w= gson.fromJson(weatherString, WeatherResponse.class);

            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }


            return w;
        }

        @Override
        protected void onPostExecute(WeatherResponse  json) {
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


