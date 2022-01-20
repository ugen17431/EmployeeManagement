package com.empmang;

import java.util.Scanner;

import com.empmang.DB.DataBase;
import com.empmang.Models.Manager;
import com.empmang.Models.SalesPerson;
import com.empmang.validator.Validator;

public class EmployeeRunner {

	static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {
		System.out.println("EMPLOYEE MANAGEMENT DASHBOARD\n" + "-----(Menu)----\n" + "Enter 1 to create SalesPerson\n"
				+ "Enter 2 to create Manager\n" + "Enter 3 to Display SalesPerson Sales Status By Id\n"
				+ "Enter 4 to Display Manager & SalesPerson Details\n" + "Enter 5 to Logout");
		String menuoption = sc.nextLine().strip();
		if (!Validator.isNumeric(menuoption)) {
			System.out.println("Enter Only Number!");
			main(args);
		} else {
			switch (menuoption) {
			case "1":
				SalesPerson.createSalesPerson();
				break;
			case "2":
				Manager.createManager();
				break;
			case "3":
				SalesPerson.salesPersonsStatusById();
				break;
			case "4":
				DataBase.displayDB();
				break;
			case "5":
				logout();
				break;
			default:
				System.out.println("Your Option is not available!!");
				main(args);

			}
		}
	}

	public static void logout() {
		System.out.println("User is Signed Out!!");
		System.exit(0);
	}

}
