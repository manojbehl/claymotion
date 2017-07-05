package com.claymotion.hasoffer.domain;

import java.util.ArrayList;
import java.util.List;

public class HasOfferData {

	private String name;
	private String description;
	private String category;
	private String advertiser;
	private String androidPackage;
	private String revenueType;
	private double revenueRate;
	private int campaignId;
	private double storeRating;
	private String country;
	private String currency;
	private String appUrl;
	private int id;
	private String offerId;
	private String status;
	private int doesLinkGenerate;
	private String developer;
	private String icon;
	private String version;
	private List<CreativeDomain> listOfCreativeDomains;
	private int affiliateId;
	private String clickUrl;
	private String title;
	private String subDescription;
	private Object appSize;
	private int priority;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAdvertiser() {
		return advertiser;
	}
	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}
	public String getAndroidPackage() {
		return androidPackage;
	}
	public void setAndroidPackage(String androidPackage) {
		this.androidPackage = androidPackage;
	}
	public String getRevenueType() {
		return revenueType;
	}
	public void setRevenueType(String revenueType) {
		this.revenueType = revenueType;
	}
	public double getRevenueRate() {
		return revenueRate;
	}
	public void setRevenueRate(double revenueRate) {
		this.revenueRate = revenueRate;
	}
	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	public double getStoreRating() {
		return storeRating;
	}
	public void setStoreRating(double storeRating) {
		this.storeRating = storeRating;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDoesLinkGenerate() {
		return doesLinkGenerate;
	}
	public void setDoesLinkGenerate(int doesLinkGenerate) {
		this.doesLinkGenerate = doesLinkGenerate;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<CreativeDomain> getListOfCreativeDomains() {
		if(listOfCreativeDomains == null)
			listOfCreativeDomains = new ArrayList<CreativeDomain>();
		return listOfCreativeDomains;
	}
	public void setListOfCreativeDomains(List<CreativeDomain> listOfCreativeDomains) {
		this.listOfCreativeDomains = listOfCreativeDomains;
	}
	public int getAffiliateId() {
		return affiliateId;
	}
	public void setAffiliateId(int affiliateId) {
		this.affiliateId = affiliateId;
	}
	public String getClickUrl() {
		return clickUrl;
	}
	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubDescription() {
		return subDescription;
	}
	public void setSubDescription(String subDescription) {
		this.subDescription = subDescription;
	}
	public Object getAppSize() {
		return appSize;
	}
	public void setAppSize(Object appSize) {
		this.appSize = appSize;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
