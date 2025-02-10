import models.Person;
import utils.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "D:/Projects/TestJavaProject.csv";
        String outputFilePath = "D:/Projects/output.csv";

        List<Person> people = CsvReader.read(filePath, fields ->
                PersonParser.parse(fields).orElse(null)
        );

        Map<String, List<Person>> personMap = PersonGrouping.groupByDepartment(people);
        CsvWriter.write(outputFilePath, personMap, ",", "name", true);
        ConsoleWriter.printGroupedByDepartment(personMap, "name", false);
    }
}