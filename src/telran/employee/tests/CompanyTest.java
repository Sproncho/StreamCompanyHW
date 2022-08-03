package telran.employee.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employee.dao.Company;
import telran.employee.dao.CompanyMapImpl;
import telran.employee.model.Employee;
import telran.employee.model.Manager;
import telran.employee.model.SalesManager;
import telran.employee.model.WageEmployee;

class CompanyTest {
	Company company;
	Employee[] firm;

	@BeforeEach
	void setUp() throws Exception {
		company = new CompanyMapImpl(5);
		firm = new Employee[4];
		firm[0] = new Manager(1000, "John", "Smith", 182, 20_000, 20);
		firm[1] = new WageEmployee(2000, "Mary", "Smith", 182, 40);
		firm[2] = new SalesManager(3000, "Peter", "Jackson", 182, 40_000, 0.1);
		firm[3] = new SalesManager(4000, "Tigran", "Petrosyan", 91, 80_000, 0.1);
		for (int i = 0; i < firm.length; i++) {
			company.addEmployee(firm[i]);
		}

	}

	@Test
	void testAddEmployee() {
		assertFalse(company.addEmployee(firm[3]));
		Employee employee = new SalesManager(5000, "Dan", "Shmidt", 182, 24_000, 0.3);
		assertTrue(company.addEmployee(employee));
		assertEquals(5, company.getSize());
		employee = new SalesManager(6000, "Peter", "Jackson", 182, 40_000, 0.1);
		assertFalse(company.addEmployee(employee));
	}

	@Test
	void testRemoveEmployee() {
		assertNull(company.removeEmployee(5000)); // check for null
		assertEquals(null, company.removeEmployee(5000));
		assertEquals(firm[1], company.removeEmployee(2000));
		assertEquals(3, company.getSize());
		assertEquals(firm[2], company.removeEmployee(3000));
		assertEquals(2, company.getSize());
	}

	@Test
	void testFindEmployee() {
		Employee employee = company.findEmployee(1000);
		assertEquals(employee, company.findEmployee(1000));
		employee = company.findEmployee(2000);
		assertEquals(employee, company.findEmployee(2000));
		employee = company.findEmployee(5000);
		assertEquals(null, company.findEmployee(5000));
	}

	@Test
	void testTotalSalary() {
		assertEquals(44380.0, company.totalSalary(), 0.01); // 0.01 - is accuracy
		Employee employee = new SalesManager(5000, "Dan", "Shmidt", 182, 24_000, 0.3);
		assertTrue(company.addEmployee(employee));
		assertEquals(5, company.getSize());
		assertEquals(51580.0, company.totalSalary());
	}

	@Test
	void testAverageSalary() {
		assertEquals(11095.0, company.averageSalary(), 0.01); // 0.01 - is accuracy
	}

	@Test
	void testTotalSales() {
		assertEquals(120000.0, company.totalSales(), 0.01); // 0.01 - is accuracy
	}

	@Test
	void testGetSize() {
		assertEquals(4, company.getSize());
		Employee employee = new SalesManager(5000, "Dan", "Shmidt", 182, 24_000, 0.3);
		company.addEmployee(employee);
		assertEquals(5, company.getSize());
	}

	@Test
	void testPrintEmployees() {
		company.printEmployees();
	}

	@Test
	//Отсортировать actual, чтобы проходил тест
	void findEmployeesHoursGreaterThan() {
		Employee[] actual = company.findEmployeesHoursGreaterThan(100);
		Arrays.sort(actual, (e1, e2) -> Integer.compare(e1.getId(), e2.getId()));
		Employee[] expected = { firm[0], firm[1], firm[2] };
		assertArrayEquals(expected, actual);
	}

	@Test
	void findEmployeesSalaryBetween() {
		Employee[] actual = company.findEmployeesSalaryBetween(10000, 25000);
		Arrays.sort(actual, (e1, e2) -> Integer.compare(e1.getId(), e2.getId()));
		Employee[] expected = { firm[0] };
		assertArrayEquals(expected, actual);
	}

}
