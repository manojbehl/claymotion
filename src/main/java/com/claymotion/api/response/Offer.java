package com.claymotion.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.claymotion.advertiser.IronSource;
import com.claymotion.advertiser.IronSourceList;
import com.claymotion.hasoffer.domain.AffiseOfferList;
import com.claymotion.webservice.client.WebServiceClient;

public class Offer {

	private int id;
	private String offer_id;
	private String advertiser;
	private boolean hide_payments;
	private String title;
	private String micro_url;
	private String macro_url;
	private String url;
	private  String url_preview;
	private String preview_url;
	private String domain_url;
	private boolean use_https;
	private boolean use_http;
	
	private String description;
	
	private List<String> countries;
	private List sources;
	private String logo;
	private String status;
	
	private String freshness;
	private String privacy;
	
	private int is_top;
	
	private List payments;
	
	private List partner_payments;
	
	private double total_cap;
	
	private String total_cap_start_date;
	private int daily_cap;
	private int daily_cap_partner;
	
	private List daily_cap_partners;
	
	private List landings;
	
	private int strictly_country;

	private Object strictly_os;
	
	private List categories;
	
	private List full_categories;
	
	private float cr;
	
	private float epc;
	
	private String action_status_url;
	
	private String notes;
	
	private List allowed_ip;
	private String hash_password;
	
	private boolean allow_deeplink;
	
	private boolean hide_referer;
	
	private String start_at;
	
	private String stop_at;
	
	private int auto_offer_connect;
	
	private boolean required_approval;
	private boolean is_cpi;
	private Object creatives;
	
	private Object creatives_zip;
	
	private boolean send_emails;
	
	private boolean is_redirect_overcap;
	
	private int hold_period;
	
	private String packageName;
	
	private String click_session;
	
	private Object notice_percent_overcap;
	
	private String description_lang;
	
	private double revenueRate;
	private String currency;
	
	private String created_at;

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

