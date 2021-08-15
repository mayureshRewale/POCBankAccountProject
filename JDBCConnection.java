package com.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
	
	static Connection con = null;
	
	public static Connection openConnection() {
		try {
			JDBCProperty.loadProperties();
			Class.forName(JDBCProperty.driverClass);
			//System.out.println("Driver Loaded");
			System.out.println("------------------Process-------------------");
			
			con = DriverManager.getConnection(JDBCProperty.url, JDBCProperty.username, JDBCProperty.password);
			//System.out.println("Connetion Established");
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return con;
	}
	
	public static void closeConnection() {
		
		try {
			
			if(con!=null) {
				con.close();
				//System.out.println("Connetion Closed");
				System.out.println("------------------Process-------------------");
			}
			
		}catch(Exception e) {
			
		}
		
	}

}
