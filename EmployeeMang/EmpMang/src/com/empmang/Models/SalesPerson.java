package com.empmang.Models;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.empmang.EmployeeRunner;
import com.empmang.DB.DataBase;
import com.empmang.validator.Validator;

public class SalesPerson extends Employee {

	static Scanner sc = new Scanner(System.in);

	// Should have a static field to track weekly sales target
	static int weeklySalesTarget = 50;

	// Should have a multi dimensional array that tracks daily sales for each week
	private int salesArr[][] = new int[4][7];

	// Should have an array to store the sales target achievement for each week
	private List<Integer> salesAchievementPerWeek = new ArrayList<Integer>();

	public SalesPerson() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SalesPerson(String employeeId, String employeeName, Roles employeeRole, String employeeEmail,
			String employeePhone, String employeeAddress) {
		super(employeeId, employeeName, employeeRole, employeeEmail, employeePhone, employeeAddress);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Should have a method that calculates the total weekly sales and determine
	 * whether the salesperson has achieved sales target for each week
	 * 
	 */

	public void salesAnalysis() {
		System.out.println("SALES ANALYSIS:");
		for (int i = 0; i < salesArr.length; i++) {
			int weeklySalesSum = 0;
			StringBuilder sb = new StringBuilder("");
			sb.append("Week" + (i + 1) + " Sales Data { ");
			for (int j = 0; j < salesArr[i].length; j++) {
				weeklySalesSum += salesArr[i][j];
				if(j!=salesArr[i].length-1)
				   sb.append(salesArr[i][j]).append(" , ");
				else
				   sb.append(salesArr[i][j]);
			}
			sb.append(" } ");
			if (weeklySalesSum >= weeklySalesTarget) {
				sb.append(" ACHIEVED ");
			} else {
				sb.append("NOT ACHIEVED ");
			}

			System.out.println(sb);

		}
	}

	public int[][] getSalesArr() {
		return salesArr;
	}

	public void setSalesArr(int[][] salesArr) {
		this.salesArr = salesArr;
	}

	public List<Integer> getSalesAchievementPerWeek() {
		return salesAchievementPerWeek;
	}

	public void setSalesAchievementPerWeek(List<Integer> salesAchievementPerWeek) {
		this.salesAchievementPerWeek = salesAchievementPerWeek;
	}

	public static void salesPersonsStatusById() {
		System.out.println("Enter SalesPerson Id:");
		String salesPersonId = sc.nextLine().strip();
		if (salesPersonId.substring(0, 2).equals("SP")) {
			DataBase.salespersonDB.stream().filter(x -> x.getEmployeeId().equals(salesPersonId)).forEach(y -> {
				System.out.println(y.getEmployeeId() + " - " + y.getEmployeeName() + " - " + y.getEmployeeEmail());
				System.out.println("-----------------------------------------------------------------");
				System.out.println("|                                                                |");
				y.salesAnalysis();
				System.out.println("|                                                                |");
				System.out.println("-----------------------------------------------------------------");
			});
			EmployeeRunner.main(null);
		} else {
			System.out.println("Invalid Sales Id");
			EmployeeRunner.main(null);
		}
	}

	public static void getSalesDataFromSalesPerson(SalesPerson sp) {
		System.out.println("Enter the sales Details for a Month");
		int salesData[][] = new int[4][7];
		List<Integer> weeklySalesData = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			int weeklySalesTotal = 0;
			System.out.println("Week " + (i + 1) + " Sales Data:");
			for (int j = 0; j < 7; j++) {

				while (true) {
					System.out.println("Week " + (i + 1) + " Day " + (j + 1) + " Sales Data:");
					int flag = 0;
					String oneDaySalesData = sc.nextLine().strip();
					if (Validator.isNumeric(oneDaySalesData)) {
						salesData[i][j] = Integer.parseInt(oneDaySalesData);
						weeklySalesTotal += salesData[i][j];
						flag += 1;
					} else {
						System.out.println("Enter only numbers");
					}
					if (flag > 0)
						break;

				}

			}
			weeklySalesData.add(weeklySalesTotal);
			sp.setSalesAchievementPerWeek(weeklySalesData);
		}

		sp.setSalesArr(salesData);
		System.out.println("Sales Data is uploaded successfully for { " + sp.getEmployeeName() + " }");
		// sc.nextLine();
	}

	public static void createSalesPerson() {
		System.out.println("WELCOME TO CREATE SALES PERSON DASHBOARD:");
		System.out.println("Enter No Of SalesPersons You want to add: ");
		String noOfSalesPersons = sc.nextLine().strip();
		if (!Validator.isNumeric(noOfSalesPersons)) {
			System.out.println("Enter Only Numbers!");
			createSalesPerson();
		} else {
			for (int i = 0; i < Integer.parseInt(noOfSalesPersons); i++) {
				StringBuilder salesPersonId = new StringBuilder("SP");
				if (DataBase.salespersonDB.size() == 0)
					salesPersonId.append(1);
				else
					salesPersonId.append(DataBase.salespersonDB.size() + 1);
				System.out.println("Enter SalesPerson " + (i + 1) + " Name:");
				String salesPersonName = sc.nextLine().strip();
				Roles salesPersonRole = Roles.SALESPERSON;
				System.out.println("Enter SalesPerson " + (i + 1) + " Email:");
				String salesPersonEmail = sc.nextLine().strip();
				System.out.println("Enter SalesPerson " + (i + 1) + " Phone:");
				String salesPersonPhone = sc.nextLine().strip();
				System.out.println("Enter SalesPerson " + (i + 1) + "Address:");
				String salesPersonAddress = sc.nextLine().strip();

				Employee employee = new SalesPerson(salesPersonId.toString(), salesPersonName, salesPersonRole,
						salesPersonEmail, salesPersonPhone, salesPersonAddress);
				if (DataBase.salespersonDB.add((SalesPerson) employee)) {
					System.out.println("SalesPerson is Added Successfully with ID : " + salesPersonId + " Note It ");
					getSalesDataFromSalesPerson((SalesPerson) employee);
				} else {
					System.out.println("SalesPerson is Not  Added Successfully Internal Server Error!");
					EmployeeRunner.main(null);
				}

			}
			EmployeeRunner.main(null);
		}

	}

}
