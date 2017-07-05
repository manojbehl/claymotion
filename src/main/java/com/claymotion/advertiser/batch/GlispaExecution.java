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
import com.claymotion.api.response.Offer;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.HasOfferUtility;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.service.IClayMotionService;
import com.claymotion.transformer.DataTransformer;
import com.claymotion.util.AffiseOfferUtility;
import com.claymotion.webservice.client.WebServiceClient;

@Component
public class GlispaExecution {

	private static String URL = "http://feed.platform.glispa.com/native-feed/b04006ac-af2f-4b37-8326-5ed7498877b7/app?countries=";

	private static String GLIPSA_NEXT_ID = "58a3115ac524bdb5108b45c9";
	
	WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();

	@Autowired
	HasOfferUtility hasOfferUtility;
	
	@Autowired
	DataTransformer dataTransformer;
	
	@Autowired
	IClayMotionDAO clayMotionDAO;
	
	@Autowired
	IClayMotionService clayMotionService;
	
	Logger logger = Logger.getLogger(GlispaExecution.class);

	//@Scheduled(cron="0 0/45 * * * ?")
	public void peformAction() throws Exception{
		actionPerformed();
	}
	
	public String actionPerformed() throws Exception {
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		
		Map<String, String> mapForCountry = clayMotionService.getCountryAndIPAddress();
		
		Set<String> listOfIPAddress = mapForCountry.keySet();
		JSONObject responseObject =  null;
		
		
		Map<String, Object> hashMapForAdvertiser = new HashMap<String, Object>();
		hashMapForAdvertiser.put("advertiser_id",""+GLIPSA_NEXT_ID);

		logger.info("Fetching all Offer for  Glispa Advertiser ");

		List<Offer>  listOfOffers = AffiseOfferUtility.getSharedInstance().getListOfAlloffers(GLIPSA_NEXT_ID);
		
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


				List<Offer> listOfRawData = dataTransformer.convertIntoOfferData(listOfGlispaRecords, GLIPSA_NEXT_ID, countryCode);
				
				/*for (Iterator iterator2 = listOfRawData.iterator(); iterator2
						.hasNext();) {
					Offer offer = (Offer) iterator2.next();
					logger.info("for offer :"+ offer.getId() + "  creatives :"+ offer.getImages());
				}*/

				logger.info(">>>>>>>>>>>>>>>>>>> Offer Converted >>>>>>>>>>>>>>");

				List<Offer> filteredListBasedOnCountry  = filteredListBasedOnCountry(listOfOffers, countryCode);

				logger.info(" Filtered offer based on country :"+ filteredListBasedOnCountry.size());

				List<Offer> appsToAdd = hasOfferUtility.getListOfAppToAdd(listOfRawData, filteredListBasedOnCountry);

				List<Offer> appsToPause = hasOfferUtility.getListOfAppToPause(listOfRawData, filteredListBasedOnCountry);

				List<Offer> appsToUpdate = hasOfferUtility.getListOfAppToUpdate(listOfRawData, filteredListBasedOnCountry);

				logger.info("offers to add :"+ appsToAdd.size());
				

				logger.info("offers to pause :"+ appsToPause.size());
				

				logger.info("offers to update:"+ appsToUpdate.size());
				
				/*for (Iterator iterator2 = appsToUpdate.iterator(); iterator2
						.hasNext();) {
					Offer offer = (Offer) iterator2.next();
					logger.info("for offer :"+ offer.getId() + "  creatives :"+ offer.getImages());
					
				}*/


				for (Iterator iterator2 = appsToPause.iterator(); iterator2.hasNext();) {
					
					Offer offer = (Offer) iterator2.next();
					
					System.err.println(offer.getTitle());
					
					AffiseOfferUtility.getSharedInstance().updateOfferStatus(offer.getOffer_id(), "stopped");
					
				}
				
				for (Iterator iterator2 = appsToUpdate.iterator(); iterator2.hasNext();) {
					
					Offer offer = (Offer) iterator2.next();
					System.err.println(offer.getTitle());
					
					AffiseOfferUtility.getSharedInstance().updateOfferStatus(offer.getOffer_id(), "active");
					
					
				}
				
				for (Iterator iterator2 = appsToAdd.iterator(); iterator2.hasNext();) {
					
					
					Offer offer = (Offer) iterator2.next();
					System.err.println(offer.getTitle());
					

					JSONObject jsonObject = null;
					try{
						Map<String, Object> fieldMap = hasOfferUtility.populateOfferCreationFields(offer);
						 AffiseOfferUtility.getSharedInstance().createOffer(fieldMap);
//						break;
					}catch(Exception ex){
						logger.error("Error while Executing hasOffer API for Offer Create :"+ ex.getMessage());
//						break;
						
					}

					
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
			if(offer.getCountries().contains(country))
				filteredList.add(offer);
		}
		
		return filteredList;
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
