package utils;

import models.Employee;
import models.Manager;
import models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonParser {

    private static final List<String> incorrectData = new ArrayList<>();

    public static Optional<Person> parse(String[] fields) {
        if (!isValidFormat(fields)) {
            incorrectData.add(String.join(",", fields));
            return Optional.empty();
        }

        String position = fields[0].trim();
        int id;
        String name = fields[2].trim();
        String salary = fields[3].trim();
        String departmentOrManagerId = fields[4].trim();

        try {
            id = Integer.parseInt(fields[1].trim());

            if (position.equalsIgnoreCase("Manager")) {
                return Optional.of(new Manager(id, name, salary, departmentOrManagerId));
            } else if (position.equalsIgnoreCase("Employee")) {
                int managerId = Integer.parseInt(departmentOrManagerId);
                return Optional.of(new Employee(id, name, salary, managerId));
            } else {
                incorrectData.add(String.join(",", fields));
            }
        } catch (Exception e) {
            incorrectData.add(String.join(",", fields));
        }

        return Optional.empty();
    }

    private static boolean isValidFormat(String[] fields) {
        if (fields.length < 5) return false;

        String position = fields[0].trim();
        String salary = fields[3].trim();

        try {
            double salaryValue = Double.parseDouble(salary);
            if (salaryValue <= 0) {
                System.err.println("Error: Salary should be positive -> " + String.join(",", fields));
                return false;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: incorrect salary -> " + String.join(",", fields));
            return false;
        }

        // Проверка количества полей
        if (position.equalsIgnoreCase("Manager") && fields.length != 5){
            System.err.println("Error: incorrect count of fields -> " + String.join(",", fields));
            return false;
        }
        if (position.equalsIgnoreCase("Employee") && fields.length != 5) return false;

        return true;
    }

    public static void addIncorrectData(String data) {
        incorrectData.add(data);
    }

    public static List<String> getIncorrectData() {
        return incorrectData;
    }
}