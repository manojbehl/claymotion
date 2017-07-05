package com.claymotion.hasoffer.domain;

public class Category {
	
	private String id;
	
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Category){
			return ((Category)obj).getTitle().equalsIgnoreCase(this.getTitle());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hashCode = this.getTitle().hashCode() + (this.getTitle().hashCode()*4/31); 
		// TODO Auto-generated method stub
		return hashCode;
	}

}
