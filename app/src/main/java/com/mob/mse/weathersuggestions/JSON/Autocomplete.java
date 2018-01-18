package com.mob.mse.weathersuggestions.JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mob.mse.weathersuggestions.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Imed on 05-Jan-18.
 */

public class Autocomplete  {

    public interface AsyncResponse {

        void processFinish(ArrayList<String> Autocompletelist);
    }


    public static class placeIdTask extends AsyncTask<String, Void, ArrayList<String>> {
        public AsyncResponse delegate = null;//Call back interface
        Context context ;
        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }
        Utils utils = new Utils(context) ;
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String input = strings[0] ;
            ArrayList<String> arrayList = new ArrayList<>() ;


            try {
                String url = utils.getplacesUrl(input);
                JSONObject raw_results = getJSON(url) ;
                JSONArray predsJsonArray = raw_results.getJSONArray("predictions");
                for (int i = 0; i < predsJsonArray.length(); i++) {
                    arrayList.add(predsJsonArray.getJSONObject(i).getString("description"));
                    Log.e("pred",predsJsonArray.getJSONObject(i).getString("description")) ;

                }


                } catch (Exception e) {
                e.printStackTrace();
            }




            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> arrayList) {
            try {

                if(arrayList != null){
                    //Log.w("i'm here",arrayList.toString());
                    //Log.d("list",list.toString()) ;

                    delegate.processFinish(arrayList);

                }
            } catch (Exception e) {
                Log.e("erreur", "Cannot process JSON results", e);
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
}