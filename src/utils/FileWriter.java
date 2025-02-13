package utils;

import models.Employee;
import models.Manager;
import models.Person;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FileWriter {

    public static void write(String filePath, Map<String, List<Person>> departmentMap, String separator, String sortBy, boolean ascending) {
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(filePath))) {

            for (Map.Entry<String, List<Person>> entry : departmentMap.entrySet()) {
                String department = entry.getKey();
                List<Person> persons = entry.getValue();

                // Сортируем сотрудников внутри департамента
                EmployeeSorter.sortEmployees(persons, sortBy, ascending);

                bw.write(department);
                bw.newLine();

                for (Person person : persons) {
                    String salaryStr = person.getSalary().orElse(BigDecimal.ZERO).toString();
                    if (person instanceof Manager) {
                        bw.write(String.join(separator, "Manager",
                                String.valueOf(person.getId()), person.getName(), salaryStr));
                    } else if (person instanceof Employee) {
                        Employee employee = (Employee) person;
                        bw.write(String.join(separator, "Employee",
                                String.valueOf(employee.getId()), employee.getName(), salaryStr));
                    }
                    bw.newLine();
                }

                // Запись статистики
                bw.write(PersonGrouping.calculateStatistics(persons, separator));
                bw.newLine();
                bw.newLine();
            }

            // Запись некорректных данных
            writeIncorrectData(bw, separator);

        } catch (IOException e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    private static void writeIncorrectData(BufferedWriter bw, String separator) throws IOException {
        List<String> incorrectData = PersonParser.getIncorrectData();
        if (!incorrectData.isEmpty()) {
            bw.write("Incorrect data:");
            bw.newLine();
            for (String line : incorrectData) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}