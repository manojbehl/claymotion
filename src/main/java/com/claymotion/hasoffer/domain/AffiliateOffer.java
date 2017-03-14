package com.claymotion.hasoffer.domain;

public class AffiliateOffer {
	
	private int id;
	private int offer_id;
	private String affiliate_id;
	private String impression_pixel;
	private String click_url;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(int offer_id) {
		this.offer_id = offer_id;
	}
	public String getAffiliate_id() {
		return affiliate_id;
	}
	public void setAffiliate_id(String affiliate_id) {
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
