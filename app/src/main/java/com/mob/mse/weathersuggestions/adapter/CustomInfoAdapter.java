package com.mob.mse.weathersuggestions.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.ItemCity;
import com.squareup.picasso.Picasso;

public class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    public int lat,lng ;
    public int zoom ;
    ItemCity itemCity ;
    Utils utils ;
    public CustomInfoAdapter(Context context, int lat, int lng, int zoom,ItemCity itemCity) {
        this.context = context;
        this.lat = lat;
        this.lng = lng;
        this.zoom = zoom;
        this.itemCity = itemCity ;
       utils = new Utils(context);
    }
    TextView tv_f_temp;
    TextView tv_f_city;
    TextView tv_f_desc;
    ImageView img_f_icon;
    TextView minmax ;
    ImageView icon ;
    LinearLayout linearLayout ;


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View convertView = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custominfo, null);

        //final TextView name_tv = view.findViewById(R.id.name);

        //TextView details_tv = view.findViewById(R.id.text1);

//     Button btn= view.findViewById(R.id.btn) ;
  //   name_tv.setText(marker.getTitle());
        //details_tv.setText(marker.getSnippet());

        tv_f_temp	= (TextView) convertView.findViewById(R.id.tv_f_temp);
        tv_f_city	= (TextView) convertView.findViewById(R.id.tv_city_name);
        tv_f_desc	= (TextView) convertView.findViewById(R.id.tv_f_desc);
        img_f_icon	= (ImageView) convertView.findViewById(R.id.img_flag);
        minmax = (TextView)convertView.findViewById(R.id.tv_f_maxmin) ;
        icon = (ImageView)convertView.findViewById(R.id.img_f_icon) ;
        linearLayout = (LinearLayout)convertView.findViewById(R.id.lyt_bg) ;


        tv_f_temp.setText(Double.toString(itemCity.getItemLocation().getJsonWeather().main.temp+0.0f)+"°C");
        tv_f_city.setText(itemCity.getItemLocation().getJsonWeather().name);
        tv_f_desc.setText(itemCity.getItemLocation().getJsonWeather().weather.get(0).description);
        String min = Integer.toString((int) (itemCity.getItemLocation().getJsonWeather().main.temp_min + 0.0f));
        String max = Integer.toString((int) (itemCity.getItemLocation().getJsonWeather().main.temp_max + 0.0f));
        minmax.setText(min + "°/" + max + "°" ) ;
        utils.setDrawableIcon(itemCity.getItemLocation().getJsonWeather().weather.get(0).icon, icon);
        try {
        int c = utils.setLytColor2(itemCity.getItemLocation().getJsonWeather().weather.get(0).icon, linearLayout);
            //Toast.makeText(context,String.valueOf(c),Toast.LENGTH_SHORT).show();
        }catch (Exception e ){
            Log.e("got u 1  ", e.toString());

        }


        try {
    Log.e("iiii",Utils.getFlagURL(itemCity.getItemLocation().getJsonWeather().sys.country.toLowerCase()));
            //conv_data.setDrawableSmallIcon(itemDetailsrrayList.get(position).getIcon(), holder.img_f_icon);
            Picasso.with(context)
                    .load(Utils.getFlagURL(itemCity.getItemLocation().getJsonWeather().sys.country.toLowerCase()))
                    .into(img_f_icon);
        }catch (Exception e ){
            Log.e("got u ", e.toString());
        }



        return convertView;
    }
}
