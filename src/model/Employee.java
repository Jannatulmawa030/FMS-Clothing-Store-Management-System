package model;

/** Abstract superclass used to demonstrate overriding with mathematical calculation. */
public abstract class Employee {
    private String id;
    private String name;
    private double baseSalary;

    protected Employee(String id, String name, double baseSalary) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBaseSalary() { return baseSalary; }

    public abstract double calculateBonus(double performanceValue);

    public double calculateTotalSalary(double performanceValue) {
        return baseSalary + calculateBonus(performanceValue);
    }
}

