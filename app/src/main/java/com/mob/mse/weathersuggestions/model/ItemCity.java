package com.mob.mse.weathersuggestions.model;

public class ItemCity {

	private String city;
	private String temp;
	private String desc;
	private String icon;
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
	
	
	
}
