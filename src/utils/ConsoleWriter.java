package utils;

import models.Employee;
import models.Manager;
import models.Person;

import java.math.BigDecimal;
import java.util.List;

import java.util.Map;

public class ConsoleWriter {

    public static void printGroupedByDepartment(Map<String, List<Person>> departmentMap, String sortBy, boolean ascending) {
        for (Map.Entry<String, List<Person>> entry : departmentMap.entrySet()) {
            String department = entry.getKey();
            List<Person> persons = entry.getValue();

            // Сортируем сотрудников внутри департамента
            PersonSorter.sortEmployees(persons, sortBy, ascending);

            System.out.println(department);
            for (Person person : persons) {
                String salaryStr = person.getSalary().orElse(BigDecimal.ZERO).toString();
                if (person instanceof Manager) {
                    System.out.printf("Manager,%d, %s, %s%n", person.getId(), person.getName(), salaryStr);
                } else if (person instanceof Employee) {
                    System.out.printf("Employee,%d, %s, %s%n", person.getId(), person.getName(), salaryStr);
                }
            }

            // Вывод статистики
            System.out.println(PersonGrouping.calculateStatistics(persons, ", "));
            System.out.println();
        }

        // Вывод некорректных данных
        printIncorrectData();
    }

    private static void printIncorrectData() {
        List<String> incorrectData = PersonParser.getIncorrectData();
        if (!incorrectData.isEmpty()) {
            System.out.println("Некорректные данные:");
            for (String line : incorrectData) {
                System.out.println(line);
            }
        }
    }
}