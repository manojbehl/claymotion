package com.claymotion.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.claymotion.advertiser.Advertiser;
import com.claymotion.api.response.Offer;
import com.claymotion.api.response.OfferReturned;
import com.claymotion.hasoffer.domain.AffiseOfferList;
import com.claymotion.hasoffer.domain.Category;
import com.claymotion.hasoffer.domain.CategoryList;
import com.claymotion.hasoffer.domain.ListOfOffers;
import com.claymotion.webservice.client.WebServiceClient;

public class AffiseOfferUtility {
	
	Logger logger = Logger.getLogger(AffiseOfferUtility.class);

	private static AffiseOfferUtility sharedInstance = null;
	
	private List<Category> listOfCategories = null;
	
//	private String destinationFile = "C:/Manoj/images/";
	
	private String destinationFile =  "/usr/images/";

	
	public static AffiseOfferUtility getSharedInstance(){
		if(sharedInstance == null){
			sharedInstance = new AffiseOfferUtility();
		}
		
		return sharedInstance;
	}
	
	public List<Advertiser> getListOfAdvertiser() throws Exception{
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");
		
		
		
		 String URL ="http://api.dynamyn.affise.com/2.1/admin/advertisers?limit=0";

		 WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		 
		 List<Advertiser> listOfAdvertiser = new ArrayList<Advertiser>();
		 
		Object object = webServiceClient.getSharedInstance().executeGetMethod(URL, Object.class, hashMap);
		
		LinkedHashMap linkedHashMap = (LinkedHashMap)object;
		
		List listOfObjects = (List) linkedHashMap.get("advertisers");
		
		Advertiser advertiser = null;
		
		for (Iterator iterator = listOfObjects.iterator(); iterator.hasNext();) {
			LinkedHashMap object2 = (LinkedHashMap) iterator.next();
			
			advertiser = new Advertiser();
			
			advertiser.setAdvertiserId((String)object2.get("id"));
			advertiser.setContact((String)object2.get("contact"));
			advertiser.setTitle((String)object2.get("title"));
			advertiser.setSkype((String)object2.get("skype"));
			
			
			
			
			listOfAdvertiser.add(advertiser);
		}
		
		
		return listOfAdvertiser;
	}
	
	
	
