package com.claymotion.hasoffer.domain;

import java.util.List;

import com.claymotion.api.response.Offer;

public class ListOfOffers {

	private int status;

	private List<Offer> offers;

	private Pagination pagination;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
