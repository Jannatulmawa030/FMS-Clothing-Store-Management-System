package model;

public class Admin extends Employee {
    public Admin(String id, String name, double baseSalary) { super(id, name, baseSalary); }

    @Override
    public double calculateBonus(double performanceValue) {
        return getBaseSalary() * 0.15;
    }
}

