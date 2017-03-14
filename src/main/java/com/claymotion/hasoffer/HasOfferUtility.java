package com.claymotion.hasoffer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.net.URLCodec;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import com.claymotion.advertiser.batch.GlispaExecution;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.hasoffer.domain.Offer;
import com.claymotion.util.Utility;
import com.claymotion.webservice.client.HttpClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HasOfferUtility {

	Logger logger = Logger.getLogger(HasOfferUtility.class);

	
	/**
	 * 
	 * @param target
	 * @param method
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public JSONObject executeHasOfferAPI(int hasOfferAPI, Object data) throws Exception {
		
		String url ="";
		switch (hasOfferAPI) {
		case 1:
			url = HasOfferConstant.generateHasOfferCreateURL((Map<String, Object>)data);
			break;
		case 2:
			url = HasOfferConstant.generateHasOfferFindAllURL((Map<String, Object>)data);
			break;
		case 3:
			Object[] objetArray = (Object[])data;
			url = HasOfferConstant.generateHasOfferLinkURL((String)objetArray[0], (String)objetArray[1]);
			break;
		case 4:
			Object[] objectArray = (Object[])data;
			logger.info("map is "+ ((Map<String, Object>)objectArray[0]).size());
			url = HasOfferConstant.generateHasOfferUpdateURL((Map<String, Object>)objectArray[0], (String)objectArray[1]);
			break;
		case 5:
			url = HasOfferConstant.generateHasOfferPixelURL((Map<String, Object>)data);
			break;
		case 6:
			url = HasOfferConstant.generateHasOfferPixelAllowedTypes((String)data);
			break;
		case 7:
			url = HasOfferConstant.generateHasOfferFindAllPixel();
			break;
		case 8:
			url = HasOfferConstant.generateHasOfferPixelURL((String)data);
			break;
		case 9:
			url = HasOfferConstant.generatefindAllIdsByAffiliateIdURL((String)data);
			break;
		
		default:
			break;
		}


		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");

		
//		url = formatURL(url);
		
		logger.info("url to execute :"+url);
		
		JSONObject jsonObject =  (JSONObject)HttpClientService.getSharedInstance().executeGetMethod(url);
//		Object str =  (Object)WebServiceClient.getSharedInstance().executeGetMethod(url, Object.class, hashMap);
//		System.err.println(str.toString());
		return jsonObject;
	}
	
	public String formatURL(String url) throws UnsupportedEncodingException{
		
		String pathToEncode = url.substring(url.indexOf("?")+1);
		
		url = url.substring(0,url.indexOf("?")+1);
		
		pathToEncode = URLEncoder.encode(pathToEncode, "UTF-8");
		
//		pathToEncode  = UriUtils.encodePath(pathToEncode, "UTF-8");
		
	
//		url = URLEncoder.encode(url,"UTF-8");
		pathToEncode = pathToEncode.replace(" ", "+");
		
		url = url + pathToEncode;
		
		return url;
		
	}
	


	/**
	 * 
	 * @param appNext
	 * @return
	 */
	public Map<String, Object>  populateOfferCreationFields(AdvertiserRawData appNext){
		
		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		
		String time = simpleDateFormat.format(calendar.getTime());
		
		
		Map<String, Object> hashMap = new HashMap<String, Object>();
		
		// TODO - Need to use App Next Advertiser id
		hashMap.put(HasOfferConstant.ADVERTISER_ID, Utility.getAdvertiserId("API Demo ADdv1"));
		
		hashMap.put(HasOfferConstant.CONVERSION_CAP, 0);
		
		hashMap.put(HasOfferConstant.DEFAULT_PAYOUT, appNext.getRevenueRate() * 60/100);
		
		if(appNext.getDescription() != null)
			
			hashMap.put(HasOfferConstant.DESCRIPTION, appNext.getDescription());
		
		
		
		hashMap.put(HasOfferConstant.EXPIRATION_DATE, time);
		
		hashMap.put(HasOfferConstant.IS_PRIVATE, 0);
		hashMap.put(HasOfferConstant.IS_SEO_FRIENDLY_301, 0);
		
		hashMap.put(HasOfferConstant.NAME, appNext.getName() + "_" + appNext.getCountry() + "_android_nonincent");
		hashMap.put(HasOfferConstant.NOTE, appNext.getAppUrl());
		hashMap.put(HasOfferConstant.OFFER_URL, "https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D");
//		https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D
		hashMap.put(HasOfferConstant.PREVIEW_URL, "https://play.google.com/store/apps/details?id="+ appNext.getAndroidPackage());
		hashMap.put(HasOfferConstant.REQUIRE_APPROVAL, 0);
		hashMap.put(HasOfferConstant.REQUIRE_TERMS_AND_CONDITIONS, 0);
//		hashMap.put("revenue_type", "cpc");
		hashMap.put(HasOfferConstant.REVENUE_TYPE, "cpa_flat");
		hashMap.put(HasOfferConstant.MAX_PAYOUT, appNext.getRevenueRate());
		hashMap.put(HasOfferConstant.STATUS, "active");

		return hashMap;
	}

	public Map<String, Object>  populateOfferCreationFields(Offer offerData){
		
		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		
		String time = simpleDateFormat.format(calendar.getTime());
		
		
		Map<String, Object> hashMap = new HashMap<String, Object>();
		
		// TODO - Need to use App Next Advertiser id
		hashMap.put(HasOfferConstant.ADVERTISER_ID, offerData.getAdvertiser_id());
		
		hashMap.put(HasOfferConstant.CONVERSION_CAP, 0);
		
		hashMap.put(HasOfferConstant.DEFAULT_PAYOUT, offerData.getDefault_payout());
		
			
			hashMap.put(HasOfferConstant.DESCRIPTION, offerData.getDescription());
		
		
		
		hashMap.put(HasOfferConstant.EXPIRATION_DATE, offerData.getExpiration_date());
		
		hashMap.put(HasOfferConstant.IS_PRIVATE, 0);
		hashMap.put(HasOfferConstant.IS_SEO_FRIENDLY_301, 0);
		System.err.println(" offerData.getName() :"+ offerData.getName());
		hashMap.put(HasOfferConstant.NAME,offerData.getName());
		hashMap.put(HasOfferConstant.NOTE, offerData.getNote());
		hashMap.put(HasOfferConstant.OFFER_URL, offerData.getOffer_url());
//		https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D
		hashMap.put(HasOfferConstant.PREVIEW_URL, offerData.getPreview_url());
		hashMap.put(HasOfferConstant.REQUIRE_APPROVAL, 0);
		hashMap.put(HasOfferConstant.REQUIRE_TERMS_AND_CONDITIONS, 0);
//		hashMap.put("revenue_type", "cpc");
		hashMap.put(HasOfferConstant.REVENUE_TYPE, "cpa_flat");
		hashMap.put(HasOfferConstant.MAX_PAYOUT, offerData.getMax_payout());
		hashMap.put(HasOfferConstant.STATUS, "active");

		return hashMap;
	}
	
	public List<Offer> getAllOfferList(Map<String, Object> hashMap) throws Exception{
		
		
		JSONObject jsonObject = executeHasOfferAPI(HasOfferConstant.FIND_ALL_METHOD, hashMap);
		List<Offer> listOfOffers = new ArrayList<Offer>();
		JSONObject dataJsonObject = null;
		
//		return dataJsonObject.toString();
		
		try{
			dataJsonObject = jsonObject.getJSONObject("response").getJSONObject("data");
		}catch(Exception ex){
//			if(ex.getMessage().contains("is not a JSONArray")){
				listOfOffers = new ArrayList<Offer>();
				return listOfOffers;
//			}
		}
		
		
		Iterator<String> iterator = dataJsonObject.keys();
		JSONObject offerDataObject = null;
		while(iterator.hasNext()){
			String key = iterator.next();
			if(key.equalsIgnoreCase("errorMessage") || key.equalsIgnoreCase("errors")) continue;
			
			offerDataObject = dataJsonObject.getJSONObject(key).getJSONObject("Offer");
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			Offer offer = objectMapper.readValue(offerDataObject.toString(), Offer.class);
			 
			listOfOffers.add(offer);
		}
		return listOfOffers;
		
	}
	
	public List<Offer> getListOfAppToAdd(List<Offer> appFromAPI, List<Offer> appFromHasoffer){
		List<Offer> appsToAdd = new ArrayList<Offer>();
		logger.info("APPS from Advertiser");
		for (Iterator iterator = appFromAPI.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			logger.info(offer.getName() + "   " + offer.getPreview_url());
			logger.info("----------------------------------------------------");
		}

		logger.info("APPS Alreday Present");
		for (Iterator iterator = appFromHasoffer.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			logger.info(offer.getName() + "   " + offer.getPreview_url());
			logger.info("----------------------------------------------------");
		}

		for (Iterator iterator = appFromAPI.iterator(); iterator.hasNext();) {
			Offer appValue = (Offer) iterator.next();
			if(!appFromHasoffer.contains(appValue)){
				appsToAdd.add(appValue);
			}
			
/*				System.err.println(appValue.getAndroidPackage());
			System.err.println(apps238.getApps().contains(appValue));
*/				
		}
		
		return appsToAdd;
	}
	
	public List<Offer> getListOfAppToPause(List<Offer> appFromAPI, List<Offer> appFromHasOffer){
		List<Offer> appsToAdd = new ArrayList<Offer>();
		for (Iterator iterator = appFromHasOffer.iterator(); iterator.hasNext();) {
			Offer appValue = (Offer) iterator.next();
			
			if(!appFromAPI.contains(appValue)){
				logger.info(" Offer Status "+ appValue.getStatus());
//				if(!appValue.getStatus().equalsIgnoreCase("Paused"))
						uploadCreatives(appValue, appFromAPI);
						appsToAdd.add(appValue);
			}
			
/*				System.err.println(appValue.getAndroidPackage());
			System.err.println(apps238.getApps().contains(appValue));
*/				
		}	
		
		return appsToAdd;
	}
	
	private void uploadCreatives(Offer originalOffer, List<Offer> listOfApis){

		for (Iterator iterator = listOfApis.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			if(offer.getName().equals(originalOffer.getName()) && offer.getPreview_url().equalsIgnoreCase(originalOffer.getPreview_url())){
				logger.info(" upload creative offer creative :"+ offer.getCreatives());
				originalOffer.setImages(offer.getImages());
				originalOffer.setIcon(offer.getIcon());
				originalOffer.setCategory(offer.getCategory());
				originalOffer.setDefault_payout(offer.getDefault_payout());
				originalOffer.setOffer_url(offer.getOffer_url());
				break;
//				return offer.getCreatives();
			}
		}
		
	}
	
	public List<Offer> getListOfAppToUpdate(List<Offer> appFromAPI, List<Offer> appFromHasOffer){
		List<Offer> appsToUpdate = new ArrayList<Offer>();
		for (Iterator iterator = appFromHasOffer.iterator(); iterator.hasNext();) {
			Offer appValue = (Offer) iterator.next();
			
			if(appFromAPI.contains(appValue)){
//				if(appValue.getStatus().equalsIgnoreCase("Paused"))
				uploadCreatives(appValue, appFromAPI);

					appsToUpdate.add(appValue);
			}
			
/*				System.err.println(appValue.getAndroidPackage());
			System.err.println(apps238.getApps().contains(appValue));
*/				
		}	
		
		return appsToUpdate;
	}

	
	
}
