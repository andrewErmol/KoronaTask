import models.Person;
import utils.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String sortField = null;
        String sortOrder = null;
        boolean outputToConsole = true;
        boolean ascendingSortOrder = true;
        String outputFilePath = null;
        String inputFilePath = "D:/Projects/TestJavaProject.csv";

        if (args.length == 0) {
            System.err.println("Error: No parameters provided. Please specify parameters."); // Завершаем программу
        }

        for (String arg : args) {
            if (arg.startsWith("--sort=")) {
                sortField = arg.substring("--sort=".length());
            } else if (arg.startsWith("--s=")) {
                sortField = arg.substring("--s=".length());
            } else if (arg.startsWith("--order=")) {
                sortOrder = arg.substring("--order=".length());
                if (sortOrder.equals("asc")) {
                    ascendingSortOrder = true;
                } else if (sortOrder.equals("desc")) {
                    ascendingSortOrder = false;
                } else {
                    System.err.println("Error: Invalid sort order: " + sortOrder +
                            ". Allowed values are 'asc' or 'desc'.");
                    ascendingSortOrder = true;
                }
            } else if (arg.startsWith("--o=")) {
                sortOrder = arg.substring("--o=".length());
                if (sortOrder.equals("asc")) {
                    ascendingSortOrder = true;
                } else if (sortOrder.equals("desc")) {
                    ascendingSortOrder = false;
                } else {
                    System.err.println("Error: Invalid sort order: " + sortOrder +
                            ". Allowed values are 'asc' or 'desc'.");
                    ascendingSortOrder = true;
                }
            } else if (arg.startsWith("--output=")) {
                String outputType = arg.substring("--output=".length());
                if (outputType.equals("file")) {
                    outputToConsole = false;
                } else if (outputType.equals("console")) {
                    outputToConsole = true;
                } else {
                    System.err.println("Error: Invalid output type. Use 'console' or 'file'.");
                    outputToConsole = true;
                }
            } else if (arg.startsWith("--out=")) {
                    String outputType = arg.substring("--out=".length());
                    if (outputType.equals("file")) {
                        outputToConsole = false;
                    } else if (outputType.equals("console")) {
                        outputToConsole = true;
                    } else {
                        System.err.println("Error: Invalid output type. Use 'console' or 'file'.");
                        outputToConsole = true;
                    }
            } else if (arg.startsWith("--path=")) {
                outputFilePath = arg.substring("--path=".length());
            }
        }

        if (!outputToConsole && outputFilePath == null){
            System.err.println("Error: Reading from file specified without path.");
        }

        if (outputToConsole && outputFilePath != null){
            System.err.println("Error: Path specified without required output format.");
        }

        if (sortField == null && sortOrder != null) {
            System.err.println("Error: Order parameter specified without sort field.");
        }

        List<Person> people = FileReader.read(inputFilePath, fields ->
                PersonParser.parse(fields).orElse(null)
        );

        Map<String, List<Person>> personMap = PersonGrouping.groupByDepartment(people);

        if(outputToConsole)
            ConsoleWriter.printGroupedByDepartment(personMap, sortField, ascendingSortOrder);
        else
            FileWriter.write(outputFilePath, personMap, ",", sortField, ascendingSortOrder);
    }
}