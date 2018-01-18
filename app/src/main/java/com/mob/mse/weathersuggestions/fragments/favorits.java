package com.mob.mse.weathersuggestions.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link favorits.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link favorits#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favorits extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public favorits() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favorits.
     */
    // TODO: Rename and change types and number of parameters
    public static favorits newInstance(String param1, String param2) {
        favorits fragment = new favorits();
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

    TextView nofav;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorits, container, false);
preferences    = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        edit = preferences.edit();
        gson= new Gson();
        nofav = (TextView)root.findViewById(R.id.nofav);
        lv = (ListView)root.findViewById(R.id.favorislist);
        final ArrayList<ItemCity> favorits = new ArrayList<>() ;

        Set<String> cities = preferences.getStringSet("fav", new HashSet<String>());


        for (String city : cities) {
            ItemCity itemCity = gson.fromJson(city,ItemCity.class);
            favorits.add(itemCity) ;
            //Toast.makeText(getContext(),itemCity.getCity(),Toast.LENGTH_LONG).show();
            //Log.e("city ",itemCity.getCity() ) ;


        }
            if(favorits.size()==0){
                nofav.setVisibility(View.VISIBLE);
        }else{
              nofav.setVisibility(View.INVISIBLE);
            }

        final ItemCityAdapter itemCityAdapter = new ItemCityAdapter(getContext(),android.R.layout.simple_list_item_1,favorits) ;

        lv.setAdapter(itemCityAdapter);



       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               ItemCity itemCity = (ItemCity) adapterView.getItemAtPosition(i);
               showinfos(getContext(),itemCity);

           }
       });
final Context context= getContext() ;
utils = new Utils(getContext()) ;

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context) ;

                final ItemCity itemCity = (ItemCity) adapterView.getItemAtPosition(i);
            builder.setTitle("delete from favorites")
                    .setMessage("Do you want to delete "+itemCity.getCity()+" from the list of favorites ?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            favorits.remove(itemCity);
                            itemCityAdapter.notifyDataSetChanged();
                            Set<String> cities = new HashSet<String>()  ;
                            for (int j=0 ; j<favorits.size();j++){
                                ItemCity k  =favorits.get(j);
                                String thiscity = gson.toJson(k) ;
                                cities.add(thiscity) ;

                            }


                            edit.clear();
                            edit.putStringSet("fav",cities) ;
                            edit.apply();

                        }
                    }).setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }) ;
                AlertDialog diag ;
                diag = builder.create();
                diag.show();
                return false;
            }
        });



        return root;
    }


Utils utils ;
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
        linearLayout  =(LinearLayout)dialog.findViewById(R.id.listview) ;
        addtofavorits.setVisibility(View.INVISIBLE);

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
                    fcs.setDay(Utils.getDay(forecastResponse.list.get(i).dt));
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
