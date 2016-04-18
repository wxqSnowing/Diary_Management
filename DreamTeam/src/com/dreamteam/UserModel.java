package com.dreamteam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserModel {
	private String name = null;
	private String password = null;

	public UserModel(String name, String password) {
		this.name = name;
		this.password = password;
	}
	public UserModel(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}

