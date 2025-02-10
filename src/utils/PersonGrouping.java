package utils;

import models.Employee;
import models.Manager;
import models.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonGrouping {

    public static Map<String, List<Person>> groupByDepartment(List<Person> people) {
        Map<Integer, String> managerDepartmentMap = new HashMap<>();
        for (Person person : people) {
            if (person instanceof Manager) {
                Manager manager = (Manager) person;
                managerDepartmentMap.put(manager.getId(), manager.getDepartment());
            }
        }

        Map<String, List<Person>> departmentMap = new HashMap<>();
        for (Person person : people) {
            String department = null;
            if (person instanceof Manager) {
                Manager manager = (Manager) person;
                department = manager.getDepartment();
            } else if (person instanceof Employee) {
                Employee employee = (Employee) person;

                department = managerDepartmentMap.get(employee.getManagerId());
                if (department == null) {
                    String errorData = employee.getId() + "," + employee.getName() + "," +
                            employee.getSalary().orElse(BigDecimal.ZERO) + "," + employee.getManagerId();

                    System.err.println("Error: Department doesn't exist -> " + errorData);
                    PersonParser.addIncorrectData(errorData);
                    continue;
                }
            }

            departmentMap.computeIfAbsent(department, k -> new ArrayList<>()).add(person);
        }

        return departmentMap;
    }

    public static String calculateStatistics(List<Person> persons, String separator) {
        int personCount = 0;
        BigDecimal totalSalary = BigDecimal.ZERO;

        for (Person person : persons) {
            if (person.getSalary().isPresent()) {
                totalSalary = totalSalary.add(person.getSalary().get());
                personCount++;
            }
        }

        BigDecimal averageSalary = personCount > 0
                ? totalSalary.divide(BigDecimal.valueOf(personCount), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return personCount + separator + averageSalary;
    }
}
