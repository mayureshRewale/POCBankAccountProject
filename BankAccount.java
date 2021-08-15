package com.neosoft.bankv3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Customer {

	String name, address, username, pass;
	Long mobile;
	int inDeposit;
	ArrayList<String> transList;

	public Customer(String name, String address, String username, Long mobile, int inDeposit, String pass,
			ArrayList list) {
		super();
		this.name = name;
		this.address = address;
		this.username = username;
		this.mobile = mobile;
		this.inDeposit = inDeposit;
		this.pass = pass;
		this.transList = list;
	}

}

public class BankAccount {
	String DB_url = "jdbc:mysql://localhost:3306/mydb";
	String DB_user = "root";
	String DB_password = "Mayuresh@2000";
	String driver = "com.mysql.cj.jdbc.Driver";

	// static ResultSet rst;

	static String logUname = null;

	static Scanner sc;

	static ArrayList<Object> users = new ArrayList<>();

	static Map<String, Customer> cust = new HashMap<String, Customer>();

	public static void main(String[] args) throws SQLException {

		int choice;
		sc = new Scanner(System.in);

		while (true) {
			System.out.println("--------------");
			System.out.println("BANK OF MYBANK");
			System.out.println("--------------");

			System.out.println("\n1. Register Account \n2. Login \n3. Update Account \n4. Exit \n");

			System.out.println("Enter Choice : ");
			choice = sc.nextInt();

			if (choice == 1) {
				registerUser();
			} else if (choice == 2) {
				loginUser();
			} else if (choice == 3) {
				// updateUser();
			} else if (choice == 4) {
				break;
			}

		}

	}

	public static void registerUser() throws SQLException {

		String name, address, username, password;
		Long mobile;
		int deposit;
		String mobExp1 = ".*\\d{10}";
		String passExp = "^(.*[A-Z].*[a-z].*[0-9].*[!@#$%^&*_]).{8,16}";

		ArrayList<String> transList = new ArrayList<>(5);

		System.out.println("Enter Name : ");
		name = sc.next();

		System.out.println("Enter username : ");
		username = sc.next();

		System.out.println("enter Password : ");
		password = sc.next();
		/*
		 * while (!password.matches(passExp)) { password = sc.next(); }
		 */

		System.out.println("Enter Address : ");
		address = sc.next();

		System.out.println("Enter Contact number : ");
		mobile = sc.nextLong();
		while (!mobile.toString().matches(mobExp1)) {
			System.out.println("Enter Valid Contact No. : ");
			mobile = sc.nextLong();
		}

		System.out.println("Enter initial Deposit Amount : ");
		deposit = sc.nextInt();
		transList.add("Initial Deposit of Rs." + deposit + " on " + java.time.LocalDate.now() + " at "
				+ java.time.LocalTime.now());

		cust.put(username, new Customer(name, address, username, mobile, deposit, password, transList));
		insertNewCustomer(name, address, username, mobile, deposit, password);

	}

