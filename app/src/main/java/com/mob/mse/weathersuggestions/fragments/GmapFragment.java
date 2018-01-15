package com.mob.mse.weathersuggestions.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mob.mse.weathersuggestions.JSON.JSONLoader;
import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.adapter.CustomInfoAdapter;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.model.ItemLocation;


public class GmapFragment extends Fragment implements OnMapReadyCallback {


    int t = 0  ;
    Context context ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        context=getContext() ;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        LatLng marker = new LatLng(-33.867, 151.206);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));

        context=getContext() ;
        final ItemLocation itemCity ;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                String loc =Double.toString(latLng.latitude)+"|"+Double.toString(latLng.longitude);
                String urlstring = Utils.getURLweather(loc);
                String forecast = Utils.getURLforecast(loc);


                JSONLoader.placeIdTask placeIdTask = new JSONLoader.placeIdTask(new JSONLoader.AsyncResponse(){


                    @Override
                    public void processFinish(ItemLocation countryinfo) {



                       // Toast.makeText(getContext(), Double.toString(latLng.latitude)+" "+Double.toString(latLng.longitude), Toast.LENGTH_SHORT).show();
                        CustomInfoAdapter customInfoWindow = new CustomInfoAdapter(getContext(),0,0,0,countryinfo);
                        googleMap.setInfoWindowAdapter(customInfoWindow)
                        ;
                        Marker marker = googleMap.addMarker(new MarkerOptions().snippet("this is "+t).title("this is marker"+ t).position(latLng));
                        t++;

                        marker.setTag(countryinfo);


                        marker.showInfoWindow();


                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));



                    }


                });
                placeIdTask.execute(urlstring,forecast);

            }
            });



        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                ItemLocation i = (ItemLocation) marker.getTag();
                CustomInfoAdapter customInfoWindow = new CustomInfoAdapter(context,0,0,0,i);
                googleMap.setInfoWindowAdapter(customInfoWindow) ;
                marker.showInfoWindow();


                Toast.makeText(getContext(),i.getName(),Toast.LENGTH_SHORT).show();



                return false;
            }
        });



    }}