package com.claymotion.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.claymotion.advertiser.batch.OfferCreationProcessing;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.hasoffer.domain.CreativeDomain;
import com.claymotion.hasoffer.domain.HasOfferData;
import com.claymotion.util.CreativeResponse;
import com.claymotion.util.OfferRequest;
import com.claymotion.util.OfferResponse;

@Component
public class ClayMotionServiceImpl implements IClayMotionService{

	Logger logger = Logger.getLogger(ClayMotionServiceImpl.class);

	
	@Autowired
	IClayMotionDAO clayMotionDAO;
	
	public List<OfferResponse> listOfOffer(OfferRequest offerRequest){
		
		List<OfferResponse> listOfOfferResponse = new ArrayList<OfferResponse>();
		
		List<HasOfferData> listOfHasOfferDatas =  clayMotionDAO.getHasOfferData(offerRequest);
		
		
		OfferResponse offerResponse = null;
		
		logger.info("listOfHasOfferDatas hasoffer data :"+ listOfHasOfferDatas.size());
		
		
		Map<String, HasOfferData> map = new HashMap<String, HasOfferData>();
		
		for (Iterator iterator = listOfHasOfferDatas.iterator(); iterator
				.hasNext();) {
			HasOfferData hasOfferData = (HasOfferData) iterator.next();
			
			String key = hasOfferData.getAndroidPackage() + hasOfferData.getAffiliateId() ;
			
			if(map.get(key) == null){
				if( hasOfferData.getListOfCreativeDomains().get(0).getAndroidPackage() == null){
					hasOfferData.getListOfCreativeDomains().clear();
				}
				map.put(key, hasOfferData);
			}else{
				HasOfferData offerData = map.get(key);
				
				List<CreativeDomain> listOfCreativeDomains = hasOfferData.getListOfCreativeDomains();
				
				for (Iterator iterator2 = listOfCreativeDomains.iterator(); iterator2
						.hasNext();) {
					CreativeDomain creativeDomain = (CreativeDomain) iterator2
							.next();
					if(creativeDomain.getAndroidPackage() != null){
						offerData.getListOfCreativeDomains().add(creativeDomain);
					}
				}
				
			}
		}
		
		logger.info("map values :"+ map.size());
		
		Collection<HasOfferData> collection = map.values();
		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			HasOfferData hasOfferData = (HasOfferData) iterator.next();
			
			offerResponse = new OfferResponse();
			
			offerResponse.setAffiliateId(hasOfferData.getAffiliateId());
			offerResponse.setAppIcon(hasOfferData.getIcon());
			
			offerResponse.setDeveloperName(hasOfferData.getDeveloper());
			offerResponse.setRating((float)hasOfferData.getStoreRating());
			offerResponse.setTitle(hasOfferData.getTitle());
			offerResponse.setAppSize(hasOfferData.getAppSize());
			offerResponse.setSubTitle(hasOfferData.getSubDescription());
			offerResponse.setCategory(hasOfferData.getCategory());
			offerResponse.setClickUrl(hasOfferData.getClickUrl());
			offerResponse.setDescription(hasOfferData.getDescription());
			
			offerResponse.setName(hasOfferData.getName());
			offerResponse.setOfferId(hasOfferData.getOfferId());
			offerResponse.setPackageName(hasOfferData.getAndroidPackage());
			offerResponse.setVersion(hasOfferData.getVersion());
			offerResponse.setReward(hasOfferData.getRevenueRate());
			
			offerResponse.setCountry(hasOfferData.getCountry());
			offerResponse.setPriority(hasOfferData.getPriority());
			offerResponse.setCategory(hasOfferData.getCategory());
			
			List<CreativeResponse> listOfCreativeResponses = new ArrayList<CreativeResponse>();
			
			CreativeResponse creativeResponse = null;
			
			List<CreativeDomain> listOfCreativeDomains =   hasOfferData.getListOfCreativeDomains();
			
			System.err.println(hasOfferData.getAndroidPackage());
			System.err.println(" list of Creative Domains"+ listOfCreativeDomains.size());
			
			for (Iterator iterator2 = listOfCreativeDomains.iterator(); iterator2
					.hasNext();) {
				
				creativeResponse = new CreativeResponse();
				CreativeDomain creativeDomain = (CreativeDomain) iterator2
						.next();
				
				BeanUtils.copyProperties(creativeDomain, creativeResponse);
				
				listOfCreativeResponses.add(creativeResponse);
			}
			
			offerResponse.setAppScreenShots(listOfCreativeResponses);
			
			listOfOfferResponse.add(offerResponse); 
			
		}
		
		Collections.sort(listOfOfferResponse, new ComparValues());
		
		return listOfOfferResponse;
		
	}
	
	private String getClickUrl(int offerId){
		try{
			return clayMotionDAO.getHasOfferLink(offerId);
		}catch(Exception ex){
			return "";
		}
	}
	
	class ComparValues implements Comparator<OfferResponse>{

		public int compare(OfferResponse o1, OfferResponse o2) {
			// TODO Auto-generated method stub
			if(o1.getPriority() > o2.getPriority())
				return 1;
			else if (o1.getPriority() < o2.getPriority())
				return -1;
			
			return 0;
		}
		
	}
	
}