	public List<Offer> getListOfAlloffers(String advertiserId){
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");
		
		
		
		 String URL ="http://api.dynamyn.affise.com/2.1/offers?limit=0";

		 WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		 
		 List<Offer> filteredOfferList = new ArrayList<Offer>();
		 try {
			AffiseOfferList affiseOfferList  = (AffiseOfferList)webServiceClient.executeGetMethod(URL, AffiseOfferList.class, hashMap);
			List<Offer> list = affiseOfferList.getOffers();
			System.err.println(list.size());
			if(advertiserId != null){
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Offer offer = (Offer) iterator.next();
					if(offer.getAdvertiser().equalsIgnoreCase(advertiserId))
						filteredOfferList.add(offer);
				}
				
			}else{
				filteredOfferList = affiseOfferList.getOffers();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return filteredOfferList;
	}
	
	public void updateOfferStatus(String offerId, String status){

		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");
		
		 String URL ="http://api.dynamyn.affise.com/2.1/admin/offer/"+ offerId + "?status="+ status;

		 WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		 
		 List<Offer> filteredOfferList = new ArrayList<Offer>();
		 try {
			webServiceClient.executePostMethod(URL, null, null, hashMap) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	

	}
	
	public List<Offer> fetchAllActiveOffer(){

		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");
		
		 String URL ="http://api.dynamyn.affise.com/2.1/offers";

		 WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		 
		 List<Offer> filteredOfferList = new ArrayList<Offer>();
		 try {
			ListOfOffers listOfOffers = (ListOfOffers)webServiceClient.executeGetMethod(URL,ListOfOffers.class, hashMap) ;
			return listOfOffers.getOffers();
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return null;

	}
	
	
	public void linkAffiliate(List<String> offerId, int affiliateId ) throws Exception{
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/x-www-form-urlencoded");
		hashMap.put("Accept", "application/json");
		
		
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");

		 String hasOfferURL= "http://api.dynamyn.affise.com/2.1/offer/enable-affiliate";
		 
		 StringBuffer sb = new StringBuffer();
		 
		 for(int i=0; i< offerId.size(); i++){
			 if(i==0)
				sb.append("offer_id["+i+"]="+offerId.get(i));
			 else
				sb.append("&offer_id["+i+"]="+offerId.get(i));
				 
		 }
		 sb.append("&pid="+affiliateId+"&notice=1");
		 
		 WebServiceClient.getSharedInstance().executePostMethod(hasOfferURL, sb.toString(), Object.class, hashMap) ;
			
	}
	
	public void deLinkAffiliate(List<String> offerId, int affiliateId ) throws Exception{
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/x-www-form-urlencoded");
		hashMap.put("Accept", "application/json");
		
		
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");

		 String hasOfferURL= "http://api.dynamyn.affise.com/2.1/offer/disable-affiliate";
		 
		 StringBuffer sb = new StringBuffer();
		 
		 for(int i=0; i< offerId.size(); i++){
			 if(i==0)
				sb.append("offer_id["+i+"]="+offerId.get(i));
			 else
				sb.append("&offer_id["+i+"]="+offerId.get(i));
				 
		 }
		 sb.append("&pid="+affiliateId+"&notice=1");
		 
		 WebServiceClient.getSharedInstance().executePostMethod(hasOfferURL, sb.toString(), Object.class, hashMap) ;
			
	}
	
	public List<Category> getCategoryList() throws Exception{
		
		if(listOfCategories == null){
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("Content-Type", "application/x-www-form-urlencoded");
			hashMap.put("Accept", "application/json");
			
			
			hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");

			 String hasOfferURL= "http://api.dynamyn.affise.com/2.1/offer/categories";

			CategoryList categoryList =  (CategoryList)  WebServiceClient.getSharedInstance().executeGetMethod(hasOfferURL, CategoryList.class, hashMap);
			
			listOfCategories =  categoryList.getCategories();
		}

		return listOfCategories;
	}
	
	
	public void createOffer(Map<String, Object> map){
		Map<String, String> hashMap = new HashMap<String, String>();
//		hashMap.put("Content-Type", "application/x-www-form-urlencoded");
		
		hashMap.put("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
		
		hashMap.put("Accept", "application/json");
		
		
		hashMap.put("API-Key", "2fdcb8fb2ebfa39d9afe2389e8e91e4dd1a2d274");
		
/*		 String URL ="http://api.dynamyn.affise.com/2.1/admin/offer?status=active&title"+ offer.getTitle() + "&advertiser="+offer.getAdvertiser()
		 	+"&url="+offer.getUrl()+"url_preview="+offer.getUrl_preview()+"&description="+offer.getDescription()+"&countries="+offer.getCountries().get(0)
		 	+"categories="+offer.getCategories()+"&notes="+offer.getNotes();
*/		 
		 String hasOfferURL= "http://api.dynamyn.affise.com/2.1/admin/offer";
		 StringBuffer value = null;	
		 MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		 if (map != null && map.size() > 0) {
				Iterator<String> keySetIterator = map.keySet().iterator();
				 value = new StringBuffer();
				 int i =0;
				while (keySetIterator.hasNext()) {
					String key = keySetIterator.next();
					
					System.err.println(map.get(key));
					parts.add(key, map.get(key));
					
					/*if(map.get(key) instanceof String){
						String queryParameterValue = "";;
						try {
							queryParameterValue = URLEncoder.encode((String)map.get(key), "UTF-8");
							parts.add(key, queryParameterValue);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							queryParameterValue =  (String)map.get(key);
						}
						if(i==0)
							value.append( key + "=" + queryParameterValue);
						else
							value.append("&" + key + "=" + queryParameterValue);
							
					}
					else{
						if(i==0)
							value.append( key + "=" + map.get(key));
						else
							value.append("&" + key + "=" + map.get(key));
							
					}
						i++;	*/	
				}
//				hasOfferURL = hasOfferURL + value.toString();
			}

		 
		 
		 System.err.println(" Offer Create URL is :"+ hasOfferURL);
		 System.err.println(" value is :"+ value);
		 
		 WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		 
		 List<Offer> filteredOfferList = new ArrayList<Offer>();
		 try {
			 webServiceClient.executePostMethod(hasOfferURL, parts, Object.class, hashMap) ;
			
//			return createdOffer.getOffer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		
	}
	
	
	
	public static void main(String[] args) {
		
		try {
			
			List<Offer> listofOffers = AffiseOfferUtility.getSharedInstance().getListOfAlloffers(null);
			for (Iterator iterator = listofOffers.iterator(); iterator.hasNext();) {
				Offer offer = (Offer) iterator.next();
				List cataegoryList = offer.getFull_categories();
				String categoryListName = "";
				for (Iterator iterator2 = cataegoryList.iterator(); iterator2.hasNext();) {
					LinkedHashMap object = (LinkedHashMap) iterator2.next();
					categoryListName += (String)object.get("title");
					
				}
				System.err.println(categoryListName);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getDestinationFile() {
		return destinationFile;
	}

	public void setDestinationFile(String destinationFile) {
		this.destinationFile = destinationFile;
	}
	
	
	
	

}
