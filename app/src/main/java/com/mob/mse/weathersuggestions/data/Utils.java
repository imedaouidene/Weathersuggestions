package com.mob.mse.weathersuggestions.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mob.mse.weathersuggestions.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Imed on 24-Dec-17.
 */

public class Utils extends Activity{
    Context context ;
    public Utils(Context context1) {
    this.context = context1 ;
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

    public void setLytColor(String icon, RelativeLayout lyt){

        String color[] = context.getResources().getStringArray(R.array.color_weather);
        if (icon.equals("01d") || icon.equals("01n")) { // clear sky
            lyt.setBackgroundColor(Color.parseColor(color[0]));

        } else if (icon.equals("02d") || icon.equals("02n")) { // few clouds
            lyt.setBackgroundColor(Color.parseColor(color[1]));

        } else if (icon.equals("03d") || icon.equals("03n")) { // scatteredclouds
            lyt.setBackgroundColor(Color.parseColor(color[2]));

        } else if (icon.equals("04d") || icon.equals("04n")) { // broken clouds
            lyt.setBackgroundColor(Color.parseColor(color[3]));

        } else if (icon.equals("09d") || icon.equals("09n")) { // shower rain
            lyt.setBackgroundColor(Color.parseColor(color[4]));

        } else if (icon.equals("10d") || icon.equals("10n")) { // rain
            lyt.setBackgroundColor(Color.parseColor(color[5]));

        } else if (icon.equals("11d") || icon.equals("11n")) { // thunderstorm
            lyt.setBackgroundColor(Color.parseColor(color[6]));

        } else if (icon.equals("13d") || icon.equals("13n")) { // snow
            lyt.setBackgroundColor(Color.parseColor(color[7]));

        } else if (icon.equals("50d") || icon.equals("50n")) { // mist
            lyt.setBackgroundColor(Color.parseColor(color[8]));

        } else {
            lyt.setBackgroundColor(Color.parseColor(color[9]));
        }
    }


    public void setDrawableIcon(String icon, ImageView im) {
        if (icon.equals("01d")||icon.equals("01n")) { // clear sky
            im.setBackgroundResource(R.drawable.w_clear);

        } else if (icon.equals("02d")||icon.equals("02n")) { //few clouds
            im.setBackgroundResource(R.drawable.w_fewcloud);
        }else if (icon.equals("03d")||icon.equals("03n")) { // scattered clouds
            im.setBackgroundResource(R.drawable.w_cloud);
        }else if (icon.equals("04d")||icon.equals("04n")) { //broken clouds
            im.setBackgroundResource(R.drawable.w_cloud);

        }else if (icon.equals("09d")||icon.equals("09n")) {  //shower rain
            im.setBackgroundResource(R.drawable.w_shower);

        }else if (icon.equals("10d")||icon.equals("10n")) { //rain
            im.setBackgroundResource(R.drawable.w_rain);

        }else if (icon.equals("11d")||icon.equals("11n")) { //thunderstorm
            im.setBackgroundResource(R.drawable.w_thunderstorm);

        }else if (icon.equals("13d")||icon.equals("13n")) { //snow
            im.setBackgroundResource(R.drawable.w_snow);

        }else if (icon.equals("50d")||icon.equals("50n")) { //mist
            im.setBackgroundResource(R.drawable.w_mist);

        } else {
            im.setBackgroundResource(R.drawable.w_fewcloud);
        }
    }
    public static String getDay(Long l) {
        Date time=new Date(l *1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int daynum = cal.get(Calendar.DAY_OF_WEEK);

        switch (daynum) {
            case 1:
                return "Sunday" ;
            case 2:
                return "Monday" ;
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return null;
    }
}
