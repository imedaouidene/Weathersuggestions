package com.mob.mse.weathersuggestions.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.mob.mse.weathersuggestions.JSON.Autocomplete;

import java.util.ArrayList;

public class GooglePlacesACAdapter extends ArrayAdapter implements Filterable {
 private ArrayList<String> resultList;


    public GooglePlacesACAdapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);

    }



    @Override

    public int getCount() {

        return resultList.size();
    }



    @Override

    public String getItem(int index) {

        return resultList.get(index);

    }

    @Override

    public Filter getFilter() {

        Filter filter = new Filter() {
            FilterResults filterResults ;

            @Override

            protected FilterResults performFiltering(CharSequence constraint) {



                if (constraint != null) {

                    // Retrieve the autocomplete results.


                    Autocomplete.placeIdTask autocomplete = new Autocomplete.placeIdTask(new Autocomplete.AsyncResponse() {
                        @Override
                        public void processFinish(ArrayList<String> Autocompletelist) {
                            if (resultList!=null){
                            resultList.clear();
                            }
                            resultList = Autocompletelist ;
                            filterResults = new FilterResults();
                            filterResults.values = Autocompletelist;

                            filterResults.count = Autocompletelist.size();

                        }
                    });
//                    resultList = autocomplete(constraint.toString());

                    autocomplete.execute(constraint.toString()) ;

                    // Assign the data to the FilterResults



                }

                return filterResults ;

            }



            @Override

            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null && results.count > 0) {

                    notifyDataSetChanged();

                } else {

                    notifyDataSetInvalidated();

                }

            }

        };

        return filter;

    }

}
