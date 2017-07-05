package com.claymotion.advertiser;

import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppNext {

	private String title;
	private String desc;
	private String urlImg;
	private String urlImgWide;
	private String urlApp;
	private String androidPackage;
	private String revenueType;
	private String revenueRate;
	private String categories;
	private String idx;
	@JsonFormat(with=JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	private List<String> targetedDevices;
	private String targetedOSver;
	private String urlVideo;
	private String urlVideoHigh;
	private String urlVideo30Sec;
	private String urlVideo30SecHigh;
	private String bannerId;
	private String campaignId;
	private String country;
	private String campaignType;
	private String supportedVersion;
	private String storeRating;
	private String storeDownloads;
	private String appSize;
	private String campaignGoal;
	private String buttonText;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUrlImg() {
		return urlImg;
	}
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
	public String getUrlImgWide() {
		return urlImgWide;
	}
	public void setUrlImgWide(String urlImgWide) {
		this.urlImgWide = urlImgWide;
	}
	public String getUrlApp() {
		return urlApp;
	}
	public void setUrlApp(String urlApp) {
		this.urlApp = urlApp;
	}
	public String getAndroidPackage() {
		return androidPackage;
	}
	public void setAndroidPackage(String androidPackage) {
		this.androidPackage = androidPackage;
	}
	public String getRevenueType() {
		return revenueType;
	}
	public void setRevenueType(String revenueType) {
		this.revenueType = revenueType;
	}
	public String getRevenueRate() {
		return revenueRate;
	}
	public void setRevenueRate(String revenueRate) {
		this.revenueRate = revenueRate;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	/*public String getTargetedDevices() {
		return targetedDevices;
	}
	public void setTargetedDevices(String targetedDevices) {
		this.targetedDevices = targetedDevices;
	}*/
	public String getTargetedOSver() {
		return targetedOSver;
	}
	public void setTargetedOSver(String targetedOSver) {
		this.targetedOSver = targetedOSver;
	}
	public String getUrlVideo() {
		return urlVideo;
	}
	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}
	public String getUrlVideoHigh() {
		return urlVideoHigh;
	}
	public void setUrlVideoHigh(String urlVideoHigh) {
		this.urlVideoHigh = urlVideoHigh;
	}
	public String getUrlVideo30Sec() {
		return urlVideo30Sec;
	}
	public void setUrlVideo30Sec(String urlVideo30Sec) {
		this.urlVideo30Sec = urlVideo30Sec;
	}
	public String getUrlVideo30SecHigh() {
		return urlVideo30SecHigh;
	}
	public void setUrlVideo30SecHigh(String urlVideo30SecHigh) {
		this.urlVideo30SecHigh = urlVideo30SecHigh;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getSupportedVersion() {
		return supportedVersion;
	}
	public void setSupportedVersion(String supportedVersion) {
		this.supportedVersion = supportedVersion;
	}
	public String getStoreRating() {
		return storeRating;
	}
	public void setStoreRating(String storeRating) {
		this.storeRating = storeRating;
	}
	public String getStoreDownloads() {
		return storeDownloads;
	}
	public void setStoreDownloads(String storeDownloads) {
		this.storeDownloads = storeDownloads;
	}
	public String getAppSize() {
		return appSize;
	}
	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}
	
	
	public String toString(){
		return " Title  [ " + (getTitle() != null ? getTitle() : "" ) + "]" +
				" desc  [ " + (getDesc() != null ? getDesc() : "" ) + "]" +
				" urlImg  [ " + (getUrlImg() != null ? getUrlImg() : "" ) + "]" +
				" urlImgWide  [ " + (getUrlImgWide() != null ? getUrlImgWide() : "" ) + "]" +
				" urlApp  [ " + (getUrlApp() != null ? getUrlApp() : "" ) + "]" +
				" androidPackage  [ " + (getAndroidPackage() != null ? getAndroidPackage() : "" ) + "]" +
				" revenueType  [ " + (getRevenueType() != null ? getRevenueType() : "" ) + "]" +
				" revenueRate  [ " + (getRevenueRate() != null ? getRevenueRate(): "" ) + "]" +
				" categories  [ " + (getCategories() != null ? getCategories() : "" ) + "]" +
				" idx  [ " + (getIdx() != null ? getIdx() : "" ) + "]" +
				" targetedDevices  [ " + (getTargetedDevices() != null ? getTargetedDevices() : "" ) + "]" +
				" targetedSover  [ " + (getTargetedOSver() != null ? getTargetedOSver() : "" ) + "]" +
				" urlVideo  [ " + (getUrlVideo() != null ? getUrlVideo() : "" ) + "]" +
				" urlVideoHigh  [ " + (getUrlVideoHigh() != null ? getUrlVideo() : "" ) + "]" +
				" bannerId  [ " + (getBannerId() != null ? getBannerId() : "" ) + "]" +
				" compaignId  [ " + (getCampaignId() != null ? getCampaignId() : "" ) + "]" +
				" country  [ " + (getCountry() != null ? getCountry() : "" ) + "]" +
				" compaignType  [ " + (getCampaignType() != null ? getCampaignType() : "" ) + "]" +
				" supportedversion  [ " + (getSupportedVersion() != null ? getSupportedVersion() : "" ) + "]" +
				" storeRating  [ " + (getStoreRating() != null ? getStoreRating() : "" ) + "]" +
				" storeDownloads  [ " + (getStoreDownloads() != null ? getStoreDownloads() : "" ) + "]" +
				" appSize  [ " + (getAppSize() != null ? getAppSize() : "" ) + "]" ;
				
	}
	
    public boolean equals(Object obj) {
    	if(obj instanceof  AppNext){
    		return (this.getAndroidPackage().equals(((AppNext)obj).getAndroidPackage()) &&
    				this.getCountry().equals( ((AppNext)obj).getCountry()));
    	}
    	
    	return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = 3;
		hash = 7 * hash + this.getAndroidPackage().hashCode() + this.getCountry().hashCode();
//		hash = 7 * hash + this.getCompaignId().hashCode();
		return hash;
    }
    
public boolean isInServiceHours() throws Exception {
		
		int currentHour,currentMin,startHour,startMin,stopHour,stopMin;
		
			String startTime = "00:10";// getCheckinSession().getConfiguration().getString("KioskStartTime1").substring(0,5);
			String stopTime = "04:00";// getCheckinSession().getConfiguration().getString("KioskStopTime1").substring(0,5);
			
			Calendar currentCal = Calendar.getInstance();//  checkinSession.getCurrentLocationDateTime().toGregorianCalendar();			
			try{
				currentHour = currentCal.get(Calendar.HOUR_OF_DAY);
				currentMin = currentCal.get(Calendar.MINUTE);
				startHour = Integer.parseInt(startTime.substring(0, 2));
				startMin = Integer.parseInt(startTime.substring(3, 5));
				stopHour = Integer.parseInt(stopTime.substring(0, 2));
				stopMin = Integer.parseInt(stopTime.substring(3, 5));
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
				
				return false;
			}
			System.err.println("currentHour:"+ currentHour);
			System.err.println("currentMin:"+ currentMin);

			System.err.println("startHour:"+ startHour);
			System.err.println("startMin:"+ startMin);

			System.err.println("stopHour:"+ stopHour);
			System.err.println("currentMin:"+ currentMin);

			
			if (currentHour == startHour){
				if (currentMin >= startMin) {
					return true;
				} else {
					return false;
				}
			}
			else if (currentHour == stopHour){
				if (currentMin < stopMin) {
					return true;
				} else {
					return false;
				}
			}
			else if (currentHour > startHour && currentHour < stopHour){
				return true;
			}
			else {
				return false;
			}			
		}



	
	public static void main(String[] args) throws Exception {
		AppNext test = new AppNext();
		System.err.println(test.isInServiceHours());
		/*WebServiceClient webServiceClient = WebServiceClient.getSharedInstance();
		Map<String ,String> hashMap = new HashMap<String, String>();
		hashMap.put("Content-Type", "application/json");
		
		try {
			Object apps238 = webServiceClient.executeGetMethod("https://admin.appnext.com/offerWallApi.aspx?id=5a5c5ce6-d955-4a9c-8fa2-27c04fc68e13&cnt=200&type=json&ip=59.91.219.238",
					Object.class, hashMap);
			LinkedHashMap linkedHashMap = (LinkedHashMap)apps238;
			
			Collection collections = linkedHashMap.values();
			for (Iterator iterator = collections.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				List listOfObjects =  (ArrayList)object;
				for (Iterator iterator2 = listOfObjects.iterator(); iterator2.hasNext();) {
					Object object1 = (Object) iterator2.next();
					LinkedHashMap<String, Object> appNextValues = (LinkedHashMap<String, Object>)object1;
					System.err.println(appNextValues.get("title"));
					System.err.println(appNextValues.get("desc"));
					System.err.println(object1.getClass());
				}
				
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
/*		try {
			Map<String, List<Apps>> apps = (Map<String, List<Apps>>)webServiceClient.executeGetMethod("https://admin.appnext.com/offerWallApi.aspx?id=5a5c5ce6-d955-4a9c-8fa2-27c04fc68e13&cnt=200&type=json&ip=59.91.219.238",
					Map.class, hashMap);
			for (Iterator iterator = apps.keySet().iterator(); iterator.hasNext();) {
				String type = (String) iterator.next();
				System.err.println(type);
				List<Apps> listOfRecords = apps.get(type);
				System.err.println(listOfRecords.getClass());
				for (Iterator iterator2 = listOfRecords.iterator(); iterator2
						.hasNext();) {
					Object apps2 = (Object) iterator2.next();
					System.err.println(apps2.toString());
				}
			}
//			System.err.println(apps.getClass());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			AppNextList apps238 = (AppNextList)webServiceClient.executeGetMethod("https://admin.appnext.com/offerWallApi.aspx?id=5a5c5ce6-d955-4a9c-8fa2-27c04fc68e13&cnt=200&type=json&ip=59.91.219.238",
					AppNextList.class, hashMap);
//			System.err.println(hashApps.getApps());
			AppNextList apps202 = (AppNextList)webServiceClient.executeGetMethod("https://admin.appnext.com/offerWallApi.aspx?id=5a5c5ce6-d955-4a9c-8fa2-27c04fc68e13&cnt=200&type=json&ip=79.91.219.202",
					AppNextList.class, hashMap);

			for (Iterator iterator = apps202.getApps().iterator(); iterator.hasNext();) {
				Apps appValue = (Apps) iterator.next();
				System.err.println(appValue.getAndroidPackage());

			}

			System.err.println("-------------------------------------------------------------");
			for (Iterator iterator = apps238.getApps().iterator(); iterator.hasNext();) {
				Apps appValue = (Apps) iterator.next();
				System.err.println(appValue.getAndroidPackage());

			}

			List<AppNext> appsToAdd = new ArrayList<AppNext>();
			List<AppNext> appsToPause = new ArrayList<AppNext>();
			
			for (Iterator iterator = apps202.getApps().iterator(); iterator.hasNext();) {
				AppNext appValue = (AppNext) iterator.next();
				
				if(!apps238.getApps().contains(appValue)){
						appsToAdd.add(appValue);
				}
				
				System.err.println(appValue.getAndroidPackage());
				System.err.println(apps238.getApps().contains(appValue));
				
			}
			
			for (Iterator iterator = apps238.getApps().iterator(); iterator.hasNext();) {
				AppNext appValue = (AppNext) iterator.next();
				
				if(!apps202.getApps().contains(appValue)){
						appsToPause.add(appValue);
				}
				
				System.err.println(appValue.getAndroidPackage());
				System.err.println(apps238.getApps().contains(appValue));
				
			}
			
			System.err.println(" Apps to Add ------------------------------------------------");
			for (Iterator iterator2 = appsToAdd.iterator(); iterator2
					.hasNext();) {
				AppNext apps = (AppNext) iterator2.next();
				System.err.println(apps.getAndroidPackage());
			}

			System.err.println(" Apps to Pause---------------------------------------------------");
			for (Iterator iterator2 = appsToPause.iterator(); iterator2
					.hasNext();) {
				AppNext apps = (AppNext) iterator2.next();
				System.err.println(apps.getAndroidPackage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}
	public List<String> getTargetedDevices() {
		return targetedDevices;
	}
	public void setTargetedDevices(List<String> targetedDevices) {
		this.targetedDevices = targetedDevices;
	}
	public String getBannerId() {
		return bannerId;
	}
	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public String getCampaignType() {
		return campaignType;
	}
	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}
	public String getCampaignGoal() {
		return campaignGoal;
	}
	public void setCampaignGoal(String campaignGoal) {
		this.campaignGoal = campaignGoal;
	}
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	
}

