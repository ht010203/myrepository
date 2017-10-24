package com.smarty.space.user.model;

public class Book {
	
	private Long bookid;
	
	private String name ;
	
	private Integer number;
	
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Long getBookid() {
		return bookid;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "Book [bookid=" + bookid + ", name=" + name + ", number="
				+ number + "]";
	}
	
	
}
