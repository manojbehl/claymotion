package com.claymotion.hasoffer.domain;

public class AdvertiserCreative {
	
	private int id;
	private int offerId;
	private String key;
	private String value;
	private String androidPackage;
	private String countryCode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAndroidPackage() {
		return androidPackage;
	}
	public void setAndroidPackage(String androidPackage) {
		this.androidPackage = androidPackage;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
