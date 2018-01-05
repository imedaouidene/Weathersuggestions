package com.mob.mse.weathersuggestions.JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.Countryinfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Imed on 04-Jan-18.
 */

public class CountryinfoLoader {

    public interface AsyncResponse {

        void processFinish(Countryinfo countryinfo);
    }




    public static class placeIdTask extends AsyncTask<String, Void, Countryinfo> {
        public AsyncResponse delegate = null;//Call back interface
        Context context ;
        Countryinfo countryinfo ;

        public placeIdTask(AsyncResponse asyncResponse, Context context) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
            this.context = context ;

        }

        @Override
        protected Countryinfo doInBackground(String... strings) {
            String countryname = strings[0] ;
            Utils utils = new Utils(context) ;
            String countryinfourl = utils.getcountryinfoURL(countryname) ;
             countryinfo = new Countryinfo() ;

            JSONObject infosJson  = getJSON(countryinfourl) ;
            String infosJsonSTRING = infosJson.toString();

            Gson gson = new Gson();

            countryinfo = gson.fromJson(infosJsonSTRING, Countryinfo.class);

            return countryinfo;
        }

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Countryinfo countryinfos) {

            try {

                if (countryinfos != null) {
                    // Log.w("i'm here",itemLocation.get(0).getJsonWeather().name.toString());
                    //Log.d("list",list.toString()) ;
                   // p.dismiss();
                    delegate.processFinish(countryinfos);

                }
            } catch (Exception e) {
                Log.e("erreur", "Cannot process JSON results", e);
            }


            super.onPostExecute(countryinfos);
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

            JSONArray data = new JSONArray(json.toString()) ;

            JSONObject ii = data.getJSONObject(0);

            // This value will be 404 if the request was not
            // successful

            return ii;
        }catch(Exception e){
            Log.e("error",e.toString());
            return null;
        }

    }
}
