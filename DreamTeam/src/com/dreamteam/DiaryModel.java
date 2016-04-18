package com.dreamteam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DiaryModel {
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name = null;
	private String title = null;
	private String contenturl = null;
	private String picturl = null;
	private String time = null;
	private String weather = null;
	private String mood = null;

	private boolean successRex = false;


	public boolean isSuccessRex() {
		return successRex;
	}

	public void setSuccessRex(boolean successRex) {
		this.successRex = successRex;
	}

	public DiaryModel() {
	}

	public DiaryModel(String name, String title, String contenturl,
			String picturl, String time, String weather, String mood) {
		this.setName(name);
		this.setTitle(title);
		this.setContenturl(contenturl);
		this.setPicturl(picturl);
		this.setTime(time);
		this.setWeather(weather);
		this.setMood(mood);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContenturl() {
		return contenturl;
	}

	public void setContenturl(String contenturl) {
		this.contenturl = contenturl;
	}

	public String getPicturl() {
		return picturl;
	}

	public void setPicturl(String picturl) {
		this.picturl = picturl;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

}
