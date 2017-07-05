package com.claymotion.advertiser.batch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.claymotion.advertiser.AppCreative;
import com.claymotion.advertiser.Creative;
import com.claymotion.api.response.Offer;
import com.claymotion.api.response.OfferPrioirty;
import com.claymotion.dao.ClayMotionDAO;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.HasOfferUtility;
import com.claymotion.hasoffer.domain.Advertiser;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.Affiliate;
import com.claymotion.hasoffer.domain.AffiliateOffer;
import com.claymotion.hasoffer.domain.AffiseCreative;
import com.claymotion.hasoffer.domain.CreativeDomain;
import com.claymotion.hasoffer.domain.HasOfferData;
import com.claymotion.transformer.DataTransformer;
import com.claymotion.util.AffiseOfferUtility;
import com.claymotion.webservice.client.WebServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OfferCreationProcessing {

	@Autowired
	HasOfferUtility hasOfferUtility;

	@Autowired
	IClayMotionDAO clayMotionDAO;

	@Autowired
	DataTransformer dataTransformer;

	Logger logger = Logger.getLogger(OfferCreationProcessing.class);

	// @Scheduled(cron="0 0 0/1 * * ?")
	public void actionPerformed() throws Exception {

		Map<String, Object> hashMapForAdvertiser = new HashMap<String, Object>();
		hashMapForAdvertiser.put("status", "active");

		logger.info(" Fetching All Active Offer from has offer ");

		List<Offer> listOfOffers = AffiseOfferUtility.getSharedInstance().getListOfAlloffers(null);

		logger.info("All Offers Fetched from Affise :" + listOfOffers.size());

		
		logger.info("fetching Prioority Offers");
		
		
		Collection<Offer> activatedAndPriorityOffers = getPriorityOffer(listOfOffers);

		logger.info(" Total Priority Offers fetched :" + activatedAndPriorityOffers.size());
		
		List<Affiliate> listOfAffiliate = clayMotionDAO.getAffiliate();

		clayMotionDAO.updateApplicationStatus(HasOfferConstant.STATUS_PROCESSING);
		/// Delete Alreday Generated Links
		logger.info("Delete all Genearted Links");
		
		clayMotionDAO.deleteAlreadyGeneratedLinks();

		logger.info("Delete Already Created offers");
		
		clayMotionDAO.deleteAlreadyCreatedOffers();

		logger.info("Delere Already Created Creatives");
		
		clayMotionDAO.deleteAlreadyCreatedCreatives();

		logger.info("Delere Already Created Offer Priorities");
		clayMotionDAO.deleteAlreadyCreatedPriority();

		List<AffiliateOffer> listAffiliateOffers = new ArrayList<AffiliateOffer>();

		for (Iterator iterator = activatedAndPriorityOffers.iterator(); iterator.hasNext();) {

			try {

				Offer offer = (Offer) iterator.next();

				if (offer.getPreview_url().contains("itunes.apple.com"))
					continue;

				String andriodPackage = reterievePackageName(offer.getPreview_url());
				if (andriodPackage == null || andriodPackage.trim().length() == 0)
					continue;

				offer.setPackageName(andriodPackage);

				logger.info("Offer Package In Execution :" + offer.getPreview_url() + "   Advertiser id :"
						+ offer.getAdvertiser() + " Name is :" + offer.getTitle());

				logger.info(" Calling Generated Pixel method for :" + andriodPackage);
				// hasOfferUtility.executeHasOfferAPI(HasOfferConstant.GENERATE_PIXEL_METHOD,
				// ""+ offer.getId());
				System.err.println(offer.getCountries());
				
				for (Iterator iterator2 = offer.getPayments().iterator(); iterator2.hasNext();) {
					LinkedHashMap<String, Object> payments = (LinkedHashMap<String, Object>) iterator2.next();
					System.err.println(payments.get("total"));
					
					offer.setRevenueRate(Double.parseDouble(""+ payments.get("total")));
					offer.setCurrency((String)payments.get("currency"));
					
					
				}
				
				offer.getCategories().clear();
				offer.getCategories().add(getCategory(offer));
				 clayMotionDAO.addOfferData(offer);
				logger.info(" Generating Link between Offer and Affiliate");
				for (Iterator iterator2 = listOfAffiliate.iterator(); iterator2.hasNext();) {

					Affiliate affiliate = (Affiliate) iterator2.next();
					Object[] objArray = new Object[2];
					objArray[0] = "" + offer.getId();
					objArray[1] = "" + affiliate.getId();

					List<String> listOfInteger = new ArrayList<String>();
					listOfInteger.add("" + offer.getId());

					try {
						AffiseOfferUtility.getSharedInstance().deLinkAffiliate(listOfInteger, affiliate.getId());

					} catch (Exception ex) {
						logger.error(" Error  while De-Link for offer ");
						logger.error(ex.getMessage());

					}

					AffiseOfferUtility.getSharedInstance().linkAffiliate(listOfInteger, affiliate.getId());
					AffiliateOffer affiliateOffer = new AffiliateOffer();
					affiliateOffer.setOffer_id("" + offer.getId());
					affiliateOffer.setAffiliate_id(affiliate.getId());

					clayMotionDAO.addGenerateLinkData(affiliateOffer);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				// Exception handling need to done;
				logger.error(" Error  while creating Link for offer ");
				logger.error(ex.getMessage());
			}
		}

		logger.info(" Creating Offer and Creative for Prioirty Offers In DB");
		// insertCreativeData(activatedAndPriorityOffers);
		createAndStoreCreatives(activatedAndPriorityOffers);

		logger.info(" Storing Click Url Data in Database");

		setPrioirty(convertCollectionIntoList(listOfOffers));

		clayMotionDAO.updateApplicationStatus(HasOfferConstant.STATUS_PROCESSED);

	}
	
	private String getCategory(Offer offer){
		List cataegoryList = offer.getFull_categories();
		String categoryListName = "";
		for (Iterator iterator2 = cataegoryList.iterator(); iterator2.hasNext();) {
			LinkedHashMap object = (LinkedHashMap) iterator2.next();
			categoryListName += (String)object.get("title");
			
		}
		
		return categoryListName;
	}

	private void createAndStoreCreatives(Collection<Offer> activatedAndPriorityOffers) {

		for (Iterator iterator = activatedAndPriorityOffers.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			System.err.println(offer.getAdvertiser());
			System.err.println(offer.getCreatives().getClass());
			if (offer.getCreatives() instanceof LinkedHashMap) {
				LinkedHashMap linkedHashMap = (LinkedHashMap) offer.getCreatives();
				System.err.println(linkedHashMap);
				List<CreativeDomain> listOfCreatives = new ArrayList<CreativeDomain>();
				CreativeDomain creative = null;
				for (Iterator iterator2 = linkedHashMap.values().iterator(); iterator2.hasNext();) {
					LinkedHashMap<String, Object> creativeLinkedHashMap = (LinkedHashMap<String, Object>) iterator2.next();
					creative = new CreativeDomain();
					creative.setOfferId(offer.getId());
					creative.setFilename((String)creativeLinkedHashMap.get("file_name"));
					creative.setHeight((Integer)creativeLinkedHashMap.get("height"));
					creative.setWidth((Integer)creativeLinkedHashMap.get("width"));
					creative.setSize((String)creativeLinkedHashMap.get("size"));
					creative.setMimetype((String)creativeLinkedHashMap.get("type"));
					
					listOfCreatives.add(creative);
					
				}
				
				clayMotionDAO.addCreativeData(listOfCreatives);
			}

		}
	}

	private void insertCreativeData(Collection<Offer> activatedAndPriorityOffers) {

		HasOfferData hasOfferData = null;
		List<HasOfferData> listOfHasOfferData = new ArrayList<HasOfferData>();
		WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		for (Iterator iterator = activatedAndPriorityOffers.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();

			if (offer.getPreview_url().contains("itunes.apple.com"))
				continue;

			String androidPackage = reterievePackageName(offer.getPreview_url());

			if (androidPackage == null || androidPackage.trim().length() == 0)
				continue;

			hasOfferData = new HasOfferData();

			String creativeURL = HasOfferConstant.CREATIVE_URL + androidPackage.trim();

			hasOfferData.setAndroidPackage(androidPackage);

			/*
			 * if(offer.getName().indexOf("_") != -1){ String country =
			 * offer.getName().substring(offer.getName().lastIndexOf("_")+1);
			 * hasOfferData.setCountry(country);
			 * 
			 * }
			 */

			/*
			 * hasOfferData.setName(offer.getTitle());
			 * hasOfferData.setAdvertiser(offer.getAdvertiser());
			 * hasOfferData.setAppUrl(offer.getOffer_url());
			 * hasOfferData.setOfferId(offer.getId());
			 * hasOfferData.setCurrency(offer.getCurrency());
			 * hasOfferData.setRevenueType(offer.getRevenue_type());
			 */

			/*
			 * if(offer.getDefault_payout() != null)
			 * hasOfferData.setRevenueRate(Double.parseDouble(offer.
			 * getDefault_payout()));
			 */

			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("Content-Type", "application/json");

			AppCreative appCreative = null;

			try {
				System.err.println(creativeURL);
				appCreative = (AppCreative) webServiceClient.executeGetMethod(creativeURL, AppCreative.class, hashMap);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("Error While Executing Creative Link for package :" + androidPackage);

			}

			if (appCreative == null) {
				try {
					List<AdvertiserCreative> listOfAdvertiserCreative = clayMotionDAO
							.getAdvertiserCreative(offer.getId());

					List<CreativeDomain> listOfCreativeDomain = dataTransformer
							.convertIntoCreativeDomain(listOfAdvertiserCreative, offer);

					clayMotionDAO.addCreativeData(listOfCreativeDomain);

					/*
					 * if(offer.getName().indexOf("_") != -1)
					 * hasOfferData.setTitle(offer.getName().substring(0,
					 * offer.getName().indexOf("_"))); else
					 * hasOfferData.setTitle(offer.getName());
					 */

					hasOfferData.setDescription(offer.getDescription());
					// hasOfferData.setCategory(offer.getCategory());
					// hasOfferData.setSubDescription(offer.get);

				} catch (Exception ex) {
					logger.error("Error While Executing Creative Link from Advertiser :" + androidPackage, ex);

				}

			}

			if (appCreative != null) {
				hasOfferData.setDeveloper(appCreative.getDeveloper());
				hasOfferData.setIcon(appCreative.getIcon());
				hasOfferData.setStoreRating(appCreative.getRating());
				hasOfferData.setVersion(appCreative.getVersion());
				hasOfferData.setDescription(appCreative.getDescription());
				hasOfferData.setTitle(appCreative.getName());
				hasOfferData.setSubDescription(appCreative.getShort_description());
				hasOfferData.setCategory(getCatagories(appCreative.getCategory()));

				if (appCreative.getCreatives() != null && appCreative.getCreatives().size() > 0) {
					try {
						List<CreativeDomain> listOfCreativeDomain = dataTransformer
								.convertIntoCreativeDomain(appCreative.getCreatives());

						clayMotionDAO.addCreativeData(listOfCreativeDomain);

					} catch (Exception ex) {
						logger.error("Error While Executing Creative Link From Creative API:" + androidPackage, ex);

					}
				} else {
					try {
						List<AdvertiserCreative> listOfAdvertiserCreative = clayMotionDAO
								.getAdvertiserCreative(offer.getId());

						List<CreativeDomain> listOfCreativeDomain = dataTransformer
								.convertIntoCreativeDomain(listOfAdvertiserCreative, offer);

						clayMotionDAO.addCreativeData(listOfCreativeDomain);

					} catch (Exception ex) {
						logger.error("Error While Executing Creative Link from Advertiser :" + androidPackage, ex);

					}
				}
			}

			listOfHasOfferData.add(hasOfferData);

		}

		logger.info(" calling Dao layer to store data in database");
		clayMotionDAO.addHasOfferData(listOfHasOfferData);

	}

	private String getCatagories(List<String> categories) {
		String concatCategory = "";

		for (int i = 0; i < categories.size(); i++) {
			String stringValue = categories.get(i);
			if (categories.size() == 1 || i == (categories.size() - 1)) {
				concatCategory += stringValue;
			} else {
				concatCategory += stringValue + ",";
			}
		}

		return concatCategory;
	}

	public Collection<Offer> getPriorityOffer(List<Offer> offerList) {

		List<Advertiser> listOfAdvertiser = clayMotionDAO.getAdvertiser();

		Map<String, Offer> hashMap = new HashMap<String, Offer>();

		for (Iterator iterator = offerList.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();

			// logger.info("Offer Package :"+ offer.getPreview_url() + "
			// Advertiser id :"+ offer.getAdvertiser_id());

			if (!doesAdvertiserPresent(listOfAdvertiser, offer.getAdvertiser()))
				continue;

			/*
			 * Setting AndriodPackage
			 */

			String countryCode = "";

			/*
			 * if( offer.getTitle().lastIndexOf("_") != -1){ countryCode =
			 * offer.getTitle().substring(offer.getTitle().lastIndexOf("_") +
			 * 1); }
			 */

			String keyName = offer.getPreview_url() + "_" + offer.getCountries().get(0);

			if (hashMap.get(keyName) == null) {
				hashMap.put(keyName, offer);
			} else {
				Offer addedOffer = (Offer) hashMap.get(keyName);
				logger.info("Offer Added Package :" + addedOffer.getPreview_url() + "   Advertiser id :"
						+ addedOffer.getAdvertiser());
				int addedOfferPriority = getOfferAdvertiserPriority(listOfAdvertiser, addedOffer);

			int newOfferPrioirty = getOfferAdvertiserPriority(listOfAdvertiser, offer);

			if (addedOfferPriority >= newOfferPrioirty)
					hashMap.put(keyName, offer);

			}

		}
		return hashMap.values();
	}

	private String reterievePackageName(String preview_url) {
		URL url = null;
		try {
			url = new URL(preview_url);
			String queryParameter = url.getQuery();
			if (queryParameter != null & queryParameter.trim().length() > 0) {
				String[] arrayOfQueryParameter = queryParameter.split("\\=");
				return arrayOfQueryParameter[1];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	private boolean doesAdvertiserPresent(List<Advertiser> advertisers, String advertiserId) {
		for (Iterator iterator = advertisers.iterator(); iterator.hasNext();) {
			Advertiser advertiser = (Advertiser) iterator.next();
			if (advertiser.getId().equals(advertiserId))
				return true;
		}

		return false;
	}

	private int getOfferAdvertiserPriority(List<Advertiser> listOfAdvertisers, Offer offer) {
		Integer intValue = Integer.MAX_VALUE;
		for (Iterator iterator = listOfAdvertisers.iterator(); iterator.hasNext();) {
			Advertiser advertiser = (Advertiser) iterator.next();
			if(advertiser.getId().equals(offer.getAdvertiser()))
					return advertiser.getPriority();
		}

		return intValue;
	}

	public static void main(String[] args) throws MalformedURLException {
		List<Offer> listOfOffers = AffiseOfferUtility.getSharedInstance().getListOfAlloffers(null);

		for (Iterator iterator = listOfOffers.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			
			for (Iterator iterator2 = offer.getPayments().iterator(); iterator2.hasNext();) {
				LinkedHashMap<String, Object> payments = (LinkedHashMap<String, Object>) iterator2.next();
				System.err.println(payments.get("total") + " -  "+ offer.getAdvertiser());
				
				
			}
		}

	}

	private List<Offer> convertCollectionIntoList(Collection<Offer> collectionOfOffer) {

		List<Offer> listOfOffers = new ArrayList<Offer>();
		listOfOffers.addAll(collectionOfOffer);

		return listOfOffers;
	}

	@SuppressWarnings("unchecked")
	private void setPrioirty(List listOfOffer) {
		List<OfferPrioirty> listOfOfferPriority = new ArrayList<OfferPrioirty>();

		Collections.sort(listOfOffer, new SortValues());

		OfferPrioirty offerPrioirty = null;
		int i = 1;

		for (Iterator iterator = listOfOffer.iterator(); iterator.hasNext();) {
			Offer offer = (Offer) iterator.next();
			offerPrioirty = new OfferPrioirty();
			offerPrioirty.setOfferId(offer.getId());
			offerPrioirty.setPriority(i++);

			listOfOfferPriority.add(offerPrioirty);
		}

		clayMotionDAO.addPriorityData(listOfOfferPriority);
	}

}

class SortValues implements Comparator {

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		// String firstOfferMaxPayout = ((Offer)o1).getMax_payout();
		// String secondOfferMaxPayout = ((Offer)o2).getMax_payout();
		// if(firstOfferMaxPayout != null && secondOfferMaxPayout != null){
		// Double firstValue = Double.parseDouble(firstOfferMaxPayout);
		// Double secondValue = Double.parseDouble(secondOfferMaxPayout);
		// if(firstValue < secondValue)
		// return -1;
		// else if (firstValue > secondValue)
		// return 1;
		// }

		return 0;
	}

}
