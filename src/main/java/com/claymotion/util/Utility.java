package com.claymotion.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

	static Map<String, Integer> advertiserMap = null;
	
	
	public static int getAdvertiserId(String advertiser){
		if(advertiserMap == null){
			advertiserMap = getAdvertiserList();
		}
		
		return advertiserMap.get(advertiser);
		
	}
	

	private  static Map<String, Integer> getAdvertiserList(){
		Map<String, Integer> mapForAdvertiser = new HashMap<String, Integer>();
		
		mapForAdvertiser.put("API Demo ADdv1", 259);
		mapForAdvertiser.put("API Demo ADdv2", 261);
		mapForAdvertiser.put("Glispa API", 269);
		mapForAdvertiser.put("Ironsource API", 271);
		mapForAdvertiser.put("Appnext API", 273);
		return mapForAdvertiser;
	}
	
	private  static Map<String, Integer> getAffiliateList(){
		Map<String, Integer> mapForAffiliate = new HashMap<String, Integer>();
		
		mapForAffiliate.put("API Demo Aff_1", 317);
		mapForAffiliate.put("API Demo Aff_2", 319);
		mapForAffiliate.put("Boxer Browser", 85);
		mapForAffiliate.put("Boxer Browser Notification", 83);
		mapForAffiliate.put("Intex Wap", 32);
		mapForAffiliate.put("Intex Store", 120);
		mapForAffiliate.put("Intex Store Notification", 276);
		mapForAffiliate.put("Karbonn Boxer", 210);
		mapForAffiliate.put("Micromax Funzone", 110);
		mapForAffiliate.put("Micromax Opera Browser", 51);
		mapForAffiliate.put("Zen Boxer", 246);
		mapForAffiliate.put("Zen Wap", 95);
		mapForAffiliate.put("Ziox Boxer Featurephones", 214);
		mapForAffiliate.put("Ziox Boxer Smartphones", 248);
		mapForAffiliate.put("Ziox Store", 278);
		
		
		
		
		
		return mapForAffiliate;
	}
}
