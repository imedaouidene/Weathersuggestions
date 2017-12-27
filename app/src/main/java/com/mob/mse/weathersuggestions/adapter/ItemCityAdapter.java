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
import com.mob.mse.weathersuggestions.data.converting_data;
import com.mob.mse.weathersuggestions.model.ItemCity;

import java.util.ArrayList;


public class ItemCityAdapter extends ArrayAdapter<ItemCity> {

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
			convertView = layoutInflater.inflate(viewRes,parent,false);
			holder = new ViewHolder();
			holder.tv_f_temp	= (TextView) convertView.findViewById(R.id.tv_f_temp);
			holder.tv_f_city		= (TextView) convertView.findViewById(R.id.tv_city);
			holder.tv_f_desc	= (TextView) convertView.findViewById(R.id.tv_f_desc);
			holder.img_f_icon	= (ImageView) convertView.findViewById(R.id.img_f_icon);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_f_temp.setText(itemDetailsrrayList.get(position).getTemp());
		holder.tv_f_city.setText(itemDetailsrrayList.get(position).getCity());
		holder.tv_f_desc.setText(itemDetailsrrayList.get(position).getDesc());
		conv_data.setDrawableSmallIcon(itemDetailsrrayList.get(position).getIcon(), holder.img_f_icon);
		
		return convertView;
	}
	

	static class ViewHolder {
		TextView tv_f_temp;
		TextView tv_f_city;
		TextView tv_f_desc;
		ImageView img_f_icon;
	}
	
}
