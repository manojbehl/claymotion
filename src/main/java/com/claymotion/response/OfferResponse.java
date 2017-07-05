package com.claymotion.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class OfferResponse {

		private String title;
		private String name;
		private String category;
		private String country;
		private String subTitle;
		private String version;
		private float rating;
		private String packageName;
		private String clickUrl;
		private String developerName;
		private String description;
		private String appIcon;
		private List<CreativeResponse> appScreenShots;
		private Object appSize;
		private double reward;
		private int affiliateId;
		private int id;
		private String offerId;
		
		private String actionForReward;
		private int priority;
		
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getSubTitle() {
			return subTitle;
		}
		public void setSubTitle(String subTitle) {
			this.subTitle = subTitle;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public float getRating() {
			return rating;
		}
		public void setRating(float rating) {
			this.rating = rating;
		}
		
		public String getClickUrl() {
			return clickUrl;
		}
		public void setClickUrl(String clickUrl) {
			this.clickUrl = clickUrl;
		}
		public String getDeveloperName() {
			return developerName;
		}
		public void setDeveloperName(String developerName) {
			this.developerName = developerName;
		}
		
		public Object getAppSize() {
			return appSize;
		}
		public void setAppSize(Object appSize) {
			this.appSize = appSize;
		}
		public double getReward() {
			return reward;
		}
		public void setReward(double reward) {
			this.reward = reward;
		}
		public String getActionForReward() {
			return actionForReward;
		}
		public void setActionForReward(String actionForReward) {
			this.actionForReward = actionForReward;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getPackageName() {
			return packageName;
		}
		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getAppIcon() {
			return appIcon;
		}
		public void setAppIcon(String appIcon) {
			this.appIcon = appIcon;
		}
		public List<CreativeResponse> getAppScreenShots() {
			return appScreenShots;
		}
		public void setAppScreenShots(List<CreativeResponse> appScreenShots) {
			this.appScreenShots = appScreenShots;
		}
		public int getAffiliateId() {
			return affiliateId;
		}
		public void setAffiliateId(int affiliateId) {
			this.affiliateId = affiliateId;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
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
		
		
		
		
}
