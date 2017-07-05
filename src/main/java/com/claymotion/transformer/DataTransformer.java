package com.claymotion.transformer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.claymotion.advertiser.AppNext;
import com.claymotion.advertiser.Creative;
import com.claymotion.advertiser.Glispa;
import com.claymotion.advertiser.IronSource;
import com.claymotion.advertiser.IronSourceCreative;
import com.claymotion.api.response.Offer;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.hasoffer.domain.AffiseCreative;
import com.claymotion.hasoffer.domain.CreativeDomain;
import com.claymotion.util.AffiseOfferUtility;

@Component
public class DataTransformer {

	public List<AdvertiserRawData> convertIntoRawData(List<AppNext> listOfAppNext) {
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
			// advertiserRawData.setCurrency(appNext.getc);
			advertiserRawData.setDescription(appNext.getDesc());
			// advertiserRawData.setId(resultSet.getInt("id"));
			advertiserRawData.setName(appNext.getTitle());
			// advertiserRawData.setOfferId(resultSet.getInt("offer_id"));
			if (appNext.getRevenueRate() != null && appNext.getRevenueRate().trim().length() > 0)
				advertiserRawData.setRevenueRate(Double.parseDouble(appNext.getRevenueRate()));
			advertiserRawData.setRevenueType(appNext.getRevenueType());
			advertiserRawData.setStoreRating(Double.parseDouble(appNext.getStoreRating()));

			listOfAdvertiser.add(advertiserRawData);
		}

