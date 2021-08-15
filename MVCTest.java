package com.jdbc.mvcs;

import java.sql.SQLException;
import java.util.Scanner;

import com.jdbc.controller.CustomerController;
import com.jdbc.model.Customer;

public class MVCTest {
	
	static Scanner sc;

	public static void main(String[] args) throws SQLException {
		
		int choice;
		sc = new Scanner(System.in);

		while (true) {
			System.out.println("--------------");
			System.out.println("BANK OF MYBANK");
			System.out.println("--------------");

			System.out.println("\n1. Register Account \n2. Login \n3. Update Account \n4. Exit \n");

			System.out.print("Enter Choice : ");
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

	private static void loginUser() throws SQLException {
		String un, pass;
		
		System.out.print("\nEnter Username : ");
		un = sc.next();
		System.out.print("\nEnter Password : ");
		pass = sc.next();
		Customer customer = CustomerController.login(un, pass);
		
		if(customer != null) {
			System.out.print("\n Login Successful \n");
			
			while(customer != null) {
				int choice;
				
				System.out.println("-----------------------------------------");
				System.out.println("WELCOME " + customer.getName().toUpperCase());
				System.out.println("-----------------------------------------");
				
				System.out.print("\n1. Deposit "
						+ "\n2. Tansfer "
						+ "\n3. Last 5 transcations "
						+ "\n4. UserInformation "
						+ "\n5. Logout "
						+ "\nEnter Your Choice : ");
				
				choice = sc.nextInt();
				
				if(choice==1) {
					System.out.println("-------------------------------------");
					
					int amount;
					System.out.print("\nEnter Amount : ");
					amount = sc.nextInt();
					CustomerController.addDeposit(customer.getUsername(), amount);
					customer = CustomerController.updatedetails(customer.getUsername());
					System.out.print("\nUpdated Balance : " + customer.getInDeposit() + "\n");
					
					System.out.println("-------------------------------------\n");
					
				}else if(choice==2) {
					System.out.println("-------------------------------------");
					
					String pUser;
					int am;
					System.out.print("\nEnter Username of Payee : ");
					pUser = sc.next();
					System.out.print("\nEnter Amount : ");
					am = sc.nextInt();
					CustomerController.transferAmount(customer, pUser, am);
					
					System.out.print("\nBalance before deduction : " + customer.getInDeposit() + " \n ");
					customer = CustomerController.updatedetails(customer.getUsername());
					System.out.print("\nBalance afterr deduction : " + customer.getInDeposit() + " \n ");
					
					System.out.println("-------------------------------------");
					
				}else if(choice==3) {
					
				}else if(choice==4) {
					System.out.println("-------------------------------------");
					
					System.out.println("Name : " + customer.getName());
					System.out.println("Address : " + customer.getAddress());
					System.out.println("Contact No. : " + customer.getMobile());
					System.out.println("Username : " + customer.getUsername());
					System.out.println("Password : " + customer.getPass());
					System.out.println("Balance : " + customer.getInDeposit());
					
					System.out.println("-------------------------------------");
				}else if(choice==5) {
					
					customer = null;
					//break;
					
				}
				
			}
			
			
		}else {
			System.out.print("\nIncorrect userid or password\n");
		}
		
	}

	private static void registerUser() throws SQLException {
		
		String un, name, add, mobile, pass;
		int bal;
		
		System.out.print("\nEnter Name : ");
		name = sc.next();
		System.out.print("\nEnter Mobile No. : ");
		mobile = sc.next();
		System.out.print("\nEnter Address : ");
		add = sc.next();
		System.out.print("\nEnter Username : ");
		un = sc.next();
		System.out.print("\nEnter Password : ");
		pass = sc.next();
		System.out.print("\nEnter Initial Deposit : ");
		bal = sc.nextInt();
		
		CustomerController.register(new Customer(name, add, un, pass, mobile, bal));
		
	}

}
