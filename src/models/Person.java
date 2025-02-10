package models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public abstract class Person {
    private final int id;
    private final String name;
    private final Optional<BigDecimal> salary;

    public Person(int id, String name, String salary) {
        if (id <= 0) throw new IllegalArgumentException("ID should be positive");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");

        this.id = id;
        this.name = name;
        this.salary = parseSalary(salary);
    }

    private Optional<BigDecimal> parseSalary(String salary) {
        try {
            BigDecimal parsedSalary = new BigDecimal(salary);
            return Optional.of(parsedSalary.setScale(2, RoundingMode.CEILING));
        } catch (Exception e) {
            System.err.println("Error salary parsing: " + salary);
            return Optional.empty();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<BigDecimal> getSalary() {
        return salary;
    }
}