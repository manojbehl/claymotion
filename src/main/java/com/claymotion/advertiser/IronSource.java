package com.claymotion.advertiser;

import java.util.ArrayList;
import java.util.List;

public class IronSource {
	
	private String version_code;
	
	private int offer_id;

	private int campaign_id;
	
	private int internal_offer_id;

	private int internal_campaign_id;
	
	private String bid;
	
	private String platform;
	
	private String minOSVersion;
	
	private String pricingModel;
	private String campaignType;
	
	private String completionAction;
	
	private String completionActionText;
	
	private boolean deviceIdRequired;
	private String packageSize;
	
	private String packageSizeInBytes;
	
	private String downloads;
	
	private String rating;
	
	private String category;
	
	private String connectionType;
	
	private String score;
	
	private String targetSdkVersion;
	
	private String packageName;
	
	private String title;
	
	private String description;
	
	private String clickURL;
	
	private String impressionURL;
	
	private boolean global;
	
	private boolean offMarket;
	
	private List<String> deviceTypes;
	
	private List<String> geoTargeting;
	
	private List<IronSourceCreative> creatives = new ArrayList<IronSourceCreative>();

	public String getVersion_code() {
		return version_code;
	}

	public void setVersion_code(String version_code) {
		this.version_code = version_code;
	}

	public int getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(int offer_id) {
		this.offer_id = offer_id;
	}

	public int getCampaign_id() {
		return campaign_id;
	}

	public void setCampaign_id(int campaign_id) {
		this.campaign_id = campaign_id;
	}

	public int getInternal_offer_id() {
		return internal_offer_id;
	}

	public void setInternal_offer_id(int internal_offer_id) {
		this.internal_offer_id = internal_offer_id;
	}

	public int getInternal_campaign_id() {
		return internal_campaign_id;
	}

	public void setInternal_campaign_id(int internal_campaign_id) {
		this.internal_campaign_id = internal_campaign_id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getMinOSVersion() {
		return minOSVersion;
	}

	public void setMinOSVersion(String minOSVersion) {
		this.minOSVersion = minOSVersion;
	}

	public String getPricingModel() {
		return pricingModel;
	}

	public void setPricingModel(String pricingModel) {
		this.pricingModel = pricingModel;
	}

	public String getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	public String getCompletionAction() {
		return completionAction;
	}

	public void setCompletionAction(String completionAction) {
		this.completionAction = completionAction;
	}

	public String getCompletionActionText() {
		return completionActionText;
	}

	public void setCompletionActionText(String completionActionText) {
		this.completionActionText = completionActionText;
	}

	public boolean isDeviceIdRequired() {
		return deviceIdRequired;
	}

	public void setDeviceIdRequired(boolean deviceIdRequired) {
		this.deviceIdRequired = deviceIdRequired;
	}

	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}

	public String getPackageSizeInBytes() {
		return packageSizeInBytes;
	}

	public void setPackageSizeInBytes(String packageSizeInBytes) {
		this.packageSizeInBytes = packageSizeInBytes;
	}

	public String getDownloads() {
		return downloads;
	}

	public void setDownloads(String downloads) {
		this.downloads = downloads;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTargetSdkVersion() {
		return targetSdkVersion;
	}

	public void setTargetSdkVersion(String targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClickURL() {
		return clickURL;
	}

	public void setClickURL(String clickURL) {
		this.clickURL = clickURL;
	}

	public String getImpressionURL() {
		return impressionURL;
	}

	public void setImpressionURL(String impressionURL) {
		this.impressionURL = impressionURL;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isOffMarket() {
		return offMarket;
	}

	public void setOffMarket(boolean offMarket) {
		this.offMarket = offMarket;
	}

	public List<String> getDeviceTypes() {
		return deviceTypes;
	}

	public void setDeviceTypes(List<String> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

	public List<String> getGeoTargeting() {
		return geoTargeting;
	}

	public void setGeoTargeting(List<String> geoTargeting) {
		this.geoTargeting = geoTargeting;
	}

	public List<IronSourceCreative> getCreatives() {
		return creatives;
	}

	public void setCreatives(List<IronSourceCreative> creatives) {
		this.creatives = creatives;
	}
	
	
}


