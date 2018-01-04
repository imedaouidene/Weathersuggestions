package com.mob.mse.weathersuggestions.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mob.mse.weathersuggestions.JSON.CitiesLoader;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.adapter.ItemCityAdapter;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.ItemCity;
import com.mob.mse.weathersuggestions.model.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import static com.mob.mse.weathersuggestions.Main.countries1;

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



    public void setViewList(ArrayList<ItemCity> cities) {
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


                //setViewList(arrayList);


            }
        }, getContext());


        String[] weatherurls, forcasturls;
        ArrayList<String[]> infos = utils.generate_cities(countries1);
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
