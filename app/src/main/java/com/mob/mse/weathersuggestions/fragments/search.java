package com.mob.mse.weathersuggestions.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mob.mse.weathersuggestions.JSON.CitiesLoader;
import com.mob.mse.weathersuggestions.JSON.CountryinfoLoader;
import com.mob.mse.weathersuggestions.JSON.ImageLoader;
import com.mob.mse.weathersuggestions.JSON.JSONLoader;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.adapter.GooglePlacesACAdapter;
import com.mob.mse.weathersuggestions.adapter.ItemCityAdapter;
import com.mob.mse.weathersuggestions.adapter.SlidingImage_Adapter;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.Countryinfo;
import com.mob.mse.weathersuggestions.model.ForecastResponse;
import com.mob.mse.weathersuggestions.model.ImageResponse;
import com.mob.mse.weathersuggestions.model.ItemCity;
import com.mob.mse.weathersuggestions.model.ItemForecast;
import com.mob.mse.weathersuggestions.model.ItemLocation;
import com.mob.mse.weathersuggestions.model.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.mob.mse.weathersuggestions.Main.countries1;
import static com.mob.mse.weathersuggestions.R.id.listview;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search.
     */
    // TODO: Rename and change types and number of parameters
    public static search newInstance(String param1, String param2) {
        search fragment = new search();
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


    TextView sortbutton , filterButton ;
    AutoCompleteTextView searchbar ;
    double temp = 0 ;
    boolean b = true ;
    boolean cityb = false ;
    String city = "none" , country = "none" ;
    ArrayList<String> ImagesArray = new ArrayList<String>();
    int NUM_PAGES ,currentPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root  = inflater.inflate(R.layout.fragment_search, container, false);
        utils= new Utils(getContext());

        preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = preferences.edit();
        gson= new Gson();
        searchbar = (AutoCompleteTextView)root.findViewById(R.id.searchedittext) ;

        searchbar.setAdapter(new GooglePlacesACAdapter(getContext(),R.layout.search_item));


        searchbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchbar.clearListSelection();
                searchbar.setText("");
                return false ;
            }
        });
        searchbar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ImagesArray.clear();
                final Dialog dialog = new Dialog(getContext()) ;
                dialog.setContentView(R.layout.search_dialog);

                tv_temp		= (TextView) dialog.findViewById(R.id.tv_temp);
                tv_desc		= (TextView) dialog.findViewById(R.id.tv_desc);
                tv_city		= (TextView) dialog.findViewById(R.id.tv_city);
                tv_day		= (TextView) dialog.findViewById(R.id.tv_day);
                tv_humidity = (TextView)dialog.findViewById(R.id.tv_humidity);
                tv_wind = (TextView)dialog.findViewById(R.id.tv_wind);
                pager = (ViewPager)dialog.findViewById(R.id.pager) ;
                linearLayout  =(LinearLayout)dialog.findViewById(listview) ;
                img_icon	= (ImageView) dialog.findViewById(R.id.img_icon);
                //progressbar	= (ProgressBar) root.findViewById(R.id.progressbar);
                lyt_bg		= (RelativeLayout) dialog.findViewById(R.id.lyt_bg);


                String str = (String) adapterView.getItemAtPosition(i);

                try {
                    str = str.substring(0,str.indexOf(',',str.indexOf(',')+1)) ;

                }catch (Exception e){
                    Log.e("str error",e.toString()) ;
                }



                String urlstring = null;
                String forecast = null  ;
                try {
                    forecast = Utils.getcityURLForecast(str) ;
                    urlstring = Utils.getcityURLweather(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ;


                JSONLoader.placeIdTask asyntask = new JSONLoader.placeIdTask(new JSONLoader.AsyncResponse() {
                    @Override
                    public void processFinish(final ItemLocation itemLocation) {

                        WeatherResponse weatherResponse = itemLocation.getJsonWeather() ;
                        ForecastResponse forecastResponse = itemLocation.getJsonForecast() ;




                        // Log.d("final" ,weatherResponse.toString()) ;
                        try {


                        tv_temp.setText(Integer.toString((int)(weatherResponse.main.temp+0.0f))+" °C");
                        } catch (Exception e){

                            AlertDialog.Builder b = new AlertDialog.Builder(getContext()) ;

                            b.setTitle("Error");
                            b.setMessage("No Data was found , please try again") ;
                            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }) ;
                            AlertDialog alert = b.create() ;
                            alert.show();


                        }
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



                        ImageLoader.placeIdTask imageloader = new ImageLoader.placeIdTask(new ImageLoader.AsyncResponse() {
                            @Override
                            public void processFinish(ImageResponse imageResponse) {
                              //  swipeTimer.purge();
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

                            /*    swipeTimer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.post(Update);
                                    }
                                }, 3000, 3000);*/

                            }
                        },getContext()) ;

                        imageloader.execute(city);

                        dialog.show();




                    }
                });
                asyntask.execute(urlstring,forecast);



            }
        });


        searchlistview = (ListView)root.findViewById(R.id.searchlistview) ;

        CitiesLoader.placeIdTask citiesLoader = new CitiesLoader.placeIdTask(new CitiesLoader.AsyncResponse() {


            @Override
            public void processFinish(ArrayList<ItemCity> arrayList) {
                // Toast.makeText(getContext(), "ok I'm here", Toast.LENGTH_SHORT).show();
                Log.e("0", arrayList.get(0).getItemLocation().getJsonWeather().name);
                //Log.e("1",)
                Collections.sort(arrayList);


                if (cityarray!=null){
                cityarray.clear();
                }
                for (int i = 0 ; i<arrayList.size();i++){

                        cityarray.add((ItemCity)arrayList.get(i));
                        //Toast.makeText(getContext(),Double.toString(arrayList.get(i).getItemLocation().getJsonWeather().main.temp),Toast.LENGTH_SHORT).show();

                }

                cityadapter = new ItemCityAdapter(getContext(),android.R.layout.simple_list_item_1, cityarray ) ;

                searchlistview.setAdapter(cityadapter);

                searchlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        citiesLoader.execute(weatherurls, forcasturls);










        return root ;
    }



    ArrayList<ItemCity> cityarray = new ArrayList<>() ;
    TextView minText,maxText,exit ;
    int min = 0 ;
    int max = 0 ;
    private TextView tv_temp, tv_desc, tv_city, tv_day ,tv_humidity,tv_wind;
    Button apply ;
    ItemCityAdapter cityadapter ;
    private ImageView img_icon;

    private RelativeLayout lyt_bg;

    ListView searchlistview ;
    Utils utils ;

    public void setViewList(ArrayList<ItemForecast> forecasts){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        linearLayout.removeAllViews();
        for (ItemForecast obje : forecasts) {
            View view;
            view = inflater.inflate(R.layout.list_item_forecast, linearLayout, false);

            ((TextView) view.findViewById(R.id.tv_f_temp)).setText(obje.getTemp());
            ((TextView) view.findViewById(R.id.tv_f_day)).setText(obje.getDay());
            ((TextView) view.findViewById(R.id.tv_f_desc)).setText(obje.getDesc());
            ImageView img =(ImageView) view.findViewById(R.id.img_f_icon);
            utils.setDrawableSmallIcon(obje.getIcon(), img);

            linearLayout.addView(view);
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



    TextView country_name  ;
    TextView ci_population ;
    TextView ci_languages ;
    TextView ci_currency ;
    Button addtofavorits ;
    ViewPager pager ;
    LinearLayout linearLayout ;
    SharedPreferences.Editor edit;
    Gson gson ;
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
                Toast.makeText(getContext(),"successfully added to favorits ",Toast.LENGTH_LONG).show();

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
                setViewList1(forecasts,context,linearLayout);

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

                for (int i = 0; i < imageResponse.hits.size(); i++)
                    ImagesArray.add(imageResponse.hits.get(i).webformatURL+"?key=7593479-4b373fb7ca049dd32f5c81299");

                pager.setAdapter(new SlidingImage_Adapter(getContext(), ImagesArray));


                NUM_PAGES = ImagesArray.size()-1 ;





            }
        },context) ;
        imageloader.execute(city.getCity());
        dialog.show();


    }




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
