package com.mob.mse.weathersuggestions.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.mse.weathersuggestions.R;
import com.mob.mse.weathersuggestions.utils.Utils;
import com.mob.mse.weathersuggestions.model.ItemForecast;

import java.util.ArrayList;


public class ItemForecastAdapter extends ArrayAdapter<ItemForecast> {

	private  ArrayList<ItemForecast> itemDetailsrrayList;
	private Context context;
	private int viewRes;
	private Resources res;
	public ItemForecastAdapter( Context context, int resource,  ArrayList<ItemForecast> itemForecasts) {
		super(context, resource, itemForecasts);
		this.itemDetailsrrayList = itemForecasts ;
		this.viewRes= resource;
		this.context = context ;
		this.res = context.getResources() ;
		utils = new Utils(context) ;


	}

Utils utils ;
	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public ItemForecast getItem(int position) {
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
			convertView = layoutInflater.inflate(viewRes,parent,false);
			holder = new ViewHolder();
			holder.tv_f_temp	= (TextView) convertView.findViewById(R.id.tv_f_temp);
			holder.tv_f_day		= (TextView) convertView.findViewById(R.id.tv_f_day);
			holder.tv_f_desc	= (TextView) convertView.findViewById(R.id.tv_f_desc);
			holder.img_f_icon	= (ImageView) convertView.findViewById(R.id.img_f_icon);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_f_temp.setText(itemDetailsrrayList.get(position).getTemp());
		holder.tv_f_day.setText(itemDetailsrrayList.get(position).getDay());
		holder.tv_f_desc.setText(itemDetailsrrayList.get(position).getDesc());
		utils.setDrawableSmallIcon(itemDetailsrrayList.get(position).getIcon(), holder.img_f_icon);
		
		return convertView;
	}
	

	static class ViewHolder {
		TextView tv_f_temp;
		TextView tv_f_day;
		TextView tv_f_desc;
		ImageView img_f_icon;
	}
	
}
