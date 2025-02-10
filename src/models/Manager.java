package models;

public class Manager extends Person {
    private final String department;

    public Manager(int id, String name, String salary, String department) {
        super(id, name, salary);
        if (department == null || department.isBlank()) throw new IllegalArgumentException("Department is required");
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
}