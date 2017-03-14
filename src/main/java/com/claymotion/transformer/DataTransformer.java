package com.claymotion.transformer;

import java.awt.BufferCapabilities.FlipContents;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.text.Utilities;

import org.springframework.stereotype.Component;

import com.claymotion.advertiser.AppNext;
import com.claymotion.advertiser.Creative;
import com.claymotion.advertiser.Glispa;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.hasoffer.domain.CreativeDomain;
import com.claymotion.hasoffer.domain.Offer;
import com.claymotion.util.Utility;

@Component
public class DataTransformer {
	
	
	public List<AdvertiserRawData> convertIntoRawData(List<AppNext> listOfAppNext){
		List<AdvertiserRawData> listOfAdvertiser = new ArrayList<AdvertiserRawData>();

		AdvertiserRawData advertiserRawData = null;
		for (Iterator iterator = listOfAppNext.iterator(); iterator.hasNext();) {
			AppNext appNext = (AppNext) iterator.next();
			
			advertiserRawData = new AdvertiserRawData();
			
			
			advertiserRawData.setAdvertiser(259);
			advertiserRawData.setAndroidPackage(appNext.getAndroidPackage());
			advertiserRawData.setAppUrl(appNext.getUrlApp());
			advertiserRawData.setCampaignId(Integer.parseInt(appNext.getCampaignId()));
			advertiserRawData.setCategory(appNext.getCategories());
			advertiserRawData.setCountry(appNext.getCountry());
//			advertiserRawData.setCurrency(appNext.getc);
			advertiserRawData.setDescription(appNext.getDesc());
//			advertiserRawData.setId(resultSet.getInt("id"));
			advertiserRawData.setName(appNext.getTitle());
//			advertiserRawData.setOfferId(resultSet.getInt("offer_id"));
			if(appNext.getRevenueRate() !=null && appNext.getRevenueRate().trim().length() > 0)
				advertiserRawData.setRevenueRate(Double.parseDouble(appNext.getRevenueRate()));
			advertiserRawData.setRevenueType(appNext.getRevenueType());
			advertiserRawData.setStoreRating(Double.parseDouble(appNext.getStoreRating()));
			
			
			listOfAdvertiser.add(advertiserRawData);
		}
		
		return listOfAdvertiser;
		
	}
	
	
	public List<Offer> convertIntoOfferData(List listOfRecords, int advertiserId){
		List<Offer> listOfAdvertiser = new ArrayList<Offer>();
		

		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+1);
		
		String time = simpleDateFormat.format(calendar.getTime());

