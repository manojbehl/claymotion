package com.claymotion.service;

import java.util.List;
import java.util.Map;

import com.claymotion.response.OfferRequest;
import com.claymotion.response.OfferResponse;

public interface IClayMotionService {
	
	public List<OfferResponse> listOfOffer(OfferRequest offerRequest);
	
	public Map<String, String> getCountryAndIPAddress();


}
