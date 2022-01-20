package com.empmang.DB;

import java.util.ArrayList;
import java.util.List;

import com.empmang.EmployeeRunner;
import com.empmang.Models.Manager;
import com.empmang.Models.SalesPerson;

public class DataBase {
	public static List<SalesPerson> salespersonDB = new ArrayList<SalesPerson>();
	public static List<Manager> managerDB = new ArrayList<Manager>();

	public static void displayDB() {

		System.out.println("MANAGER DETAILS ALONG WITH THERE ASSIGNED SALES PERSONS:");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		if (DataBase.managerDB.size() == 0) {
			System.out.println("-------------------NO DATA AVAILABLE---------------");
		} else {
			System.out.println("Manager Id" + " | " + "Manager Name" + " | " + "Manager Email");
			for (Manager m : managerDB) {
				System.out.println(m.getEmployeeId() + " | " + m.getEmployeeName() + " | " + m.getEmployeeEmail());
				System.out.println("SalesPersons Under Manager { " + m.getEmployeeName() + " }");
				System.out.println("----------------------------------------------------");
				System.out.println("SalesPerson Id" + " | " + "SalesPerson Name" + " | " + "SalesPerson Email");
				for (String x : m.getEmployeesIdUnderManager()) {
					salespersonDB.stream().filter(z -> z.getEmployeeId().equals(x)).forEach(sp -> {
						System.out.println(
								sp.getEmployeeId() + " - " + sp.getEmployeeName() + " - " + sp.getEmployeeEmail());
						System.out.println("-----------------------------------------------------------------");
						System.out.println("|                                                                |");
						sp.salesAnalysis();
						System.out.println("|                                                                |");
						System.out.println("-----------------------------------------------------------------");

					});

				}
			}

		}

		System.out.println("SALESPERSONS ALONG WITHOUT THERE MANAGERS:");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("SalesPerson Id" + " | " + "SalesPerson Name" + " | " + "SalesPerson Email");
		if (DataBase.salespersonDB.size() == 0) {
			System.out.println("-------------------NO DATA AVAILABLE---------------");
		} else {

			for (SalesPerson sp : DataBase.salespersonDB) {
				if (DataBase.managerDB.size() == 0) {
					System.out.println(
							sp.getEmployeeId() + " - " + sp.getEmployeeName() + " - " + sp.getEmployeeEmail());
					System.out.println("-----------------------------------------------------------------");
					System.out.println("|                                                                |");
					sp.salesAnalysis();
					System.out.println("|                                                                |");
					System.out.println("-----------------------------------------------------------------");
				} else {
					if (!Manager.isSalesPersonAssignedUnderManager(sp.getEmployeeId())) {
						System.out.println(
								sp.getEmployeeId() + " - " + sp.getEmployeeName() + " - " + sp.getEmployeeEmail());
						System.out.println("-----------------------------------------------------------------");
						System.out.println("|                                                                |");
						sp.salesAnalysis();
						System.out.println("|                                                                |");
						System.out.println("-----------------------------------------------------------------");
					}

				}
			}

		}

		EmployeeRunner.main(null);
	}

}
