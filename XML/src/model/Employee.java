package model;

public class Employee {

	private String Firstname;
	private String Lastname;
	private double salary;

	public Employee(String Firstname, String Lastname, double salary) {
		this.Firstname = Firstname;
		this.Lastname = Lastname;
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "[" + Firstname + ", " + Lastname + ", " + salary + "]";
	}

}
