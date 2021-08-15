package com.jdbc.model;

import java.util.ArrayList;

public class Customer {
	
	String name, address, username, pass;
	String mobile;
	int inDeposit;
	public Customer(String name, String address, String username, String pass, String mobile, int inDeposit) {
		super();
		this.name = name;
		this.address = address;
		this.username = username;
		this.pass = pass;
		this.mobile = mobile;
		this.inDeposit = inDeposit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getInDeposit() {
		return inDeposit;
	}
	public void setInDeposit(int inDeposit) {
		this.inDeposit = inDeposit;
	}
	
	

}
