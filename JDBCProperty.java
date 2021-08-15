package com.jdbc.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class JDBCProperty {

	static String driverClass;
	static String url;
	static String password;
	static String username;

	public static void loadProperties() {

		Properties prop = null;
		InputStream in = null;

		try {

			prop = new Properties();
			in = new FileInputStream("resources/database.properties");

			prop.load(in);

			driverClass = prop.getProperty("MYSQLJDBC.driver");
			url = prop.getProperty("MYSQLJDBC.url");
			username = prop.getProperty("MYSQLJDBC.usename");
			password = prop.getProperty("MYSQLJDBC.password");

		} catch (Exception e) {

		} finally {
			try {

				if (in != null) {
					in.close();
				}
				if (prop != null) {
					prop.clear();
				}

			} catch (Exception e) {

			}
		}

	}

	/*
	 * public static void main(String args[]) { JDBCProperty.loadProperties();
	 * 
	 * System.out.println(JDBCProperty.driverClass);
	 * System.out.println(JDBCProperty.url);
	 * System.out.println(JDBCProperty.username);
	 * System.out.println(JDBCProperty.password);
	 * 
	 * }
	 */

}
