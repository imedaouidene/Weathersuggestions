package com.mob.mse.weathersuggestions.fragments;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.mse.weathersuggestions.JSON.JSONLoader;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.data.GPSTracker;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.ForecastResponse;
import com.mob.mse.weathersuggestions.model.ItemForecast;
import com.mob.mse.weathersuggestions.model.ItemLocation;
import com.mob.mse.weathersuggestions.model.WeatherResponse;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
    private TextView tv_temp, tv_desc, tv_city, tv_day ,tv_humidity,tv_wind;

    private ImageView img_icon;
    private LinearLayout listview;
    private RelativeLayout lyt_bg;
    //private ProgressBar progressbar;
    Utils utils = null ;
    double temp = 0 ;
    boolean b = true ;
    boolean cityb = false ;
    String city = "none" , country = "none" ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        utils = new Utils(getContext());
        View root  = inflater.inflate(R.layout.fragment_home, container, false);
        tv_temp		= (TextView) root.findViewById(R.id.tv_temp);
        tv_desc		= (TextView) root.findViewById(R.id.tv_desc);
        tv_city		= (TextView) root.findViewById(R.id.tv_city);
        tv_day		= (TextView) root.findViewById(R.id.tv_day);
        tv_humidity = (TextView)root.findViewById(R.id.tv_humidity);
        tv_wind = (TextView)root.findViewById(R.id.tv_wind);

        //bt_refresh	= (Button) root.findViewById(R.id.bt_refresh);
        //bt_theme	= (Button) root.findViewById(R.id.bt_theme);
        //bt_map		= (Button) root.findViewById(R.id.bt_location);
        img_icon	= (ImageView) root.findViewById(R.id.img_icon);
        //progressbar	= (ProgressBar) root.findViewById(R.id.progressbar);
        lyt_bg		= (RelativeLayout) root.findViewById(R.id.lyt_bg);
        listview 	= (LinearLayout) root.findViewById(R.id.listview);

        String loc = "46.1877542|6.1487415";


        GPSTracker gps = new GPSTracker(getContext()) ;

        Location myloc = gps.getLocation();
       // loc = Double.toString(myloc.getLatitude())+"|"+Double.toString(myloc.getLongitude()) ;
        Log.e("location",loc) ;
        String urlstring = Utils.getURLweather(loc);
        String forecast = Utils.getURLforecast(loc);
        Log.e("forcase",forecast);

        JSONLoader.placeIdTask asyntask = new JSONLoader.placeIdTask(new JSONLoader.AsyncResponse() {
            @Override
            public void processFinish(final ItemLocation itemLocation) {

                WeatherResponse weatherResponse = itemLocation.getJsonWeather() ;
                ForecastResponse forecastResponse = itemLocation.getJsonForecast() ; 




               // Log.d("final" ,weatherResponse.toString()) ;
                tv_temp.setText(Integer.toString((int)(weatherResponse.main.temp+0.0f))+" °C");
                temp = weatherResponse.main.temp ;
                tv_desc.setText(weatherResponse.weather.get(0).main.toUpperCase());
                tv_day.setText(utils.getDay(weatherResponse.dt));
                utils.setDrawableIcon(weatherResponse.weather.get(0).icon, img_icon);
                tv_humidity.setText("Humidity : "+Double.toString(weatherResponse.main.humidity)+" %");
                tv_wind.setText("Wind speed  : "+Double.toString(weatherResponse.wind.speed)+" km/hr");
                int c = utils.setLytColor(weatherResponse.weather.get(0).icon, lyt_bg);


// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window

// finally change the color
                city = weatherResponse.name ;
                country = weatherResponse.sys.country ;
                tv_city.setText(city);
                tv_city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cityb){
                            tv_city.setText(city);
                            cityb = !cityb ;
                        }else{
                            tv_city.setText(country);
                            cityb = !cityb ;
                        }
                    }
                });

                tv_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (b) {
                        double f = utils.C2F(temp) ;
                        int arrondi = (int)(f+0.5f) ;

                        tv_temp.setText(Integer.toString(arrondi)+" °F");
                         b = false ;

                            } else {
                            b=true ;
                            tv_temp.setText(Integer.toString((int)(temp+0.0f))+" °C");

                        }
                        }

                });


                ArrayList<ItemForecast> forecasts = new ArrayList<ItemForecast>();
                for (int i = 1; i < 7; i++) {
                    ItemForecast fcs = new ItemForecast();
                    fcs.setTemp(Integer.toString((int)(forecastResponse.list.get(i).temp.day+0.0f))+"°C");
                    fcs.setDay(utils.getDay(forecastResponse.list.get(i).dt));
                    fcs.setDesc(forecastResponse.list.get(i).weather.get(0).main);
                    fcs.setIcon(forecastResponse.list.get(i).weather.get(0).icon);
                    forecasts.add(fcs);
                }
                setViewList(forecasts);




            }
        });
        asyntask.execute(urlstring,forecast);



        return root ;
    }


    public void setViewList(ArrayList<ItemForecast> forecasts){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        listview.removeAllViews();
        for (ItemForecast obje : forecasts) {
            View view;
            view = inflater.inflate(R.layout.list_item_forecast, listview, false);

            ((TextView) view.findViewById(R.id.tv_f_temp)).setText(obje.getTemp());
            ((TextView) view.findViewById(R.id.tv_f_day)).setText(obje.getDay());
            ((TextView) view.findViewById(R.id.tv_f_desc)).setText(obje.getDesc());
            ImageView img =(ImageView) view.findViewById(R.id.img_f_icon);
            utils.setDrawableSmallIcon(obje.getIcon(), img);

            listview.addView(view);
        }}

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
