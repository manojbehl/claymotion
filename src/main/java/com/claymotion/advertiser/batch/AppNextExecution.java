package com.claymotion.advertiser.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.claymotion.advertiser.AppNext;
import com.claymotion.advertiser.AppNextList;
import com.claymotion.api.response.Offer;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.hasoffer.HasOfferUtility;
import com.claymotion.service.IClayMotionService;
import com.claymotion.transformer.DataTransformer;
import com.claymotion.util.AffiseOfferUtility;
import com.claymotion.webservice.client.WebServiceClient;

@Component
public class AppNextExecution {

	
	private static String URL = "https://admin.appnext.com/offerWallApi.aspx?id=5a5c5ce6-d955-4a9c-8fa2-27c04fc68e13&cnt=200&type=json&ip=";

	private static String APP_NEXT_ID = "58a31117c524bdec0d8b459e"
			+ "";
	
	WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();

	@Autowired
	HasOfferUtility hasOfferUtility;
	
	@Autowired
	DataTransformer dataTransformer;
	
	@Autowired
	IClayMotionDAO clayMotionDAO;
	
	@Autowired
	IClayMotionService clayMotionService;
	
	Logger logger = Logger.getLogger(AppNextExecution.class);
	
	//@Scheduled(cron="0 0/15 * * * ?")
	public void peformAction() throws Exception{
		actionPerformed();
	}
	
	
	public String actionPerformed() throws Exception {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");

		// TODO Auto-generated method stub
		Map<String, String> mapForCountry = clayMotionService.getCountryAndIPAddress();
		Set<String> listOfIPAddress = mapForCountry.keySet();
		JSONObject responseObject =  null;
		
		
		Map<String, Object> hashMapForAdvertiser = new HashMap<String, Object>();
		hashMapForAdvertiser.put("advertiser_id",""+APP_NEXT_ID);

		logger.info("Fetching all Offer for  App Next Advertiser ");
		List<Offer>  listOfOffers = AffiseOfferUtility.getSharedInstance().getListOfAlloffers(APP_NEXT_ID);
		logger.info(" total Offer Present :"+ listOfOffers.size());
		
		for (Iterator iterator = listOfIPAddress.iterator(); iterator.hasNext();) {
			
			String countryCode = (String) iterator.next();
			String ipAddress = mapForCountry.get(countryCode);
			String url = URL + ipAddress;
			
			logger.info("URL to Execute :"+ url  + "  For Country :"+ countryCode + " with IPAddress :"+ ipAddress);
			
			try{
				
//				AppNextList appNextList = (AppNextList) webServiceClient.executeGetMethod(url, AppNextList.class, hashMap);

				List<AppNext> listOfApps = getAppNextList(url, hashMap);
				
				logger.info("Converting Data in offer data");
				
				List<Offer> listOfRawData = dataTransformer.convertIntoOfferData(listOfApps, APP_NEXT_ID, countryCode);
				
				logger.info(">>>>>>>>>>>>>>>>>>> Offer Converted >>>>>>>>>>>>>>");
				
				List<Offer> filteredListBasedOnCountry  = filteredListBasedOnCountry(listOfOffers, countryCode);
				
				logger.info(" Filtered offer based on country ");

//				List<AdvertiserRawData> listOfDataFromDB = clayMotionDAO.getListOfApp(APP_NEXT_ID, countryCode);
				
				
				List<Offer> appsToAdd = hasOfferUtility.getListOfAppToAdd(listOfRawData, filteredListBasedOnCountry);
				
				List<Offer> appsToPause = hasOfferUtility.getListOfAppToPause(listOfRawData, filteredListBasedOnCountry);
				
				
				List<Offer> appsToUpdate = hasOfferUtility.getListOfAppToUpdate(listOfRawData, filteredListBasedOnCountry);

				
				logger.info("------APPS to PAUSE------------------");
				for (Iterator iterator2 = appsToPause.iterator(); iterator2.hasNext();) {
					
					
					Offer offer = (Offer) iterator2.next();
					
					logger.info(offer.getTitle());
					
					AffiseOfferUtility.getSharedInstance().updateOfferStatus(""+offer.getId(), "stopped");
					
					
				}
				
				logger.info("------APPS to Update------------------");
				for (Iterator iterator2 = appsToUpdate.iterator(); iterator2.hasNext();) {
					
					Offer offer = (Offer) iterator2.next();
					logger.info(offer.getTitle());
					
					AffiseOfferUtility.getSharedInstance().updateOfferStatus(""+offer.getId(), "active");
					
					
				}
				
				logger.info("------APPS to ADD ------------------");
				for (Iterator iterator2 = appsToAdd.iterator(); iterator2.hasNext();) {
					
					
					Offer offer = (Offer) iterator2.next();
					System.err.println(offer.getTitle());
					

					JSONObject jsonObject = null;
					try{
						Map<String, Object> fieldMap = hasOfferUtility.populateOfferCreationFields(offer);
						// AffiseOfferUtility.getSharedInstance().createOffer(fieldMap);
//						break;
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Create :"+ ex.getMessage());
//						break;
						
					}
					
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(" Error while exexuting :"+ url  + "  For Country :"+ countryCode + " with IPAddress :"+ ipAddress);
			}
			
			
		
		}
		String baseDir = AffiseOfferUtility.getSharedInstance().getDestinationFile();
		
		FileUtils.cleanDirectory(new File(baseDir + "AppNext"));
		
		return "Process Complete";
	}
	
	
	
	private List<Offer> filteredListBasedOnCountry(List<Offer> totalList, String country){
		List<Offer> filteredList = new ArrayList<Offer>();
		
		for (Iterator iterator = totalList.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			for (Iterator iterator2 = offer.getCountries().iterator(); iterator2.hasNext();) {
				String countryValue = (String) iterator2.next();
				if(countryValue.equalsIgnoreCase(country)){
					filteredList.add(offer);
					break;
				}
			}
		}
		
		return filteredList;
	}
	
	
	
	private List<AppNext> getAppNextList(String url, Map<String, String> hashMap) throws Exception{
		
		Object appNextList =  webServiceClient.executeGetMethod(url, Object.class, hashMap);

		
		List<AppNext> listOfAppNextObjects = new ArrayList<AppNext>();
		
		AppNext appNext = null;
		
		LinkedHashMap linkedHashMap = (LinkedHashMap)appNextList;
		
		Collection collections = linkedHashMap.values();
		for (Iterator iterator = collections.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			List listOfObjects =  (ArrayList)object;
			for (Iterator iterator2 = listOfObjects.iterator(); iterator2.hasNext();) {
				Object object1 = (Object) iterator2.next();
				LinkedHashMap<String, Object> appNextValues = (LinkedHashMap<String, Object>)object1;
				appNext = new AppNext();
				
				appNext.setTitle((String)appNextValues.get("title"));

				appNext.setDesc((String)appNextValues.get("desc"));
				
				appNext.setCountry((String)appNextValues.get("country"));
				
				appNext.setRevenueRate((String)appNextValues.get("revenueRate"));
				
				appNext.setCampaignId((String)appNextValues.get("campaignId"));
				
				appNext.setCategories((String)appNextValues.get("categories"));
				
				appNext.setUrlImg((String)appNextValues.get("urlImg"));
				
				appNext.setUrlImgWide((String)appNextValues.get("urlImgWide"));

				appNext.setUrlVideo((String)appNextValues.get("urlVideo"));

				appNext.setUrlVideo30Sec((String)appNextValues.get("urlVideo30Sec"));
				
				appNext.setAndroidPackage((String)appNextValues.get("androidPackage"));

				listOfAppNextObjects.add(appNext);
			}
			
			
		}
		
		
		return listOfAppNextObjects;
		
	}
	
	
	
	
	
	public static void main(String[] args) {

		List<Offer> appFromAPI = new ArrayList<Offer>();
		
		List<Offer> appFromHasOffer = new ArrayList<Offer>();
		
		Offer offer1  = new Offer();
		offer1.setTitle("Cleartrip -Travel   Local_Android_NonIncent_IN");
		offer1.setPreview_url("https://play.google.com/store/apps/details?id=com.cleartrip.android");
		
		appFromAPI.add(offer1);
		
		Offer offer2  = new Offer();
		offer2.setTitle("Cleartrip -Travel   Local_Android_NonIncent_IN");
		offer2.setPreview_url("https://play.google.com/store/apps/details?id=com.cleartrip.android");
		
		appFromHasOffer.add(offer2);
		
		
		AppNextExecution appNextExecution = new AppNextExecution();
		
		List<Offer> listOfOffer =  new ArrayList<Offer>();// appNextExecution.getListOfAppToAdd(appFromAPI, appFromHasOffer);
		
		System.err.println(listOfOffer.size());
		
		
		
	}
	
}
