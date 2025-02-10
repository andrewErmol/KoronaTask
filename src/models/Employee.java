package models;

public class Employee extends Person {

    private final int managerId;

    public Employee(int id, String name, String salary, int managerId) {
        super(id, name, salary);
        if (managerId <= 0) throw new IllegalArgumentException("Manager ID should be positive");
        this.managerId = managerId;
    }

    public int getManagerId() {
        return managerId;
    }
}