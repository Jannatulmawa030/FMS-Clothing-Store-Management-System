package model;

/** Customer data is kept encapsulated and saved in customers.txt. */
public class Customer {
    private String id;
    private String name;
    private String phone;
    private int loyaltyPoints;

    public Customer(String id, String name, String phone, int loyaltyPoints) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
    public void addLoyaltyPoints(int points) { loyaltyPoints += Math.max(points, 0); }

    public String toFileString() {
        return id + "|" + name + "|" + phone + "|" + loyaltyPoints;
    }

    @Override
    public String toString() { return id + " - " + name; }
}

