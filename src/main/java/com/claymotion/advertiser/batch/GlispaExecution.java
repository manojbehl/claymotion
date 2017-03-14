package com.claymotion.advertiser.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.claymotion.advertiser.AppNextList;
import com.claymotion.advertiser.Glispa;
import com.claymotion.advertiser.GlispaList;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.HasOfferUtility;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.hasoffer.domain.Offer;
import com.claymotion.transformer.DataTransformer;
import com.claymotion.webservice.client.WebServiceClient;

@Component
public class GlispaExecution {

	private static String URL = "http://feed.platform.glispa.com/native-feed/b04006ac-af2f-4b37-8326-5ed7498877b7/app?countries=";

	private static int GLIPSA_NEXT_ID = 295;
	
	WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();

	@Autowired
	HasOfferUtility hasOfferUtility;
	
	@Autowired
	DataTransformer dataTransformer;
	
	@Autowired
	IClayMotionDAO clayMotionDAO;
	
	Logger logger = Logger.getLogger(GlispaExecution.class);

	//@Scheduled(cron="0 0/45 * * * ?")
	public void peformAction() throws Exception{
		actionPerformed();
	}
	
	public String actionPerformed() throws Exception {
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		
		Map<String, String> mapForCountry = getIPAddress();
		
		Set<String> listOfIPAddress = mapForCountry.keySet();
		JSONObject responseObject =  null;
		
		
		Map<String, Object> hashMapForAdvertiser = new HashMap<String, Object>();
		hashMapForAdvertiser.put("advertiser_id",""+GLIPSA_NEXT_ID);

		logger.info("Fetching all Offer for  Glispa Advertiser ");

		List<Offer>  listOfOffers = hasOfferUtility.getAllOfferList(hashMapForAdvertiser);
		
		logger.info(" total Offer Present :"+ listOfOffers.size());

		
		GlispaList glispaList = new GlispaList();
		
		for (Iterator iterator = listOfIPAddress.iterator(); iterator.hasNext();) {
			
			String countryCode = (String) iterator.next();
			
			String url = URL + countryCode;
			
			logger.info("URL to Execute :"+ url  + "  For Country :"+ countryCode );
			
			try{
				
				glispaList = (GlispaList) webServiceClient.executeGetMethod(url, GlispaList.class, hashMap);
				
				List<Glispa> listOfGlispaRecords = glispaList.getData();
				
				logger.info("Converting Data in offer data :"+ listOfGlispaRecords.size());


				List<Offer> listOfRawData = dataTransformer.convertIntoOfferData(listOfGlispaRecords, GLIPSA_NEXT_ID);
				
				for (Iterator iterator2 = listOfRawData.iterator(); iterator2
						.hasNext();) {
					Offer offer = (Offer) iterator2.next();
					logger.info("for offer :"+ offer.getId() + "  creatives :"+ offer.getImages());
				}

				logger.info(">>>>>>>>>>>>>>>>>>> Offer Converted >>>>>>>>>>>>>>");

				List<Offer> filteredListBasedOnCountry  = filteredListBasedOnCountry(listOfOffers, countryCode);

				logger.info(" Filtered offer based on country :"+ filteredListBasedOnCountry.size());

				List<Offer> appsToAdd = hasOfferUtility.getListOfAppToAdd(listOfRawData, filteredListBasedOnCountry);

				List<Offer> appsToPause = hasOfferUtility.getListOfAppToPause(listOfRawData, filteredListBasedOnCountry);

				List<Offer> appsToUpdate = hasOfferUtility.getListOfAppToUpdate(listOfRawData, filteredListBasedOnCountry);

				logger.info("offers to add :"+ appsToAdd.size());
				

				logger.info("offers to pause :"+ appsToPause.size());
				

				logger.info("offers to update:"+ appsToUpdate.size());
				
				for (Iterator iterator2 = appsToUpdate.iterator(); iterator2
						.hasNext();) {
					Offer offer = (Offer) iterator2.next();
					logger.info("for offer :"+ offer.getId() + "  creatives :"+ offer.getImages());
					
				}


				for (Iterator iterator2 = appsToPause.iterator(); iterator2.hasNext();) {
					
					Offer offer = (Offer) iterator2.next();
					offer.setStatus("paused");
					
					Map<String, Object> updateHashMap = new HashMap<String, Object>();
					updateHashMap.put("status", "paused");
					
					logger.info("offer to paused :"+ offer.getId());
					
					Object[] objArray = new Object[]{updateHashMap, ""+offer.getId()};
				
					try{
						JSONObject jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.UPDATE_METHOD, objArray);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Pause :"+ ex.getMessage(), ex);
					}
					
					try{
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
					updateHashMap.put("default_payout", offer.getDefault_payout());
					updateHashMap.put("offer_url", offer.getOffer_url());
					
					
					
					Object[] objArray = new Object[]{updateHashMap, ""+offer.getId()};
				
					try{
						JSONObject jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.UPDATE_METHOD, objArray);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Update :"+ ex.getMessage(), ex);
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
				
				for (Iterator iterator2 = appsToAdd.iterator(); iterator2.hasNext();) {
					
					
					Offer offer = (Offer) iterator2.next();

					offer.setStatus("active");
					
					System.err.println(" offer name is :"+ offer.getName());

					Map<String, Object> fieldMap = hasOfferUtility.populateOfferCreationFields(offer);
					JSONObject  jsonObject =  null;
					try{
						jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.CREATE_METHOD, fieldMap);
						
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Create :"+ ex.getMessage());
						
					}
					
					try{
						processCreatives(offer, countryCode);
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for setting Advertiser Creative :"+ ex.getMessage(), ex);
						
					}
//					return jsonObject;
					responseObject = jsonObject;
					
				}

				
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(" Error while exexuting :"+ url  + "  For Country :"+ countryCode );

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
	
	private void processCreatives(Offer offer, String countryCode){
		
		List<AdvertiserCreative> listOfAdvertiserCreatives = new ArrayList<AdvertiserCreative>();
		logger.info(" offer cratives :"+ offer.getImages());
		if(offer.getImages() != null){
			HashMap<String, List<String>> linkedHashMap = (HashMap<String, List<String>>)offer.getImages();
			
			List<String> strings = linkedHashMap.get("564x294");

			logger.info("  stirngs are :"+ strings);
			if(strings != null && strings.size() > 0){
				String value = strings.get(0);
				AdvertiserCreative advertiserCreative = new AdvertiserCreative();
				advertiserCreative.setOfferId(offer.getId());
				advertiserCreative.setKey("564x294");
				advertiserCreative.setValue(value);
				advertiserCreative.setCountryCode(countryCode);

				listOfAdvertiserCreatives.add(advertiserCreative);
				
			}
			
			if(offer.getIcon() != null){
				AdvertiserCreative advertiserCreative = new AdvertiserCreative();
				advertiserCreative.setOfferId(offer.getId());
				advertiserCreative.setKey("default");
				advertiserCreative.setValue(offer.getIcon());
				advertiserCreative.setCountryCode(countryCode);

				listOfAdvertiserCreatives.add(advertiserCreative);
			}
			
			
			/*Iterator<String> itr = linkedHashMap.keySet().iterator();
			AdvertiserCreative advertiserCreative = null;
			while(itr.hasNext()){
				String key = itr.next();
				advertiserCreative = new AdvertiserCreative();
				advertiserCreative.setOfferId(offer.getId());
				advertiserCreative.setKey(key);
				advertiserCreative.setValue(linkedHashMap.get(key));

				listOfAdvertiserCreatives.add(advertiserCreative);
			}
*/
			
			clayMotionDAO.addAdvertiserCreative(listOfAdvertiserCreatives);
			
			AdvertiserRawData advertiserRawData = new AdvertiserRawData();
			advertiserRawData.setOfferId(offer.getId());
			advertiserRawData.setCategory(offer.getCategory());
			
			clayMotionDAO.addRawData(advertiserRawData);
			
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		
		DataTransformer dataTransformer = new DataTransformer();

		WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		
		GlispaList glispaList = new GlispaList();
		
		String URL = "http://feed.platform.glispa.com/native-feed/b04006ac-af2f-4b37-8326-5ed7498877b7/app?countries=IN";
		
		glispaList = (GlispaList) webServiceClient.executeGetMethod(URL, GlispaList.class, hashMap);
		
		List<Glispa> listOfGlispaRecords = glispaList.getData();

		for (Iterator iterator = listOfGlispaRecords.iterator(); iterator
				.hasNext();) {
			
			Glispa glispa = (Glispa) iterator.next();
			
			System.err.println(glispa.getPayout_amount());
			
			/*Map<String, List<String>> values = glispa.getImages();
			List<String> strings = values.get("564x294");
			for (Iterator iterator3 = strings.iterator(); iterator3.hasNext();) {
				String string = (String) iterator3.next();
				System.err.println(string);*/
//			}
//			Iterator<String> iterator2 = values.keySet().iterator();
//			while(iterator2.hasNext()){
//				String key = iterator2.next();
//				System.err.println(key);
//				List<String> str = values.get(key);
//				for (Iterator iterator3 = str.iterator(); iterator3.hasNext();) {
//					String string = (String) iterator3.next();
//					System.err.println(string);
//				}
//			}
			
		} 
		
		/*List<Offer> listOfRawData = dataTransformer.convertIntoOfferData(listOfGlispaRecords, GLIPSA_NEXT_ID);

		
		for (Iterator iterator = listOfRawData.iterator(); iterator
				.hasNext();) {
			Offer glispa = (Offer) iterator.next();
			LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>)glispa.getCreatives();
			Iterator<String> itr = linkedHashMap.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				String value = linkedHashMap.get(key);
				System.err.println(key);
				System.err.println(value);
			}
			System.err.println( glispa.getCreatives().getClass() );
		}
*/

	}
}
