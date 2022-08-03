package telran.employee.model;

public class SalesManager extends Employee {
	private double salesValue;
	private double percentage;

	public SalesManager(int id, String firstName, String lastName, double hours, double salesValue, double percentage) {
		super(id, firstName, lastName, hours);
		this.salesValue = salesValue;
		this.percentage = percentage;
	}

	public double getSalesValue() {
		return salesValue;
	}

	public void setSalesValue(double salesValue) {
		this.salesValue = salesValue;
	}

	public double getPercentage() {
		return percentage;
	}
	
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	@Override
	public double calcSalary() {
		double salary = salesValue * percentage;
		if (salary < minWage * hours) {
			salary = minWage * hours;
		}
		return salary;
	}
	
}
