package com.example.dexter;

public class ICE_Contact {
	
	private int id;
	
	private String name;
	
	private String phone_number;
	
	public ICE_Contact() {
		// TODO Auto-generated constructor stub
	}
	
	public ICE_Contact(String name, String phone_number) {
		super();
		this.name = name;
		this.phone_number = phone_number;
	}
	
	public ICE_Contact(int i, String name, String phone_number) {
		super();
		this.id = id;
		this.name = name;
		this.phone_number = phone_number;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}


	
}
