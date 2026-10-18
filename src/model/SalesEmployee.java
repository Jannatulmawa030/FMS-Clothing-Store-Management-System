package model;

public class SalesEmployee extends Employee {
    public SalesEmployee(String id, String name, double baseSalary) { super(id, name, baseSalary); }

    @Override
    public double calculateBonus(double totalSales) {
        return totalSales * 0.05;
    }
}

