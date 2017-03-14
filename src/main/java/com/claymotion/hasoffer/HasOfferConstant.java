package com.claymotion.hasoffer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class HasOfferConstant {
	
	
	static Logger logger = Logger.getLogger(HasOfferUtility.class);
	
	
	public static String STATUS_PROCESSING= "Processing";
	public static String STATUS_PROCESSED= "Processed";
	


	public static String URL = "https://api.hasoffers.com/Apiv3/json";
	public static String NETWORK_TOKEN = "NETL9XoHDnv4ReiJAUpTLEt99VZaaH";
	public static String NETWORK_ID = "binjinn";
	public static String OFFER_TARGET_NAME ="offer";
	public static String CREATE_METHOD_NAME ="create";
	public static String CREATIVE_URL ="http://creatives.downloadapps.in/api/creative/";
	
	
	
	public static String ADVERTISER_ID = "advertiser_id";
	public static String CONVERSION_CAP = "conversion_cap";
	public static String DEFAULT_PAYOUT = "default_payout";
	public static String DESCRIPTION = "description";
	public static String EXPIRATION_DATE = "expiration_date";
	public static String IS_PRIVATE = "is_private";
	public static String IS_SEO_FRIENDLY_301 = "is_seo_friendly_301";
	public static String NAME = "name";
	public static String NOTE = "note";
	public static String OFFER_URL = "offer_url";
	public static String PREVIEW_URL = "preview_url";
	public static String REQUIRE_APPROVAL = "require_approval";
	public static String REQUIRE_TERMS_AND_CONDITIONS = "require_terms_and_conditions";
	public static String REVENUE_TYPE = "revenue_type";
	public static String MAX_PAYOUT = "max_payout";
	public static String STATUS = "status";
	
	
	public static final Integer CREATE_METHOD =1;
	public static final	Integer FIND_ALL_METHOD =2;
	public static final	Integer GENERATE_LINK_METHOD =3;
	public static final	Integer UPDATE_METHOD =4;
	public static final	Integer OFFER_PIXEL_METHOD =5;
	public static final	Integer PIXEL_ALLOWED_TYPE_METHOD =6;
	public static final	Integer FIND_ALL_PIXEL =7;
	public static final	Integer GENERATE_PIXEL_METHOD =8;
	public static final	Integer FIND_ALL_AFFILIATE_METHOD =9;
	
	
	
	
	
	
	
	
	
	private static String generateCommonURL(String target, String method){
		String hasOfferURL = URL + "?NetworkId=" + NETWORK_ID
				+ "&NetworkToken=" + NETWORK_TOKEN + "&Target=" + target
				+ "&Method=" + method ;

		return hasOfferURL;
	}

	public static String generateHasOfferCreateURL(Map<String, Object> map) {
		String hasOfferURL = generateCommonURL("offer", "create");
		if (map != null && map.size() > 0) {
			Iterator<String> keySetIterator = map.keySet().iterator();
			StringBuffer value = new StringBuffer();
			while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				if(map.get(key) instanceof String){
					String queryParameterValue = "";;
					try {
						queryParameterValue = URLEncoder.encode((String)map.get(key), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						queryParameterValue =  (String)map.get(key);
					}
					value.append("&data[" + key + "]=" + queryParameterValue);
				}else{
					value.append("&data[" + key + "]=" + map.get(key));
					
				}
							
			}
			hasOfferURL = hasOfferURL + value.toString();
		}

		return hasOfferURL;

	}
	
	
	public static String generateHasOfferUpdateURL(Map<String, Object> map, String id) {
		String hasOfferURL = generateCommonURL("offer", "update");
		logger.info("map object:"+ map.size());
		if (map != null && map.size() > 0) {
			Iterator<String> keySetIterator = map.keySet().iterator();
			StringBuffer value = new StringBuffer();
			logger.info(" value is :"+ value);
			while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				System.err.println(" key is :"+ key);
				if(map.get(key) instanceof String){
					String queryParameterValue = "";;
					try {
						queryParameterValue = URLEncoder.encode((String)map.get(key), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						queryParameterValue =  (String)map.get(key);
					}
					value.append("&data[" + key + "]=" + queryParameterValue);
				}else{
					value.append("&data[" + key + "]=" + map.get(key));
					
				}
			}
			hasOfferURL = hasOfferURL + value.toString() + "&id="+id;
		}

		return hasOfferURL;

	}
	
	public static String generateHasOfferFindAllURL(Map<String, Object> map) {
		String hasOfferURL = generateCommonURL("offer", "findAll");
		if (map != null && map.size() > 0) {
			Iterator<String> keySetIterator = map.keySet().iterator();
			StringBuffer value = new StringBuffer();
			while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				
				if(map.get(key) instanceof String){
					String queryParameterValue = "";;
					try {
						queryParameterValue = URLEncoder.encode((String)map.get(key), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						queryParameterValue =  (String)map.get(key);
					}
					value.append("&filters[" + key + "]=" + queryParameterValue);
				}else{
					value.append("&filters[" + key + "]=" + map.get(key));
					
				}
			}
			hasOfferURL = hasOfferURL + value.toString();
		}

		return hasOfferURL;

	}
	
	public static String generateHasOfferLinkURL(String offerId, String affiliateId) {
		String hasOfferURL = generateCommonURL("offer", "generateTrackingLink");
		
		hasOfferURL += hasOfferURL + "&offer_id="+offerId+"&affiliate_id="+affiliateId;

		return hasOfferURL;
	}

	
	public static String generateHasOfferPixelURL(String offerId) {
		String hasOfferURL = generateCommonURL("offer", "generateTrackingPixel");
		
		hasOfferURL += hasOfferURL + "&offer_id="+offerId;

		return hasOfferURL;
	}
	
	public static String generatefindAllIdsByAffiliateIdURL(String affiliateId) {
		String hasOfferURL = generateCommonURL("offer", "findAllIdsByAffiliateId");
		
		hasOfferURL += hasOfferURL + "&affiliate_id="+affiliateId;

		return hasOfferURL;
	}
	
	
	
	

	
	public static String generateHasOfferPixelURL(Map<String, Object> map) {
		String hasOfferURL = generateCommonURL("OfferPixel", "create");
		
		if (map != null && map.size() > 0) {
			Iterator<String> keySetIterator = map.keySet().iterator();
			StringBuffer value = new StringBuffer();
			while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				if(map.get(key) instanceof String){
					String queryParameterValue = "";;
					try {
						queryParameterValue = URLEncoder.encode((String)map.get(key), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						queryParameterValue =  (String)map.get(key);
					}
					value.append("&data[" + key + "]=" + queryParameterValue);
				}else{
					value.append("&data[" + key + "]=" + map.get(key));
					
				}
			}
			hasOfferURL = hasOfferURL + value.toString();
		}

		return hasOfferURL;
	}
	

	public static String generateHasOfferFindAllPixel() {
		String hasOfferURL = generateCommonURL("OfferPixel", "findAll");
		
//		hasOfferURL += hasOfferURL + "&offer_id="+offerId;

		return hasOfferURL;
	}

	
	public static String generateHasOfferPixelAllowedTypes(String offerId) {
		String hasOfferURL = generateCommonURL("OfferPixel", "getAllowedTypes");
		
		hasOfferURL += hasOfferURL + "&offer_id="+offerId;

		return hasOfferURL;
	}
	
}