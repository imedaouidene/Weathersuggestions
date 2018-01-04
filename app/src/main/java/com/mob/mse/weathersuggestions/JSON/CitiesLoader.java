package com.mob.mse.weathersuggestions.JSON;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mob.mse.weathersuggestions.model.ForecastResponse;
import com.mob.mse.weathersuggestions.model.ItemCity;
import com.mob.mse.weathersuggestions.model.ItemLocation;
import com.mob.mse.weathersuggestions.model.WeatherResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Imed on 11-Oct-17.
 */
public class CitiesLoader {


    public interface AsyncResponse {

        void processFinish(ArrayList<ItemCity> arrayList);
    }


    public static class placeIdTask extends AsyncTask<String[], Void, ArrayList<ItemCity>> {
        Context context;
        private String weatherString = null;
        private String forecastString = null;
        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse, Context context) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
            this.context = context;
        }


        WeatherResponse w;
        ForecastResponse f;
        ProgressDialog p;


        @Override
        protected void onPreExecute() {

            p = new ProgressDialog(context);
            p.setMessage("Loading Data ... ");
            p.setIndeterminate(true);
            p.show();


            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(ArrayList<ItemCity> itemLocation) {
            try {

                if (itemLocation != null) {
                    // Log.w("i'm here",itemLocation.get(0).getJsonWeather().name.toString());
                    //Log.d("list",list.toString()) ;
                    p.dismiss();
                    delegate.processFinish(itemLocation);

                }
            } catch (Exception e) {
                Log.e("erreur", "Cannot process JSON results", e);
            }


        }

        @Override
        protected ArrayList<ItemCity> doInBackground(String[]... strings) {


            ArrayList<ItemCity> myarray = new ArrayList<>();


            for (int i = 0; i < strings[0].length; i++) {
                Log.e("forcasssttt",strings[1][i]) ;
                ItemLocation itemLocation = new ItemLocation() ;
                JSONObject weatherResponse = null;
                JSONObject forcastResponse = null;
                try {
                    Gson gson = new Gson();
                    Log.e(" size : ", Integer.toString(myarray.size()));
                    try {
                        Log.e("weatherrrrrr",strings[0][i]) ;
                    weatherResponse = getJSON(strings[0][i]);}


                    catch(Exception e){
                        Log.e("!!" , e.toString());}
                    forcastResponse = getJSON(strings[1][i]);

                    weatherString = weatherResponse.toString();
                    forecastString = forcastResponse.toString();

                    w = gson.fromJson(weatherString, WeatherResponse.class);
                    try{
                    f = gson.fromJson(forecastString , ForecastResponse.class) ;
                    }catch(Exception e){
                        Log.e("error here ?" , e.toString())
;                    }
                    itemLocation.setJsonWeather(w);
                    itemLocation.setJsonForecast(f);
                    itemLocation.setId(w.id + "");
                    itemLocation.setName(w.name);
                    itemLocation.setCode(w.sys.country);
           //         	public ItemCity(String city, String country, String temp, String max, String min, String desc, String icon, ItemLocation itemLocation) {

                        ItemCity itemCity = new ItemCity(w.name,w.sys.country,Double.toString(w.main.temp),Double.toString(w.main.temp_max),Double.toString(w.main.temp_min),
                                w.weather.get(0).description,w.weather.get(0).icon,itemLocation);


                    itemCity.setItemLocation(itemLocation);


                    myarray.add(itemCity);
                } catch (Exception e) {
                    Log.d("Error", "size : " + myarray.size() + "Cannot process JSON results", e);
                }
            }


            return myarray;
        }
    }


    public static JSONObject getJSON(String myurl) {
        try {
            URL url = new URL(String.format(myurl));
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            //	connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(2048);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();


            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful

            return data;
        } catch (Exception e) {

            Log.e("error 007 ! ", myurl.toString() + "       " + e.toString());
            return null;
        }

    }
}


