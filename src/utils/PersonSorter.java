package utils;

import models.Manager;
import models.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PersonSorter {

    // Сортируем только сотрудников внутри департамента
    public static void sortEmployees(List<Person> people, String sortBy, boolean ascending) {
        Comparator<Person> comparator = getComparator(sortBy);

        if (!ascending) {
            comparator = comparator.reversed();
        }

        // Разделяем менеджера и сотрудников
        List<Person> employees = new ArrayList<>();
        Person manager = null;

        for (Person person : people) {
            if (person instanceof Manager) {
                manager = person;  // Запоминаем менеджера
            } else {
                employees.add(person);
            }
        }

        employees.sort(comparator);

        // Очищаем список, добавляем менеджера первым, затем отсортированных сотрудников
        people.clear();
        if (manager != null) {
            people.add(manager);
        }
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