		return listOfAdvertiser;

	}
	
	private void saveImage(String imgURl, Offer affiseOffer){
		String imageName = "";
		/*if(imgURl.indexOf("?") != -1)
			imageName = imgURl.substring(imgURl.lastIndexOf("/") + 1, imgURl.indexOf("?"));
		else*/
			imageName = imgURl.substring(imgURl.lastIndexOf("/") + 1);
			
//		String destinationFile = "/usr/images/AppNext/" + imageName;

		String destinationFile = AffiseOfferUtility.getSharedInstance().getDestinationFile() +  "AppNext/" + imageName;

		if (imageExists(imgURl, destinationFile)){

			AffiseCreative affiseCreative = new AffiseCreative();
			affiseCreative.setFile_name(destinationFile);
			((List)affiseOffer.getCreatives()).add(affiseCreative);
		}
	}
	
	
	public List<Offer> convertIntoOfferData(List listOfRecords, String advertiserId, String country)  {
		List<Offer> listOfAdvertiser = new ArrayList<Offer>();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);

		String time = simpleDateFormat.format(calendar.getTime());

		Offer affiseOffer = null;
		for (Iterator iterator = listOfRecords.iterator(); iterator.hasNext();) {
			Object record = iterator.next();

			affiseOffer = new Offer();

			affiseOffer.setAdvertiser(advertiserId);
			if (record instanceof AppNext) {
				AppNext appNext = (AppNext) record;

				affiseOffer.setTitle(appNext.getTitle() + "_Android_NonIncent_" + appNext.getCountry());

				affiseOffer.setDescription(appNext.getDesc());

				affiseOffer.getCategories().add(appNext.getCategories());

				affiseOffer.setDomain_url(
						"https://admin.appnext.com/appLink.aspx?b=139993&e=160480&q={clickid}&subid={pid}_{sub1}");
				affiseOffer.setUrl(
						"https://admin.appnext.com/appLink.aspx?b=139993&e=160480&q={clickid}&subid={pid}_{sub1}");
				// https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D

				if (appNext.getRevenueRate() != null && appNext.getRevenueRate().trim().length() > 0) {
					double value = (Double.parseDouble(appNext.getRevenueRate()) * 60 / 100);
					affiseOffer.setTotal_cap(value);

				}

				affiseOffer.getSources().add(appNext.getCampaignId());

				affiseOffer.getCountries().add(appNext.getCountry());

				// affiseOffer.setUrl("https://play.google.com/store/apps/details?id="+appNext.getAndroidPackage());

				affiseOffer
						.setPreview_url("https://play.google.com/store/apps/details?id=" + appNext.getAndroidPackage());

				affiseOffer
						.setUrl_preview("https://play.google.com/store/apps/details?id=" + appNext.getAndroidPackage());
				

				// Creatives Addition
				
				if(affiseOffer.getCreatives() == null){
					affiseOffer.setCreatives(new ArrayList());
				}
				
				if(appNext.getUrlImg().trim().length() > 0){
					saveImage(appNext.getUrlImg(), affiseOffer);
				}
				if(appNext.getUrlImgWide().trim().length() > 0){
					saveImage(appNext.getUrlImgWide(), affiseOffer);
				}
				if(appNext.getUrlVideo().trim().length() > 0){
					saveImage(appNext.getUrlVideo(), affiseOffer);
				}
				if(appNext.getUrlVideo30Sec().trim().length() > 0){
					saveImage(appNext.getUrlVideo30Sec(), affiseOffer);
				}

				 

				// affiseOffer.setCreatives(creatives);

			} else if (record instanceof Glispa) {
				Glispa glispa = (Glispa) record;
				//
				// if(glispa.getMobile_platform() == null |
				// !glispa.getMobile_platform().equalsIgnoreCase("Android"))
				// continue;
				//
				// //offer.setAdvertiser_id(advertiserId);
				// if(glispa.getCountries() != null){
				// country = glispa.getCountries().get(0);
				// }
				// offer.setName(glispa.getName() + "_Android_NonIncent_" +
				// country);
				// offer.setPreview_url("https://play.google.com/store/apps/details?id="+
				// glispa.getMobile_app_id());
				// System.err.println(" glispa.getPayout_amount() :"+
				// glispa.getPayout_amount());
				//// if(glispa.getPayout_amount() != null ){
				// double value = (glispa.getPayout_amount() * 60/100 );
				// offer.setDefault_payout("" + value);
				//
				// System.err.println("offer.getDefault_payout():"+
				// offer.getDefault_payout());
				//
				//// }
				//
				// offer.setCategory(glispa.getCategory());
				//// "http://trk.glispa.com/c/sPBAIAYsrP-vTi9OS0I3x4O3JsRecNdcSYOI6HdGt8o/CF"
				// offer.setOffer_url("http://trk.glispa.com/c/sCtAYAbQrKOvey_pS0g39YOLJpRet9evSa6InHdat8o/CF?m.gaid={unid}&subid1={transaction_id}&pub.subid2={affiliate_id}&placement={offer_ref}");
				//
				// offer.setDescription(glispa.getShort_description());
				//
				// if(glispa.getEnd_date() != null &&
				// glispa.getEnd_date().trim().length() >0){
				//
				// SimpleDateFormat simpleDateFormat1 = new
				// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				//
				// try {
				// Date date = simpleDateFormat1.parse(glispa.getEnd_date());
				// offer.setExpiration_date(simpleDateFormat.format(date));
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				//
				//
				//
				// }else{
				// offer.setExpiration_date(time);
				//
				// }
				//
				// offer.setImages(glispa.getImages());
				// offer.setIcon(glispa.getIcon());
				//
				// offer.setNote(glispa.getIcon_256());
				//// https%3A%2F%2Fadmin.appnext.com%2FappLink.aspx%3Fb%3D143949%26e%3D160480%26q%3D%7Btransaction_id%7D%26subid%3D%7Baffiliate_id%7D
				// offer.setMax_payout( "" + glispa.getPayout_amount());
				// offer.setAndroidPackage(glispa.getMobile_app_id());

				// System.err.println(" Name being Set :"+ offer.getName());

				affiseOffer.setTitle(glispa.getName() + "_Android_NonIncent_" + country);

				affiseOffer.setDescription(glispa.getDescription());

				affiseOffer.setAdvertiser(advertiserId);

				affiseOffer.getCategories().add(glispa.getCategory());

				affiseOffer.setDomain_url(
						"http://trk.glispa.com/c/sOdA1QYirM6vey-3S0Y3vIOfJjpecNc1Sb-ItXdEtwI/CF?m.gaid={sub5}&subid1={clickid}&subid2={pid}_{sub1}&placement={pid}.{rand}");

				affiseOffer.setUrl(
						"http://trk.glispa.com/c/sOdA1QYirM6vey-3S0Y3vIOfJjpecNc1Sb-ItXdEtwI/CF?m.gaid={sub5}&subid1={clickid}&subid2={pid}_{sub1}&placement={pid}.{rand}");

				double value = (glispa.getPayout_amount() * 60 / 100);
				affiseOffer.setTotal_cap(value);

				affiseOffer.getSources().add(glispa.getCampaign_id());

				affiseOffer.getCountries().addAll(glispa.getCountries());

				// affiseOffer.setUrl("https://play.google.com/store/apps/details?id="+glispa.getMobile_app_id());

				affiseOffer
						.setPreview_url("https://play.google.com/store/apps/details?id=" + glispa.getMobile_app_id());

				affiseOffer
						.setUrl_preview("https://play.google.com/store/apps/details?id=" + glispa.getMobile_app_id());
				
				/*if(affiseOffer.getCreatives() == null){
					affiseOffer.setCreatives(new ArrayList());
				}
				if (imageExists(appNext.getUrlImg())){

					AffiseCreative affiseCreative = new AffiseCreative();
					affiseCreative.setFile_name(appNext.getUrlImg());
					((List)affiseOffer.getCreatives()).add(affiseCreative);
				}
				if (imageExists(appNext.getUrlImgWide())){

					AffiseCreative affiseCreative = new AffiseCreative();
					affiseCreative.setFile_name(appNext.getUrlImgWide());
					((List)affiseOffer.getCreatives()).add(affiseCreative);
				}
				if (imageExists(appNext.getUrlVideo())){

					AffiseCreative affiseCreative = new AffiseCreative();
					affiseCreative.setFile_name(appNext.getUrlVideo());
					((List)affiseOffer.getCreatives()).add(affiseCreative);
				}
				if (imageExists(appNext.getUrlVideo30Sec())){

					AffiseCreative affiseCreative = new AffiseCreative();
					affiseCreative.setFile_name(appNext.getUrlVideo30Sec());
					((List)affiseOffer.getCreatives()).add(affiseCreative);
				}
*/
			} else if (record instanceof IronSource) {
				IronSource ironSource = (IronSource) record;

				affiseOffer.setTitle(ironSource.getTitle() + "_Android_NonIncent_" + country);

				affiseOffer.setDescription(ironSource.getDescription());

				affiseOffer.getCategories().add(ironSource.getCategory());
				
				double value = (Float.parseFloat(ironSource.getBid()) * 60 / 100);

				affiseOffer.setTotal_cap(value);

				affiseOffer.getSources().add(ironSource.getCampaign_id());

				affiseOffer.setAdvertiser(advertiserId);

				affiseOffer.getCountries().add(country);

				affiseOffer.setDomain_url(
						"https://click.apprevolve.com/static/b8921ef2805443d19b1c9ccff95f1451/97571/4565059/17bcc1de38fa411d?timestamp=1495162598&bundleId=com.dci.magzter&strategyId=103&"
								+ "fallbackId=com.dci.magzter&p1=clickid&v1={clickid}&aff_sub1={pid}_(sub1}&deviceId={sub5}");

				affiseOffer
						.setUrl("https://click.apprevolve.com/static/b8921ef2805443d19b1c9ccff95f1451/97571/4565059/17bcc1de38fa411d?timestamp=1495162598&bundleId=com.dci.magzter&strategyId=103&"
								+ "fallbackId=com.dci.magzter&p1=clickid&v1={clickid}&aff_sub1={pid}_(sub1}&deviceId={sub5}");

				// affiseOffer.setUrl("https://play.google.com/store/apps/details?id="+ironSource.getPackageName());

				affiseOffer
						.setPreview_url("https://play.google.com/store/apps/details?id=" + ironSource.getPackageName());

				affiseOffer
						.setUrl_preview("https://play.google.com/store/apps/details?id=" + ironSource.getPackageName());

				List<IronSourceCreative> ironSourceCreatives = ironSource.getCreatives();

				// String str = "";
				
				AffiseCreative affiseCreative = null;
				for (Iterator iterator2 = ironSourceCreatives.iterator(); iterator2.hasNext();) {
					IronSourceCreative ironSourceCreative = (IronSourceCreative) iterator2.next();
					String str = ironSourceCreative.getType() + ":" + ironSourceCreative.getUrl() + "\n";
					affiseCreative = new AffiseCreative();
					
					String imageName = ironSourceCreative.getUrl().substring(ironSourceCreative.getUrl().lastIndexOf("/") + 1);

//					String destinationFile = "C:/Manoj/images/IronSource/" + imageName;

					String destinationFile = AffiseOfferUtility.getSharedInstance().getDestinationFile() +"IronSource/" + imageName;

				
					try{
						if (imageExists(ironSourceCreative.getUrl(), destinationFile )){
								affiseCreative.setFile_name(destinationFile);
		
//								affiseCreative.setFile_name(ironSourceCreative.getUrl());
		
								affiseCreative.setType(ironSourceCreative.getType());
								if(affiseOffer.getCreatives() == null){
									affiseOffer.setCreatives(new ArrayList());
								}
								((List)affiseOffer.getCreatives()).add(affiseCreative);
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}

			}

			// offer.setProtocol("server");

			listOfAdvertiser.add(affiseOffer);
		}

		return listOfAdvertiser;

	}

	public static boolean imageExists(String imageUrl, String destinationFile )  {
		InputStream is = null;
		OutputStream os = null;

		try {
			System.err.println("imageURL:"+ imageUrl);
			URL url = new URL(imageUrl);
			is = url.openStream();
			os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}

	public List<CreativeDomain> convertIntoCreativeDomain(List<Creative> listOfCreative) {

		List<CreativeDomain> listOfCreativeDomain = new ArrayList<CreativeDomain>();
		CreativeDomain creativeDomain = null;
		for (Iterator iterator = listOfCreative.iterator(); iterator.hasNext();) {
			Creative creative = (Creative) iterator.next();
			creativeDomain = new CreativeDomain();
			creativeDomain.setId(creative.getId());
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
			creativeDomain.setOfferId(creative.getOfferId());

			listOfCreativeDomain.add(creativeDomain);

		}

		return listOfCreativeDomain;
	}

	public List<CreativeDomain> convertIntoCreativeDomain(List<AdvertiserCreative> listOfAdvertiserCreatives,
			Offer offer) {

		List<CreativeDomain> listOfCreativeDomain = new ArrayList<CreativeDomain>();
		CreativeDomain creativeDomain = null;
		for (Iterator iterator = listOfAdvertiserCreatives.iterator(); iterator.hasNext();) {
			AdvertiserCreative creative = (AdvertiserCreative) iterator.next();
			creativeDomain = new CreativeDomain();
			creativeDomain.setWidthHeight(creative.getKey());
			creativeDomain.setUploadPath(creative.getValue());
			creativeDomain.setAndroidPackage(offer.getPackageName());
			creativeDomain.setCountryCode(creative.getCountryCode());
			// creativeDomain.setPartner_id(partner_id);

			listOfCreativeDomain.add(creativeDomain);

		}

		return listOfCreativeDomain;
	}

	@Autowired
	public static void main(String[] args) throws ParseException {
		 byte[] data = null;
	        ByteArrayOutputStream bas = null;
		try {
            URL u = new URL(
                    "https://cx.ssacdn.com/image/fetch/f_jpg,f_auto,q_80/https://supersonicads-a.akamaihd.net/banners/c_2814677_12947_51.png");
            
            URL url = new URL("https://cx.ssacdn.com/image/fetch/f_jpg,f_auto,q_80/https://supersonicads-a.akamaihd.net/banners/c_1816515_1_2_86082_49.png");
            InputStream is = url.openStream();
            System.err.println(is.available());
            /*HttpURLConnection con1 = (HttpURLConnection) u.openConnection();
            con1.setAllowUserInteraction(true);
            con1.setRequestMethod("GET");
            con1.connect();
            InputStream is = con1.getInputStream();
            BufferedImage imgToServe = null;
            if (is != null) {
                imgToServe = ImageIO.read(is);
            }
            bas = new ByteArrayOutputStream();
            ImageIO.write(imgToServe, "jpg", bas);

            File f = new File("C:\\img.jpg");
            ImageIO.write(imgToServe, "jpg", f);

//            data = bas.toByteArray();
            byte[] encodedImage = Base64.encodeBase64(bas.toByteArray());
            String str = new String(encodedImage);
            System.err.println(str);*/
//            String str = "";
            /*for (int i = 0; i < data.length; i++) {
                str = str + toBinary(data[i]);
            }
            System.out.println("string is "+ str);*/

        } catch (Exception he) {
        	he.printStackTrace();
        } 

		
	}
	
	 private static String toBinary(byte b) {
	        StringBuilder sb = new StringBuilder("00000000");

	        for (int bit = 0; bit < 8; bit++) {
	            if (((b >> bit) & 1) > 0) {
	                sb.setCharAt(7 - bit, '1');
	            }
	        }
	        return (sb.toString());
	    }

}
