package com.claymotion.advertiser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.claymotion.webservice.client.WebServiceClient;

public class AppCreative {

	private String app_id;
	private String version;
	private String name;
	private String short_description;
	private double rating;
	private String developer;
	private String description;
	private String icon;
	private List<Creative> creatives;
	private Object appSize;
	private List<String> category;
	
	
	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Creative> getCreatives() {
		return creatives;
	}

	public void setCreatives(List<Creative> creatives) {
		this.creatives = creatives;
	}

	public static void main(String[] args) throws Exception {
		WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		Map<String ,String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");

		
		AppCreative appCreative = (AppCreative)webServiceClient.executeGetMethod("http://creatives.downloadapps.in/api/creative/com.flipkart.android",
				AppCreative.class, hashMap);
		
		System.err.println( appCreative.getCreatives().size());
		
	}

	public Object getAppSize() {
		return appSize;
	}

	public void setAppSize(Object appSize) {
		this.appSize = appSize;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}
}
