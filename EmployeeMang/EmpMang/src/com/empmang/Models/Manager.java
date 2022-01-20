package com.empmang.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.empmang.EmployeeRunner;
import com.empmang.DB.DataBase;
import com.empmang.validator.Validator;

public class Manager extends Employee {

	/*
	 * Should have an array field that keeps track of sales persons employee id,
	 * working under this manager
	 */

	private List<String> employeesIdUnderManager = new ArrayList<String>();
	static Scanner sc = new Scanner(System.in);

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager(String employeeId, String employeeName, Roles employeeRole, String employeeEmail,
			String employeePhone, String employeeAddress) {
		super(employeeId, employeeName, employeeRole, employeeEmail, employeePhone, employeeAddress);
		// TODO Auto-generated constructor stub
	}

	public List<String> getEmployeesIdUnderManager() {
		return employeesIdUnderManager;
	}

	public void setEmployeesIdUnderManager(List<String> employeesIdUnderManager) {
		this.employeesIdUnderManager = employeesIdUnderManager;
	}

	public static void addSalesPersonUnderManager(Manager employee) {
		System.out.println(" Do You Want to Add any SalesPersons Below  manager " + employee.getEmployeeName() + " ?"
				+ "\n If Yes Enter Y or y \n " + "else Enter N or n");
		String addSalesPersonOption = sc.nextLine().strip();
		if (!addSalesPersonOption.toLowerCase().equals("y") && !addSalesPersonOption.toLowerCase().equals("n")) {
			System.out.println("Your choose is invalid !");
			addSalesPersonUnderManager(employee);
		} 
		else if (addSalesPersonOption.toLowerCase().equals("y")) {
			System.out.println(
					"How many salespersons u want to add under this manager" + employee.getEmployeeName() + " ?");
			String noOfSalesPersonsWantoAddUnderManager = sc.nextLine().strip();
			if (!Validator.isNumeric(noOfSalesPersonsWantoAddUnderManager)) {
				System.out.println("Enter Only Numbers!");
				addSalesPersonUnderManager(employee);

			}
			else
			{
				List<String> salesPerUnderMangArr = new ArrayList<String>();

				for (int i = 0; i < Integer.parseInt(noOfSalesPersonsWantoAddUnderManager); i++) {
					System.out.println("Enter the SalesPerson Id whom you want to add ?");
					String salesPersonId = sc.nextLine().strip();
					boolean f = false;
					if (salesPersonId.length() > 2) {
						if (salesPersonId.substring(0, 2).equals("SP")) {
							f = true;
						}
					}
					if (f) {

						int salesPersonUnderMangerArrsizeBeforeAdding = salesPerUnderMangArr.size();
						DataBase.salespersonDB.stream().filter(x -> x.getEmployeeId().equals(salesPersonId))
								.forEach(y -> salesPerUnderMangArr.add(salesPersonId));
						;
						if (salesPersonUnderMangerArrsizeBeforeAdding == salesPerUnderMangArr.size()) {
							System.out.println("SalesPerson ( " + salesPersonId + " )doesn\'t Exists 404 Error");
						} else {
							System.out.println("SalesPerson ( " + salesPersonId + " ) Successfully Added Under "
									+ employee.getEmployeeName());
							employee.setEmployeesIdUnderManager(salesPerUnderMangArr);
						}
					} else {
						System.out.println("SalesPerson Id is not valid!");
						addSalesPersonUnderManager(employee);

					}
				}
				return;
			}
	

		} else if (addSalesPersonOption.toLowerCase().equals("n")) {
			System.out.println("None Sales Persons will be added under this manager " + employee.getEmployeeName());
			return;

		}
		EmployeeRunner.main(null);
	}

	public static void createManager() {
		System.out.println("WELCOME TO CREATE MANAGER DASHBOARD:");
		System.out.println("Enter No Of Managers You want to add: ");
		String noOfManagers = sc.nextLine();
		if (!Validator.isNumeric(noOfManagers.strip())) {
			System.out.println("Enter Only Numbers!");
			createManager();
		} else {
			for (int i = 0; i < Integer.parseInt(noOfManagers); i++) {
				StringBuilder managerId = new StringBuilder("M");
				if (DataBase.managerDB.size() == 0)
					managerId.append(1);
				else
					managerId.append(DataBase.managerDB.size() + 1);
				System.out.println("Enter Manager " + (i + 1) + "Name:");
				String managerName = sc.nextLine().strip();
				Roles managerRole = Roles.MANAGER;
				System.out.println("Enter Manager " + (i + 1) + "Email Id:");
				String managerEmail = sc.nextLine().strip();
				System.out.println("Enter Manager " + (i + 1) + "Phone:");
				String managerPhone = sc.nextLine().strip();
				System.out.println("Enter Manager " + (i + 1) + " Address:");
				String managerAddress = sc.nextLine().strip();

				Employee employee = new Manager(managerId.toString(), managerName, managerRole, managerEmail,
						managerPhone, managerAddress);
				if (DataBase.managerDB.add((Manager) employee)) {
					System.out.println("Manager is Added Successfully with ID : " + managerId + " Note It ");
				} else {
					System.out.println("Manager is Not  Added Successfully Internal Server Error!");
					EmployeeRunner.main(null);
				}

				addSalesPersonUnderManager((Manager) employee);

			}
			EmployeeRunner.main(null);
		}

	}
	
	public static boolean isSalesPersonAssignedUnderManager(String salesPersonId)
	{
		for(Manager m:DataBase.managerDB)
		{
			for(String id:m.getEmployeesIdUnderManager())
			{
				if(salesPersonId.equals(id))
				{
					return true;
				}
			}
		}
		return false;
		
	}

}
