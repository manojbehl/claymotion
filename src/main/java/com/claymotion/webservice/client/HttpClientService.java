package com.claymotion.webservice.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpClientService {

	private static HttpClientService httpClientService = null;

	private HttpClient httpClient = null;

	private HttpClientService() {

	}

	public synchronized static HttpClientService getSharedInstance() {

		if (httpClientService == null) {
			httpClientService = new HttpClientService();
		}

		return httpClientService;
	}

	private HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = HttpClientBuilder.create().build();

		}
		// System.err.println(restTemplate);
		return httpClient;
	}
	
	public Object executePostMethod(String url) throws Exception {

		HttpPost request = new HttpPost("https://mars.citruspay.com/txnapi/api/v1/txn/voidMultiMID");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("merchantAccessKey", "C02G8416DRJM"));
		urlParameters.add(new BasicNameValuePair("merchantTransactionId", "sss"));
		urlParameters.add(new BasicNameValuePair("signature", "sjsjsj"));

		request.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = getHttpClient().execute(request);
		System.out.println("Response Code : "
		                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		System.err.println(result.toString());
		return result.toString();
	}


	/**
	 * 
	 */
	public JSONObject executeGetMethod(String url) throws Exception {
		
		CloseableHttpClient httpClient = null;
		JSONObject jsonObject =  null;
		String result= null;
		try{
			httpClient = HttpClientBuilder.create().build();

			HttpGet request = new HttpGet(url);

			// add request header
			// request.addHeader("User-Agent", USER_AGENT);
			HttpResponse response = httpClient.execute(request);

			System.out.println("Response Code : "
					+ response.getStatusLine().getStatusCode());

			
			 result=  EntityUtils.toString(response.getEntity());
			 
			 System.err.println("result is :"+ result);
			
			jsonObject = new JSONObject(result);
			
			
		}finally{
			httpClient.close();
		}

		
		return jsonObject;
	}
	
	public static void main(String[] args) {
		HttpClientService servcie = httpClientService.getSharedInstance();
		try {
			servcie.executePostMethod("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
