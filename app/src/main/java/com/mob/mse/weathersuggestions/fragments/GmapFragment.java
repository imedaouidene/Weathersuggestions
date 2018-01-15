package com.mob.mse.weathersuggestions.fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mob.mse.weathersuggestions.JSON.CitiesLoader;
import com.mob.mse.weathersuggestions.JSON.CountryinfoLoader;
import com.mob.mse.weathersuggestions.JSON.ImageLoader;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.adapter.CustomInfoAdapter;
import com.mob.mse.weathersuggestions.adapter.SlidingImage_Adapter;
import com.mob.mse.weathersuggestions.data.GPSTracker;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.Countryinfo;
import com.mob.mse.weathersuggestions.model.ForecastResponse;
import com.mob.mse.weathersuggestions.model.ImageResponse;
import com.mob.mse.weathersuggestions.model.ItemCity;
import com.mob.mse.weathersuggestions.model.ItemForecast;
import com.mob.mse.weathersuggestions.model.ItemLocation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.mob.mse.weathersuggestions.R.id.listview;


public class GmapFragment extends Fragment implements OnMapReadyCallback {


    int t = 0;
    Context context;
    Utils utils;
    GPSTracker gps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = preferences.edit();
        gps = new GPSTracker(getContext());

        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        context = getContext();
        utils = new Utils(getContext());
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        LatLng larlng = new LatLng(gps.getLatitude(), gps.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(larlng, 13));
        context=getContext() ;
        final ItemLocation itemCity ;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                String loc =Double.toString(latLng.latitude)+"|"+Double.toString(latLng.longitude);
                String urlstring = Utils.getURLweather(loc);
                String forecast = Utils.getURLforecast(loc);


                CitiesLoader.placeIdTask placeIdTask = new CitiesLoader.placeIdTask(new CitiesLoader.AsyncResponse() {
                    @Override
                    public void processFinish(ArrayList<ItemCity> arrayList) {

                        ItemCity i = arrayList.get(0) ;
                        // Toast.makeText(getContext(), Double.toString(latLng.latitude)+" "+Double.toString(latLng.longitude), Toast.LENGTH_SHORT).show();
                        CustomInfoAdapter customInfoWindow = new CustomInfoAdapter(getContext(),0,0,0,i);
                        googleMap.setInfoWindowAdapter(customInfoWindow);
                        Marker marker = googleMap.addMarker(new MarkerOptions().title(i.getItemLocation().getName()).position(latLng));


                        marker.setTag(i);


                        marker.showInfoWindow();


                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                    }
                },context) ;


                String[] weatherurls = {urlstring};
                String[] forcasturls  = {forecast};

                placeIdTask.execute(weatherurls,forcasturls);





            }
            });



        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                ItemCity i = (ItemCity) marker.getTag();
                CustomInfoAdapter customInfoWindow = new CustomInfoAdapter(context,0,0,0,i);
                googleMap.setInfoWindowAdapter(customInfoWindow) ;
                marker.showInfoWindow();

                showinfos(context,i) ;




                //Toast.makeText(getContext(),i.getName(),Toast.LENGTH_SHORT).show();



                return false;
            }
        });

googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
    @Override
    public void onInfoWindowClick(Marker marker) {
        ItemCity itemCity = (ItemCity) marker.getTag();
        CustomInfoAdapter customInfoWindow = new CustomInfoAdapter(context,0,0,0,itemCity);


        showinfos(context,itemCity) ;

    }
});

    }
    TextView country_name  ;
    TextView ci_population ;
    TextView ci_languages ;
    TextView ci_currency ;
    Button addtofavorits ;
    ViewPager pager ;
    LinearLayout linearLayout ;
    SharedPreferences.Editor edit;
    Gson gson = new Gson() ;
    SharedPreferences preferences;

    public void showinfos(final Context context , final ItemCity city){


        Dialog dialog = new Dialog(context) ;
        dialog.setContentView(R.layout.city_info);
        ImageView flag = (ImageView)dialog.findViewById(R.id.city_flag) ;
        TextView city_name =(TextView)dialog.findViewById(R.id.city_name) ;
        country_name = (TextView)dialog.findViewById(R.id.ci_country_name) ;
        ci_population = (TextView)dialog.findViewById(R.id.ci_population) ;
        ci_languages = (TextView)dialog.findViewById(R.id.ci_languages) ;
        ci_currency = (TextView)dialog.findViewById(R.id.ci_currency) ;
        addtofavorits = (Button)dialog.findViewById(R.id.addtofavorits) ;
        pager = (ViewPager)dialog.findViewById(R.id.pager) ;
        linearLayout  =(LinearLayout)dialog.findViewById(listview) ;
        addtofavorits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String thiscity = gson.toJson(city) ;
                Set<String> cities = preferences.getStringSet("fav", new HashSet<String>());
                cities.add(thiscity) ;
                edit.clear();
                edit.putStringSet("fav",cities) ;
                edit.commit();
                //Toast.makeText(getContext(),city.getCity(),Toast.LENGTH_LONG).show();

                Toast.makeText(getContext(),"successfully added to favorits ", Toast.LENGTH_LONG).show();

            }
        }) ;

        //filling data
        Picasso.with(getContext())
                .load(Utils.getFlagURL(city.getItemLocation().getJsonWeather().sys.country.toLowerCase()))
                .into(flag);

        city_name.setText(city.getCity());

        CountryinfoLoader.placeIdTask placeIdTask = new CountryinfoLoader.placeIdTask(new CountryinfoLoader.AsyncResponse(){

            @Override
            public void processFinish(Countryinfo countryinfo) {
                country_name.setText(countryinfo.name);
                ci_population.setText(Long.toString(countryinfo.population));
                String language ="";
                for (int i=0 ; i<countryinfo.languages.size();i++){
                    language = language + countryinfo.languages.get(i).name+";";
                }
                // Toast.makeText(context,language,Toast.LENGTH_SHORT).show();
                ci_languages.setText(language);
                String currencies = "" ;
                for(int i=0; i<countryinfo.currencies.size();i++){
                    currencies = currencies + countryinfo.currencies.get(i).name+";";

                }
                // Toast.makeText(context,countryinfo.languages.get(0).,Toast.LENGTH_SHORT).show();
                ArrayList<ItemForecast> forecasts = new ArrayList<ItemForecast>();

                ForecastResponse forecastResponse = city.getItemLocation().getJsonForecast();
                for (int i = 1; i < 7; i++) {
                    ItemForecast fcs = new ItemForecast();
                    fcs.setTemp(Integer.toString((int)(forecastResponse.list.get(i).temp.day+0.0f))+"°C");
                    fcs.setDay(utils.getDay(forecastResponse.list.get(i).dt));
                    fcs.setDesc(forecastResponse.list.get(i).weather.get(0).main);
                    fcs.setIcon(forecastResponse.list.get(i).weather.get(0).icon);
                    forecasts.add(fcs);
                }
                setViewList1(forecasts,context,linearLayout);

                ci_currency.setText(currencies) ;



            }
        },getContext());


        placeIdTask.execute(city.getCountry());


        ImageLoader.placeIdTask imageloader = new ImageLoader.placeIdTask(new ImageLoader.AsyncResponse() {
            @Override
            public void processFinish(ImageResponse imageResponse) {

                for (int i = 0; i < imageResponse.hits.size(); i++)
                    ImagesArray.add(imageResponse.hits.get(i).webformatURL+"?key=7593479-4b373fb7ca049dd32f5c81299");


                pager.setAdapter(new SlidingImage_Adapter(getContext(), ImagesArray));


                NUM_PAGES = ImagesArray.size()-1 ;





            }
        },context) ;
        imageloader.execute(city.getCity());
        dialog.show();


    }
    ArrayList<String> ImagesArray = new ArrayList<String>();
    int NUM_PAGES ,currentPage;
    public void setViewList1(ArrayList<ItemForecast> forecasts,Context context,LinearLayout linearLayout){
        ImagesArray.clear();
        LayoutInflater inflater = LayoutInflater.from(context);
        linearLayout.removeAllViews();
        for (ItemForecast obje : forecasts) {
            View view;
            view = inflater.inflate(R.layout.weather_element, linearLayout, false);

            ((TextView) view.findViewById(R.id.tv_f_temp)).setText(obje.getTemp());
            ((TextView) view.findViewById(R.id.tv_f_day)).setText(obje.getDay());
            ((TextView) view.findViewById(R.id.tv_f_desc)).setText(obje.getDesc());
            ImageView img =(ImageView) view.findViewById(R.id.img_f_icon);
            utils.setDrawableSmallIcon(obje.getIcon(), img);
            linearLayout.addView(view);
        }}

}