	private static void insertNewCustomer(String name, String address, String username, Long mobile, int deposit,
			String password) throws SQLException {

		String DB_url = "jdbc:mysql://localhost:3306/mybank";
		String DB_user = "root";
		String DB_password = "Mayuresh@2000";
		String driver = "com.mysql.cj.jdbc.Driver";

		Connection con = null;

		PreparedStatement pst = null;
		String query = "INSERT INTO customerdetails VALUES(?,?,?,?,?,?)";

		try {
			Class.forName(driver);
			System.out.println("Driver Loaded");

			con = DriverManager.getConnection(DB_url, DB_user, DB_password);
			System.out.println("Connection Established");

			/**/
			pst = con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, name);
			pst.setString(3, address);
			pst.setString(4, mobile.toString());
			pst.setString(5, password);
			pst.setInt(6, deposit);

			System.out.println("Inserted :" + pst.executeUpdate());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				pst.close();
			}
			if (con != null) {
				con.close();
				System.out.println("Connection Closed");
			}
		}

	}

	public static void loginUser() throws SQLException {

		String un, upass;
		boolean uf = false;
		Customer loggedIn = null;

		System.out.println("Enter username : ");
		un = sc.next();

		System.out.println("Enter Password : ");
		upass = sc.next();

		getUserByPass(un, upass);

		/*
		 * if (uf) { while (uf) { int choice;
		 * System.out.println("-----------------------------------------");
		 * System.out.println("WELCOME " + logUname.toUpperCase());
		 * System.out.println("-----------------------------------------");
		 * 
		 * System.out.println(
		 * "\n1. Deposit \n2. Tansfer \n3. Last 5 transcations \n4. UserInformation \n5. Logout \nEnter Your Choice"
		 * ); choice = sc.nextInt();
		 * 
		 * if (choice == 1) { // Deposit System.out.println("Enter Amount : "); int
		 * amount = sc.nextInt(); //loggedIn.inDeposit = (int) (loggedIn.inDeposit +
		 * amount);
		 * 
		 * int bal = rst.getInt("balance");
		 * 
		 * System.out.println("Balance : " + bal);
		 * 
		 * } else if (choice == 2) { // Transfer boolean bl = false;
		 * System.out.println("Enter Amount : "); int tamount = sc.nextInt(); if
		 * (loggedIn.inDeposit > tamount) {
		 * System.out.println("Enter Payee Username : "); String tUname = sc.next();
		 * 
		 * for (Map.Entry<String, Customer> pair : cust.entrySet()) {
		 * 
		 * if (pair.getKey().equals(tUname)) { bl = true; pair.getValue().inDeposit =
		 * (int) (pair.getValue().inDeposit + tamount); loggedIn.inDeposit = (int)
		 * (loggedIn.inDeposit - tamount); System.out.println("Balance : " +
		 * loggedIn.inDeposit); } } if (!bl) {
		 * System.out.println("Payee Username Not Found"); } bl = false; } else {
		 * System.out.println("IIInsufficient Balance in Acount"); } } else if (choice
		 * == 3) {
		 * 
		 * } else if (choice == 4) {
		 * 
		 * } else if (choice == 5) { loggedIn = null; uf = false; break; }
		 * 
		 * } } else { System.out.println("Incorrect Username Or Password"); }
		 */

	}

	private static void getUserByPass(String un, String upass) throws SQLException {
		String DB_url = "jdbc:mysql://localhost:3306/mybank";
		String DB_user = "root";
		String DB_password = "Mayuresh@2000";
		String driver = "com.mysql.cj.jdbc.Driver";

		boolean uf = false;

		Connection con = null;

		PreparedStatement pst = null;
		String query = "SELECT * FROM customerdetails WHERE username=? AND pass=?";

		try {
			Class.forName(driver);
			System.out.println("Driver Loaded");

			con = DriverManager.getConnection(DB_url, DB_user, DB_password);
			System.out.println("Connection Established");

			/**/

			pst = con.prepareStatement(query);
			pst.setString(1, un);
			pst.setString(2, upass);
			ResultSet infoRst = pst.executeQuery();

			if (infoRst.next()) {

				System.out.println("User Found");
				logUname = infoRst.getString(1);
				// return true;

			} else {
				System.out.println("Incorrect Username Or Password");
			}

			while (infoRst != null) {

				int choice;
				System.out.println("-----------------------------------------");
				System.out.println("WELCOME " + logUname.toUpperCase());
				System.out.println("-----------------------------------------");

				System.out.print("\n1. Deposit "
						+ "\n2. Tansfer "
						+ "\n3. Last 5 transcations "
						+ "\n4. UserInformation "
						+ "\n5. Logout "
						+ "\nEnter Your Choice : ");
				choice = sc.nextInt();

				if (choice == 1) {
					// Deposit
					System.out.print("Enter Amount : ");
					int amount = sc.nextInt();

					int bal = infoRst.getInt("balance");

					PreparedStatement upPst = con
							.prepareStatement("UPDATE customerdetails SET balance=? WHERE username=?");
					upPst.setInt(1, (bal + amount));
					upPst.setString(2, logUname);

					if (upPst.executeUpdate() == 1) {
						pst = con.prepareStatement("SELECT * FROM customerdetails WHERE username = ?");
						pst.setString(1, logUname);
						infoRst = pst.executeQuery();
						if(infoRst.next())
							bal = infoRst.getInt("balance");
						System.out.println("Balance : " + infoRst.getInt("balance"));
					}

				} else if (choice == 2) {
					// Transfer
					boolean bl = false;
					System.out.println("Enter Amount : ");
					int tamount = sc.nextInt();
					/*
					 * if (loggedIn.inDeposit > tamount) {
					 * System.out.println("Enter Payee Username : "); String tUname = sc.next();
					 * 
					 * for (Map.Entry<String, Customer> pair : cust.entrySet()) {
					 * 
					 * if (pair.getKey().equals(tUname)) { bl = true; pair.getValue().inDeposit =
					 * (int) (pair.getValue().inDeposit + tamount); //loggedIn.inDeposit = (int)
					 * (loggedIn.inDeposit - tamount); //System.out.println("Balance : " +
					 * loggedIn.inDeposit); } } if (!bl) {
					 * System.out.println("Payee Username Not Found"); } bl = false; } else {
					 * System.out.println("IIInsufficient Balance in Acount"); }
					 */
					if(infoRst.getInt("balance") > tamount) {
						System.out.println("sufficient Balance in Acount");
						
						System.out.println("Enter Payee Username : "); 
						String tUname = sc.next();
						
						PreparedStatement pPst = con.prepareStatement("SELECT * FROM customerdetails WHERE username = ?");
						pPst.setString(1, tUname);
						if(pPst.executeQuery().next()) {
							System.out.println("Payee Username Found");
						}
						
					}
				} else if (choice == 3) {

				} else if (choice == 4) {

				} else if (choice == 5) {
					// loggedIn = null;
					// uf = false;
					break;
				}

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				pst.close();
			}
			if (con != null) {
				con.close();
				System.out.println("Connection Closed");
			}
		}
		// return false;
	}

}
