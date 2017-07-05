package com.claymotion.advertiser.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.claymotion.advertiser.Advertiser;
import com.claymotion.dao.IClayMotionDAO;
import com.claymotion.util.AffiseOfferUtility;

@Component
public class AdvertiserExecution {

	@Autowired
	IClayMotionDAO clayMotionDAO;

	//@Scheduled(cron="0 0/59 * * * ?")
	public void actionPerformed() throws Exception {

		List<Advertiser> listOfAdvertiser = AffiseOfferUtility.getSharedInstance().getListOfAdvertiser();

		List<com.claymotion.hasoffer.domain.Advertiser> existingAdvertiserList = clayMotionDAO
				.getAllExistingAdvertiser();
		
		List<com.claymotion.hasoffer.domain.Advertiser> filteredList = new ArrayList<com.claymotion.hasoffer.domain.Advertiser>();
		
		for (Iterator iterator = listOfAdvertiser.iterator(); iterator.hasNext();) {
			Advertiser advertiser = (Advertiser) iterator.next();
			boolean doesAlreadyExisting = false;
			
			for (Iterator iterator2 = existingAdvertiserList.iterator(); iterator2.hasNext();) {
				com.claymotion.hasoffer.domain.Advertiser existingAdvertiser = (com.claymotion.hasoffer.domain.Advertiser) iterator2.next();
				
				if(advertiser.getAdvertiserId().equalsIgnoreCase(existingAdvertiser.getId())){
					doesAlreadyExisting = true;
					break;
				}
				
			}
			
			if(!doesAlreadyExisting){
				com.claymotion.hasoffer.domain.Advertiser advertiserToInsret = new com.claymotion.hasoffer.domain.Advertiser();
				advertiserToInsret.setId(advertiser.getAdvertiserId());
				advertiserToInsret.setName(advertiser.getTitle());
				advertiserToInsret.setPriority(0);
				filteredList.add(advertiserToInsret);
			}
		}

		clayMotionDAO.insertAdvertiserData(filteredList);
	}
}
