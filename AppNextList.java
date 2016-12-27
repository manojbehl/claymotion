package com.claymotion.advertiser;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class AppNextList {

	@JsonProperty("apps")
	List<AppNext> apps;

	public List<AppNext> getApps() {
		return apps;
	}

	public void setApps(List<AppNext> apps) {
		this.apps = apps;
	}

	


}
