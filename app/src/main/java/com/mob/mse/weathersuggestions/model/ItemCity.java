package com.mob.mse.weathersuggestions.model;

import android.support.annotation.NonNull;

public class ItemCity implements Comparable<ItemCity> {

	private String city, country;
	private String temp,max,min;
	private String desc;
	private String icon;

	private ItemLocation itemLocation ;

	private String image ;
	private String infos ;

	public ItemCity(String city, String country, String temp, String max, String min, String desc, String icon, ItemLocation itemLocation) {
		this.city = city;
		this.country = country;
		this.temp = temp;
		this.max = max;
		this.min = min;
		this.desc = desc;
		this.icon = icon;
		this.itemLocation = itemLocation;
		this.image = "";
		this.infos = "";
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public ItemLocation getItemLocation() {
		return itemLocation;
	}

	public void setItemLocation(ItemLocation itemLocation) {
		this.itemLocation = itemLocation;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getInfos() {
		return infos;
	}

	public void setInfos(String infos) {
		this.infos = infos;
	}

	public ItemCity() {

		super();
		// TODO Auto-generated constructor stub
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ItemCity(String city, String temp, String desc, String icon) {

		this.city = city;
		this.temp = temp;
		this.desc = desc;
		this.icon = icon;
	}



    public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}




    @Override
		public int compareTo(@NonNull ItemCity itemCity) {

			double temp = itemCity.getItemLocation().getJsonWeather().main.temp ;
			return (int) (temp-this.getItemLocation().getJsonWeather().main.temp);

		}
	}