		Offer offer= null;
		for (Iterator iterator = listOfRecords.iterator(); iterator.hasNext();) {
			Object record =  iterator.next();
			
			offer = new Offer();
			
			
			if( record instanceof AppNext){
				AppNext appNext = (AppNext)record;
				
				offer.setAdvertiser_id(advertiserId);
				offer.setName(appNext.getTitle() + "_Android_NonIncent_" + appNext.getCountry() );
				offer.setPreview_url("https://play.google.com/store/apps/details?id="+ appNext.getAndroidPackage());
				if(appNext.getRevenueRate() != null && appNext.getRevenueRate().trim().length() > 0){
					double value = ( Double.parseDouble(appNext.getRevenueRate()) * 60/100);
					offer.setDefault_payout("" + value);
					
				}
				
				offer.setDescription(appNext.getDesc());
				
				offer.setExpiration_date(time);
				
				offer.setNote(appNext.getUrlApp());
				offer.setOffer_url("https://admin.appnext.com/appLink.aspx?b=143949&e=160480&q={transaction_id}&subid={affiliate_id}");
//				https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D
				offer.setMax_payout(appNext.getRevenueRate());
				
				offer.setAndroidPackage(appNext.getAndroidPackage());
				
				Map<String, List<String>> hashMap = new HashMap<String, List<String>>();
				
				List<String> listOfString = new ArrayList<String>();
				
				listOfString.add(appNext.getUrlImg());
				
				hashMap.put("urlImg", listOfString);

				offer.setCategory(appNext.getCategories());
				
				List<String> listOfString1 = new ArrayList<String>();
				
				listOfString1.add(appNext.getUrlImgWide());

				hashMap.put("urlImgWide", listOfString1);
				
				offer.setImages(hashMap);
				
				
			}
			else if( record instanceof Glispa){
				Glispa glispa = (Glispa)record;
				
				if(glispa.getMobile_platform() == null | !glispa.getMobile_platform().equalsIgnoreCase("Android")) continue;
				
				offer.setAdvertiser_id(advertiserId);
				String country = "";
				if(glispa.getCountries() != null){
					country = glispa.getCountries().get(0);
				}
				offer.setName(glispa.getName() + "_Android_NonIncent_" + country);
				offer.setPreview_url("https://play.google.com/store/apps/details?id="+ glispa.getMobile_app_id());
				System.err.println(" glispa.getPayout_amount() :"+ glispa.getPayout_amount());
//				if(glispa.getPayout_amount() != null ){
					double value = (glispa.getPayout_amount() * 60/100 );
					offer.setDefault_payout("" + value);
					
					System.err.println("offer.getDefault_payout():"+ offer.getDefault_payout());
					
//				}
					
				offer.setCategory(glispa.getCategory());
//				"http://trk.glispa.com/c/sPBAIAYsrP-vTi9OS0I3x4O3JsRecNdcSYOI6HdGt8o/CF"
				offer.setOffer_url("http://trk.glispa.com/c/sCtAYAbQrKOvey_pS0g39YOLJpRet9evSa6InHdat8o/CF?m.gaid={unid}&subid1={transaction_id}&pub.subid2={affiliate_id}&placement={offer_ref}");
				
				offer.setDescription(glispa.getShort_description());
				
				if(glispa.getEnd_date() != null && glispa.getEnd_date().trim().length() >0){
					
					SimpleDateFormat  simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					
					try {
						Date date = simpleDateFormat1.parse(glispa.getEnd_date());
						offer.setExpiration_date(simpleDateFormat.format(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}else{
					offer.setExpiration_date(time);
					
				}
				
				offer.setImages(glispa.getImages());
				offer.setIcon(glispa.getIcon());
				
				offer.setNote(glispa.getIcon_256());
//				https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D
				offer.setMax_payout( "" + glispa.getPayout_amount());
				offer.setAndroidPackage(glispa.getMobile_app_id());
				
				System.err.println(" Name being Set :"+ offer.getName());
			}
			
			

			
			offer.setProtocol("server");
			
			
			
			listOfAdvertiser.add(offer);
		}
		
		return listOfAdvertiser;
		
	}
	
	public List<CreativeDomain> convertIntoCreativeDomain(List<Creative> listOfCreative){
		
		List<CreativeDomain> listOfCreativeDomain = new ArrayList<CreativeDomain>();
		CreativeDomain creativeDomain = null;
		for (Iterator iterator = listOfCreative.iterator(); iterator.hasNext();) {
			Creative creative = (Creative) iterator.next();
			creativeDomain = new CreativeDomain();
			creativeDomain.setId(creative.get_id());
			creativeDomain.setAndroidPackage(creative.getApp_id());
			creativeDomain.setDescription(creative.getDescription());
			creativeDomain.setFilename(creative.getFilename());
			creativeDomain.setHeight(creative.getHeight());
			creativeDomain.setIs_active(creative.isIs_active());
			creativeDomain.setMimetype(creative.getMimetype());
			creativeDomain.setPartner_id(creative.getPartner_id());
			creativeDomain.setSize(creative.getSize());
			creativeDomain.setTitle(creative.getTitle());
			creativeDomain.setUploadPath(creative.getUploadPath());
			creativeDomain.setVersion(creative.get__v());
			creativeDomain.setWidth(creative.getWidth());
			
			
			listOfCreativeDomain.add(creativeDomain);
			
		}
		
		return listOfCreativeDomain;
	}
	
	public List<CreativeDomain> convertIntoCreativeDomain(List<AdvertiserCreative> listOfAdvertiserCreatives, Offer offer){
		
		List<CreativeDomain> listOfCreativeDomain = new ArrayList<CreativeDomain>();
		CreativeDomain creativeDomain = null;
		for (Iterator iterator = listOfAdvertiserCreatives.iterator(); iterator.hasNext();) {
			AdvertiserCreative creative = (AdvertiserCreative) iterator.next();
			creativeDomain = new CreativeDomain();
			creativeDomain.setWidthHeight(creative.getKey());
			creativeDomain.setUploadPath(creative.getValue());
			creativeDomain.setAndroidPackage(offer.getAndroidPackage());
			creativeDomain.setCountryCode(creative.getCountryCode());
//			creativeDomain.setPartner_id(partner_id);
			
			listOfCreativeDomain.add(creativeDomain);
			
		}
		
		return listOfCreativeDomain;
	}

	
	public static void main(String[] args) throws ParseException {
		/*String str = "2032-12-31T00:00:00+00:00";

		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Date date = simpleDateFormat.parse(str);
		
		SimpleDateFormat  simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		System.err.println(simpleDateFormat1.format(date));*/
		
		double one = 0.05;
		
		Glispa glisap = new Glispa();
		glisap.setPayout_amount(0.05);
		
//		double conversion = (60/100)
		
		double value = (glisap.getPayout_amount() * 60/100);
		System.err.println(value);
		
	}

}
