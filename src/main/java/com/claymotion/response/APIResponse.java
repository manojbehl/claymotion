package com.claymotion.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class APIResponse {
	
	
	private Map<String, List<OfferResponse>> status;
	
	private boolean processingRecords = false;

	public Map<String, List<OfferResponse>> getStatus() {
		return status;
	}

	public void setStatus(Map<String, List<OfferResponse>> status) {
		this.status = status;
	}

	public boolean isProcessingRecords() {
		return processingRecords;
	}

	public void setProcessingRecords(boolean processingRecords) {
		this.processingRecords = processingRecords;
	}

}
