package com.claymotion.advertiser.batch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
import com.claymotion.advertiser.IronSource;
import com.claymotion.advertiser.IronSourceCreative;
import com.claymotion.advertiser.IronSourceList;
import com.claymotion.api.response.Offer;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.HasOfferUtility;
import com.claymotion.hasoffer.domain.CountryIPList;
import com.claymotion.service.IClayMotionService;
import com.claymotion.transformer.DataTransformer;
import com.claymotion.util.AffiseOfferUtility;
import com.claymotion.webservice.client.WebServiceClient;

@Component
public class IronSourceExecution {

	private static String URL = "http://export.apprevolve.com/v2/getAds?accessKey=5a14c3ed56d6&secretKey=12f5cab3e94209973cd3cb7786e4619a&applicationKey=53219e55&platform=android&country=";

	private static String IRON_SOURCE_ID = "592c096ec524bdcb3e8b504d";

	WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();

	@Autowired
	HasOfferUtility hasOfferUtility;

	@Autowired
	DataTransformer dataTransformer;

	@Autowired
	IClayMotionDAO clayMotionDAO;

	@Autowired
	IClayMotionService clayMotionService;

	Logger logger = Logger.getLogger(IronSourceExecution.class);

	// @Scheduled(cron="0 0/15 * * * ?")
	public void peformAction() throws Exception {
		actionPerformed();
	}

