package com.mob.mse.weathersuggestions.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.mse.weathersuggestions.JSON.CitiesLoader;
import com.mob.mse.weathersuggestions.JSON.CountryinfoLoader;
import com.mob.mse.weathersuggestions.JSON.ImageLoader;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.adapter.ItemCityAdapter;
import com.mob.mse.weathersuggestions.adapter.SlidingImage_Adapter;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.Countryinfo;
import com.mob.mse.weathersuggestions.model.ForecastResponse;
import com.mob.mse.weathersuggestions.model.ImageResponse;
import com.mob.mse.weathersuggestions.model.ItemCity;
import com.mob.mse.weathersuggestions.model.ItemForecast;
import com.mob.mse.weathersuggestions.model.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import static com.mob.mse.weathersuggestions.Main.countries1;
import static com.mob.mse.weathersuggestions.R.id.listview;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link suggestion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link suggestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class suggestion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public suggestion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment suggestion.
     */
    // TODO: Rename and change types and number of parameters
    public static suggestion newInstance(String param1, String param2) {
        suggestion fragment = new suggestion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Utils utils;



    public void setViewList0(ArrayList<ItemCity> cities) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        hot_cities.removeAllViews();
        cold_cities.removeAllViews();
        int i, j;
        i = 0;
        j = 0;
        for (ItemCity city : cities) {


            WeatherResponse weatherResponse = city.getItemLocation().getJsonWeather();
            Log.e("desc", weatherResponse.weather.get(0).main);
            View view;


            String cold_color[] = getContext().getResources().getStringArray(R.array.cold_array);
            String hot_color[] = getContext().getResources().getStringArray(R.array.hot_array);
            int len1 = cold_color.length;
            int len2 = hot_color.length;
            if (weatherResponse.main.temp > 10) {


                view = inflater.inflate(R.layout.list_item_city, hot_cities, false);

                ((TextView) view.findViewById(R.id.tv_f_temp)).setText(Integer.toString((int) (weatherResponse.main.temp + 0.0f)) + "°C");
                ((TextView) view.findViewById(R.id.tv_city_name)).setText(weatherResponse.name);
                ((TextView) view.findViewById(R.id.tv_f_desc)).setText(weatherResponse.weather.get(0).main);

                String min = Integer.toString((int) (weatherResponse.main.temp_min + 0.0f));
                String max = Integer.toString((int) (weatherResponse.main.temp_max + 0.0f));
                utils.setDrawableIcon(weatherResponse.weather.get(0).icon, ((ImageView) view.findViewById(R.id.img_f_icon)));
                ((TextView) view.findViewById(R.id.tv_f_maxmin)).setText(min + "°/" + max + "°");
                view.setBackgroundColor(Color.parseColor(hot_color[i]));

                if (i < len2 - 1) {
                    i += 1;
                }


                Picasso.with(getContext()).load(Utils.getFlagURL(weatherResponse.sys.country.toLowerCase())).into((ImageView) view.findViewById(R.id.img_flag));


                hot_cities.addView(view);

            } else {
                view = inflater.inflate(R.layout.list_item_city, cold_cities, false);
                ((TextView) view.findViewById(R.id.tv_f_temp)).setText((int) (weatherResponse.main.temp + 0.0f) + "°C");
                ((TextView) view.findViewById(R.id.tv_city_name)).setText(weatherResponse.name);
                ((TextView) view.findViewById(R.id.tv_f_desc)).setText(weatherResponse.weather.get(0).description);
                Picasso.with(getContext()).load(Utils.getFlagURL(weatherResponse.sys.country.toLowerCase())).into((ImageView) view.findViewById(R.id.img_flag));
                view.setBackgroundColor(Color.parseColor(cold_color[j]));
                if (j < len1 - 1) j = j + 1;

                cold_cities.addView(view);

            }



    /*    for (ItemForecast obje : forecasts) {


            ((TextView) view.findViewById(R.id.tv_f_temp)).setText(obje.getTemp());
            ((TextView) view.findViewById(R.id.tv_f_day)).setText(obje.getDay());
            ((TextView) view.findViewById(R.id.tv_f_desc)).setText(obje.getDesc());
            ImageView img =(ImageView) view.findViewById(R.id.img_f_icon);
            utils.setDrawableSmallIcon(obje.getIcon(), img);

            listview.addView(view);
        }*/


        }


    }


    LinearLayout hot_cities, cold_cities;
    ListView hot_cities_listView,cold_cities_listView;
    ItemCityAdapter coldadapter,hotadapter ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_suggestion, container, false);
        utils = new Utils(getContext());

        //hot_cities = (LinearLayout) root.findViewById(R.id.hot_cities);
        //cold_cities = (LinearLayout) root.findViewById(R.id.cold_cities);

        hot_cities_listView = (ListView)root.findViewById(R.id.hot_cities) ;
        cold_cities_listView  = (ListView)root.findViewById(R.id.cold_cities) ;



        CitiesLoader.placeIdTask citiesLoader = new CitiesLoader.placeIdTask(new CitiesLoader.AsyncResponse() {


            @Override
            public void processFinish(ArrayList<ItemCity> arrayList) {
                // Toast.makeText(getContext(), "ok I'm here", Toast.LENGTH_SHORT).show();
                Log.e("0", arrayList.get(0).getItemLocation().getJsonWeather().name);
                //Log.e("1",)
                Collections.sort(arrayList);
                ArrayList<ItemCity> coldarray = new ArrayList<>() ;
                ArrayList<ItemCity> hotarray = new ArrayList<>() ;

                for (int i = 0 ; i<arrayList.size();i++){
                    if (arrayList.get(i).getItemLocation().getJsonWeather().main.temp<10) {
                        coldarray.add((ItemCity)arrayList.get(i));
                        //Toast.makeText(getContext(),Double.toString(arrayList.get(i).getItemLocation().getJsonWeather().main.temp),Toast.LENGTH_SHORT).show();
                    }else{

                        hotarray.add((ItemCity)arrayList.get(i));

                    }
                }

                coldadapter = new ItemCityAdapter(getContext(),android.R.layout.simple_list_item_1, coldarray ) ;
                hotadapter = new ItemCityAdapter(getContext(),android.R.layout.simple_list_item_1, hotarray ) ;
                hot_cities_listView.setAdapter(hotadapter);
                cold_cities_listView.setAdapter(coldadapter);

                hot_cities_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ItemCity itemCity = (ItemCity) adapterView.getItemAtPosition(i);

                        showinfos(getContext(),itemCity) ;



                    }
                });
                cold_cities_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ItemCity itemCity = (ItemCity) adapterView.getItemAtPosition(i);

                        showinfos(getContext(),itemCity) ;
                    }
                });


                //setViewList(arrayList);


            }
        }, getContext());


        String[] weatherurls, forcasturls;
        ArrayList<String[]> infos = null;
        try {
            infos = utils.generate_cities(countries1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        weatherurls = infos.get(0);
        forcasturls = infos.get(1);

        //Log.e("weatherurls",weatherurls[0]) ;
        // Log.e("forcasturls",forcasturls[1]) ;



      /*  JSONLoader.placeIdTask asyntask = new JSONLoader.placeIdTask(new JSONLoader.AsyncResponse() {
            @Override
            public void processFinish(final ItemLocation itemLocation) {

                WeatherResponse weatherResponse = itemLocation.getJsonWeather() ;
                ForecastResponse forecastResponse = itemLocation.getJsonForecast() ;



                ArrayList<ItemForecast> forecasts = new ArrayList<ItemForecast>();
                for (int i = 1; i < 7; i++) {
                    ItemForecast fcs = new ItemForecast();
                    fcs.setTemp(Integer.toString((int)(forecastResponse.list.get(i).temp.day+0.0f))+"°C");
                    fcs.setDay(utils.getDay(forecastResponse.list.get(i).dt));
                    fcs.setDesc(forecastResponse.list.get(i).weather.get(0).main);
                    fcs.setIcon(forecastResponse.list.get(i).weather.get(0).icon);
                    forecasts.add(fcs);
                }
                //setViewList(forecasts);




            }
        });*/
        citiesLoader.execute(weatherurls, forcasturls);




        return root;

    }
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    Timer swipeTimer = new Timer();
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private static final String[] IMAGES = new String[3];

    public void setViewList(ArrayList<ItemForecast> forecasts,Context context,LinearLayout linearLayout){
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


    TextView country_name  ;
    TextView ci_population ;
    TextView ci_languages ;
    TextView ci_currency ;
    Button addtofavorits ;
    ViewPager pager ;
    LinearLayout linearLayout ;
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
                language = language + countryinfo.languages.get(i).name;
            }
           // Toast.makeText(context,language,Toast.LENGTH_SHORT).show();
            ci_languages.setText(language);
            String currencies = "" ;
            for(int i=0; i<countryinfo.currencies.size();i++){
                currencies = currencies + countryinfo.currencies.get(i).name;

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
            setViewList(forecasts,context,linearLayout);

            ci_currency.setText(currencies) ;
            addtofavorits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"This is context",Toast.LENGTH_SHORT).show();
                }
            }) ;


        }
    },getContext());


    placeIdTask.execute(city.getCountry());


        ImageLoader.placeIdTask imageloader = new ImageLoader.placeIdTask(new ImageLoader.AsyncResponse() {
            @Override
            public void processFinish(ImageResponse imageResponse) {
                swipeTimer.purge();
                for (int i = 0; i < imageResponse.hits.size(); i++)
                    ImagesArray.add(imageResponse.hits.get(i).webformatURL+"?key=7593479-4b373fb7ca049dd32f5c81299");

                pager.setAdapter(new SlidingImage_Adapter(getContext(), ImagesArray));


                NUM_PAGES = ImagesArray.size()-1 ;

                 //Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        pager.setCurrentItem(currentPage++, true);
                    }
                };

                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 3000, 3000);

            }
        },context) ;
        imageloader.execute(city.getCity());
     dialog.show();


}



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
