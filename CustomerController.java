package com.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jdbc.model.Customer;

public class CustomerController {

	public static Customer updatedetails(String un) throws SQLException {

		Customer customer = null;

		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "SELECT * FROM customerdetails WHERE username=?";

		try {
			pst = con.prepareStatement(query);
			pst.setString(1, un);
			rs = pst.executeQuery();

			if (rs != null) {

				while (rs.next()) {
					customer = new Customer(rs.getString("cname"), rs.getString("address"), rs.getString("username"),
							rs.getString("pass"), rs.getString("mobile"), rs.getInt("balance"));
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			JDBCConnection.closeConnection();
		}

		return customer;

	}

	public static Customer login(String un, String pass) throws SQLException {

		Customer customer = null;

		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "SELECT * FROM customerdetails WHERE username=? AND pass = ?";

		try {
			pst = con.prepareStatement(query);
			pst.setString(1, un);
			pst.setString(2, pass);
			rs = pst.executeQuery();

			if (rs != null) {

				while (rs.next()) {
					customer = new Customer(rs.getString("cname"), rs.getString("address"), rs.getString("username"),
							rs.getString("pass"), rs.getString("mobile"), rs.getInt("balance"));
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			JDBCConnection.closeConnection();
		}

		return customer;

	}

	public static void register(Customer customer) throws SQLException {

		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "INSERT INTO customerdetails VALUES(?,?,?,?,?,?)";

		try {

			pst = con.prepareStatement(query);
			pst.setString(1, customer.getUsername());
			pst.setString(2, customer.getName());
			pst.setString(3, customer.getAddress());
			pst.setString(4, customer.getMobile());
			pst.setString(5, customer.getPass());
			pst.setInt(6, customer.getInDeposit());

			System.out.println("Inserted :" + pst.executeUpdate());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			JDBCConnection.closeConnection();
		}

	}

	public static void addDeposit(String un, int amount) throws SQLException {

		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "UPDATE customerdetails SET balance=(balance+?) WHERE username=?";

		try {

			pst = con.prepareStatement(query);
			pst.setInt(1, amount);
			pst.setString(2, un);
			System.out.println("Updated :" + pst.executeUpdate());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			JDBCConnection.closeConnection();
		}

	}

	public static void transferAmount(Customer customer, String unP, int am) throws SQLException {

		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		String tquery = "UPDATE customerdetails SET balance=(balance+?) WHERE username=?";
		String uquery = "UPDATE customerdetails SET balance=(balance-?) WHERE username=?";

		/**/
		
		try {

			if (customer.getInDeposit() < am) {
				System.out.println("Insufficient Balance");
			} else {

				if (checkUserExist(unP)) {
					
					// Dedacting Amount from Payee
					pst1 = con.prepareStatement(uquery);
					pst1.setInt(1, am);
					pst1.setString(2, customer.getUsername());
					System.out.println("Deducted Amount :" + pst1.executeUpdate());

					// adding amount to user
					pst2 = con.prepareStatement(tquery);
					pst2.setInt(1, am);
					pst2.setString(2, unP);
					System.out.println("Added Amount :" + pst2.executeUpdate());
					
				}else {
					System.out.println("User Not Found");
				}
				
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pst1 != null) {
				pst1.close();
				pst2.close();
			}
			JDBCConnection.closeConnection();
		}

	}

	private static boolean checkUserExist(String unP) throws SQLException {

		Customer user = null;
		boolean bl = false;

		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "SELECT * FROM customerdetails WHERE username=?";

		try {
			pst = con.prepareStatement(query);
			pst.setString(1, unP);
			// pst.setString(2, pass);
			rs = pst.executeQuery();

			if (rs != null) {
				//System.out.println("Payee Found");
				return true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			JDBCConnection.closeConnection();
		}

		return false;

	}

}
