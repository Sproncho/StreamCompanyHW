package telran.employee.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyMapImpl implements Company {
	Map<Integer, Employee> employees;
	int capacity;

	public CompanyMapImpl(int capacity) {
		employees = new HashMap<Integer, Employee>();
		this.capacity = capacity;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		if (employee == null || employees.size() == capacity) {
			return false;
		}
		return employees.putIfAbsent(employee.getId(), employee) == null;
	}

	// O(1)
	@Override
	public Employee removeEmployee(int id) {
		return employees.remove(id);
	}

	// O(1)
	@Override
	public Employee findEmployee(int id) {
		return employees.get(id);
	}

	// O(n)
	@Override
	public double totalSalary() {
		Collection<Employee> collection = employees.values();

		double res = collection.stream()
				.map(employee -> employee.calcSalary())
				.reduce(0.0d,(acc,salary) -> acc +salary);
		return res;
	}

	@Override
	public double totalSales() {
		Collection<Employee> collection = employees.values();
		double res = 0;
		for (Employee employee : collection) {
			if (employee instanceof SalesManager) {
				res += ((SalesManager) employee).getSalesValue();
			}
		}
		res =  collection.stream()
				.filter(emp -> emp instanceof SalesManager)
				.map(emp -> ((SalesManager) emp).getSalesValue())
				.reduce(0.0d,(acc,sales) -> acc + sales);
		return res;
	}

	@Override
	public void printEmployees() {
		Collection<Employee> collection = employees.values();
		collection.forEach(System.out::println);
	}

	@Override
	public int getSize() {
		return employees.size();
	}

	@Override
	public Employee[] findEmployeesHoursGreaterThan(int hours) {
//		return findEmployeesByPredicate(e -> e.getHours() >= hours);
		return (Employee[]) employees.values().stream()
				.filter(e -> e.getHours() >= hours)
				.toArray(Employee[]::new);
	}

	@Override
	public Employee[] findEmployeesSalaryBetween(double min, double max) {
//		Predicate<Employee> predicate = new Predicate<>() {
//
//			@Override
//			public boolean test(Employee t) {
//				return t.calcSalary() >= min && t.calcSalary() < max;
//			}
//		};
//		return findEmployeesByPredicate(predicate);
		return  employees.values().stream()
				.filter(e -> e.calcSalary() >= min && e.calcSalary() < max)
				.toArray(Employee[]::new);
	}

	private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
		ArrayList<Employee> res = new ArrayList<Employee>();
		for (Employee employee : employees.values()) {
			if (predicate.test(employee)) {
				res.add(employee);

			}
		}
		return res.toArray(new Employee[res.size()]);
	}

}
