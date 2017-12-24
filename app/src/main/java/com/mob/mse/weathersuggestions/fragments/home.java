package com.mob.mse.weathersuggestions.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.mse.weathersuggestions.JSON.JSONLoader;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.WeatherResponse;


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
    private TextView tv_temp, tv_desc, tv_city, tv_day;
    private Button bt_refresh, bt_theme, bt_map;
    private ImageView img_icon;
    private LinearLayout listview;
    private RelativeLayout lyt_bg;
    private ProgressBar progressbar;
    Utils utils = new Utils(getContext());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root  = inflater.inflate(R.layout.fragment_home, container, false);
        tv_temp		= (TextView) root.findViewById(R.id.tv_temp);
        tv_desc		= (TextView) root.findViewById(R.id.tv_desc);
        tv_city		= (TextView) root.findViewById(R.id.tv_city);
        tv_day		= (TextView) root.findViewById(R.id.tv_day);
        bt_refresh	= (Button) root.findViewById(R.id.bt_refresh);
        bt_theme	= (Button) root.findViewById(R.id.bt_theme);
        bt_map		= (Button) root.findViewById(R.id.bt_map);
        img_icon	= (ImageView) root.findViewById(R.id.img_icon);
        progressbar	= (ProgressBar) root.findViewById(R.id.progressbar);
        lyt_bg		= (RelativeLayout) root.findViewById(R.id.lyt_bg);
        listview 	= (LinearLayout) root.findViewById(R.id.listview);

        String loc = "46.1877542|6.1487415";
        String urlstring = Utils.getURLweather(loc);
        String forecast = Utils.getURLforecast(loc);
        JSONLoader.placeIdTask asyntask = new JSONLoader.placeIdTask(new JSONLoader.AsyncResponse() {
            @Override
            public void processFinish(WeatherResponse output1) {
               // Log.d("final" ,output1.toString()) ;
                tv_temp.setText(Double.toString(output1.main.temp));

                tv_desc.setText(output1.weather.get(0).main.toUpperCase());
                tv_day.setText(utils.getDay(output1.dt));
                utils.setDrawableIcon(output1.weather.get(0).icon, img_icon);
                utils.setLytColor(output1.weather.get(0).icon, lyt_bg);









            }
        });
        asyntask.execute(urlstring,forecast);



        return root ;
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