	public String actionPerformed() throws Exception {
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");

		// TODO Auto-generated method stub
		Map<String, String> mapForCountry = clayMotionService.getCountryAndIPAddress();
		Set<String> listOfIPAddress = mapForCountry.keySet();
		JSONObject responseObject = null;

		logger.info("Fetching all Offer for  Iron Source Advertiser ");
		List<Offer> listOfOffers = AffiseOfferUtility.getSharedInstance().getListOfAlloffers(IRON_SOURCE_ID);
		logger.info(" total Offer Present :" + listOfOffers.size());

		for (Iterator iterator = listOfIPAddress.iterator(); iterator.hasNext();) {

			String countryCode = (String) iterator.next();
			String ipAddress = mapForCountry.get(countryCode);
			String url = URL + countryCode;

			logger.info("URL to Execute :" + url + "  For Country :" + countryCode + " with IPAddress :" + ipAddress);

			try {

/*				IronSourceList ironSourceList = (IronSourceList) webServiceClient.executeGetMethod(url,
						IronSourceList.class, hashMap);
*/
				List<IronSource> listOfApps = getIronSourceList(url, hashMap);

				logger.info("Converting Data in offer data");

				List<Offer> listOfRawData = dataTransformer.convertIntoOfferData(listOfApps, IRON_SOURCE_ID,
						countryCode);

				logger.info(">>>>>>>>>>>>>>>>>>> Offer Converted >>>>>>>>>>>>>>");

				List<Offer> filteredListBasedOnCountry = filteredListBasedOnCountry(listOfOffers, countryCode);

				logger.info(" Filtered offer based on country ");

				// List<AdvertiserRawData> listOfDataFromDB =
				// clayMotionDAO.getListOfApp(APP_NEXT_ID, countryCode);

				List<Offer> appsToAdd = hasOfferUtility.getListOfAppToAdd(listOfRawData, filteredListBasedOnCountry);

				List<Offer> appsToPause = hasOfferUtility.getListOfAppToPause(listOfRawData,
						filteredListBasedOnCountry);

				List<Offer> appsToUpdate = hasOfferUtility.getListOfAppToUpdate(listOfRawData,
						filteredListBasedOnCountry);

				System.err.println("---------- Offer To Paused ------------------------");
				for (Iterator iterator2 = appsToPause.iterator(); iterator2.hasNext();) {

					Offer offer = (Offer) iterator2.next();

					System.err.println(offer.getTitle());

					AffiseOfferUtility.getSharedInstance().updateOfferStatus(""+offer.getId(), "stopped");

				}

				System.err.println("---------- Offer To UPDATE ------------------------");

				for (Iterator iterator2 = appsToUpdate.iterator(); iterator2.hasNext();) {

					Offer offer = (Offer) iterator2.next();
					System.err.println(offer.getTitle());

					AffiseOfferUtility.getSharedInstance().updateOfferStatus(""+offer.getId(), "active");

				}

				System.err.println("---------- Offer To ADD ------------------------");

				for (Iterator iterator2 = appsToAdd.iterator(); iterator2.hasNext();) {

					Offer offer = (Offer) iterator2.next();
					// System.err.println(offer.getTitle());
					System.err.println(offer.getPreview_url() + "----" + offer.getTitle());

					JSONObject jsonObject = null;
					try {
						Map<String, Object> fieldMap = hasOfferUtility.populateOfferCreationFields(offer);
						AffiseOfferUtility.getSharedInstance().createOffer(fieldMap);
						// break;
					} catch (Exception ex) {
						logger.error("Error while Executing hasOffer API for Offer Create :" + ex.getMessage());
						// break;

					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(" Error while exexuting :" + url + "  For Country :" + countryCode + " with IPAddress :"
						+ ipAddress);
			}

		}
		
		String baseDir = AffiseOfferUtility.getSharedInstance().getDestinationFile();
		
		FileUtils.cleanDirectory(new File(baseDir + "IronSource"));


		return "Process Complete";
	}

	
	private List<IronSource> getIronSourceList(String url, Map<String, String> hashMap) throws Exception{

		Object ironSourceObjects = WebServiceClient.getSharedInstance().executeGetMethod(url, Object.class, hashMap);

		
		List<IronSource> ironSourceList = new ArrayList<IronSource>();
		
		IronSource ironSource = null;
				
		
		LinkedHashMap linkedHashMap = (LinkedHashMap) ironSourceObjects;

		Collection collections = (Collection)linkedHashMap.get("ads");
		for (Iterator iterator = collections.iterator(); iterator.hasNext();) {
			
			LinkedHashMap object = (LinkedHashMap) iterator.next();
			
			ironSource = new IronSource();
			
			ironSource.setTitle((String)object.get("title"));
			ironSource.setDescription((String)object.get("description"));

			ironSource.setCategory((String)object.get("category"));
			
			ironSource.setBid((String)object.get("bid"));
			
			ironSource.setCampaign_id((Integer)object.get("campaign_id"));
			
			ironSource.setPackageName((String)object.get("packageName"));
			
			
			List listOfCreatives = (List)object.get("creatives");
			IronSourceCreative ironSourceCreative = null;
			
			for (Iterator iterator2 = listOfCreatives.iterator(); iterator2.hasNext();) {
				LinkedHashMap  creativeMap = (LinkedHashMap) iterator2.next();
				ironSourceCreative = new IronSourceCreative();
				
				ironSourceCreative.setType((String)creativeMap.get("type"));
				ironSourceCreative.setUrl((String)creativeMap.get("url"));
				
				ironSource.getCreatives().add(ironSourceCreative);
			}
			

			ironSourceList.add(ironSource);
		}

		
		return ironSourceList;
		
	}
	

	private List<Offer> filteredListBasedOnCountry(List<Offer> totalList, String country) {
		List<Offer> filteredList = new ArrayList<Offer>();

		for (Iterator iterator = totalList.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			for (Iterator iterator2 = offer.getCountries().iterator(); iterator2.hasNext();) {
				String countryValue = (String) iterator2.next();
				if (countryValue.equalsIgnoreCase(country)) {
					filteredList.add(offer);
					break;
				}
			}
		}

		return filteredList;
	}

	private boolean checkIfOfferAlredayExists(AppNext appNext) throws Exception {

		String offerName = appNext.getTitle() + "_android_nonincent_" + appNext.getCountry();
		String advetiserId = "" + IRON_SOURCE_ID;

		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("name", offerName);
		hashMap.put("advertiser_id", advetiserId);

		JSONObject jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.FIND_ALL_METHOD, hashMap);

		return false;
	}

}
