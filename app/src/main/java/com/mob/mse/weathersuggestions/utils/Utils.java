package com.mob.mse.weathersuggestions.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mob.mse.weathersuggestions.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Imed on 24-Dec-17.
 */

public class Utils{
    Context context ;
    public Utils(Context context) {
    this.context = context ;
    }

    public String getLastUpdate(Long l){
        Date curDate = new Date(l *1000);
        SimpleDateFormat format = new SimpleDateFormat("EEE d MMM yyyy HH:mm");
        String dateToStr = format.format(curDate);
        return "LAST UPDATE : "+dateToStr.toUpperCase();
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


    public static String getcityURLweather(String name) throws UnsupportedEncodingException {
        Uri.Builder builder = new Uri.Builder();
        String URL;
        builder.scheme("http").authority("api.openweathermap.org")
                .appendPath("data").appendPath("2.5")
                .appendPath("weather")
                .appendQueryParameter("q", name)
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("APPID", WEATHER_API_KEY);
        URL = builder.build().toString();
        Log.d("URL weather city", URL);
        return URL;
    }
    public static String getcityURLForecast(String name) throws UnsupportedEncodingException {
        Uri.Builder builder = new Uri.Builder();
        String URL;
        builder.scheme("http").authority("api.openweathermap.org")
                .appendPath("data").appendPath("2.5")
                .appendPath("forecast")
                .appendPath("daily")
                .appendQueryParameter("q", name)
                .appendQueryParameter("cnt", "7")
                .appendQueryParameter("mode", "json")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("APPID", WEATHER_API_KEY);
        URL = builder.build().toString();
        Log.d("URL forecast weather : ", URL);
        return URL;
    }

    //https://restcountries.eu/rest/v2/alpha?codes=ch

    public static String getcountryinfoURL (String name) throws UnsupportedEncodingException {
        Uri.Builder builder = new Uri.Builder();
        String URL;
        builder.scheme("https")
                .authority("restcountries.eu")
                .appendPath("rest")
                .appendPath("v2").appendPath("alpha")
                .appendQueryParameter("codes",URLEncoder.encode(name, "utf8")) ;
        URL = builder.build().toString();
        Log.d("URL country info : ", URL);


        return URL;

    }
//https://pixabay.com/api/?key=7593479-4b373fb7ca049dd32f5c81299&q=switzerland&image_type=photo&pretty=true
    public static String getcountryimages (String name) throws UnsupportedEncodingException {
        Uri.Builder builder = new Uri.Builder();
        String URL;
        builder.scheme("https")
                .authority("pixabay.com")
                .appendPath("api")

                .appendQueryParameter("key","7593479-4b373fb7ca049dd32f5c81299")
        .appendQueryParameter("q",URLEncoder.encode(name, "utf8"))
        .appendQueryParameter("image_type","photo")
        .appendQueryParameter("pretty","true");
        URL = builder.build().toString();
        Log.d("URL country info : ", URL);


        return URL;

    }


    public  static  String getplacesUrl(String input) throws UnsupportedEncodingException {
        String url ;
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json" );

        sb.append("?key=AIzaSyBAG7l6FUU-kEcHRoREmsLCp82yvOtmVKA");



        sb.append("&input=" + URLEncoder.encode(input, "utf8"));
        url=sb.toString();

        return  url ;
    }


    public double C2F(double temp) {
         double f = 0 ;

        f = temp *1.8 +32 ;


        return f ;
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
    //https://flagpedia.net/data/flags/normal/ch.png
    public static String getFlagURL(String country){
        Uri.Builder builder = new Uri.Builder();
        String URL;
        builder.scheme("https").authority("flagpedia.net")
                .appendPath("data").appendPath("flags")
                .appendPath("normal").appendPath(country+".png") ;

        URL = builder.build().toString();
        Log.d("URL flag", URL);
        return URL;
    }



    public int setLytColor(String icon, RelativeLayout lyt){

        String color[] = context.getResources().getStringArray(R.array.color_weather);
        if (icon.equals("01d") || icon.equals("01n")) { // clear sky
            lyt.setBackgroundColor(Color.parseColor(color[0]));
            return Color.parseColor(color[0]) ;

        } else if (icon.equals("02d") || icon.equals("02n")) { // few clouds
            lyt.setBackgroundColor(Color.parseColor(color[1]));
            return Color.parseColor(color[1]) ;


        } else if (icon.equals("03d") || icon.equals("03n")) { // scatteredclouds
            lyt.setBackgroundColor(Color.parseColor(color[2]));
            return Color.parseColor(color[2]) ;


        } else if (icon.equals("04d") || icon.equals("04n")) { // broken clouds
            lyt.setBackgroundColor(Color.parseColor(color[3]));
            return Color.parseColor(color[3]) ;

        } else if (icon.equals("09d") || icon.equals("09n")) { // shower rain
            lyt.setBackgroundColor(Color.parseColor(color[4]));
            return Color.parseColor(color[4]) ;

        } else if (icon.equals("10d") || icon.equals("10n")) { // rain
            lyt.setBackgroundColor(Color.parseColor(color[5]));
            return Color.parseColor(color[5]) ;

        } else if (icon.equals("11d") || icon.equals("11n")) { // thunderstorm
            lyt.setBackgroundColor(Color.parseColor(color[6]));
            return Color.parseColor(color[6]) ;

        } else if (icon.equals("13d") || icon.equals("13n")) { // snow
            lyt.setBackgroundColor(Color.parseColor(color[7]));
            return Color.parseColor(color[7]) ;

        } else if (icon.equals("50d") || icon.equals("50n")) { // mist
            lyt.setBackgroundColor(Color.parseColor(color[8]));
            return Color.parseColor(color[8]) ;

        } else {
            lyt.setBackgroundColor(Color.parseColor(color[9]));
            return Color.parseColor(color[9]) ;

        }
    }
    public int setLytColor2(String icon, LinearLayout lyt){

        String color[] = context.getResources().getStringArray(R.array.color_weather);
        if (icon.equals("01d") || icon.equals("01n")) { // clear sky
            lyt.setBackgroundColor(Color.parseColor(color[0]));
            return Color.parseColor(color[0]) ;

        } else if (icon.equals("02d") || icon.equals("02n")) { // few clouds
            lyt.setBackgroundColor(Color.parseColor(color[1]));
            return Color.parseColor(color[1]) ;


        } else if (icon.equals("03d") || icon.equals("03n")) { // scatteredclouds
            lyt.setBackgroundColor(Color.parseColor(color[2]));
            return Color.parseColor(color[2]) ;


        } else if (icon.equals("04d") || icon.equals("04n")) { // broken clouds
            lyt.setBackgroundColor(Color.parseColor(color[3]));
            return Color.parseColor(color[3]) ;

        } else if (icon.equals("09d") || icon.equals("09n")) { // shower rain
            lyt.setBackgroundColor(Color.parseColor(color[4]));
            return Color.parseColor(color[4]) ;

        } else if (icon.equals("10d") || icon.equals("10n")) { // rain
            lyt.setBackgroundColor(Color.parseColor(color[5]));
            return Color.parseColor(color[5]) ;

        } else if (icon.equals("11d") || icon.equals("11n")) { // thunderstorm
            lyt.setBackgroundColor(Color.parseColor(color[6]));
            return Color.parseColor(color[6]) ;

        } else if (icon.equals("13d") || icon.equals("13n")) { // snow
            lyt.setBackgroundColor(Color.parseColor(color[7]));
            return Color.parseColor(color[7]) ;

        } else if (icon.equals("50d") || icon.equals("50n")) { // mist
            lyt.setBackgroundColor(Color.parseColor(color[8]));
            return Color.parseColor(color[8]) ;

        } else {
            lyt.setBackgroundColor(Color.parseColor(color[9]));
            return Color.parseColor(color[9]) ;

        }
    }
    public void setDrawableSmallIcon(String icon, ImageView im) {

        if (icon.equals("01d")||icon.equals("01n")) { // clear sky
            im.setBackgroundResource(R.drawable.w_small_clear);

        } else if (icon.equals("02d")||icon.equals("02n")) { //few clouds
            im.setBackgroundResource(R.drawable.w_small_fewcloud);

        }else if (icon.equals("03d")||icon.equals("03n")) { // scattered clouds
            im.setBackgroundResource(R.drawable.w_small_cloud);

        }else if (icon.equals("04d")||icon.equals("04n")) { //broken clouds
            im.setBackgroundResource(R.drawable.w_small_cloud);

        }else if (icon.equals("09d")||icon.equals("09n")) {  //shower rain
            im.setBackgroundResource(R.drawable.w_small_shower);

        }else if (icon.equals("10d")||icon.equals("10n")) { //rain
            im.setBackgroundResource(R.drawable.w_small_rain);

        }else if (icon.equals("11d")||icon.equals("11n")) { //thunderstorm
            im.setBackgroundResource(R.drawable.w_small_thunderstorm);

        }else if (icon.equals("13d")||icon.equals("13n")) { //snow
            im.setBackgroundResource(R.drawable.w_small_snow);

        }else if (icon.equals("50d")||icon.equals("50n")) { //mist
            im.setBackgroundResource(R.drawable.w_small_mist);

        } else {
            im.setBackgroundResource(R.drawable.w_small_fewcloud);
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


    public ArrayList<String[]> generate_cities(String[] countries1) throws UnsupportedEncodingException {

        ArrayList<String[]> to_return = new ArrayList<>();
                String[]     weather = new String[countries1.length] ;
                String[]     forcast = new String[countries1.length] ;
        for (int i = 0 ; i< countries1.length ; i++ ){

            weather[i] = getcityURLweather(countries1[i]).toString();
            forcast[i] = getcityURLForecast(countries1[i]).toString() ;




        }







        to_return.add(weather);
        to_return.add(forcast);
     return to_return ;
    }





}
