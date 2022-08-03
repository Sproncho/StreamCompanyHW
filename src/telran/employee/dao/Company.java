package telran.employee.dao;

import telran.employee.model.Employee;

public interface Company {

	boolean addEmployee(Employee employee);

	Employee removeEmployee(int id);

	Employee findEmployee(int id);

	double totalSalary();

	default double averageSalary() {
		return totalSalary() / getSize();
	}

	double totalSales();

	void printEmployees();

	int getSize();
	
	Employee[] findEmployeesHoursGreaterThan(int hours);
	
	Employee[] findEmployeesSalaryBetween(double min, double max);
}
