package com.claymotion.service;

import java.util.List;

import com.claymotion.util.OfferRequest;
import com.claymotion.util.OfferResponse;

public interface IClayMotionService {
	
	public List<OfferResponse> listOfOffer(OfferRequest offerRequest);

}
