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

    void processFinish(JSONObject jsonObject);
}
public static class placeIdTask extends AsyncTask<URL,Void,JSONObject> {




    @Override
    protected JSONObject doInBackground(URL... urls) {
        JSONObject jsonWeather = null;

        try {
            jsonWeather = getWeatherJSON(urls[0]);
            Log.d("progress", "here1");
        } catch (Exception e) {
            Log.d("Error", "Cannot process JSON results", e);
        }


        return jsonWeather;

    }


    public AsyncResponse delegate = null;//Call back interface
    public placeIdTask(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }

    protected void onPostExecute(JSONObject json) {
        delegate.processFinish(json);

    }


    public  JSONObject getWeatherJSON(URL url){
        try {
            Log.d("progress", "here2");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            Log.d("progress", "here3");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Log.d("progress", "here4");

            StringBuffer json = new StringBuffer(2048);
            String tmp="";
            Log.d("progress", "here5");
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            Log.d("progress", "here6");

            Log.d("json",json.toString());
            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }

    }

}
}
