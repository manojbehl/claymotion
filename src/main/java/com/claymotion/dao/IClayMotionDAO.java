package com.claymotion.dao;

import java.util.List;

import com.claymotion.api.response.Offer;
import com.claymotion.api.response.OfferPrioirty;
import com.claymotion.hasoffer.domain.Advertiser;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.hasoffer.domain.Affiliate;
import com.claymotion.hasoffer.domain.AffiliateOffer;
import com.claymotion.hasoffer.domain.CountryIPList;
import com.claymotion.hasoffer.domain.CreativeDomain;
import com.claymotion.hasoffer.domain.HasOfferData;
import com.claymotion.response.OfferRequest;

public interface IClayMotionDAO {

	
	public List<AdvertiserRawData> getListOfApp(int advertiserId, String country);
	
	public void addRawData(Offer advertiserRawData);
	
	public void addOfferData(Offer offer);

	public void updateOfferData(Offer offer);

	public void updateStatus(AdvertiserRawData advertiserRawData);
	
	public void addGenerateLinkData(AffiliateOffer affiliateOffer);
	
	public void updateGenerateLink(int offerId);
	
	public List<HasOfferData> getUnGeneratedOffer();
	
	public List<Affiliate> getAffiliate();
	
	public List<Advertiser> getAdvertiser();
	
	public void deleteAlreadyGeneratedLinks();
	
	public void deleteAlreadyCreatedOffers();

	public void deleteAlreadyCreatedCreatives();
	
	public void deleteAlreadyCreatedPriority();

	
//	public void addGenerateLinkDataList(final List<AffiliateOffer> listOfAffiliateOffer);
	
	public void addHasOfferData(final List<HasOfferData> listOfHasOfferData) ;
	
	public void addCreativeData(final List<CreativeDomain> listOfCreativeDomains) ;
	
	public List<HasOfferData> getHasOfferData(OfferRequest offerRequest);
	
	public String getHasOfferLink(int offerId);
	
	public void addPriorityData(final List<OfferPrioirty> listOfOfferPriority);
	
	public void addAdvertiserCreative(final List<AdvertiserCreative> listOfAdvertiserCreatives);
	
	public List<AdvertiserCreative> getAdvertiserCreative(int offerId);


	public boolean doesCreativeExists(int offerId);
	
	public String getOfferData();
	
	public void updateApplicationStatus(String status);
	
	public String getApplicationStatus();
	
	public List<CountryIPList> getCountryIPList();

	public void insertAdvertiserData(final List<Advertiser> listOfAdvertiser);
	
	public List<Advertiser> getAllExistingAdvertiser();

}
