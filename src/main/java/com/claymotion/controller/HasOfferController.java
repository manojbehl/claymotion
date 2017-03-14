package com.claymotion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.claymotion.advertiser.GlispaList;
import com.claymotion.advertiser.batch.AppNextExecution;
import com.claymotion.advertiser.batch.GlispaExecution;
import com.claymotion.advertiser.batch.OfferCreationProcessing;
import com.claymotion.dao.ClayMotionDAO;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.HasOfferUtility;
import com.claymotion.hasoffer.domain.HasOfferData;
import com.claymotion.hasoffer.domain.Offer;
import com.claymotion.service.IClayMotionService;
import com.claymotion.util.APIResponse;
import com.claymotion.util.OfferRequest;
import com.claymotion.util.OfferResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HasOfferController {
	
	@Autowired
	AppNextExecution appNextExecution;
	
	@Autowired
	GlispaExecution glispaExecution;
	
	@Autowired
	OfferCreationProcessing offerCreationProcessing;
	
	@Autowired
	IClayMotionService clayMotionService;


	@RequestMapping(value = "/executeTestService", method = RequestMethod.GET)
	@ResponseBody
	public APIResponse executeTestService() throws Exception {
	
		APIResponse apiResponse= new APIResponse();
		
		List<OfferResponse> str = new ArrayList<OfferResponse>();
		
		str.add(new OfferResponse());
		str.add(new OfferResponse());
		
		Map<String, List<OfferResponse>> map = new HashMap<String, List<OfferResponse>>();
		
		map.put("data", str);
		
		apiResponse.setStatus(map);
		
		
		 return apiResponse;
	}
	
	@Autowired
	ClayMotionDAO clayMotionDAO;

	@RequestMapping(value = "/executeTest", method = RequestMethod.GET)
	@ResponseBody
	public APIResponse executeTest() throws Exception {
	
		APIResponse apiResponse= new APIResponse();
		
		List<OfferResponse> str = new ArrayList<OfferResponse>();
		
		OfferResponse offerResponse = new OfferResponse();
		offerResponse.setName(clayMotionDAO.getOfferData());
		
		str.add(offerResponse);
//		str.add(new OfferResponse());
		
		Map<String, List<OfferResponse>> map = new HashMap<String, List<OfferResponse>>();
		
		map.put("data", str);
		
		apiResponse.setStatus(map);
		
		
		 return apiResponse;
	}


	@RequestMapping(value = "/offers", method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	@ResponseBody
	public APIResponse executeOfferResponse(
						@RequestParam(value="country", required=false) String country,
						@RequestParam(value="category", required=false) String category,
						@RequestParam(value="partnerName", required=false) String partnerName,
						@RequestParam(value="limit", required=false) String limit,
						HttpServletResponse servletResponse) throws Exception {
		
		
		OfferRequest offerRequest = new OfferRequest();
		offerRequest.setCategory(category);
		offerRequest.setCountry(country);
		offerRequest.setPartnerName(partnerName);
		try{
			offerRequest.setLimit(Integer.parseInt(limit));
			
		}catch(Exception ex){
			offerRequest.setLimit(0);
			
		}
		
		APIResponse apiResponse= new APIResponse();
		List<OfferResponse> listOfofferResponse =   new ArrayList<OfferResponse>();
		if(clayMotionDAO.getApplicationStatus().equalsIgnoreCase(HasOfferConstant.STATUS_PROCESSING)){
			apiResponse.setProcessingRecords(true);
		}else{
			apiResponse.setProcessingRecords(false);
			
			listOfofferResponse =  clayMotionService.listOfOffer(offerRequest);
			
		}
		
			
		Map<String, List<OfferResponse>> map = new HashMap<String, List<OfferResponse>>();
		
		map.put("data", listOfofferResponse);
		
		apiResponse.setStatus(map);
		
		servletResponse.setCharacterEncoding("UTF-8");
		
		
		return apiResponse;
	}

	
	@RequestMapping(value = "/offerCreation", method = RequestMethod.GET)
	@ResponseBody
	public void offerCreation() throws Exception {
	
		 offerCreationProcessing.actionPerformed();
	}
	
	@RequestMapping(value = "/execute", method = RequestMethod.GET)
	@ResponseBody
	public String executeAppnext() throws Exception {
	
		return appNextExecution.actionPerformed();
	}
	
	@RequestMapping(value = "/executeGlispa", method = RequestMethod.GET)
	@ResponseBody
	public String executeFlispa() throws Exception {
	
		return glispaExecution.actionPerformed();
	}
	
	@RequestMapping(value = "/hasoffer", method = RequestMethod.GET)
	@ResponseBody
	public String executeMethod() {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();

		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("advertiser_id", 259);
		hashMap.put("conversion_cap", 0);
		hashMap.put("default_payout", 100);
		hashMap.put("description", "iMobile+is+ICICI");
		hashMap.put("expiration_date", "2017-12-11+11:15:15");
		hashMap.put("is_private", 0);
		hashMap.put("is_seo_friendly_301", 0);
		hashMap.put("name", "Test_android_nonincent_in");
		hashMap.put("note", "https://appnext.hs.llnwd.net/banner/qHksxzqTJoSbngl_square.png");
		hashMap.put("offer_url", "https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D");
//		https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D
		hashMap.put("preview_url", "https://play.google.com/store/apps/details?id=+com.test.testurl");
		hashMap.put("require_approval", 0);
		hashMap.put("require_terms_and_conditions", 0);
//		hashMap.put("revenue_type", "cpc");
		hashMap.put("revenue_type", "cpa_flat");
		hashMap.put("max_payout", 10);
		
		
		hashMap.put("status", "active");
//		hashMap.put("return_object", 1);
		
		
		
		try {
			return hasOfferUtility.executeHasOfferAPI(HasOfferConstant.CREATE_METHOD, hashMap).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
//		return "execute Successfully";
	}
	
	
	@RequestMapping(value = "/generateLink", method = RequestMethod.GET)
	@ResponseBody
	public String generateLink() {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("status", "paused");

		try {
			return hasOfferUtility.executeHasOfferAPI(HasOfferConstant.GENERATE_LINK_METHOD, new Object[]{"2399","317"}).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/generatePixel/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String generatePixel(@PathVariable("id") String id) {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("status", "paused");

		try {
			return hasOfferUtility.executeHasOfferAPI(HasOfferConstant.GENERATE_PIXEL_METHOD, id).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	@RequestMapping(value = "/updateOffer/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updateoffer(@PathVariable("id") String id) {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("status", "active");

		try {
			return hasOfferUtility.executeHasOfferAPI(HasOfferConstant.UPDATE_METHOD, new Object[]{hashMap,id}).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	@RequestMapping(value = "/offerPixel/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String offerPixel(@PathVariable("id") String id) {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("offer_id", id);
		hashMap.put("type", "code");
		hashMap.put("code", "code");
		
		

		try {
			return hasOfferUtility.executeHasOfferAPI(HasOfferConstant.OFFER_PIXEL_METHOD, hashMap).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/findAllPixel", method = RequestMethod.GET)
	@ResponseBody
	public String findAllPixel() {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();
		
		

		try {
			return hasOfferUtility.executeHasOfferAPI(HasOfferConstant.FIND_ALL_PIXEL, null).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/pixelAllowedType/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String pixelAllowedType(@PathVariable("id") String id) {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("offer_id", id);

		try {
			return hasOfferUtility.executeHasOfferAPI(HasOfferConstant.PIXEL_ALLOWED_TYPE_METHOD, id).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/getofferforAffiliate/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getoFferforAffiiate(@PathVariable("id") String id) {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();
		
		JSONObject jsonObject = null;
		try {
			jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.FIND_ALL_AFFILIATE_METHOD, id);
			
			
			JSONObject dataJsonObject = jsonObject.getJSONObject("response");
			
			return dataJsonObject.toString();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
//		return "execute Successfully";
	}
	
	@RequestMapping(value = "/getOffer", method = RequestMethod.GET)
	@ResponseBody
	public List<Offer> getOffer() throws Exception {
		HasOfferUtility hasOfferUtility = new HasOfferUtility();

		Map<String, Object> hashMapForAdvertiser = new HashMap<String, Object>();
		hashMapForAdvertiser.put("status","active");

		
	
		/*hashMap.put("conversion_cap", 0);
		hashMap.put("default_payout", 100);
		hashMap.put("description", "iMobile+is+ICICI");
		hashMap.put("expiration_date", "2017-12-11+11:15:15");
		hashMap.put("is_private", 0);
		hashMap.put("is_seo_friendly_301", 0);
		hashMap.put("name", "Test_android_nonincent_in");
		hashMap.put("note", "https://appnext.hs.llnwd.net/banner/qHksxzqTJoSbngl_square.png");
		hashMap.put("offer_url", "https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D");
//		https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D
		hashMap.put("preview_url", "https://play.google.com/store/apps/details?id=+com.test.testurl");
		hashMap.put("require_approval", 0);
		hashMap.put("require_terms_and_conditions", 0);
//		hashMap.put("revenue_type", "cpc");
		hashMap.put("revenue_type", "cpa_flat");
		hashMap.put("max_payout", 10);
		
		
		hashMap.put("status", "active");*/
//		hashMap.put("return_object", 1);
		
		List<Offer> listOfOffers = new ArrayList<Offer>();
		JSONObject jsonObject = null;
		
		try {
			jsonObject = hasOfferUtility.executeHasOfferAPI(HasOfferConstant.FIND_ALL_METHOD, hashMapForAdvertiser);
			
			
			
			JSONObject dataJsonObject = jsonObject.getJSONObject("response");
			
//			return dataJsonObject.toString();
			
			try{
				dataJsonObject = jsonObject.getJSONObject("response").getJSONObject("data");
			}catch(Exception ex){
				if(ex.getMessage().contains("is not a JSONArray")){
					listOfOffers = new ArrayList<Offer>();
					return listOfOffers;
				}
			}
			
			
			Iterator<String> iterator = dataJsonObject.keys();
			JSONObject offerDataObject = null;
			while(iterator.hasNext()){
				String key = iterator.next();
				if(key.equalsIgnoreCase("errorMessage") || key.equalsIgnoreCase("errors")) continue;
				
				offerDataObject = dataJsonObject.getJSONObject(key).getJSONObject("Offer");
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				Offer offer = objectMapper.readValue(offerDataObject.toString(), Offer.class);
				 
				listOfOffers.add(offer);
			}
			
			return listOfOffers;
			
			
//			return dataJsonObject.toString();
			/*
			Iterator<String> iterator = dataJsonObject.keys();
			JSONObject offerDataObject = null;
			while(iterator.hasNext()){
				String key = iterator.next();
				
				offerDataObject = dataJsonObject.getJSONObject(key).getJSONObject("Offer");
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				
				
				Offer offer = objectMapper.readValue(offerDataObject.toString(), Offer.class);
				listOfOffers.add(offer);
			}*/

			
			
//			JSONObject responseJsonObject = jsonObject.getJSONObject("response");
			
//			return responseJsonObject.isNull("data") ? " Not Present " : responseJsonObject.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listOfOffers;
//			return e.getMessage();
		}
//		return listOfOffers;
//		return "execute Successfully";
	}
	
	public static void main(String[] args) {
		HasOfferController controller = new HasOfferController();
		System.err.println(controller.executeMethod());
	}
}
