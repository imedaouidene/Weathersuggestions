package com.mob.mse.weathersuggestions.data;

import android.net.Uri;
import android.util.Log;

/**
 * Created by Imed on 24-Dec-17.
 */

public class Utils {
    public Utils() {
    }

    public static final String WEATHER_API_KEY = "a1f506c969f20b0814e8650d99f1e6c5";

    public static String getURLweather(String loc){
        Uri.Builder builder = new Uri.Builder();
        String URL;
        builder.scheme("http").authority("api.openweathermap.org")
                .appendPath("data").appendPath("2.5")
                .appendPath("weather")
                .appendQueryParameter("lat", splitLoc(loc)[0])
                .appendQueryParameter("lon", splitLoc(loc)[1])
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("APPID", WEATHER_API_KEY);
        URL = builder.build().toString();
        Log.d("URL weather", URL);
        return URL;
    }

    private static String[] splitLoc(String loc){
        //Double d=Double.valueOf(s);
        String s[] = null;
        if(loc.contains("|")){
            s=loc.split("\\|");
        }
        return s;
    }

    public static String getURLforecast(String loc){
        Uri.Builder builder = new Uri.Builder();
        String URL;
        builder.scheme("http").authority("api.openweathermap.org")
                .appendPath("data").appendPath("2.5")
                .appendPath("forecast").appendPath("daily")
                .appendQueryParameter("lat", splitLoc(loc)[0])
                .appendQueryParameter("lon", splitLoc(loc)[1])
                .appendQueryParameter("cnt", "7")
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("APPID", WEATHER_API_KEY);
        URL = builder.build().toString();
        Log.d("URL forecast", URL);
        return URL;
    }
}
