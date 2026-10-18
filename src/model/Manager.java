package model;

public class Manager extends Employee {
    public Manager(String id, String name, double baseSalary) { super(id, name, baseSalary); }

    @Override
    public double calculateBonus(double totalTeamSales) {
        return totalTeamSales * 0.10;
    }
}
