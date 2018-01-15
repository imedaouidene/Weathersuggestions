package com.mob.mse.weathersuggestions.JSON;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.ImageResponse;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static com.mob.mse.weathersuggestions.JSON.JSONLoader.getJSON;


/**
 * Created by Imed on 05-Jan-18.
 */

public class ImageLoader {

    public interface AsyncResponse {

        void processFinish(ImageResponse imageResponse);
    }

    public static class placeIdTask extends AsyncTask<String, Void, ImageResponse> {
        public AsyncResponse delegate = null;//Call back interface
        Context context ;
        ImageResponse imageResponse ;

        public placeIdTask(AsyncResponse asyncResponse, Context context) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
            this.context = context ;



        }

        @Override
        protected ImageResponse doInBackground(String... strings) {
            String countryname = strings[0] ;
            Utils utils = new Utils(context) ;
            String countryinfourl = null;
            try {
                countryinfourl = utils.getcountryimages(countryname);
                Log.e("image website ", countryinfourl) ;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            imageResponse = new ImageResponse() ;

            JSONObject imagesJSON  = getJSON(countryinfourl) ;
            String imagesJsonSTRING = imagesJSON.toString();

            Gson gson = new Gson();

             imageResponse= gson.fromJson(imagesJsonSTRING, ImageResponse.class);

            return imageResponse;
        }


    @Override
    protected void onPostExecute(ImageResponse imageResponse) {

        try {

            if (imageResponse != null) {
                // Log.w("i'm here",itemLocation.get(0).getJsonWeather().name.toString());
                //Log.d("list",list.toString()) ;
                // p.dismiss();
                delegate.processFinish(imageResponse);

            }
        } catch (Exception e) {
            Log.e("erreur", "Cannot process JSON results", e);
        }


        super.onPostExecute(imageResponse);
    }

    }
}
