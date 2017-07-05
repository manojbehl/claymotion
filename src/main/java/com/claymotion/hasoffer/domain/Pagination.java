package com.claymotion.hasoffer.domain;

public class Pagination {
	private String per_page;
	private int total_count;
	private int page;
	private int next_page;

	public String getPer_page() {
		return per_page;
	}

	public void setPer_page(String per_page) {
		this.per_page = per_page;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNext_page() {
		return next_page;
	}

	public void setNext_page(int next_page) {
		this.next_page = next_page;
	}
}