package utils;

import models.Manager;
import models.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeSorter {

    public static void sortEmployees(List<Person> people, String sortBy, boolean ascending) {
        // Разделяем менеджеров и сотрудников
        List<Person> employees = new ArrayList<>();
        List<Person> managers = new ArrayList<>();

        for (Person person : people) {
            if (person instanceof Manager) {
                managers.add(person);  // Добавляем всех менеджеров
            } else {
                employees.add(person);
            }
        }

        // Если параметр сортировки не указан — оставляем порядок сотрудников неизменным
        if (sortBy == null || (!sortBy.equalsIgnoreCase("name") && !sortBy.equalsIgnoreCase("salary"))) {
            if (sortBy != null) {
                System.err.println("Error: Invalid sort field: " + sortBy +
                        ". Allowed values are 'name' or 'salary'. Sorting will not be applied.");
            }
            people.clear();
            people.addAll(managers);
            people.addAll(employees);
            return;
        }

        // Получаем компаратор для сортировки
        Comparator<Person> comparator = getComparator(sortBy);
        if (!ascending) {
            comparator = comparator.reversed();
        }

        // Сортируем только сотрудников
        employees.sort(comparator);

        // Объединяем менеджеров и отсортированных сотрудников
        people.clear();
        people.addAll(managers);
        people.addAll(employees);
    }

    // Вспомогательный метод для получения компаратора
    private static Comparator<Person> getComparator(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "name":
                return Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER);
            case "salary":
                return Comparator.comparing(p -> p.getSalary().orElse(BigDecimal.ZERO));
            default:
                throw new IllegalArgumentException("Unsupported sort field: " + sortBy);
        }
    }
}