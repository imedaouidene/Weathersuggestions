package com.mob.mse.weathersuggestions.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.data.Utils;
import com.mob.mse.weathersuggestions.data.converting_data;
import com.mob.mse.weathersuggestions.model.ItemCity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ItemCityAdapter extends ArrayAdapter<ItemCity> {
	public static int pos =0;
	private  ArrayList<ItemCity> itemDetailsrrayList;
	private Context context;
	private int viewRes;
	private Resources res;
	converting_data conv_data = new converting_data() ;
	public ItemCityAdapter(Context context, int resource, ArrayList<ItemCity> ItemCitys) {
		super(context, resource, ItemCitys);
		this.itemDetailsrrayList = ItemCitys ;
		this.viewRes= resource;
		this.context = context ;
		this.res = context.getResources() ;


	}
	Utils utils = new Utils(context) ;


	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public ItemCity getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater)
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.list_item_city,parent,false);
			holder = new ViewHolder();
			holder.tv_f_temp	= (TextView) convertView.findViewById(R.id.tv_f_temp);
			holder.tv_f_city	= (TextView) convertView.findViewById(R.id.tv_city_name);
			holder.tv_f_desc	= (TextView) convertView.findViewById(R.id.tv_f_desc);
			holder.img_f_icon	= (ImageView) convertView.findViewById(R.id.img_flag);
			holder.minmax = (TextView)convertView.findViewById(R.id.tv_f_maxmin) ;
			holder.icon = (ImageView)convertView.findViewById(R.id.img_f_icon) ;
			holder.linearLayout = (LinearLayout)convertView.findViewById(R.id.city_back) ;
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Log.e("ddd",itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().main.temp+0.0f+"°C");
		holder.tv_f_temp.setText(Double.toString(itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().main.temp+0.0f)+"°C");
		holder.tv_f_city.setText(itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().name);
		holder.tv_f_desc.setText(itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().weather.get(0).description);
		String min = Integer.toString((int) (itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().main.temp_min + 0.0f));
		String max = Integer.toString((int) (itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().main.temp_max + 0.0f));
		holder.minmax.setText(min + "°/" + max + "°" ) ;
				utils.setDrawableIcon(itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().weather.get(0).icon, holder.icon);

		try {

		//conv_data.setDrawableSmallIcon(itemDetailsrrayList.get(position).getIcon(), holder.img_f_icon);
		Picasso.with(getContext())
				.load(Utils.getFlagURL(itemDetailsrrayList.get(position).getItemLocation().getJsonWeather().sys.country.toLowerCase()))
				.into(holder.img_f_icon);
		}catch (Exception e ){
			Log.e("got u ", e.toString());
		}
		return convertView;
	}
	

	static class ViewHolder {
		TextView tv_f_temp;
		TextView tv_f_city;
		TextView tv_f_desc;
		ImageView img_f_icon;
		TextView minmax ;
		ImageView icon ;
		LinearLayout linearLayout ;
	}
	
}