	public String getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}

	public boolean isHide_payments() {
		return hide_payments;
	}

	public void setHide_payments(boolean hide_payments) {
		this.hide_payments = hide_payments;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMicro_url() {
		return micro_url;
	}

	public void setMicro_url(String micro_url) {
		this.micro_url = micro_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl_preview() {
		return url_preview;
	}

	public void setUrl_preview(String url_preview) {
		this.url_preview = url_preview;
	}

	public String getPreview_url() {
		return preview_url;
	}

	public void setPreview_url(String preview_url) {
		this.preview_url = preview_url;
	}

	public String getDomain_url() {
		return domain_url;
	}

	public void setDomain_url(String domain_url) {
		this.domain_url = domain_url;
	}

	public boolean isUse_https() {
		return use_https;
	}

	public void setUse_https(boolean use_https) {
		this.use_https = use_https;
	}

	public boolean isUse_http() {
		return use_http;
	}

	public void setUse_http(boolean use_http) {
		this.use_http = use_http;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getCountries() {
		if(countries == null)
			countries = new ArrayList<String>();
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	public List getSources() {
		if(sources == null)
			sources = new ArrayList();
		return sources;
	}

	public void setSources(List sources) {
		this.sources = sources;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFreshness() {
		return freshness;
	}

	public void setFreshness(String freshness) {
		this.freshness = freshness;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public int getIs_top() {
		return is_top;
	}

	public void setIs_top(int is_top) {
		this.is_top = is_top;
	}

	public List getPayments() {
		return payments;
	}

	public void setPayments(List payments) {
		this.payments = payments;
	}

	public List getPartner_payments() {
		return partner_payments;
	}

	public void setPartner_payments(List partner_payments) {
		this.partner_payments = partner_payments;
	}

	public double getTotal_cap() {
		return total_cap;
	}

	public void setTotal_cap(double total_cap) {
		this.total_cap = total_cap;
	}

	public String getTotal_cap_start_date() {
		return total_cap_start_date;
	}

	public void setTotal_cap_start_date(String total_cap_start_date) {
		this.total_cap_start_date = total_cap_start_date;
	}

	public int getDaily_cap() {
		return daily_cap;
	}

	public void setDaily_cap(int daily_cap) {
		this.daily_cap = daily_cap;
	}

	public int getDaily_cap_partner() {
		return daily_cap_partner;
	}

	public void setDaily_cap_partner(int daily_cap_partner) {
		this.daily_cap_partner = daily_cap_partner;
	}

	public List getDaily_cap_partners() {
		return daily_cap_partners;
	}

	public void setDaily_cap_partners(List daily_cap_partners) {
		this.daily_cap_partners = daily_cap_partners;
	}

	public List getLandings() {
		return landings;
	}

	public void setLandings(List landings) {
		this.landings = landings;
	}

	public int getStrictly_country() {
		return strictly_country;
	}

	public void setStrictly_country(int strictly_country) {
		this.strictly_country = strictly_country;
	}

	public Object getStrictly_os() {
		return strictly_os;
	}

	public void setStrictly_os(Object strictly_os) {
		this.strictly_os = strictly_os;
	}

	public List getCategories() {
		if(categories == null)
			categories = new ArrayList();
		return categories;
	}

	public void setCategories(List categories) {
		this.categories = categories;
	}

	public List getFull_categories() {
		return full_categories;
	}

	public void setFull_categories(List full_categories) {
		this.full_categories = full_categories;
	}

	public float getCr() {
		return cr;
	}

	public void setCr(float cr) {
		this.cr = cr;
	}

	public float getEpc() {
		return epc;
	}

	public void setEpc(float epc) {
		this.epc = epc;
	}

	public String getAction_status_url() {
		return action_status_url;
	}

	public void setAction_status_url(String action_status_url) {
		this.action_status_url = action_status_url;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List getAllowed_ip() {
		return allowed_ip;
	}

	public void setAllowed_ip(List allowed_ip) {
		this.allowed_ip = allowed_ip;
	}

	public String getHash_password() {
		return hash_password;
	}

	public void setHash_password(String hash_password) {
		this.hash_password = hash_password;
	}

	public boolean isAllow_deeplink() {
		return allow_deeplink;
	}

	public void setAllow_deeplink(boolean allow_deeplink) {
		this.allow_deeplink = allow_deeplink;
	}

	public boolean isHide_referer() {
		return hide_referer;
	}

	public void setHide_referer(boolean hide_referer) {
		this.hide_referer = hide_referer;
	}

	public String getStart_at() {
		return start_at;
	}

	public void setStart_at(String start_at) {
		this.start_at = start_at;
	}

	public String getStop_at() {
		return stop_at;
	}

	public void setStop_at(String stop_at) {
		this.stop_at = stop_at;
	}

	public int getAuto_offer_connect() {
		return auto_offer_connect;
	}

	public void setAuto_offer_connect(int auto_offer_connect) {
		this.auto_offer_connect = auto_offer_connect;
	}

	public boolean isRequired_approval() {
		return required_approval;
	}

	public void setRequired_approval(boolean required_approval) {
		this.required_approval = required_approval;
	}

	public boolean isIs_cpi() {
		return is_cpi;
	}

	public void setIs_cpi(boolean is_cpi) {
		this.is_cpi = is_cpi;
	}

	public Object getCreatives() {
		return creatives;
	}

	public void setCreatives(Object creatives) {
		this.creatives = creatives;
	}

	public Object getCreatives_zip() {
		return creatives_zip;
	}

	public void setCreatives_zip(Object creatives_zip) {
		this.creatives_zip = creatives_zip;
	}

	public boolean isSend_emails() {
		return send_emails;
	}

	public void setSend_emails(boolean send_emails) {
		this.send_emails = send_emails;
	}
	
	public static void main(String[] args) {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");
		
		 String URL ="http://api.dynamyn.affise.com/2.1/offers";

		 WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		 
		 try {
			AffiseOfferList affiseOfferList  = (AffiseOfferList)webServiceClient.executeGetMethod(URL, AffiseOfferList.class, hashMap);
			List<Offer> list = affiseOfferList.getOffers();
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Offer ironSource = (Offer) iterator.next();
				System.err.println(ironSource.getTitle());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMacro_url() {
		return macro_url;
	}

	public void setMacro_url(String macro_url) {
		this.macro_url = macro_url;
	}

	public boolean getIs_redirect_overcap() {
		return is_redirect_overcap;
	}

	public void setIs_redirect_overcap(boolean is_redirect_overcap) {
		this.is_redirect_overcap = is_redirect_overcap;
	}

	public int getHold_period() {
		return hold_period;
	}

	public void setHold_period(int hold_period) {
		this.hold_period = hold_period;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	
	public boolean equals(Object obj) {
    	if(obj instanceof  Offer){
    		return (this.getTitle().equals(((Offer)obj).getTitle()) &&
    				this.getPreview_url().equals( ((Offer)obj).getPreview_url()));
    	}
    	
    	return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = 3;
		hash = 7 * hash + this.getTitle().hashCode() + this.getPreview_url().hashCode();
//		hash = 7 * hash + this.getCompaignId().hashCode();
		return hash;
    }

	public String getClick_session() {
		return click_session;
	}

	public void setClick_session(String click_session) {
		this.click_session = click_session;
	}

	public Object getNotice_percent_overcap() {
		return notice_percent_overcap;
	}

	public void setNotice_percent_overcap(Object notice_percent_overcap) {
		this.notice_percent_overcap = notice_percent_overcap;
	}

	public String getDescription_lang() {
		return description_lang;
	}

	public void setDescription_lang(String description_lang) {
		this.description_lang = description_lang;
	}

	public double getRevenueRate() {
		return revenueRate;
	}

	public void setRevenueRate(double revenueRate) {
		this.revenueRate = revenueRate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
}
