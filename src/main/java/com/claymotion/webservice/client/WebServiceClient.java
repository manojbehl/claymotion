package com.claymotion.webservice.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class WebServiceClient {

	private static WebServiceClient webServiceClient = null;

	private RestTemplate restTemplate = null;

	private WebServiceClient() {

	}

	public synchronized static WebServiceClient getSharedInstance() {

		if (webServiceClient == null) {
			webServiceClient = new WebServiceClient();
		}

		return webServiceClient;
	}

	private RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setBufferRequestBody(false);
			restTemplate = new RestTemplate(factory);

			MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
			
			StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
			
			FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
			
			

			// MappingJacksonHttpMessageConverter
			// mappingJacksonHttpMessageConverter = new
			// MappingJacksonHttpMessageConverter();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
			messageConverters.add(mappingJackson2HttpMessageConverter);
			messageConverters.add(stringHttpMessageConverter);
			messageConverters.add(formHttpMessageConverter);
			restTemplate.setMessageConverters(messageConverters);
		}
		System.err.println(restTemplate);
		return restTemplate;
	}

	/**
	 * 
	 * @param url
	 * @param responseTypeClass
	 * @return
	 * @throws Exception
	 */
	public Object executeGetMethod(String url, Class responseTypeClass, Map<String, String> hashMap)
			throws Exception {

		URI uri = new URI(url);
		ResponseEntity responseEntity = getRestTemplate().exchange(url,HttpMethod.GET, 
				getEntityWithObjectOrHeader(null, hashMap),responseTypeClass);
		return responseEntity.getBody();
	}

	/**
	 * 
	 * @param url
	 * @param requestObject
	 * @param responseTypeClass
	 * @return
	 * @throws Exception
	 */
	public Object executePostMethod(String url, Object requestObject,
			Class responseTypeClass, Map<String, String> hashMap) throws Exception {

		URI uri = new URI(url);

		

		ResponseEntity responseEntity = getRestTemplate().postForEntity(uri,
				getEntityWithObjectOrHeader(requestObject, hashMap), responseTypeClass);
		
		System.err.println(responseEntity.getBody());

		return responseEntity.getBody();
	}

	public Object executePutMethod(String url, Object requestObject,
			Class responseTypeClass, Map<String, String> hashMap) throws Exception {

		URI uri = new URI(url);

		HttpEntity httpEntity = new HttpEntity(requestObject);

		ResponseEntity responseEntity = getRestTemplate().exchange(url,
				HttpMethod.PUT, getEntityWithObjectOrHeader(requestObject, hashMap), responseTypeClass);

		return responseEntity.getBody();
	}

	public Object executeDeleteMethod(String url, Object requestObject,
			Class responseTypeClass, Map<String, String> headers ) throws Exception {

		URI uri = new URI(url);

		HttpEntity httpEntity = new HttpEntity(requestObject);

		ResponseEntity responseEntity = getRestTemplate().exchange(url,
				HttpMethod.DELETE, getEntityWithObjectOrHeader(requestObject, headers), responseTypeClass);

		return responseEntity.getBody();
	}

	private HttpEntity getEntityWithObjectOrHeader(Object requestObject, Map<String, String> hashMap) {
		HttpHeaders headers = null;

		if (hashMap != null) {
			headers = new HttpHeaders();
			Iterator<String> iterator = hashMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = hashMap.get(key);
				headers.add(key, value);
			}
		}
		
		HttpEntity httpEntity = null;
		if(requestObject != null && headers != null){
			httpEntity = new HttpEntity(requestObject, headers);
		}else if( requestObject != null && headers == null){
			httpEntity = new HttpEntity(requestObject);
		}else if (requestObject == null && headers != null){
			httpEntity = new HttpEntity(headers);
		}
		System.err.println(httpEntity.getBody());
		return httpEntity;
	}


}
