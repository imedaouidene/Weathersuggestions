package com.mob.mse.weathersuggestions.JSON;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mob.mse.weathersuggestions.model.ForecastResponse;
import com.mob.mse.weathersuggestions.model.ItemLocation;
import com.mob.mse.weathersuggestions.model.WeatherResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by Imed on 11-Oct-17.
 */
public class JSONLoader {


    public interface AsyncResponse {

        void processFinish(ItemLocation itemLocation);
    }
    public static class placeIdTask extends AsyncTask<String, Void, ItemLocation> {

        private String weatherString = null ;
        private  String forecastString = null ;
        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }
        WeatherResponse w ;
        ForecastResponse f ;

        @Override
        protected ItemLocation  doInBackground(String... params) {
            WeatherResponse weather		= new WeatherResponse();
            ForecastResponse forecast 	= new ForecastResponse();
            ItemLocation itemLocation  = new ItemLocation() ;
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

                try {
                    f = gson.fromJson(forecastString , ForecastResponse.class) ;


                }catch (Exception e) {
                    Log.e(":(" , e.toString()) ;
                }
                w= gson.fromJson(weatherString, WeatherResponse.class);
                itemLocation.setJsonWeather(w);
                itemLocation.setJsonForecast(f);
                itemLocation.setId(w.id+"");
                itemLocation.setName(w.name);
                itemLocation.setCode(w.sys.country);

            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }


            return itemLocation;
        }

        @Override
        protected void onPostExecute(ItemLocation  itemLocation) {
            try {

                if(itemLocation != null){
                    Log.w("i'm here",itemLocation.toString());
                    //Log.d("list",list.toString()) ;

                    delegate.processFinish(itemLocation);

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


