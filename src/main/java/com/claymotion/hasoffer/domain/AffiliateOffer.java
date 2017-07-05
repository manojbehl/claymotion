package com.claymotion.hasoffer.domain;

public class AffiliateOffer {
	
	private int id;
	private String offer_id;
	private int affiliate_id;
	private String impression_pixel;
	private String click_url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
	public int getAffiliate_id() {
		return affiliate_id;
	}
	public void setAffiliate_id(int affiliate_id) {
		this.affiliate_id = affiliate_id;
	}
	public String getImpression_pixel() {
		return impression_pixel;
	}
	public void setImpression_pixel(String impression_pixel) {
		this.impression_pixel = impression_pixel;
	}
	public String getClick_url() {
		return click_url;
	}
	public void setClick_url(String click_url) {
		this.click_url = click_url;
	}
	

}
