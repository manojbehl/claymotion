package com.claymotion.advertiser.batch;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.claymotion.advertiser.AppNext;
import com.claymotion.advertiser.AppNextList;
import com.claymotion.dao.ClayMotionDAO;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.HasOfferUtility;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.hasoffer.domain.Offer;
import com.claymotion.transformer.DataTransformer;
import com.claymotion.webservice.client.WebServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AppNextExecution {

	
	private static String URL = "https://admin.appnext.com/offerWallApi.aspx?id=5a5c5ce6-d955-4a9c-8fa2-27c04fc68e13&cnt=200&type=json&ip=";

	private static int APP_NEXT_ID = 297;
	
	WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();

	@Autowired
	HasOfferUtility hasOfferUtility;
	
	@Autowired
	DataTransformer dataTransformer;
	
	@Autowired
	IClayMotionDAO clayMotionDAO;
	
	Logger logger = Logger.getLogger(AppNextExecution.class);
	
	//@Scheduled(cron="0 0/15 * * * ?")
	public void peformAction() throws Exception{
		actionPerformed();
	}
	
	
	public String actionPerformed() throws Exception {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");

		// TODO Auto-generated method stub
		Map<String, String> mapForCountry = getIPAddress();
		Set<String> listOfIPAddress = mapForCountry.keySet();
		JSONObject responseObject =  null;
		
		
		Map<String, Object> hashMapForAdvertiser = new HashMap<String, Object>();
		hashMapForAdvertiser.put("advertiser_id",""+APP_NEXT_ID);

		logger.info("Fetching all Offer for  App Next Advertiser ");
		List<Offer>  listOfOffers = hasOfferUtility.getAllOfferList(hashMapForAdvertiser);
		logger.info(" total Offer Present :"+ listOfOffers.size());
		
		for (Iterator iterator = listOfIPAddress.iterator(); iterator.hasNext();) {
			
			String countryCode = (String) iterator.next();
			String ipAddress = mapForCountry.get(countryCode);
			String url = URL + ipAddress;
			
			logger.info("URL to Execute :"+ url  + "  For Country :"+ countryCode + " with IPAddress :"+ ipAddress);
			
			try{
				
				AppNextList appNextList = (AppNextList) webServiceClient.executeGetMethod(url, AppNextList.class, hashMap);

				List<AppNext> listOfApps = appNextList.getApps();
				
				logger.info("Converting Data in offer data");
				
				List<Offer> listOfRawData = dataTransformer.convertIntoOfferData(listOfApps, APP_NEXT_ID);
				
				logger.info(">>>>>>>>>>>>>>>>>>> Offer Converted >>>>>>>>>>>>>>");
				
				List<Offer> filteredListBasedOnCountry  = filteredListBasedOnCountry(listOfOffers, countryCode);
				
				logger.info(" Filtered offer based on country ");

//				List<AdvertiserRawData> listOfDataFromDB = clayMotionDAO.getListOfApp(APP_NEXT_ID, countryCode);
				
				
				List<Offer> appsToAdd = hasOfferUtility.getListOfAppToAdd(listOfRawData, filteredListBasedOnCountry);
				
				List<Offer> appsToPause = hasOfferUtility.getListOfAppToPause(listOfRawData, filteredListBasedOnCountry);
				
				
				List<Offer> appsToUpdate = hasOfferUtility.getListOfAppToUpdate(listOfRawData, filteredListBasedOnCountry);

				
				
				for (Iterator iterator2 = appsToPause.iterator(); iterator2.hasNext();) {
					
					Offer offer = (Offer) iterator2.next();
					offer.setStatus("Paused");
					
					Map<String, Object> updateHashMap = new HashMap<String, Object>();
					updateHashMap.put("status", "paused");
					
					Object[] objArray = new Object[]{updateHashMap, ""+offer.getId()};
					
					try{
						JSONObject jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.UPDATE_METHOD, objArray);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Update :"+ ex.getMessage());
					}
					
					
					try{
						logger.info("offer id :"+ offer.getId());
						boolean doesCreativeExisst= clayMotionDAO.doesCreativeExists(offer.getId());
						logger.info(" value for if creative exists :"+ doesCreativeExisst);
						if(!clayMotionDAO.doesCreativeExists(offer.getId()))
							processCreatives(offer, countryCode);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for setting Advertiser Creative :"+ ex.getMessage(), ex);
						
					}
					
					
				}
				
				for (Iterator iterator2 = appsToUpdate.iterator(); iterator2.hasNext();) {
					
					Offer offer = (Offer) iterator2.next();
					offer.setStatus("active");
					
					Map<String, Object> updateHashMap = new HashMap<String, Object>();
					updateHashMap.put("status", "active");
					updateHashMap.put("offer_url", offer.getOffer_url());

					
					Object[] objArray = new Object[]{updateHashMap, ""+offer.getId()};
				
					try{
						JSONObject jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.UPDATE_METHOD, objArray);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Update :"+ ex.getMessage(), ex);
					}
					
					
					try{
						if(!clayMotionDAO.doesCreativeExists(offer.getId()))
							processCreatives(offer, countryCode);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for setting Advertiser Creative :"+ ex.getMessage(), ex);
						
					}
					
					
				}
				
				for (Iterator iterator2 = appsToAdd.iterator(); iterator2.hasNext();) {
					
					
					Offer offer = (Offer) iterator2.next();

					offer.setStatus("active");
					
//					clayMotionDAO.addRawData(advertiserRawData);

					
					JSONObject jsonObject = null;
					try{
						Map<String, Object> fieldMap = hasOfferUtility.populateOfferCreationFields(offer);
						jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.CREATE_METHOD, fieldMap);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Create :"+ ex.getMessage());
						
					}
					
					try{
						processCreatives(offer, countryCode);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for setting Advertiser Creative :"+ ex.getMessage(), ex);
						
					}
					responseObject = jsonObject;
					
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(" Error while exexuting :"+ url  + "  For Country :"+ countryCode + " with IPAddress :"+ ipAddress);
			}
			

			
		}
		
		
		return "Process Complete";
	}
	
	
	
	private List<Offer> filteredListBasedOnCountry(List<Offer> totalList, String country){
		List<Offer> filteredList = new ArrayList<Offer>();
		
		for (Iterator iterator = totalList.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			if(offer.getName().contains(country))
				filteredList.add(offer);
		}
		
		return filteredList;
	}
	
	private boolean checkIfOfferAlredayExists(AppNext appNext) throws Exception{
	
		String offerName = appNext.getTitle() + "_android_nonincent_"+ appNext.getCountry();
		String advetiserId = "" + APP_NEXT_ID;
		
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("name", offerName);
		hashMap.put("advertiser_id",advetiserId);

		JSONObject jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.FIND_ALL_METHOD, hashMap);
		
		
		return false;
	}

	private Map<String, String> getIPAddress() {
		
		Map<String, String> mapForCountry = new HashMap<String, String>();
		
		mapForCountry.put("IN", "59.91.219.238");
		mapForCountry.put("UA", "5.255.160.0");
		mapForCountry.put("RU", "5.144.64.0");
		mapForCountry.put("HK", "14.102.240.0");
		mapForCountry.put("SG", "39.109.128.0");
		mapForCountry.put("PH", "49.157.0.0");
		mapForCountry.put("TH", "49.48.0.0");
		mapForCountry.put("MY", "42.188.0.0");
		mapForCountry.put("ID", "110.232.64.0");
		mapForCountry.put("VN", "61.11.224.0");

		return mapForCountry;
	}
	
	
	
	
	/*private List<AdvertiserRawData> getListOfAppToAdd(List<Offer> appFromAPI, List<AdvertiserRawData> appFromDB){
		List<AdvertiserRawData> appsToAdd = new ArrayList<AdvertiserRawData>();
		for (Iterator iterator = appFromAPI.iterator(); iterator.hasNext();) {
			AdvertiserRawData appValue = (AdvertiserRawData) iterator.next();
			
			if(!appFromDB.contains(appValue)){
				appsToAdd.add(appValue);
			}
			
				System.err.println(appValue.getAndroidPackage());
			System.err.println(apps238.getApps().contains(appValue));
				
		}
		
		return appsToAdd;
	}
	
	private List<AdvertiserRawData> getListOfAppToPause(List<AdvertiserRawData> appFromAPI, List<AdvertiserRawData> appFromDB){
		List<AdvertiserRawData> appsToAdd = new ArrayList<AdvertiserRawData>();
		for (Iterator iterator = appFromDB.iterator(); iterator.hasNext();) {
			AdvertiserRawData appValue = (AdvertiserRawData) iterator.next();
			
			if(!appFromAPI.contains(appValue)){
					appsToAdd.add(appValue);
			}
			
				System.err.println(appValue.getAndroidPackage());
			System.err.println(apps238.getApps().contains(appValue));
				
		}	
		
		return appsToAdd;
	}*/
	
	private void processCreatives(Offer offer, String countryCode){
		
		List<AdvertiserCreative> listOfAdvertiserCreatives = new ArrayList<AdvertiserCreative>();
		logger.info(" offer cratives :"+ offer.getImages());
		if(offer.getImages() != null){
			HashMap<String, List<String>> linkedHashMap = (HashMap<String, List<String>>)offer.getImages();
			
			Iterator<String> iterator = linkedHashMap.keySet().iterator();
			AdvertiserCreative advertiserCreative = null;
			while(iterator.hasNext()){
				
				String key = iterator.next();
				
				List<String> values = linkedHashMap.get(key);
				
				for (Iterator iterator2 = values.iterator(); iterator2
						.hasNext();) {
					String string = (String) iterator2.next();
					advertiserCreative =   new AdvertiserCreative();
					advertiserCreative.setOfferId(offer.getId());
					advertiserCreative.setKey(key);
					advertiserCreative.setValue(string);
					advertiserCreative.setCountryCode(countryCode);

					listOfAdvertiserCreatives.add(advertiserCreative);
				}
				
			}
			
			clayMotionDAO.addAdvertiserCreative(listOfAdvertiserCreatives);
			
			AdvertiserRawData advertiserRawData = new AdvertiserRawData();
			advertiserRawData.setOfferId(offer.getId());
			advertiserRawData.setCategory(offer.getCategory());
			
			clayMotionDAO.addRawData(advertiserRawData);
			
		}
		
	}
	

	
	
	public static void main(String[] args) {

		List<Offer> appFromAPI = new ArrayList<Offer>();
		
		List<Offer> appFromHasOffer = new ArrayList<Offer>();
		
		Offer offer1  = new Offer();
		offer1.setName("Cleartrip -Travel   Local_Android_NonIncent_IN");
		offer1.setPreview_url("https://play.google.com/store/apps/details?id=com.cleartrip.android");
		
		appFromAPI.add(offer1);
		
		Offer offer2  = new Offer();
		offer2.setName("Cleartrip -Travel   Local_Android_NonIncent_IN");
		offer2.setPreview_url("https://play.google.com/store/apps/details?id=com.cleartrip.android");
		
		appFromHasOffer.add(offer2);
		
		
		AppNextExecution appNextExecution = new AppNextExecution();
		
		List<Offer> listOfOffer =  new ArrayList<Offer>();// appNextExecution.getListOfAppToAdd(appFromAPI, appFromHasOffer);
		
		System.err.println(listOfOffer.size());
		
		
		
	}
	
}
