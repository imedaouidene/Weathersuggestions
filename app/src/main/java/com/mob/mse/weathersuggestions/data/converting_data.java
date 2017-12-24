package com.mob.mse.weathersuggestions.data;

import android.widget.ImageView;

import com.mob.mse.weathersuggestions.R;

/**
 * Created by Imed on 24-Dec-17.
 */

public class converting_data{


    public converting_data() {
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










}
