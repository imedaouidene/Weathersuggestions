package com.mob.mse.weathersuggestions.model;

public class ItemLocation {
	private String id, name, code; //city attribute
	private WeatherResponse jsonWeather;
	private ForecastResponse jsonForecast;
	public ItemLocation() {
		super();
	}
	public ItemLocation(String id, String name, String code,
                        WeatherResponse jsonWeather, ForecastResponse jsonForecast) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.jsonWeather = jsonWeather;
		this.jsonForecast = jsonForecast;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public WeatherResponse getJsonWeather() {
		return jsonWeather;
	}
	public void setJsonWeather(WeatherResponse jsonWeather) {
		this.jsonWeather = jsonWeather;
	}
	public ForecastResponse getJsonForecast() {return jsonForecast;}
	public void setJsonForecast(ForecastResponse jsonForecast) {
		this.jsonForecast = jsonForecast;
	}
	
}
