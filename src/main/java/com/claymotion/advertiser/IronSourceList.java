package com.claymotion.advertiser;

import java.util.List;

public class IronSourceList {

	private boolean error;
	
	private String errorMessage;
	
	private List<IronSource> ads;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	
	public List<IronSource> getAds() {
		return ads;
	}

	public void setAds(List<IronSource> ads) {
		this.ads = ads;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
